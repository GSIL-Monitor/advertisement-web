package com.yuanshanbao.dsp.controller.user.card;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.bankcard.service.BankCardService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;

/**
 * Created by Administrator on 2018/10/23.
 */
@RequestMapping("/i/bankCard")
@Controller
public class UserBankCardController extends BaseController {

	@Autowired
	private BankCardService bankCardService;

	@RequestMapping("/applyCard")
	@ResponseBody
	public Object applyCard(HttpServletRequest request, String token, @RequestParam("productId") String productId,
			@RequestParam("userName") String userName, @RequestParam("mobile") String mobile, String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		try {

			if (!ValidateUtil.isPhoneNo(mobile)) {
				throw new BusinessException(ComRetCode.WRONG_MOBILE);
			}
			if (StringUtils.isBlank(userId)) {
				User loginUser = getLoginUser(token);
				bankCardService.getApplyBankCardInfo(loginUser, Long.valueOf(productId), userName, mobile);
			} else {
				User user = userService.selectUserById(userId);
				if (user != null && user.getLevel() == UserLevel.VIP_AGENT) {
					bankCardService.addVIPAgentOrBankCardInfo(user.getUserId(), Long.valueOf(productId), userName,
							mobile);
				} else {
					User loginUser = getLoginUser(token);
					bankCardService.getApplyBankCardInfo(loginUser, Long.valueOf(productId), userName, mobile);
				}

			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[productList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

}
