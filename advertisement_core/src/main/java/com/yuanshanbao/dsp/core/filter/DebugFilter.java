package com.yuanshanbao.dsp.core.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.SecurityUtil;
import com.yuanshanbao.dsp.user.model.User;

public class DebugFilter implements Filter {

	private static Set<String> paraMD5 = new HashSet<String>(); // 需要md5的参数

	static {
		paraMD5.add("jysPass");
		paraMD5.add("oldPass");
		paraMD5.add("newPass");
		paraMD5.add("rePass");

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		accessDebugLog(request);// 记录access_log
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 记录访问sessionId,userip,url及参数，referer,userAgent等信息
	 * 
	 * @param request
	 */
	private static void accessDebugLog(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer(256);
		String sessionId = request.getSession().getId();
		sb.append("sessionID=").append(sessionId).append(", ");
		String sid = CookieUtils.getCookieValue(request, "sid");
		String pid = CookieUtils.getCookieValue(request, "pid");
		sb.append("sid=" + sid + ", ");
		sb.append("pid=" + pid + ", ");
		User user = (User) request.getSession().getAttribute("session_account");
		if (user != null) {
			sb.append("userId=" + user.getUserId() + ", ");
			sb.append("mobile=" + user.getMobile() + ", ");
		}
		sb.append("ip=" + JSPHelper.getRemoteAddr(request)).append(", ");
		sb.append("url=");
		sb.append("http://");
		sb.append(request.getHeader("Host"));
		sb.append(request.getRequestURI());
		sb.append("?");
		String name = request.getParameter("name");
		Iterator<String> iterator = request.getParameterMap().keySet().iterator();
		String paramname = "";
		while (iterator.hasNext()) {
			paramname = iterator.next();
			String value = request.getParameter(paramname);
			String md5Value;// 不做任何特殊处理的值
			if (StringUtils.isBlank(value)) {
				continue;
			}
			sb.append(paramname);
			sb.append("=");
			// 是否需要md5
			if (paraMD5.contains(paramname)) {// 密码
				if (value.length() == 32) {// 已经是MD5加密后的密码
					md5Value = SecurityUtil.getMd5(value);
					sb.append(md5Value);
				} else {// 明文密码
					md5Value = SecurityUtil.getMd5(SecurityUtil.getMd5(value));
					sb.append(md5Value);
				}
			} else {
				sb.append(value);
			}
			sb.append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(", ");
		String referer = request.getHeader("Referer");
		String userAgent = request.getHeader("User-Agent");
		String xff = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(referer)) {
			referer = "null";
		}
		if (StringUtils.isBlank(userAgent)) {
			userAgent = "null";
		}
		if (StringUtils.isBlank(xff)) {
			xff = "null";
		}
		sb.append("referer=").append(referer.replaceAll("\r|\n| ", "")).append(", userAgent=").append(userAgent)
				.append(", xff=").append(xff);
		// 获取请求的json
		String header = request.getHeader("Content-Type");
		if (header != null && header.contains("json")) {
			try {
				sb.append(", json=");
				// sb.append(getRequestJsonString(request));
			} catch (Exception e) {
				LoggerUtil.error("access json error", e);
			}
		}
		LoggerUtil.access(sb.toString());
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}

	public static String getRequestJsonString(HttpServletRequest request) throws IOException {
		String submitMehtod = request.getMethod();
		// GET
		if (submitMehtod.equals("GET")) {
			return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
			// POST
		} else {
			return getRequestPostStr(request);
		}
	}

	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}

	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

}
