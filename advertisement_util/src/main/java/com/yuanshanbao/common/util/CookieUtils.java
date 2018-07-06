package com.yuanshanbao.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static String COOKIE_DOMAIN = "ms.51yuanshan.com";

	public static String COOKIE_PATH = "/";

	public static int SESSION_EXPIRE_TIME = -1;

	public static int PERSIST_EXPIRE_TIME = 30 * 24 * 3600;

	public static int STATISTICS_EXPIRE_TIME = 24 * 3600;

	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;

		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie.getName().equals(key)) {
					return sCookie.getValue();
				}
			}
		}
		return null;
	}

	public static void setCookieValue(HttpServletResponse response, String cookieDomain, String key, String value,
			int expire) {
		Cookie sessionCookie = new Cookie(key, value);
		sessionCookie.setMaxAge(expire);
		if (cookieDomain != null && cookieDomain.length() > 0) {
			sessionCookie.setDomain(cookieDomain);
		}
		sessionCookie.setPath(COOKIE_PATH);
		response.addCookie(sessionCookie);
	}

	public static void setSessionCookieValue(HttpServletResponse response, String key, String value) {
		String[] segs = COOKIE_DOMAIN.split(",");
		for (String domain : segs) {
			setCookieValue(response, domain, key, value, SESSION_EXPIRE_TIME);
		}
	}

	public static void setPersistCookieValue(HttpServletResponse response, String key, String value) {
		String[] segs = COOKIE_DOMAIN.split(",");
		for (String domain : segs) {
			setCookieValue(response, domain, key, value, PERSIST_EXPIRE_TIME);
		}
	}

	public static void setStatisticsCookieValue(HttpServletResponse response, String key, String value) {
		String[] segs = COOKIE_DOMAIN.split(",");
		for (String domain : segs) {
			setCookieValue(response, domain, key, value, STATISTICS_EXPIRE_TIME);
		}
	}

	public static void setPersistCookieValue(HttpServletResponse response, String key, String value, int expireTime) {
		String[] segs = COOKIE_DOMAIN.split(",");
		for (String domain : segs) {
			setCookieValue(response, domain, key, value, expireTime);
		}
	}
}
