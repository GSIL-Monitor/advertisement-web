package com.yuanshanbao.dsp.controller.user.card;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.StringUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.bankcard.model.vo.GetBank;
import com.yuanshanbao.dsp.bankcard.service.BankCardService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.information.service.InformationService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.ProductStatus;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.sms.service.VerifyCodeService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Created by Administrator on 2018/10/23.
 */
@RequestMapping("/i/bankCard")
@Controller
public class UserBankCardController extends BaseController {

    private static final String EDUCATION_APP = "education_app";
    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private ProductService productService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private InformationService informationService;

    @ResponseBody
    @RequestMapping("list")
    public Object listCard(HttpServletRequest request, BankCard card, PageBounds pageBounds, String token) {
        Map<String, Object> resultMap = new HashMap<>();
        List<BankCard> bankCardList = new ArrayList<>();
        try {

            //获取当前用户信息
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            User user = userService.selectUserById(loginToken.getUserId());
            if (user == null) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NOT_LOGIN);
                return resultMap;
            }
            //查询是否有银行卡
            card.setUserId(user.getUserId());
            List<BankCard> bankCards = bankCardService.selectBankCards(card, pageBounds);
            if (bankCards.size() == 0) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NO_BANK_CARD);
                return resultMap;
            }
            for (BankCard bankCard : bankCards) {
                bankCard.getCardNumber().replace(bankCard.getCardNumber().substring(0, 11), "**** **** ****");
                bankCardList.add(bankCard);
            }
            resultMap.put("bankCards", bankCardList);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }


    @RequestMapping("/applyCard")
    @ResponseBody
    public Object applyCard(String token, @RequestParam("productId") Long productId, @RequestParam("userName") String userName, @RequestParam("mobile") String mobile) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginUser = getLoginUser(token);
            if (!ValidateUtil.isPhoneNo(mobile)) {
                throw new BusinessException(ComRetCode.WRONG_MOBILE);
            }
            bankCardService.getApplyBankCardInfo(productId, userName, mobile);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    @RequestMapping("/insertBank")
    @ResponseBody
    public Object insertCard(HttpServletRequest request, String token, String appId, String params) {
        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer stringBuffer = new StringBuffer();

        try {
            //获取当前用户信息
            User loginToken = tokenService.verifyLoginToken(token);
            Map<String, String> parameterMap = appService.decryptParameters(appId, params);
            if (loginToken == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            User user = userService.selectUserById(loginToken.getUserId());
            if (user == null) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NOT_LOGIN);
                return resultMap;
            }
            //添加卡
            String userName = parameterMap.get("userName");
            String cardNumber = parameterMap.get("cardNumber");
            String smsCode = parameterMap.get("code");
            String userIp = JSPHelper.getRemoteAddr(request);
            //与第一次添加姓名一致
            if (!(StringUtil.isEmpty(user.getName()) || userName.equals(user.getName()))) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NO_USERNAME);
                return resultMap;
            }
            verifyCodeService.validateSmsCode(loginToken.getMobile(), smsCode, "", userIp);
            //验证银行卡是否合法
            if (!ValidateUtil.isBankCardNo(cardNumber)) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.BANK_CAED_ERRO);
                return resultMap;
            }
            //获取银行卡归属
            String bankName = GetBank.getname(cardNumber);
            BankCard card = new BankCard();
            card.setUserId(user.getUserId());
            card.setCardNumber(cardNumber);
            card.setCardName(bankName);
            card.setUserName(userName);
            bankCardService.insertBankCard(card);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/card")
    public Object card(HttpServletRequest request, String token) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null)
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            User user = userService.selectUserById(loginToken.getUserId());
            resultMap.put("user", user);
            Activity activity = ConfigManager.getActivityByKey(EDUCATION_APP);
            if (activity == null) {
                throw new BusinessException();
            }
            // 产品列表
            Product product = new Product();
            product.setActivityId(activity.getActivityId());
            product.setStatus(ProductStatus.ONLINE);
            PageList<Product> productList = (PageList<Product>) productService.selectProducts(product,
                    new PageBounds());
            resultMap.put("productList", productList);
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

}
