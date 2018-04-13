package com.yuanshanbao.dsp.core.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yuanshanbao.common.constant.SpringContextConstants;
import com.yuanshanbao.dsp.common.redis.base.RedisService;

public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

	String sid = "";

	private RedisService sessionCacheService;

	public HttpServletRequestWrapper(String sid, HttpServletRequest request) {
		super(request);
		this.sid = sid;
		this.sessionCacheService = SpringContextConstants.APPCTX.getBean(RedisService.class);
	}

	public HttpSession getSession(boolean create) {
		return new HttpSessionSidWrapper(this.sid, sessionCacheService, super.getSession());
	}

	public HttpSession getSession() {
		return new HttpSessionSidWrapper(this.sid, sessionCacheService, super.getSession());
	}
}