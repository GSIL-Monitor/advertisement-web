package com.yuanshanbao.dsp.controller.web.education;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.AESUtil;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NumberUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.common.util.VerifyIdcard;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.config.ConfigWrapper;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.information.model.ExtendInfo;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.model.InformationType;
import com.yuanshanbao.dsp.information.service.ExtendInfoService;
import com.yuanshanbao.dsp.information.service.InformationService;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.sms.service.VerifyCodeService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.AESUtils;

public class BaseInformationController extends BaseController {

	public static final String MERCHANT_LIANGHUAPAI = "lianghuapai";

	public static final String MERCHANT_PUFAXINYONGKA = "pufaxinyongka";

	public static final String MERCHANT_VIPKID = "vid";

	public static final String MERCHANT_LEXUEWANG = "lexuewang";

	public static final String MERCHANT_SAYABC = "sayabc";

	public static final String MERCHANT_SHIJIJIAYUAN = "shijijiayuan";

	public static final String MERCHANT_ZHANGMEN = "zhangmen";

	public static final String MERCHANT_HUIXUELA = "huixuela";

	public static final String MERCHANT_DADA = "dada";

	public static final String CODE_FAKE_MOBILE = "13800138000";

	public static final String GORDER_ENCRYPT_KEY = "aadecfe68c1c06a7";

	@Autowired
	protected ExtendInfoService extendInfoService;

	@Autowired
	protected InformationService informationService;

	@Autowired
	protected MerchantService merchantService;

	@Autowired
	protected VerifyCodeService smsCodeService;

	@Autowired
	protected ActivityService activityService;

	protected void setChannelVariables(HttpServletRequest request, Map<String, Object> resultMap, String pageKey) {
		String from = request.getParameter("channel");
		if (StringUtils.isEmpty(from) || from.equals("null")) {
			from = null;
		}
		String code = request.getParameter("code");
		request.getSession().setAttribute(pageKey + SessionConstants.SESSION_USER_FROM, from);
		request.getSession().setAttribute(SessionConstants.SESSION_PAGE_KEY, pageKey);
		resultMap.put("channel", ConfigManager.getChannel(from));
		resultMap.put("uvCountChannel", from);
		resultMap.put("registerFrom", from);
		if (StringUtils.isNotBlank(code)) {
			// resultMap.put("code", codeService.getCode(code));
			resultMap.put("fakeMobile", CODE_FAKE_MOBILE);
		}
		setSmsToken(request, resultMap);
		setShareVariables(request, resultMap);
		resultMap.put("genderList", ConstantsManager.getTagsList(ConstantsManager.GENDER));
		Activity activity = ConfigManager.getActivityByKey(pageKey);
		if (activity != null) {
			ConfigManager.setConfigMap(resultMap, activity.getActivityId(), from);
			handleThirdPartParameters(request, activity.getActivityId(), from);
		}
	}

	private void setShareVariables(HttpServletRequest request, Map<String, Object> resultMap) {
		String shareMobile = request.getParameter("shareMobile");
		String shareActivityId = request.getParameter("shareActivityId");
		if (StringUtils.isBlank(shareMobile) && StringUtils.isBlank(shareActivityId)) {
			resultMap.put("weChatConfig", "true");
			return;
		}
		resultMap.put("shareMobile", shareMobile);
		resultMap.put("shareActivityId", shareActivityId);
		resultMap.put("weChatConfig", "false");
	}

