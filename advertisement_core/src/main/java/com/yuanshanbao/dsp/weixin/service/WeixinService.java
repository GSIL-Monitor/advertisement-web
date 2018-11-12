package com.yuanshanbao.dsp.weixin.service;

import com.github.sd4324530.fastweixin.api.response.GetSignatureResponse;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yuanshanbao.dsp.common.model.Feedback;
import com.yuanshanbao.dsp.common.model.ServerLog;
import com.yuanshanbao.dsp.user.model.User;

public interface WeixinService {

	public static final String CONFIG_SERVICE = "service";
	public static final String CONFIG_APP = "app";
	public static final String CONFIG_WZXCX = "wzxcx";

	public abstract boolean hasSubscribe(User user);

	public abstract OauthGetTokenResponse getTokenResponse(String code);

	public abstract String getRedirectUrl(String returnUrl);

	public abstract String getUserInfoRedirectUrl(String returnUrl);

	public GetUserInfoResponse getUserInfo(User user);

	public GetSignatureResponse getJSSignature(String url);

	public void sendServerAlarmMessage(ServerLog serverLog);

	public abstract void sendFeedbackAlert(Feedback feedback);

	public void sendStatsticsAlert(int count, String type, String content);

	public GetUserInfoResponse getUserInfo(String openId);

	public OauthGetTokenResponse getTokenResponse(String weixinType, String code);

	public GetUserInfoResponse getUserInfo(String weixinType, User user);

	public GetUserInfoResponse getUserInfo(String weixinType, String openId);

	public GetUserInfoResponse getUserInfo(String weixinType, String token, String openId);

	public String getAppId(String key);
	
	public String getAppSecret(String key);

	public byte[] dealQRCode(String key, String scene, String page);

	public String getAssessToken();

}
