package com.yuanshanbao.ad.user.model;

import java.sql.Timestamp;

public class LoginToken {

	private String userId;
	private String appId;
	private String token;
	private Timestamp loginTime;
	private String loginIp;
	private UserVo user;
	private boolean register;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = new UserVo(user);
	}

	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

}
