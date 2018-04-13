package com.yuanshanbao.dsp.user.service;

import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;

public interface TokenService {

	public abstract LoginToken generateLoginToken(String appId, String userId, String loginIp);

	public abstract String generateTempToken(String appId, String userId);

	public abstract String generateTempToken(String appId, int expireTime, String userId);

	public abstract User verifyLoginToken(String token);

	public abstract User verifyTempToken(String token);

}