	private void handleThirdPartParameters(HttpServletRequest request, Long activityId, String from) {
		try {
			String code = ConfigWrapper.getThirdStatCodeConfig(activityId, from);
			if (StringUtils.isNotBlank(code)) {
				if (code.startsWith("duiba")) {
					String id = request.getParameter("a_tuiaId");
					request.getSession().setAttribute(SessionConstants.SESSION_THIRD_STAT_CODE_CONFIG, id);
				} else if (code.startsWith("bxm")) {
					String id = request.getParameter("bxm_id");
					request.getSession().setAttribute(SessionConstants.SESSION_THIRD_STAT_CODE_CONFIG, id);
				} else if (code.startsWith("hdt")) {
					String id = request.getParameter("utm_click");
					request.getSession().setAttribute(SessionConstants.SESSION_THIRD_STAT_CODE_CONFIG, id);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[handleThirdPartParameters]", e);
		}
	}

	protected void commit(HttpServletRequest request, Map<String, Object> resultMap, Information information,
			String activityKey, String originalMerchantName) {
		Activity activity = activityService.selectActivity(activityKey);
		if (activity == null) {
			throw new BusinessException(ComRetCode.ACTIVITY_NOT_EXIST_ERROR);
		}
		information.setActivityId(activity.getActivityId());
		information.setType(InformationType.EDUCATION);
		information.setIp(JSPHelper.getRemoteAddr(request));
		String channel = (String) request.getSession().getAttribute(activityKey + SessionConstants.SESSION_USER_FROM);
		information.setChannel(channel);
		getShareVariables(request, information);
		request.getSession().setAttribute(SessionConstants.SESSION_INFORMATION, information);

		validateInformation(information);
		Long activityId = activity.getActivityId();
		if (ConfigWrapper.hasVerifyCode(activityId, channel)) {
			smsCodeService.validateSmsCode(information.getMobile(), request.getParameter("smsCode"), null,
					request.getRemoteAddr());
		}
		verifySmsToken(request);
		String code = request.getParameter("code");
		if (StringUtils.isNotBlank(code)) {
			// Code codeObject = codeService.getCode(code);
			// if (codeObject != null) {
			// if (StringUtils.isNotBlank(codeObject.getMobile()) &&
			// CODE_FAKE_MOBILE.equals(information.getMobile())) {
			// information.setMobile(codeObject.getMobile());
			// }
			// }
		}
		informationService.checkExist(information);
		User user = updateUserInfo(request, information, channel);
		activityService.applyActivityForInformation(user, information, activity);
		boolean needSubmitSurvey = ConfigWrapper.isSurveyIndex(activity.getActivityId(), channel);
		if (needSubmitSurvey) {
			submitSurvey(request, information);
		}
		handleThirdPartCommit(request, information);
		informationService.tryDeliver(information, false, false);

		resultMap.put("informationId", information.getInformationId());
		try {
			String informationKey = AESUtil.encrypt(information.getInformationId() + "", GORDER_ENCRYPT_KEY);
			resultMap.put("informationKey", URLEncoder.encode(informationKey, "UTF-8"));
			request.getSession().setAttribute(SessionConstants.SESSION_GORDER_ID, information.getInformationId());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		resultMap.put("hasPopupSurvey",
				ConfigWrapper.isSurveyPopup(activityId, channel, information.getMerchantId(), null));
		// resultMap.put("goods",
		// goodsService.selectGoods(information.getGoodsId()));
		InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
	}

	private void getShareVariables(HttpServletRequest request, Information information) {
		String shareMobileKey = information.getShareMobile();
		String shareActivityKey = information.getShareActivityId();
		if (StringUtils.isBlank(shareMobileKey) && StringUtils.isBlank(shareActivityKey)) {
			return;
		}
		try {
			String shareMobile = AESUtils.decrypt(MOBILE_ENCRYPT_KEY, shareMobileKey);
			String shareActivityId = AESUtils.decrypt(MOBILE_ENCRYPT_KEY, shareActivityKey);
			information.setShareMobile(shareMobile);
			information.setShareActivityId(shareActivityId);
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
	}

	protected void validateInformation(Information information) {
		if (!ValidateUtil.isPhoneNo(information.getMobile())) {
			throw new BusinessException("mobile", ComRetCode.MOBILE_FORMAT_ERROR);
		}
		if (ConfigWrapper.noIdentityCard(information.getActivityId(), information.getChannel(),
				information.getMerchantId(), null)) {
			LoggerUtil.info("[ValidateInsurant] hasIdentityCardConfig:no mobile=" + information.getMobile()
					+ ", channel=" + information.getChannel());
		} else if (!ConfigWrapper.hasIdentityCard(information.getActivityId(), information.getChannel(),
				information.getMerchantId(), null)) {
			if (information.getBirthday() == null) {
				throw new BusinessException("birthday", ComRetCode.BIRTHDAY_FORMAT_ERROR);
			}
			if (!ConstantsManager.validateConstants(ConstantsManager.GENDER, information.getGender())) {
				throw new BusinessException("gender", ComRetCode.GENDER_FORMAT_ERROR);
			}
		} else if (!VerifyIdcard.verifyIdCard(information.getIdentityCard())) {
			throw new BusinessException("identityCard", ComRetCode.IDNO_FORMAT_ERROR);
		}
		if (!ValidateUtil.isChineseName(information.getName())) {
			throw new BusinessException("name", ComRetCode.NAME_FORMAT_ERROR);
		}
		if (!ConfigWrapper.isEmailOption(information.getActivityId(), information.getChannel(),
				information.getMerchantId(), null)
				&& StringUtils.isBlank(information.getEmail())) {
			throw new BusinessException("email", ComRetCode.EMAIL_FORMAT_ERROR);
		}
		if (StringUtils.isNotBlank(information.getEmail()) && !ValidateUtil.isEmail(information.getEmail())) {
			throw new BusinessException("email", ComRetCode.EMAIL_FORMAT_ERROR);
		}
	}

	private User updateUserInfo(HttpServletRequest request, Information information, String from) {
		User user = (User) request.getSession().getAttribute(SessionConstants.SESSION_ACCOUNT);
		String weixinId = (String) request.getSession().getAttribute(SessionConstants.SESSION_WEIXIN);
		if (user == null) {
			user = userService.selectUserByMobile(information.getMobile());
			if (user == null) {
				user = new User();
				user.setMobile(information.getMobile());
				user.setRegisterFrom(from);
				user.setWeixinId(weixinId);
				userService.insertOrUpdateUser(user);
				request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, user);
			}
		}
		return user;
	}

	private void handleThirdPartCommit(HttpServletRequest request, Information information) {
		try {
			String id = (String) request.getSession().getAttribute(SessionConstants.SESSION_THIRD_STAT_CODE_CONFIG);
			if (StringUtils.isNotBlank(id)) {
				// redisCacheService.setWithEnv(RedisConstant.getAgentNotifyHandlerKey(information.getMobile()),
				// id, 60L);
			}
		} catch (Exception e) {
			LoggerUtil.error("[handleThirdPartCommit]", e);
		}
	}

	protected void submitSurvey(HttpServletRequest request, Information information) {
		int questionCount = ConfigWrapper.getSurveyCount(information.getActivityId(), information.getChannel(),
				information.getMerchantId(), null);
		for (int i = 1; i <= questionCount; i++) {
			String question = request.getParameter("question" + i);
			String answer = request.getParameter("answer" + i);
			if (StringUtils.isNotBlank(question) && StringUtils.isNotBlank(answer)) {
				ExtendInfo extendInfo = new ExtendInfo(information.getActivityId(), information.getChannel(),
						information.getMerchantId(), information.getInformationId(), question, answer);
				extendInfo.setStatus(CommonStatus.ONLINE);
				extendInfoService.insertExtendInfo(extendInfo);
			}
		}
	}

	/**
	 * 强制跳转到链接
	 * 
	 * @return
	 */
	protected String forceRedirectUrl(HttpServletRequest request, String channel, String pageKey) {
		if (StringUtils.isBlank(pageKey)) {
			return null;
		}

		Activity activity = ConfigManager.getActivityByKey(pageKey);
		if (activity != null) {
			String redirectUrl = ConfigWrapper.getRedirectUrl(activity.getActivityId(), channel, null, null);
			if (StringUtils.isNotBlank(redirectUrl)) {
				return redirectUrl;
			}
			String hostUrl = hostRedirectUrl(request, channel, activity);
			if (StringUtils.isNotBlank(hostUrl)) {
				return hostUrl;
			}
		}
		return null;
	}

	/**
	 * 域名跳转
	 * 
	 * @return
	 */
	protected String hostRedirectUrl(HttpServletRequest request, String channel, Activity activity) {
		// Channel c = ConfigManager.getChannel(channel);
		// String host = request.getHeader("Host");
		// String isHttps = request.getHeader("IsHttps");
		// String redirectHost = "";
		// String redirectUrl = "";
		//
		// // 青蓝域名强跳
		// redirectHost = "www.huhabao.com";
		//
		// if (StringUtils.isNotBlank(host) && !host.contains(redirectHost) && c
		// != null
		// && ChannelType.QINGLAN.equals(c.getDeliverType())
		// && ConfigWrapper.hasHostRedirect(activity.getActivityId(), channel))
		// {
		// if ("false".equals(isHttps)) {
		// redirectUrl = "http://" + redirectHost + request.getRequestURI() +
		// "?" + request.getQueryString();
		// } else {
		// redirectUrl = "https://" + redirectHost + request.getRequestURI() +
		// "?" + request.getQueryString();
		// }
		// return redirectUrl;
		// }
		return null;
	}

	protected void setResultShareVariables(Map<String, Object> resultMap, Information information) {
		if (information != null) {
			if (StringUtils.isBlank(information.getShareMobile())) {
				resultMap.put("weChatConfig", "true");
			} else {
				resultMap.put("weChatConfig", "false");
			}
		}
	}

	/**
	 * 获取活动倒数次数
	 * 
	 * @return
	 */
	public List<Integer> getReverseShowCount(String channel, String activityKey) {
		String key = RedisConstant.getReverseShowCountKey(channel, activityKey);
		String str = (String) redisCacheService.get(key);
		long count = 0;
		if (ValidateUtil.isNumber(str)) {
			count = Long.parseLong(str);
		}
		if (count == 0) {
			count = (long) IniBean.getIniIntegerValue("reverse_show_count", 999);
			redisCacheService.set(key, count + "");
			redisCacheService.expire(key, 24 * 3600);
		}

		return NumberUtil.toList(count);
	}
}
