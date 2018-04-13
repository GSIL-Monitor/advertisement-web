package com.yuanshanbao.ms.filter.security;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.model.admin.UserMonitorLog;
import com.yuanshanbao.ms.model.cache.UserLoginFailInfo;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.ms.service.cache.UserLoginFailCacheService;
import com.yuanshanbao.ms.service.monitor.UserMonitorLogService;
import com.yuanshanbao.ms.utils.UserLoginInfo;
import com.yuanshanbao.ms.utils.security.EntryptDecryptUtils;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.project.model.Project;
import com.yuanshanbao.dsp.project.service.ProjectService;

public class SysUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public static final String VALIDATE_CODE = "validateCode";

	public static final String USERNAME = "username";

	public static final String PASSWORD = "password";

	public static final int MAX_LOGIN_FAILCOUNT = 5;

	public static final String SPLIT = ";";

	private static Logger loginLogger = LoggerFactory.getLogger("login");

	// 授信IP地址列表，如办公室出口IP等
	protected static Map<String, String> secureIps;

	static {
		secureIps = new HashMap<String, String>();
		secureIps.put("61.135.255.88", "1");// 北京办公区IP
		secureIps.put("61.135.255.86", "1");
		secureIps.put("61.135.255.84", "1");
		secureIps.put("203.86.46.130", "1");
		secureIps.put("203.86.63.98", "1");
		secureIps.put("112.64.161.210", "1");// 上海办公区IP
		secureIps.put("112.64.161.211", "1");
		secureIps.put("114.113.197.131", "1");// 杭州办公区IP
		secureIps.put("114.113.197.132", "1");
		secureIps.put("123.58.191.67", "1");
		secureIps.put("123.58.191.68", "1");
		secureIps.put("223.252.194.10", "1");
		secureIps.put("218.107.55.254", "1");
	}

	@Autowired
	private AdminUserService userService;

	@Autowired
	private UserMonitorLogService userMonitorLogService;

	@Autowired
	private UserLoginFailCacheService ulfcService;

	@Autowired
	private ProjectService projectService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		long start = System.currentTimeMillis();
		String username = null;

		String password = null;

		User user = null;

		String requestMethod = request.getMethod();

		if ("GET".equals(requestMethod)) {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("uif".equals(cookie.getName())) {
						try {
							String loginCookieValue = EntryptDecryptUtils.aesDecrypt2Str(cookie.getValue());

							String[] loginCookieStrs = loginCookieValue.split(SPLIT);

							if (loginCookieStrs.length == 2 && StringUtils.isNumeric(loginCookieStrs[1])) {
								long lastActiveTime = Long.valueOf(loginCookieStrs[1]);

								if ((System.currentTimeMillis() - lastActiveTime) > (long) 4 * 60 * 60 * 1000) {
									throw new AuthenticationServiceException("您的请求已超时，请重新尝试登陆！");
								}

								UserLoginFailInfo ulfi = ulfcService.query(username);

								int count = 0;

								if (ulfi != null) {
									count = ulfi.getFailCount();

									if (count >= 5) {
										loginLogger.info("login faile->[username:" + username + ";userip:"
												+ request.getRemoteAddr() + ";type:login fail count error]");

										request.getSession().setAttribute("msg", "您今天登录错误次数过多，请联系管理员！");
										throw new AuthenticationServiceException("您今天登录错误次数过多，请联系管理员！");
									}
								}

								User query = new User();
								query.setUsername(username);
								query.setProjectId(ConstantsManager.getProjectId(projectService, request));
								List<User> users = userService.queryUsers(query, new PageBounds());
								if (users != null && users.size() > 0) {
									user = users.get(0);
								}

								if (user == null) {
									loginLogger.info("login faile->[username:" + username + ";userip:"
											+ request.getRemoteAddr() + ";type:not exist]");
									request.getSession().setAttribute("msg", "用户不存在！");
									throw new AuthenticationServiceException("用户不存在！");
								} else {
									username = user.getUsername();
									password = user.getPassword();
								}
							} else {
								request.getSession().setAttribute("msg", "用户名或密码错误！");
								throw new AuthenticationServiceException("用户名或密码错误！");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				request.getSession().setAttribute("msg", "用户名或密码错误！");
				throw new AuthenticationServiceException("用户名或密码错误！");
			}
			request.getSession().setAttribute("msg", "用户名或密码错误！");
			throw new AuthenticationServiceException("用户名或密码错误！");
		} else if ("POST".equals(requestMethod)) {
			username = obtainUsername(request);
			password = obtainPassword(request);

			int count = 0;

			UserLoginFailInfo ulfi = ulfcService.query(username);
			if (ulfi != null) {
				count = ulfi.getFailCount();

				if (count >= 20) {
					loginLogger.info("login faile->[username:" + username + ";userip:" + request.getRemoteAddr()
							+ ";type:login fail count error]");

					request.getSession().setAttribute("msg", "您今天登录错误次数过多，请联系管理员！");
					throw new AuthenticationServiceException("您今天登录错误次数过多，请联系管理员！");
				}
			}

			// 图片验证码校验
			// String checkcode = request.getParameter("checkcode");

			// if (!org.springframework.util.StringUtils.hasText(checkcode)) {
			// throw new AuthenticationServiceException("请输入图片验证码！");
			// }

			// Object checkcodeObj =
			// request.getSession().getAttribute(SystemConstants.SCODE_LOGIN);

			// if (checkcodeObj != null) {
			// String loginCheckcodeInSess = (String) checkcodeObj;
			//
			// String[] strs =
			// loginCheckcodeInSess.split(SystemConstants.SCODE_SPLIT_STR);
			//
			// if (strs.length == 2) {
			// long internal = System.currentTimeMillis() -
			// Long.valueOf(strs[1]);
			//
			// if (internal > 5 * 60 * 1000) {
			// request.getSession().removeAttribute(SystemConstants.SCODE_LOGIN);
			// throw new AuthenticationServiceException("您的请求已超时，请重新尝试登陆！");
			// } else {
			// if (!checkcode.equals(strs[0])) {
			// request.getSession().removeAttribute(SystemConstants.SCODE_LOGIN);
			// throw new AuthenticationServiceException("请输入正确的图片验证码！");
			// }

			// request.getSession().removeAttribute(SystemConstants.SCODE_LOGIN);
			// }
			// }
			// } else {
			// throw new AuthenticationServiceException("您的请求已超时，请重新尝试登陆！");
			// }

			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			// 验证用户账号与密码是否对应
			username = username.trim();

			User query = new User();
			query.setUsername(username);
			List<User> users = userService.queryUsers(query, new PageBounds());
			if (users != null && users.size() > 0) {
				user = users.get(0);
			}

			if (user == null) {
				loginLogger.info("login faile->[username:" + username + ";userip:" + request.getRemoteAddr()
						+ ";type:not exist]");
				request.getSession().setAttribute("msg", "用户名或密码错误！");
				throw new AuthenticationServiceException("用户名或密码错误！");
			} else if (!user.getPassword().equals(encoder.encodePassword(password, null))) {
				loginLogger.info("login faile->[username:" + username + ";userip:" + request.getRemoteAddr()
						+ ";type:password error]");
				if (ulfi == null) {
					ulfi = new UserLoginFailInfo();
				}
				ulfi.setFailCount(count + 1);
				ulfcService.update(username, ulfi);
				request.getSession().setAttribute("msg", "用户名或密码错误！");
				throw new AuthenticationServiceException("用户名或密码错误！");
			}

			password = encoder.encodePassword(password, null);
		}

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 用户行为跟踪
		user.setLogin_count(user.getLogin_count() + 1);
		Timestamp old = user.getLast_chgpwd_time();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int leftDays = user.getPwd_keep_time() - UserLoginInfo.getDays(now.getTime() - old.getTime());
		leftDays = leftDays > 0 ? leftDays : 0;
		String ip = UserLoginInfo.getIpAddr(request);
		request.getSession().setAttribute("leftDays", leftDays);
		request.getSession().setAttribute("lastLogin", user.getLast_time());
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("ip", ip);
		user.setLast_time(now);
		userService.updateUserLoginInfo(user);

		loginLogger.info(user.getUsername() + " login success.");

		UserMonitorLog userMonitorLog = new UserMonitorLog();

		userMonitorLog.setUserName(user.getUsername());
		userMonitorLog.setOperateTime(new Date());
		userMonitorLog.setOperateType("login");
		userMonitorLog.setOperateIp(ip);

		userMonitorLogService.insertUserMonitorLog(userMonitorLog);
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}

}