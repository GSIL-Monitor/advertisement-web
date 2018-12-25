package com.yuanshanbao.dsp.payment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.RSAUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.common.constant.ResultConstant;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.order.model.Order;

@Service
public class PaymentInterfaceServiceImpl implements PaymentInterfaceService {

	private static final String PLATFORM_ID = "10003";
	private static final String SALE_ACCOUNT_ID = "wangzhuan";
	private static final String SALE_ACCOUNT_NAME = "wangzhuan";

	@Override
	public Map<String, Object> getPrePayToken(Order order, String paymethod) {
		return null;
	}

	private String generateChargeParameters(String userId, String chargeAmount, String paymethod, String userIp) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("platformId", PLATFORM_ID);
		parameters.put("saleAccountId", SALE_ACCOUNT_ID);
		parameters.put("saleAccountName", SALE_ACCOUNT_NAME);
		parameters.put("buyAccountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameters.put("goodsName", "充值");
		parameters.put("orderAmount", chargeAmount);
		parameters.put("balancePayAmount", "0");
		parameters.put("platformOrderId", "charge" + userId + System.currentTimeMillis());
		parameters.put("orderType", "4");
		parameters.put("paymethod", paymethod);
		parameters.put("platformOrderTime", DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS));
		parameters.put("userIp", userIp);
		return addSignature(parameters);
	}

	private String addSignature(Map<String, String> parameters) {
		String source = CommonUtil.formatParaMap(parameters, false, true, false);
		String sign = RSAUtil.generateSHA1withRSASigature(PropertyUtil.getProperty(CommonConstant.PAY_PRIVATE_KEY),
				source);
		parameters.put("signature", sign);
		return CommonUtil.formatParaMap(parameters, true, true, false);
	}

	String generateMemoStr(String type, String detail) {
		return new StringBuffer("[{\"title\":\"类目\",\"content\":\"").append(type)
				.append("\"},{\"title\":\"详情\"," + "\"content\":\"").append(detail).append("\"}]").toString();
	}

	@Override
	public Map<String, Object> charge(String userId, String chargeAmount, String paymethod, String userIp) {
		try {
			String url = PropertyUtil.getProperty("payment.host") + PropertyUtil.getProperty("payment.url.addOrder");
			String parameters = generateChargeParameters(userId, chargeAmount, paymethod, userIp);
			String result = HttpUtil.sendPostRequest(url, parameters, "UTF-8");
			JSONObject jsonObject = (JSONObject) JSON.parse(result);
			String code = jsonObject.get("retCode").toString();
			if (ValidateUtil.isNumber(code)) {
				int retCode = Integer.parseInt(code);
				if (ComRetCode.SUCCESS == retCode) {
					String paymentOrderId = (String) jsonObject.get("paymentOrderId");
					if (StringUtils.isBlank(paymentOrderId)) {
						LoggerUtil.error("[getChargePrePayToken] error: " + result);
						throw new BusinessException("哎呀，网络不给力！");
					}
					jsonObject.put("chargeId", paymentOrderId);
					return jsonObject;
				} else {
					throw new BusinessException(retCode);
				}
			} else {
				LoggerUtil.error("[getChargePrePayToken] error: " + result);
				throw new BusinessException(ComRetCode.FAIL);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public Map<String, Object> queryBalance(String userId) {
		return commonPaymentsInterface("queryBalance", userId);
	}

	@Override
	public Map<String, Object> queryBillList(String userId, int page) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("page", page + "");
		return commonPaymentsInterface("queryBillList", parameterMap, false);
	}

	@Override
	public Map<String, Object> queryBillAmount(String userId, int type) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("type", type + "");
		return commonPaymentsInterface("queryBillAmount", parameterMap, false);
	}

	@Override
	public Map<String, Object> queryIdentity(String userId) {
		return commonPaymentsInterface("queryIdentity", userId);
	}

	@Override
	public Map<String, Object> verifyIdentity(String userId, String mobile, String name, String identityCard,
			String userIp) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("mobile", mobile);
		parameterMap.put("name", name);
		parameterMap.put("identityCard", identityCard);
		return commonPaymentsInterface("verifyIdentity", parameterMap, true);
	}

	@Override
	public Map<String, Object> queryBankCard(String userId) {
		return commonPaymentsInterface("queryBankCard", userId);
	}

	@Override
	public Map<String, Object> bindBankCard(String userId, String bankCardNumber, String userIp) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("bankCardNumber", bankCardNumber);
		parameterMap.put("userIp", userIp);
		return commonPaymentsInterface("bindBankCard", parameterMap, true);
	}

	@Override
	public Map<String, Object> queryOrderPay(String userId, String orderId) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("platformOrderId", orderId);
		return commonPaymentsInterface("queryOrder", parameterMap, false);
	}

	@Override
	public Map<String, Object> queryChargePay(String userId, String chargeId) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("paymentOrderId", chargeId);
		return commonPaymentsInterface("queryOrder", parameterMap, false);
	}

	@Override
	public Map<String, Object> withdraw(String userId, String withdrawAmount, String userIp) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameterMap.put("withdrawAmount", withdrawAmount);
		parameterMap.put("userIp", userIp);
		return commonPaymentsInterface("withdraw", parameterMap, true);
	}

	@Override
	public Map<String, Object> queryPaymethod(String userId) {
		return commonPaymentsInterface("queryPaymethod", userId);
	}

	@Override
	public Map<String, Object> reconciliationAmount(Timestamp startTime, Timestamp endTime) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("startTime", DateUtils.format(startTime, DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS));
		parameterMap.put("endTime", DateUtils.format(endTime, DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS));
		return commonPaymentsInterface("reconciliationAmount", parameterMap, true);
	}

	@Override
	public Map<String, Object> reconciliationOrderDetail(List<Order> orderList) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		String orderIds = "";
		for (Order order : orderList) {
			orderIds += order.getOrderId() + ",";
		}
		parameterMap.put("orderIds", orderIds);
		return commonPaymentsInterface("reconciliationOrderDetail", parameterMap, true);
	}

	private Map<String, Object> commonPaymentsInterface(String key, String userId) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("accountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		return commonPaymentsInterface(key, parameterMap, false);
	}

	private Map<String, Object> commonPaymentsInterface(String key, Map<String, String> parameterMap,
			boolean hasSignature) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String url = PropertyUtil.getProperty("payment.host") + PropertyUtil.getProperty("payment.url." + key);
			parameterMap.put("platformId", PLATFORM_ID);
			String parameters = null;
			if (hasSignature) {
				parameters = addSignature(parameterMap);
			} else {
				parameters = CommonUtil.formatParaMap(parameterMap, true, true, false);
			}
			String result = HttpUtil.sendPostRequest(url, parameters, "UTF-8");
			JSONObject jsonObject = (JSONObject) JSON.parse(result);
			String code = jsonObject.get("retCode").toString();
			if (ValidateUtil.isNumber(code)) {
				int retCode = Integer.parseInt(code);
				if (ComRetCode.SUCCESS == retCode) {
					resultMap.putAll(jsonObject);
					return resultMap;
				} else {
					throw new BusinessException(retCode);
				}
			} else {
				LoggerUtil.error("支付网关接口" + key + "调用失败: " + result);
				throw new BusinessException(ComRetCode.FAIL);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public Map<String, Object> refund(String userId, String orderId, String refundId, BigDecimal refundAmount,
			String refundReason, int refundType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			}
			String url = PropertyUtil.getProperty("payment.host") + PropertyUtil.getProperty("payment.url.refund");
			String parameters = generateRefundParameters(userId, orderId, refundId, refundAmount, refundReason,
					refundType);
			String result = HttpUtil.sendPostRequest(url, parameters, "UTF-8");
			JSONObject jsonObject = (JSONObject) JSON.parse(result);
			return jsonObject;
		} catch (Exception e) {
			LoggerUtil.error("process order failed for refund", e);
			String msg = ResultConstant.NEED_RELOGIN.equals(e.getMessage()) ? e.getMessage() : ComRetCode.FAIL_DESC;
			throw new BusinessException(msg);
		}
	}

	private String generateRefundParameters(String userId, String orderId, String refundId, BigDecimal refundAmount,
			String refundReason, int refundType) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("platformId", PLATFORM_ID);
		parameters.put("platformAccountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameters.put("platformOrderId", orderId);
		parameters.put("platformRefundId", refundId);
		parameters.put("refundAmount", refundAmount.toString());
		parameters.put("refundReason", refundReason);
		parameters.put("refundType", refundType + "");
		return addSignature(parameters);
	}

	@Override
	public Map<String, Object> distribute(String userId, String handleId, String orderId, BigDecimal trueAmount) {
		try {
			String url = PropertyUtil.getProperty("payment.host") + PropertyUtil.getProperty("payment.url.distribute");
			String parameters = generateDistributeParameters(userId, handleId, orderId, trueAmount);
			LoggerUtil.info("[distribute : url=]" + url);
			LoggerUtil.info("[distribute : parameters=]" + parameters);
			String result = HttpUtil.sendPostRequest(url, parameters, "UTF-8");
			JSONObject jsonObject = (JSONObject) JSON.parse(result);
			String code = jsonObject.get("retCode").toString();
			LoggerUtil.info("[distribute : resultInfo=]" + result);

			LoggerUtil.info("[distribute : code=]" + code);
			if (ValidateUtil.isNumber(code)) {

				int retCode = Integer.parseInt(code);
				if (ComRetCode.SUCCESS == retCode) {
					return jsonObject;
				} else {
					throw new BusinessException(retCode);
				}
			} else {
				LoggerUtil.error("[callPaymentsDistribute] error: " + result);
				throw new BusinessException(ComRetCode.FAIL);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	private String generateDistributeParameters(String userId, String handleId, String orderId, BigDecimal trueAmount) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("platformId", PLATFORM_ID);
		parameters.put("platformAccountId", CommonConstant.PAYMENT_PLATFROM_NAME + userId);
		parameters.put("handleId", handleId);
		parameters.put("platformOrderId", orderId);
		parameters.put("distributeAmount", trueAmount.toString());
		parameters.put("checkDistributeUrl",
				PropertyUtil.getProperty("wangzhuan.host") + PropertyUtil.getProperty("wangzhuan.url.checkDistribute"));
		return addSignature(parameters);
	}
}
