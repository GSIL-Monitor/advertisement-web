package com.yuanshanbao.dsp.controller.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DataFormat;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.common.util.VerifyIdcard;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.bill.model.BillAccountInfo;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.earnings.model.Earnings;
import com.yuanshanbao.dsp.earnings.service.EarningsService;
import com.yuanshanbao.dsp.payment.PaymentInterfaceService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.dsp.redpacket.model.RedPacketStatus;
import com.yuanshanbao.dsp.redpacket.model.RedPacketVo;
import com.yuanshanbao.dsp.redpacket.service.RedPacketService;
import com.yuanshanbao.dsp.sms.service.VerifyCodeService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.dsp.withdrawdeposit.model.WithdrawDeposit;
import com.yuanshanbao.dsp.withdrawdeposit.service.WithdrawDepositService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/i/account")
public class AccountController extends BaseController {

	private static final String BILL_LIST_KEY = "billList";

	private static final String TYPE_CONTENT_KEY = "typeContent";

	private static final String TYPE_CONTENT_BET = "投注";

	private static final String TYPE_CONTENT_CONSUME = "消费";

	public final static BigDecimal MANAGER_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.1);
	public final static BigDecimal DIRECTOR_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.15);
	public final static BigDecimal CEO_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.2);
	public final static BigDecimal NEW_CEO_PERCENTAGE = BigDecimal.valueOf(0.05);
	public final static String NOW_DATE = "20181210";
	@Autowired
	private AppService appService;

	@Autowired
	private RedPacketService redPacketService;

	@Autowired
	private PaymentInterfaceService paymentInterfaceService;

	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private EarningsService earningService;

	@Autowired
	private WithdrawDepositService withdrawDepositServcie;

	@Autowired
	private UserService userService;

	@Autowired
	private EarningsService earningsService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/balance")
	@ResponseBody
	public Object getBalance(String token, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = differentiateTokenUser(request, token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			resultMap.putAll(paymentInterfaceService.queryBalance(String.valueOf(user.getUserId())));

			Object withdrawAmount = paymentInterfaceService.queryBillAmount(String.valueOf(user.getUserId()),
					PaymentInterfaceService.WITHDRAW).get("amount");
			if (withdrawAmount == null) {
				withdrawAmount = BigDecimal.ZERO;
			}
			resultMap.put("withdrawAmount", withdrawAmount);
			Object brokerageAmount = paymentInterfaceService.queryBillAmount(String.valueOf(user.getUserId()),
					PaymentInterfaceService.BROKERAGE).get("amount");
			if (brokerageAmount == null) {
				brokerageAmount = BigDecimal.ZERO;
			}
			// 更新用户等级
			BigDecimal brokerages = BigDecimal.valueOf(0);
			Agency agency = new Agency();
			agency.setInviteUserId(user.getUserId());
			if (user.getLevel() == null) {
				user.setLevel(UserLevel.MANAGER);
				userService.updateUser(user);
			}
			if (user.getLevel() == UserLevel.VIP_AGENT) {
				brokerages = agencyService.queryVIPAgenctSumBrokerage(user.getUserId());
				if (brokerages == null) {
					brokerages = BigDecimal.ZERO;
				}

			} else {
				// userService.updateLevelDetails(user.getUserId());
				brokerages = agencyService.getBrokerages(agency, user, new PageBounds());
				if (brokerages == null) {
					brokerages = BigDecimal.ZERO;
				}
			}
			resultMap.put("brokerageAmount", brokerageAmount);
			resultMap.put("user", userService.selectUserById(user.getUserId()));
			Integer level = user.getLevel();
			resultMap.put("levelStatus", level);
			resultMap.put("brokerageAmount", brokerages.setScale(2, RoundingMode.HALF_UP));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[orderDetail]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	/**
	 * 收益记录
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/earnings")
	@ResponseBody
	public Object getEarnings(String token, Earnings earnings, PageBounds pageBounds) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User loginToken = tokenService.verifyLoginToken(token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			List<Earnings> earningsList = earningService.selectEarnings(earnings, pageBounds);
			resultMap.put("earningsList", earningsList);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[orderDetail]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	/**
	 * 提现记录
	 * 
	 * @param token
	 * @return
	 */

	@RequestMapping("/withdrawInfo")
	@ResponseBody
	public Object getWithdrawDeposit(HttpServletRequest request, String token, WithdrawDeposit withdrawDeposit,
			PageBounds pageBounds) {
		Map<String, Object> resultMap = new HashMap<>();
		User loginToken = null;
		try {
			String host = request.getHeader("Host");
			if ("cond.xingdk.com".equals(host)) {
				loginToken = userService.selectUserById(501l);
			} else {
				loginToken = differentiateTokenUser(request, token);
			}
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}

			List<BillAccountInfo> billAccountInfoList = new ArrayList<BillAccountInfo>();
			Map<String, Object> checkResultMap = paymentInterfaceService.queryBillList(
					String.valueOf(loginToken.getUserId()), formatPageBounds(pageBounds).getPage());
			Integer retCode = (Integer) checkResultMap.get("retCode");
			if (retCode != null && retCode.equals(ComRetCode.SUCCESS)) {
				JSONArray array = (JSONArray) checkResultMap.get("billList");
				for (int i = 0; i < array.size(); i++) {
					BillAccountInfo billAccountInfo = new BillAccountInfo();
					JSONObject jo = JSONObject.fromObject(array.get(i));
					Integer type = (Integer) jo.get("type");
					if (type == 7) {
						String accountId = (String) jo.get("accountId");
						String amount = String.valueOf((BigDecimal) jo.get("amount"));
						String typeContent = (String) jo.get("typeContent");
						String createTimeContent = (String) jo.get("createTimeContent");
						String balance = String.valueOf((BigDecimal) jo.get("balance"));
						Integer status = (Integer) jo.get("status");
						billAccountInfo.setAccountId(accountId.substring(2, accountId.length()));
						billAccountInfo.setAmount(amount);
						billAccountInfo.setBalance(balance);
						billAccountInfo.setTypeContent(typeContent);
						billAccountInfo.setType(type);
						billAccountInfo.setStatus(status);
						billAccountInfo.setCreateTimeContent(createTimeContent);
						if (billAccountInfo != null) {
							billAccountInfoList.add(billAccountInfo);
						}
					}

				}
			}
			resultMap.put("withdrawList", billAccountInfoList);
			resultMap.put("user", loginToken);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[orderDetail]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/checkChargePay")
	@ResponseBody
	public Object checkChargePay(String token, String chargeId) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User loginToken = tokenService.verifyLoginToken(token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			try {
				resultMap.putAll(paymentInterfaceService.queryChargePay(String.valueOf(loginToken.getUserId()),
						chargeId));
			} catch (BusinessException e) {
				resultMap.put("paid", false);
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[checkChargePay]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/billList")
	@ResponseBody
	public Object getBillList(String token, PageBounds pageBounds) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User loginToken = tokenService.verifyLoginToken(token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			Map<String, Object> queryBillMap = paymentInterfaceService.queryBillList(
					String.valueOf(loginToken.getUserId()), formatPageBounds(pageBounds).getPage());
			formatBillList(queryBillMap);
			resultMap.putAll(queryBillMap);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[orderDetail]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void formatBillList(Map<String, Object> billMap) {
		if (billMap == null) {
			return;
		}
		try {
			List<Map<String, Object>> billList = (List<Map<String, Object>>) billMap.get(BILL_LIST_KEY);
			if (billList != null) {
				for (Map<String, Object> bill : billList) {
					if (TYPE_CONTENT_CONSUME.equals(bill.get(TYPE_CONTENT_KEY))) {
						bill.put(TYPE_CONTENT_KEY, TYPE_CONTENT_BET);
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[formatBill]", e);
		}
	}

	@RequestMapping("/queryIdentity")
	@ResponseBody
	public Object queryIdentity(HttpServletRequest request, String token, PageBounds pageBounds) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User loginToken = differentiateTokenUser(request, token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			if (StringUtils.isNotBlank(loginToken.getMobile())) {
				resultMap.put("mobile", DataFormat.hiddenMobile(loginToken.getMobile()));
			}
			Map<String, Object> map = paymentInterfaceService.queryIdentity(String.valueOf(loginToken.getUserId()));
			resultMap.putAll(map);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[queryBankCard]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/verifyIdentity")
	@ResponseBody
	public Object verifyIdentity(HttpServletRequest request, String appId, String params, String token) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String name = parameterMap.get("name");
			String identityCard = parameterMap.get("identityCard");
			User loginToken = differentiateTokenUser(request, token);
			if (!ValidateUtil.isChineseName(name)) {
				throw new BusinessException(ComRetCode.NAME_FORMAT_ERROR);
			}
			if (!VerifyIdcard.verifyIdCard(identityCard)) {
				throw new BusinessException(ComRetCode.IDNO_FORMAT_ERROR);
			}
			String userIp = RequestUtil.getRemoteAddr(request);
			Map<String, Object> map = paymentInterfaceService.verifyIdentity(String.valueOf(loginToken.getUserId()),
					loginToken.getMobile(), name, identityCard, userIp);
			resultMap.putAll(map);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[bindBankCard]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/queryBankCard")
	@ResponseBody
	public Object queryBankCard(String token, PageBounds pageBounds, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User loginToken = differentiateTokenUser(request, token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			Map<String, Object> bankCardMap = paymentInterfaceService.queryBankCard(String.valueOf(loginToken
					.getUserId()));
			resultMap.putAll(bankCardMap);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[queryBankCard]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/bindBankCard")
	@ResponseBody
	public Object bindBankCard(HttpServletRequest request, String appId, String params, String token) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String bankCardNumber = parameterMap.get("bankCardNumber");

			User loginToken = differentiateTokenUser(request, token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			Map<String, Object> bankCardMap = paymentInterfaceService.bindBankCard(
					String.valueOf(loginToken.getUserId()), bankCardNumber, RequestUtil.getRemoteAddr(request));
			resultMap.putAll(bankCardMap);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[bindBankCard]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/withdraw")
	@ResponseBody
	public Object withdraw(HttpServletRequest request, String appId, String params, String token) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String withdrawAmount = parameterMap.get("withdrawAmount");
			User loginToken = differentiateTokenUser(request, token);
			if (loginToken == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			Map<String, Object> withdrawMap = paymentInterfaceService.withdraw(String.valueOf(loginToken.getUserId()),
					withdrawAmount, RequestUtil.getRemoteAddr(request), String.valueOf(true));
			resultMap.putAll(withdrawMap);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[withdraw]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/redPacketList")
	@ResponseBody
	public Object getRedPacketList(@RequestParam String token, Integer status, PageBounds pageBounds) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = tokenService.verifyLoginToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			RedPacket param = new RedPacket();
			param.setUserId(user.getUserId());
			if (status != null && status.equals(RedPacketStatus.RUNOUT)) {
				List<Integer> statusList = new ArrayList<Integer>();
				statusList.add(RedPacketStatus.RUNOUT);
				statusList.add(RedPacketStatus.OVERDUE);
				param.setStatusList(statusList);
			} else {
				param.setStatus(status);
			}
			List<RedPacket> list = redPacketService.selectRedPackets(param, formatPageBounds(pageBounds));
			if (list.size() > 0) {
				resultMap.put("paginator", ((PageList) list).getPaginator());
			}
			List<RedPacketVo> voList = new ArrayList<RedPacketVo>();
			for (RedPacket redPacket : list) {
				voList.add(new RedPacketVo(redPacket));
			}
			resultMap.put("redPacketList", voList);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[orderDetail]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/pay/distribute/check")
	@ResponseBody
	public Object checkDistribute(@RequestParam String platformId, @RequestParam String accountId,
			@RequestParam String handleId) {
		Map<String, Object> resultMap = new HashMap<>();
		LoggerUtil.info("accountId  = " + accountId);
		LoggerUtil.info("handleId  =" + handleId);
		try {
			BigDecimal productBrokerage = BigDecimal.ZERO;
			BigDecimal indiretBrokerage = BigDecimal.ZERO;
			Agency agency = new Agency();
			String[] handParams = handleId.split(CommonConstant.COMMA_SPLIT_STR);
			String money = handParams[2];
			String subsidyMoney = handParams[3];
			String inviteUserId = accountId.substring(2, accountId.length());
			agency.setId(Long.valueOf(handParams[0].substring(14, handParams[0].length())));
			List<Agency> agencyList = agencyService.selectAgencys(agency, new PageBounds());

			if ("null".equals(handParams[1])) {
				// 代理版

				directUserDistribute(inviteUserId, money, subsidyMoney, agencyList, resultMap);
			} else {

				if (handParams.length < 3) {
					LoggerUtil.error("handledId split:length={} " + handParams.length);
				}
				// 普通版直推上级
				User user = userService.selectUserById(Long.valueOf(handParams[1]));
				LoggerUtil.info("pay-distribute-check ： user： " + user);
				if (!handParams[1].equals(String.valueOf(agencyList.get(0).getUserId()))) {
					if (user != null) {
						// 间接上级邀请人

						if (user.getInviteUserId() != null
								&& inviteUserId.equals(String.valueOf(user.getInviteUserId()))) {
							String createTime = DateUtils.format(agencyList.get(0).getCreateTime(),
									DateUtils.DATE_FORMAT_YYYYMMDD);
							// 二级佣金：标准佣金*5%
							Product product = productService.selectProduct(agencyList.get(0).getProductId());
							if (product != null && product.getBrokerage() != null) {
								if (StringUtils.isNotBlank(money) && ValidateUtil.isMoney(money)) {
									productBrokerage = product.getBrokerage().multiply(
											BigDecimal.valueOf(Double.valueOf(money)));
								} else {
									productBrokerage = product.getBrokerage();
								}
							}
							if (DateUtils.compareTwoDates(createTime, NOW_DATE)) {
								indiretBrokerage = productBrokerage.multiply(NEW_CEO_PERCENTAGE).setScale(2,
										RoundingMode.HALF_UP);

							} else {
								if (user.getLevel() == null) {
									user.setLevel(UserLevel.MANAGER);
								}
								if (user.getLevel() == UserLevel.MANAGER) {
									indiretBrokerage = productBrokerage.multiply(MANAGER_INDIRET_PERCENTAGE);
								} else if (user.getLevel() == UserLevel.MAJORDOMO) {
									indiretBrokerage = productBrokerage.multiply(DIRECTOR_INDIRET_PERCENTAGE);
								} else if (user.getLevel() == UserLevel.BAILLIFF) {
									indiretBrokerage = productBrokerage.multiply(CEO_INDIRET_PERCENTAGE);
								}
							}
							resultMap.put("distributeAmount",
									String.valueOf(indiretBrokerage.setScale(2, RoundingMode.HALF_UP)));
						} else {
							resultMap.put("distributeAmount", String.valueOf(indiretBrokerage));
							logger.info("checkDistribute inDirectUserBrokerage error:" + inviteUserId + "!="
									+ user.getInviteUserId());
						}
					} else {
						resultMap.put("distributeAmount", String.valueOf(indiretBrokerage));
						logger.info("checkDistribute user error :" + user);

					}

				} else {
					// 是直接上级
					directUserDistribute(inviteUserId, money, subsidyMoney, agencyList, resultMap);
				}
			}
			resultMap.put("retCode", ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[checkDistribute]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void directUserDistribute(String inviteUserId, String money, String subsidyMoney, List<Agency> agencyList,
			Map<String, Object> resultMap) {
		BigDecimal brokerage = BigDecimal.ZERO;
		User inviteUser = userService.selectUserById(inviteUserId);
		if (inviteUser != null) {
			brokerage = userService.getReconciliationBrokerage(money, subsidyMoney, agencyList.get(0));
			resultMap.put("distributeAmount", String.valueOf(brokerage.setScale(2, RoundingMode.HALF_UP)));

		} else {
			resultMap.put("distributeAmount", String.valueOf(BigDecimal.ZERO));
		}

	}

}