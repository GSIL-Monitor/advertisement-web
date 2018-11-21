package com.yuanshanbao.dsp.weixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sd4324530.fastweixin.api.JsAPI;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.TemplateAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.api.response.GetSignatureResponse;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.message.TemplateMsg;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.HttpsUtil;
import com.yuanshanbao.common.util.JsonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.StringUtil;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.model.Alarm;
import com.yuanshanbao.dsp.common.model.Feedback;
import com.yuanshanbao.dsp.common.model.ServerLog;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.common.service.AlarmService;
import com.yuanshanbao.dsp.common.service.AlarmType;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.UserService;

@Service
public class WeixinServiceImpl implements WeixinService {

	private String config = PropertyUtil.getProperty("weixin.config");
	private String appId = PropertyUtil.getProperty("weixin.appid");
	private String appSercet = PropertyUtil.getProperty("weixin.appsecret");
	public final static String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

	private static String ALARM_SERVER_TEMPLATE = "DycgCxDn0XmH1FPbNHpkrL4yq3bn37-bJ-FEdhGrzc4";

	private static String ALARM_FEEDBACK_TEMPLATE = "K98kza5DWrxljSxSPoRtqXrI3yniDAG9PHRDfvJdSck";

	private static String ALARM_STATISTICS_TEMPLATE = "tRap0Y5zDy7Ij-aQrqz0vZeQIoexS-eGKOglaqBBJ9w";

	@Autowired
	private UserService userService;

	@Autowired
	private AlarmService alarmService;

	@Autowired
	protected RedisService redisCacheService;

	private Map<String, OauthAPI> oauthApiMap = new HashMap<>();
	private Map<String, UserAPI> userApiMap = new HashMap<>();
	private Map<String, TemplateAPI> templateApiMap = new HashMap<>();
	private Map<String, JsAPI> jsApiMap = new HashMap<>();
	private Map<String, String> appIdMap = new HashMap<>();
	private Map<String, String> appSecretMap = new HashMap<>();
	private Map<String, ApiConfig> apiConfigMap = new HashMap<>();

	@PostConstruct
	public void init() {
		String[] configSegs = config.split(",");
		String[] appIdSegs = appId.split(",");
		String[] appSecretSegs = appSercet.split(",");
		for (int i = 0; i < configSegs.length; i++) {
			ApiConfig apiConfig = new ApiConfig(appIdSegs[i], appSecretSegs[i], true);
			OauthAPI oauthApi = new OauthAPI(apiConfig);
			UserAPI userApi = new UserAPI(apiConfig);
			TemplateAPI templateApi = new TemplateAPI(apiConfig);
			JsAPI jsApi = new JsAPI(apiConfig);
			oauthApiMap.put(configSegs[i], oauthApi);
			userApiMap.put(configSegs[i], userApi);
			templateApiMap.put(configSegs[i], templateApi);
			jsApiMap.put(configSegs[i], jsApi);
			appIdMap.put(configSegs[i], appIdSegs[i]);
			appSecretMap.put(configSegs[i], appSecretSegs[i]);
			apiConfigMap.put(configSegs[i], apiConfig);
		}
	}

	@Override
	public String getRedirectUrl(String returnUrl) {
		return getOAuthApi(null).getOauthPageUrl(returnUrl, OauthScope.SNSAPI_BASE, null);
	}

	@Override
	public String getUserInfoRedirectUrl(String returnUrl) {
		return getOAuthApi(null).getOauthPageUrl(returnUrl, OauthScope.SNSAPI_USERINFO, null);
	}

	@Override
	public OauthGetTokenResponse getTokenResponse(String code) {
		return getTokenResponse(null, code);
	}

	@Override
	public OauthGetTokenResponse getTokenResponse(String weixinType, String code) {
		try {
			OauthGetTokenResponse tokenResponse = getOAuthApi(weixinType).getToken(code);
			return tokenResponse;
		} catch (Exception e) {
			LoggerUtil.error("getOpenId", e);
		}
		return null;
	}

	@Override
	public boolean hasSubscribe(User user) {
		try {
			if (StringUtils.isNotBlank(user.getWeixinId())) {
				GetUserInfoResponse userInfo = getUserApi(null).getUserInfo(user.getWeixinId());
				return userInfo.getSubscribe() > 0;
			}
		} catch (Exception e) {
			LoggerUtil.error("hasSubscribe", e);
		}
		return false;
	}

	@Override
	public GetUserInfoResponse getUserInfo(User user) {
		return getUserInfo(null, user);
	}

	@Override
	public GetUserInfoResponse getUserInfo(String weixinType, User user) {
		try {
			if (user != null && StringUtils.isNotBlank(user.getWeixinId())) {
				GetUserInfoResponse userInfo = getUserApi(weixinType).getUserInfo(user.getWeixinId());
				if (userInfo.getErrcode() != null) {
					LoggerUtil.error("ErrorCode: " + userInfo.getErrcode() + "; ErrorMessage: " + userInfo.getErrmsg(),
							new IllegalArgumentException());
					return null;
				}
				return userInfo;
			}
		} catch (Exception e) {
			LoggerUtil.error("getUserInfo", e);
		}
		return null;
	}

