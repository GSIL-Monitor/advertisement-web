package com.yuanshanbao.dsp.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanshanbao.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.app.model.AppType;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.common.times.TimesLimitConstants;
import com.yuanshanbao.dsp.common.times.TimesLimitModel;
import com.yuanshanbao.dsp.common.times.TimesLimitService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.sms.model.SmsVerifyCode;
import com.yuanshanbao.dsp.sms.service.VerifyCodeService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Controller
@RequestMapping({ "/i/sms", "/m/sms" })
public class SmsController extends BaseController {

	@Autowired
	private VerifyCodeService smsCodeService;

	@Autowired
	private TimesLimitService timesLimitService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService accountService;

	@Autowired
	private AppService appService;

	@Autowired
	private UserService userService;

	/**
	 * 开户接口2.短信发送接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/send")
	@ResponseBody
	public Map<String, Object> SendSMSMob(HttpServletRequest request, HttpServletResponse response, String voice,
										  String smsToken) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			String mobile = request.getParameter("mobile");
			String type = request.getParameter("type");
			String channel = request.getParameter("from");
			String userIp = JSPHelper.getRemoteAddr(request);
			String appKey = request.getParameter("appKey");
			if (isApp(request)) {
				Map<String, String> parameterMap = appService.decryptParameters(request.getParameter("appId"),
						request.getParameter("params"));
				mobile = parameterMap.get("mobile");
				channel = parameterMap.get("from");
				type = parameterMap.get("type");
				appKey = appService.getAppKey(request.getParameter("appId"));
			} else {
				// String sessionToken = (String)
				// request.getSession().getAttribute(SessionConstants.SMS_TOKEN);
				// Long time = (Long)
				// request.getSession().getAttribute(SessionConstants.TOKEN_TIME);
				// if (!(StringUtils.isNotBlank(sessionToken) &&
				// sessionToken.equals(smsToken)) || time == null
				// || (System.currentTimeMillis() - time) < 4000) {
				// InterfaceRetCode.setAppCodeDesc(resultMap,
				// ComRetCode.WRONG_PARAMETER);
				// return resultMap;
				// }
			}
			// 1.手机号不为空，则使用传入的手机号
			if (StringUtils.isBlank(mobile)) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_MOBILE);
				return resultMap;
			} else if (!ValidateUtil.isPhoneNo(mobile)) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_MOBILE);
				return resultMap;
			}else if (TimesLimitConstants.REGISTER.equals(type)){
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.MOBILE_EXIST);
				return resultMap;
			}

			// String signature = getSignatureValue(type, mobile, appKey);

			// 3.短信发送是否超过上限,如果没有则发送短信
			TimesLimitModel timesLimit = timesLimitService.getTimesLimitModel(TimesLimitConstants.SEND_SMS_RANDOM_CODE,
					mobile);

			if (timesLimit.isOverTimesLimit()) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SMS_SEND_TIME_LIMIT_OVER);
				return resultMap;
			} else {
				boolean voiceValue = false;
				if ("true".equals(voice)) {
					voiceValue = true;
				}
				String signName = getSmsSignName(request);
				SmsVerifyCode smsVerifyCode = smsCodeService.sendSmsCode(signName, mobile, channel, userIp, voiceValue);
				timesLimit.increaseTimes();
				resultMap.put("signValue", smsVerifyCode.getSign());
				resultMap.put("expiredTime", smsVerifyCode.getExpiredTime());
				resultMap.put("effectiveTime", smsVerifyCode.getEffectiveTime());
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			}
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("Send sms error", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	private String getSignatureValue(String type, String mobile, String appKey) {
		String signature = "兴贷";
		if (StringUtils.isNotBlank(appKey) && appKey.equals(AppType.RUIDAI)) {
			signature = AppType.RUIDAI_DESCRIPTION;
		} else if (StringUtils.isNotBlank(appKey) && appKey.equals(AppType.XINGDAI)) {
			signature = AppType.XINGDAI_DESCRIPTION;
		}
		if (StringUtils.isNotBlank(type)) {
			User existUser = accountService.selectUserByMobile(mobile);
			if ("register".equals(type)) {
				if (existUser != null && StringUtils.isNotBlank(existUser.getPassword())) {
					throw new BusinessException(ComRetCode.USER_EXIST);
				}
			}
			if ("resetpasswd".equals(type)) {
				if (existUser == null) {
					throw new BusinessException(ComRetCode.USER_NOT_EXIST);
				}
			}
		}
		return signature;
	}

	@ResponseBody
	@RequestMapping("/agent/send")
	public Map<String, Object> agentSend(HttpServletRequest request, HttpServletResponse response, String channel,
			String type) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String mobile = request.getParameter("mobile");
			String userIp = JSPHelper.getRemoteAddr(request);
			// 1.手机号不为空，则使用传入的手机号
			if (StringUtils.isBlank(mobile)) {
				throw new BusinessException(ComRetCode.MOBILE_FORMAT_ERROR);
			} else if (!ValidateUtil.isPhoneNo(mobile)) {
				throw new BusinessException(ComRetCode.MOBILE_FORMAT_ERROR);
			}

			String signature = getSignatureValue(type, mobile, AppType.XINGDAI);

			// 3.短信发送是否超过上限,如果没有则发送短信
			TimesLimitModel timesLimit = timesLimitService.getTimesLimitModel(TimesLimitConstants.SEND_SMS_RANDOM_CODE,
					mobile);

			if (timesLimit.isOverTimesLimit()) {
				throw new BusinessException(ComRetCode.SMS_SEND_TIME_LIMIT_OVER);
			} else {
				SmsVerifyCode smsVerifyCode = smsCodeService.sendSmsCode(signature, mobile, channel, userIp, false);
				timesLimit.increaseTimes();
				resultMap.put("expiredTime", smsVerifyCode.getExpiredTime());
				resultMap.put("effectiveTime", smsVerifyCode.getEffectiveTime());
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			}
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
		} catch (Exception e) {
			logger.error("[agent send sms error]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/mobile")
	@ResponseBody
	public Map<String, Object> getMobile(HttpServletRequest request, HttpServletResponse response, String mobile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mobile)) {
				if (mobile.length() < 5) {
					map.put("mobile", "00" + mobile + "%");
				} else {
					map.put("mobile", mobile.trim());
				}
			}
			List<SmsVerifyCode> list = smsCodeService.selectMobileVerify(map, new PageBounds());
			resultMap.put("smsCode", list.get(0).getRandomCode());
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("Send sms error", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}

	}

}
