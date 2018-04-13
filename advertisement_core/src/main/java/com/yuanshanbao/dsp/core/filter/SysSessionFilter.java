package com.yuanshanbao.dsp.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.constant.SpringContextConstants;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.http.HttpServletRequestWrapper;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.UserService;

/**
 * <p>
 * Store session to other place if necessary.
 * </p>
 * 
 * <p>
 * When system is large enough, we will consider store our session to
 * 
 * disstributed storage.
 * </p>
 * 
 * @author rnjin
 *
 */
@SuppressWarnings("serial")
public class SysSessionFilter extends HttpServlet implements Filter {

	private UserService userService;

	private RedisService redisService;

	private AppService appService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpServletRequestWrapper buildRequestWrapper = buildRequestWrapper(request, response);
		if (buildRequestWrapper != null) {
			filterChain.doFilter(buildRequestWrapper, servletResponse);
		}
	}

	private HttpServletRequestWrapper buildRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
		String sid = CookieUtils.getCookieValue(request, SessionConstants.COOKIE_SESSION_ID);
		HttpServletRequestWrapper requestWrapper = null;
		if (sid == null || sid.length() == 0) {
			sid = generateSid(request);
			requestWrapper = new HttpServletRequestWrapper(sid, request);

			CookieUtils.setSessionCookieValue(response, SessionConstants.COOKIE_SESSION_ID, sid);
			String pid = CookieUtils.getCookieValue(requestWrapper, SessionConstants.COOKIE_PERSIST_ID);
			User user = (User) requestWrapper.getSession().getAttribute(SessionConstants.SESSION_ACCOUNT);
			if (!StringUtils.isBlank(pid) && user == null) {
				if (redisService == null) {
					redisService = SpringContextConstants.APPCTX.getBean(RedisService.class);
				}
				if (userService == null) {
					userService = SpringContextConstants.APPCTX.getBean(UserService.class);
				}
				String userId = (String) redisService.get("pid-" + pid);
				if (userId != null) {
					user = userService.selectUserById(userId);
					if (user != null) {
						requestWrapper.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, user);
						LoggerUtil.loginInfo("type=pidLogin, userId=" + String.valueOf(user.getUserId()) + ", mobile="
								+ String.valueOf(user.getMobile()) + ", pid=" + pid);
					}
				}
			}
			if (user == null && !CommonUtil.isApp(request)) {
				if (RequestUtil.redirectToWeixin(request, response)) {
					return null;
				} else {
					return requestWrapper;
				}
			}
		} else {
			requestWrapper = new HttpServletRequestWrapper(sid, request);
		}
		return requestWrapper;
	}

	private String generateSid(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		try {
			if (StringUtils.isBlank(appId)) {
				appId = request.getHeader("appId");
			}
			if (StringUtils.isNotBlank(appId)) {
				if (appService == null) {
					appService = SpringContextConstants.APPCTX.getBean(AppService.class);
				}
				String applicationType = appService.getApplicationType(appId);
				if (StringUtils.isNotBlank(applicationType)
						&& !applicationType.startsWith(CommonConstant.APPLICATION_TYPE_WAP)) {
					return MD5Util.get(appId);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[generateSid]", e);
		}
		return CommonUtil.getRandomID();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		String cookieDomain = filterConfig.getInitParameter("cookieDomain");
		LoggerUtil.info("SysSessionFilter at domain: " + cookieDomain);
		if (!StringUtils.isBlank(cookieDomain)) {
			CookieUtils.COOKIE_DOMAIN = cookieDomain;
		}

		String cookiePath = filterConfig.getInitParameter("cookiePath");
		if (!StringUtils.isBlank(cookiePath)) {
			CookieUtils.COOKIE_PATH = cookiePath;
		}
	}
}