	@Override
	public GetUserInfoResponse getUserInfo(String openId) {
		return getUserInfo(null, openId);
	}

	@Override
	public GetUserInfoResponse getUserInfo(String weixinType, String openId) {
		try {
			if (StringUtils.isNotBlank(openId)) {
				GetUserInfoResponse userInfo = getUserApi(weixinType).getUserInfo(openId);
				if (userInfo.getErrcode() != null) {
					LoggerUtil.error("ErrorCode: " + userInfo.getErrcode() + "; ErrorMessage: " + userInfo.getErrmsg(),
							new IllegalArgumentException());
					return null;
				}
				return userInfo;
			}
		} catch (Exception e) {
			LoggerUtil.error("getUserInfo", e);
		}
		return null;
	}

	@Override
	public GetUserInfoResponse getUserInfo(String weixinType, String token, String openId) {
		try {
			if (StringUtils.isNotBlank(openId)) {
				GetUserInfoResponse userInfo = getOAuthApi(weixinType).getUserInfo(token, openId);
				if (userInfo.getErrcode() != null) {
					LoggerUtil.error("ErrorCode: " + userInfo.getErrcode() + "; ErrorMessage: " + userInfo.getErrmsg(),
							new IllegalArgumentException());
					return null;
				}
				return userInfo;
			}
		} catch (Exception e) {
			LoggerUtil.error("getUserInfo", e);
		}
		return null;
	}

	@Override
	public GetSignatureResponse getJSSignature(String url) {
		return getJsApi(null).getSignature(url);
	}

