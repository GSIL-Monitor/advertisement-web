package com.yuanshanbao.dsp.core.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yuanshanbao.common.constant.SpringContextConstants;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.redis.base.RedisService;

public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

	String sid = "";

	private RedisService sessionCacheService;
	private byte[] body;

	public HttpServletRequestWrapper(String sid, HttpServletRequest request) {
		super(request);
		this.sid = sid;
		this.sessionCacheService = SpringContextConstants.APPCTX.getBean(RedisService.class);
		try {
			this.body = toByteArray(request.getInputStream());
		} catch (IOException e) {
			LoggerUtil.error("[HttpServletRequestWrapper] toByteArray", e);
		}
	}

	public HttpSession getSession(boolean create) {
		return new HttpSessionSidWrapper(this.sid, sessionCacheService, super.getSession());
	}

	public HttpSession getSession() {
		return new HttpSessionSidWrapper(this.sid, sessionCacheService, super.getSession());
	}

	private byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}
}