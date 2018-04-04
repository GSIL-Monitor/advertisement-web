package com.yuanshanbao.ad.user.service;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.ad.common.constant.RedisConstant;
import com.yuanshanbao.ad.common.redis.base.RedisService;
import com.yuanshanbao.ad.user.dao.UserDao;
import com.yuanshanbao.ad.user.model.LoginToken;
import com.yuanshanbao.ad.user.model.User;

@Service
public class TokenServiceImpl implements TokenService {

	private static final int TEMP_TOKEN_EXPIRE_TIME = 60 * 10;

	@Autowired
	private RedisService redisCacheService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@Override
	public LoginToken generateLoginToken(String appId, String userId, String loginIp) {
		LoginToken loginToken = new LoginToken();
		String token = generateToken(appId);
		loginToken.setAppId(appId);
		loginToken.setToken(token);
		loginToken.setUserId(userId);
		loginToken.setLoginTime(DateUtils.getCurrentTime());
		loginToken.setLoginIp(loginIp);
		userDao.saveLoginToken(loginToken);
		redisCacheService.set(RedisConstant.getLoginTokenCacheKey(token), userId + "");
		return loginToken;
	}

	@Override
	public String generateTempToken(String appId, int expireTime, String userId) {
		String token = "temp_token_" + generateToken(appId);
		redisCacheService.set(RedisConstant.getTempTokenCacheKey(token), expireTime, userId);
		return token;
	}

	@Override
	public String generateTempToken(String appId, String userId) {
		return generateTempToken(appId, TEMP_TOKEN_EXPIRE_TIME, userId);
	}

	private String generateToken(String id) {
		String tokenSource = id + System.nanoTime() + new Random().nextInt();
		return MD5Util.get(tokenSource);
	}

	@Override
	public User verifyLoginToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		String userId = redisCacheService.get(RedisConstant.getLoginTokenCacheKey(token));
		User user = null;
		if (ValidateUtil.isNumber(userId)) {
			user = userService.selectUserById(userId);
		}
		if (user != null) {
			return user;
		}
		user = verifyTempToken(token);
		if (user != null) {
			return user;
		}
		LoginToken param = new LoginToken();
		param.setToken(token);
		LoginToken loginToken = userDao.getLoginToken(param);
		if (loginToken != null) {
			return userService.selectUserById(loginToken.getUserId());
		}
		return null;
	}

	@Override
	public User verifyTempToken(String token) {
		String userId = redisCacheService.get(RedisConstant.getTempTokenCacheKey(token));
		if (ValidateUtil.isNumber(userId)) {
			return userService.selectUserById(userId);
		}
		return null;
	}

}