	@Override
	public void sendServerAlarmMessage(ServerLog serverLog) {
		try {
			if ("test".equals(CommonUtil.getEnvironment())) {
				return;
			}
			LoggerUtil.sendMessageInfo("[Send Server Log serverLogId=" + serverLog.getLogId() + "; name="
					+ serverLog.getTitle());
			TemplateMsg templateMsg = new TemplateMsg();
			templateMsg.setTemplateId(ALARM_SERVER_TEMPLATE);
			templateMsg.setUrl(PropertyUtil.getProperty("host.web.open") + "/admin/serverLog.html?logId="
					+ serverLog.getLogId());
			templateMsg.putData("first", "服务器报警" + serverLog.getCount() + "次", "#F67072");
			templateMsg.putData("keyword1", serverLog.getTypeValue() + "");
			templateMsg.putData("keyword2", DateUtils.format(serverLog.getCreateTime(), null));
			templateMsg.putData("remark", serverLog.getTitle());

			List<Alarm> alarmList = alarmService.selectAlarmsByType(AlarmType.SERVER);
			for (Alarm alarm : alarmList) {
				String weixinId = alarm.getWeixinId();
				if (StringUtils.isBlank(weixinId)) {
					User user = userService.selectUserByMobile(alarm.getMobile());
					if (user != null && StringUtils.isNotBlank(user.getWeixinId())) {
						weixinId = user.getWeixinId();
					}
				}
				if (StringUtils.isNotBlank(weixinId)) {
					ResultType result = getTemplateApi(null).sendCustomMessage(weixinId, templateMsg);
					LoggerUtil.sendMessageInfo(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendFeedbackAlert(Feedback feedback) {
		try {
			LoggerUtil.sendMessageInfo("[Send Server Log serverLogId=" + feedback.getFeedbackId());
			TemplateMsg templateMsg = new TemplateMsg();
			templateMsg.setTemplateId(ALARM_FEEDBACK_TEMPLATE);
			templateMsg.putData("first", "有新的用户反馈", "#F67072");
			templateMsg.putData("keyword1", generateUserInfo(feedback));
			templateMsg.putData("keyword2", DateUtils.format(feedback.getCreateTime(), null));
			templateMsg.putData("remark", feedback.getMessage());

			List<Alarm> alarmList = alarmService.selectAlarmsByType(AlarmType.SERVER);
			for (Alarm alarm : alarmList) {
				User user = userService.selectUserByMobile(alarm.getMobile());
				if (user != null && StringUtils.isNotBlank(user.getWeixinId())) {
					ResultType result = getTemplateApi(null).sendCustomMessage(user.getWeixinId(), templateMsg);
					LoggerUtil.sendMessageInfo(result);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("sendServerAlarmMessage", e);
		}
	}

	@Override
	public void sendStatsticsAlert(int count, String type, String content) {
		try {
			TemplateMsg templateMsg = new TemplateMsg();
			templateMsg.setTemplateId(ALARM_STATISTICS_TEMPLATE);
			templateMsg.putData("first", "有" + count + "个渠道数据异常", "#F67072");
			templateMsg.putData("keyword1", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
			templateMsg.putData("keyword2", type);
			templateMsg.putData("remark", content);

			List<Alarm> alarmList = alarmService.selectAlarmsByType(AlarmType.STATISTICS_ALERT);
			for (Alarm alarm : alarmList) {
				User user = userService.selectUserByMobile(alarm.getMobile());
				if (user != null && StringUtils.isNotBlank(user.getWeixinId())) {
					ResultType result = getTemplateApi(null).sendCustomMessage(user.getWeixinId(), templateMsg);
					LoggerUtil.sendMessageInfo(result);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("sendServerAlarmMessage", e);
		}
	}

	private String generateUserInfo(Feedback feedback) {
		if (feedback.getUserId() != null) {
			User user = userService.selectUserById(feedback.getUserId());
			if (user != null) {
				String content = "";
				if (StringUtils.isNotBlank(user.getName())) {
					content += "姓名：" + user.getName() + "; ";
				}
				if (StringUtils.isNotBlank(user.getMobile())) {
					content += "电话：" + user.getMobile() + "; ";
				}
				return content;
			}
		}
		String content = "";
		if (StringUtils.isNotBlank(feedback.getName())) {
			content += "姓名：" + feedback.getName() + "; ";
		}
		if (StringUtils.isNotBlank(feedback.getMobile())) {
			content += "电话：" + feedback.getMobile() + "; ";
		}
		if (StringUtils.isNotBlank(feedback.getEmail())) {
			content += "邮箱：" + feedback.getEmail() + "; ";
		}
		return content;
	}

	private OauthAPI getOAuthApi(String key) {
		if (StringUtils.isBlank(key)) {
			return oauthApiMap.get(CONFIG_SERVICE);
		}
		return oauthApiMap.get(key);
	}

	private UserAPI getUserApi(String key) {
		if (StringUtils.isBlank(key)) {
			return userApiMap.get(CONFIG_SERVICE);
		}
		return userApiMap.get(key);
	}

	private TemplateAPI getTemplateApi(String key) {
		if (StringUtils.isBlank(key)) {
			return templateApiMap.get(CONFIG_SERVICE);
		}
		return templateApiMap.get(key);
	}

	private JsAPI getJsApi(String key) {
		if (StringUtils.isBlank(key)) {
			return jsApiMap.get(CONFIG_SERVICE);
		}
		return jsApiMap.get(key);
	}

	@Override
	public String getAppId(String key) {
		if (StringUtils.isBlank(key)) {
			return appIdMap.get(CONFIG_SERVICE);
		}
		return appIdMap.get(key);
	}

	@Override
	public String getAppSecret(String key) {
		if (StringUtils.isBlank(key)) {
			return appSecretMap.get(CONFIG_SERVICE);
		}
		return appSecretMap.get(key);
	}

	public byte[] dealQRCode(String key, String scene, String page) {

		ApiConfig apiConfig = apiConfigMap.get(key);
		/*
		 * String accessToken = apiConfig.getAccessToken(); String accessToken =
		 * redisCacheService.get(RedisConstant.ACCESS_TOKEN); if (accessToken ==
		 * null) { String newAssessToken = getAssessToken(); byte[] newResult =
		 * getQrCode(scene, page, newAssessToken); return newResult; }
		 */
		byte[] result = getQrCode(scene, page, getAccessToken());
		if (result == null) {
			redisCacheService.del(RedisConstant.ACCESS_TOKEN);
			for (int i = 0; i < 3; i++) {
				byte[] qrCode = getQrCode(scene, page, getAccessToken());
				if (qrCode != null) {
					return qrCode;
				}
			}
		} else {
			return result;
		}
		return null;
	}

	@Override
	public String getAccessToken() {
		String resultAssessToken = null;
		try {
			String access = redisCacheService.get(RedisConstant.ACCESS_TOKEN);
			if (!StringUtil.isEmpty(access)) {
				return access;
			} else {
				resultAssessToken = HttpsUtil.doGet(getAccessTokenUrl, "grant_type=client_credential&appid="
						+ getAppId(CONFIG_WZXCX) + "&secret=" + getAppSecret(CONFIG_WZXCX), "UTF-8", 30000, 30000);
				if (!StringUtil.isEmpty(resultAssessToken)) {
					Map<String, Object> resultMap = JsonUtil.jsonToMap(resultAssessToken);
					String accessToken = (String) resultMap.get("access_token");
					redisCacheService.set(RedisConstant.ACCESS_TOKEN, 60 * 60 + 60 * 30, accessToken);
					LoggerUtil.info("update accessToken" + accessToken);
					return accessToken;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] getQrCode(String scene, String page, String accessToken) {
		try {
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
			JSONObject param = new JSONObject();
			param.put("scene", scene);
			param.put("page", page);
			byte[] byteArr = HttpUtil.sendPostRequestForBytes(url, param.toString(), "UTF-8");
			if (byteArr.length < 1024) {
				String errorLog = new String(byteArr, "UTF-8");
				LoggerUtil.info("getQrCode error info=" + errorLog);
				return null;
			}
			return byteArr;
		} catch (Exception e) {
			LoggerUtil.error("[bxm_nofity]", e);
		}
		return null;
	}
}
