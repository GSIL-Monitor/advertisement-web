package com.yuanshanbao.dsp.controller.account;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.common.util.VerifyIdcard;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.earnings.model.Earnings;
import com.yuanshanbao.dsp.earnings.service.EarningsService;
import com.yuanshanbao.dsp.payment.PaymentInterfaceService;
import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.dsp.redpacket.model.RedPacketStatus;
import com.yuanshanbao.dsp.redpacket.model.RedPacketVo;
import com.yuanshanbao.dsp.redpacket.service.RedPacketService;
import com.yuanshanbao.dsp.sms.service.VerifyCodeService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.withdrawdeposit.model.WithdrawDeposit;
import com.yuanshanbao.dsp.withdrawdeposit.service.WithdrawDepositService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/i/account")
public class AccountController extends BaseController {

    private static final String BILL_LIST_KEY = "billList";

    private static final String TYPE_CONTENT_KEY = "typeContent";

    private static final String TYPE_CONTENT_BET = "投注";

    private static final String TYPE_CONTENT_CONSUME = "消费";

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

    @RequestMapping("/balance")
    @ResponseBody
    public Object getBalance(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            resultMap.putAll(paymentInterfaceService.queryBalance(String.valueOf(loginToken.getUserId())));
            resultMap.put("redPacketCount", redPacketService.selectShowRedPacketCount(String.valueOf(loginToken.getUserId())));
            resultMap.put("user", userService.selectUserById(loginToken.getUserId()));
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
     * @param token
     * @return
     */
    @RequestMapping("/earnings")
    @ResponseBody
    public Object getEarnings(String token ,  Earnings earnings , PageBounds pageBounds) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            List<Earnings> earningsList= earningService.selectEarnings(earnings, pageBounds);
            resultMap.put("earningsList",earningsList);
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
     * @param token
     * @return
     */
    @RequestMapping("/withdrawDeposit")
    @ResponseBody
    public Object getWithdrawDeposit(String token, WithdrawDeposit withdrawDeposit ,PageBounds pageBounds) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            withdrawDeposit.setUserId(loginToken.getUserId());
            List<WithdrawDeposit> withdrawDepositList = withdrawDepositServcie.selectWithdrawDeposits(withdrawDeposit, pageBounds);
            resultMap.put("withdrawDepositList",withdrawDepositList);
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
                resultMap.putAll(paymentInterfaceService.queryChargePay(String.valueOf(loginToken.getUserId()), chargeId));
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
            Map<String, Object> queryBillMap = paymentInterfaceService.queryBillList(String.valueOf(loginToken.getUserId()),
                    formatPageBounds(pageBounds).getPage());
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
    public Object queryIdentity(String token, PageBounds pageBounds) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
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
    public Object verifyIdentity(HttpServletRequest request, String appId, String params) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, String> parameterMap = appService.decryptParameters(appId, params);
            String token = parameterMap.get("token");
            String mobile = parameterMap.get("mobile");
            String name = parameterMap.get("name");
            String identityCard = parameterMap.get("identityCard");
            String smsCode = parameterMap.get("smsCode");

            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            if (!ValidateUtil.isPhoneNo(mobile)) {
                throw new BusinessException(ComRetCode.MOBILE_FORMAT_ERROR);
            }
            if (!ValidateUtil.isChineseName(name)) {
                throw new BusinessException(ComRetCode.NAME_FORMAT_ERROR);
            }
            if (!VerifyIdcard.verifyIdCard(identityCard)) {
                throw new BusinessException(ComRetCode.IDNO_FORMAT_ERROR);
            }
            String userIp = RequestUtil.getRemoteAddr(request);
            verifyCodeService.validateSmsCode(mobile, smsCode, "", userIp);
            Map<String, Object> map = paymentInterfaceService
                    .verifyIdentity(String.valueOf(loginToken.getUserId()), mobile, name, identityCard, userIp);
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
    public Object queryBankCard(@RequestParam String token, PageBounds pageBounds) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            Map<String, Object> bankCardMap = paymentInterfaceService.queryBankCard(String.valueOf(loginToken.getUserId()));
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
    public Object bindBankCard(HttpServletRequest request, String appId, String params) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, String> parameterMap = appService.decryptParameters(appId, params);
            String token = parameterMap.get("token");
            String bankCardNumber = parameterMap.get("bankCardNumber");

            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            Map<String, Object> bankCardMap = paymentInterfaceService.bindBankCard(String.valueOf(loginToken.getUserId()), bankCardNumber,
                    RequestUtil.getRemoteAddr(request));
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
    public Object withdraw(HttpServletRequest request, @RequestParam String token, String withdrawAmount) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            Map<String, Object> withdrawMap = paymentInterfaceService.withdraw(String.valueOf(loginToken.getUserId()), withdrawAmount,
                    RequestUtil.getRemoteAddr(request));
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

}