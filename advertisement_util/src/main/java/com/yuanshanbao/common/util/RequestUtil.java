package com.yuanshanbao.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class RequestUtil {
	/**
	 * 获得用户最初ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static final String getRemoteAddr(HttpServletRequest request) {

		String rip = request.getHeader("RemoteAddr");
		if (StringUtils.isBlank(rip)) {
			rip = request.getRemoteAddr();
		}
		try {
			String xff = request.getHeader("X-Forwarded-For");
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("Proxy-Client-IP");
			}
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("WL-Proxy-Client-IP");
			}
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("HTTP_CLIENT_IP");
			}
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			String ip;
			if (xff != null && xff.length() != 0) {
				int px = xff.indexOf(',');
				if (px != -1) {
					ip = xff.substring(0, px);
				} else {
					ip = xff;
				}
			} else {
				ip = rip;
			}
			return ip.trim();
		} catch (Exception e) {
			LoggerUtil.error("[getRemoteAddr]", e);
		}
		return rip.trim();
	}

	public static final boolean isFromMobile(HttpServletRequest request) {
		try {
			String userAgent = request.getHeader("user-agent").toLowerCase();
			return check(userAgent);
		} catch (Exception e) {
			return true;
		}
	}

	static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
			+ "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
			+ "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	// 移动设备正则匹配：手机端、平板
	static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 检测是否是移动设备访问
	 * 
	 * @Title: check
	 * @Date : 2014-7-7 下午01:29:07
	 * @param userAgent
	 *            浏览器标识
	 * @return true:移动设备接入，false:pc端接入
	 */
	public static boolean check(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取请求参数map，并将原始parameterMap的value的类型String[]转换为String
	 * 
	 * @param request
	 *
	 * @return
	 */
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Map rawMap = request.getParameterMap();
		@SuppressWarnings("unchecked")
		Iterator<String> it = rawMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object valueObj = rawMap.get(key);
			if (valueObj != null) {
				String value = "";
				if (valueObj instanceof String[]) {
					for (String v : (String[]) valueObj) {
						value = value + v + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = (String) valueObj;
				}
				retMap.put(key, value);
			}
		}

		return retMap;
	}

	public static String getEncodeStr(String content) {
		try {
			return URLEncoder.encode(content, "UTF-8");
		} catch (Exception e) {
			System.err.println("encodeStrError: " + content + " " + e.toString());
			return content;
		}
	}

	public static String getRequestUrl(HttpServletRequest request) {
		String requestUrl = request.getRequestURI() + "?";
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value = request.getParameter(key);
			requestUrl += key + "=" + value + "&";
		}
		return requestUrl.substring(0, requestUrl.length() - 1);
	}

	public static String getCompleteRequestUrl(HttpServletRequest request) {
		String host = request.getHeader("Host");
		return "http://" + host + getRequestUrl(request);
	}

	public static boolean redirectToWeixin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String weixinId = (String) request.getSession().getAttribute("session_weixin");
			String weixinChecked = (String) request.getSession().getAttribute("session_weixin_check");
			if (isFromWeixin(request) && StringUtils.isBlank(weixinId) && StringUtils.isBlank(weixinChecked)) {
				// String active = System.getProperty("spring.profiles.active");
				String requestURI = request.getRequestURI();
				if (requestURI.contains("/weixin/oauth")) {
					return false;
				}
				String url = RequestUtil.getRequestUrl(request);
				// if ("dev1".equals(active)) {
				// response.sendRedirect("/weixin/oauth/devLogin.html?returnUrl="
				// + URLEncoder.encode(url, "utf-8"));
				// return null;
				// }
				response.sendRedirect("/weixin/oauth/auth.html?returnUrl=" + URLEncoder.encode(url, "utf-8"));
				return true;
			}
		} catch (Exception e) {
			LoggerUtil.error("[Weixin Auth]", e);
		}
		return false;
	}

	public static boolean isFromWeixin(HttpServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("user-agent");
		if (StringUtils.isBlank(header)) {
			return false;
		}
		String ua = header.toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		}
		return false;
	}

	public static boolean isFromIOS(HttpServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("user-agent");
		if (StringUtils.isBlank(header)) {
			return false;
		}
		String ua = header.toLowerCase();
		if (ua.indexOf("iphone") > 0 || ua.indexOf("ipad") > 0 || ua.indexOf("ipod") > 0 || ua.indexOf("mac") > 0) {
			return true;
		}
		return false;
	}

	public static String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (StringUtils.isBlank(token)) {
			token = request.getHeader("token");
		}
		return token;
	}

	public static boolean isFromLanchouApp(HttpServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("user-agent");
		if (StringUtils.isBlank(header)) {
			return false;
		}
		String ua = header.toLowerCase();
		if (ua.indexOf("lanchou") > 0) {
			return true;
		}
		return false;
	}

	public static String getPostData(HttpServletRequest request) {
		InputStream is = null;
		try {
			is = request.getInputStream();
			if (is == null) {
				return null;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			// 读取HTTP请求内容
			String buffer = null;
			StringBuffer sb = new StringBuffer();
			while ((buffer = br.readLine()) != null) {
				sb.append(buffer);
			}
			return sb.toString();
		} catch (IOException e) {
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String getFirstLevelHost(HttpServletRequest request) {
		String host = request.getHeader("Host");
		if (StringUtils.isNotBlank(host)) {
			if ("dev".equals(CommonUtil.getEnvironment())) {
				return host;
			}
			String[] segs = host.split("\\.");
			if (segs.length > 1) {
				return segs[segs.length - 2] + "." + segs[segs.length - 1];
			}
		}
		return null;
	}
}
