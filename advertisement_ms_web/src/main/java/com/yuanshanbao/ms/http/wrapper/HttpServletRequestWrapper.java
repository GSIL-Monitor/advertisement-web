package com.yuanshanbao.ms.http.wrapper;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.constant.SpringContextConstants;
import com.yuanshanbao.ms.service.cache.SessionCacheService;

public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

	String sid = "";

	private SessionCacheService sessionCacheService;

	public HttpServletRequestWrapper(String sid, HttpServletRequest request) {
		super(request);
		this.sid = sid;
		this.sessionCacheService = SpringContextConstants.APPCTX.getBean(SessionCacheService.class);
	}

	public HttpSession getSession(boolean create) {
		return new HttpSessionSidWrapper(this.sid, sessionCacheService, null);
	}

	public HttpSession getSession() {
		return new HttpSessionSidWrapper(this.sid, sessionCacheService, null);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}
		String[] result = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = values[i];
			if (!StringUtils.isBlank(values[i])) {
				try {
					result[i] = URLDecoder.decode(values[i], "UTF-8");
				} catch (Exception e) {
				}
			}
		}
		return result;
	}
}