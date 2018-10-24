package com.yuanshanbao.dsp.controller.user.card;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRet;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.StringUtil;
import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.bankcard.service.BankCardService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/10/23.
 */
@RequestMapping("/card")
@Controller
public class UserBankCardController extends BaseController{
    @Autowired
    private BankCardService bankCardService;


    @ResponseBody
    @RequestMapping("list")
    public Object listCard(HttpServletRequest request , BankCard card, PageBounds pageBounds ,String token){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            //获取当前用户信息
          /*  User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException();
            }
            User user = userService.selectUserById(loginToken.getUserId());*/
            User user = userService.selectUserById((long) 2);
            if (user == null) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NOT_LOGIN);
                return resultMap;
            }
            //查询是否有银行卡
            card.setUserId(user.getUserId());
            List<BankCard> bankCards = bankCardService.selectBankCards(card, pageBounds);
            if (bankCards.size() == 0 ) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NO_BANK_CARD);
                return resultMap;
            }
            resultMap.put("bankCards",bankCards);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        }catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return  resultMap;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Object insertCard(HttpServletRequest request ,BankCard card ,String token){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //获取当前用户信息
          /*  User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException();
            }
            User user = userService.selectUserById(loginToken.getUserId());*/
            User user = userService.selectUserById((long) 2);
            if (user == null) {
                InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NOT_LOGIN);
                return resultMap;
            }
            //验证是否实名
            if (StringUtil.isEmpty(user.getIdentity())){
                InterfaceRetCode.setAppCodeDesc(resultMap,ComRetCode.NO_IDENTITY);
                return  resultMap;
            }
            //添加卡
            String userName = request.getParameter("userName");
            String cardNumber = request.getParameter("cardNumber");
            String smsCode = request.getParameter("code");
            //与第一次添加姓名一致
            if (!(StringUtil.isEmpty(user.getUserName()) || userName.equals(user.getUserName()))){
                InterfaceRetCode.setAppCodeDesc(resultMap,ComRetCode.NO_USERNAME);
                return resultMap;
            }
            card.setCreateTime((Timestamp) new Date());
            card.setUserId(user.getUserId());
            card.setCardNumber(cardNumber);
            card.setUserName(userName);
            bankCardService.insertBankCard(card);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        }catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

}
