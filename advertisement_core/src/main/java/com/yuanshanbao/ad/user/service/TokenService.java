package com.yuanshanbao.ad.user.service;

import com.yuanshanbao.ad.user.model.LoginToken;
import com.yuanshanbao.ad.user.model.User;

public interface TokenService {

	public abstract LoginToken generateLoginToken(String appId, String userId, String loginIp);

	public abstract String generateTempToken(String appId, String userId);

	public abstract String generateTempToken(String appId, int expireTime, String userId);

	public abstract User verifyLoginToken(String token);

	public abstract User verifyTempToken(String token);

}