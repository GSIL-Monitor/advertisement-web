package com.yuanshanbao.dsp.controller.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.user.model.BaseInfo;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.model.UserStatus;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.dsp.weixin.service.WeixinService;

@Controller
@RequestMapping("/i/weixin/oauth")
public class WeixinController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	protected RedisService redisCacheService;

	@Autowired
	private TokenService tokenService;

	@RequestMapping("/auth")
	public String auth(HttpServletRequest request, String returnUrl) throws UnsupportedEncodingException {
		try {
			String host = request.getHeader("Host");
			String redirectUrl = "https://" + host + "/i/weixin/oauth/login?returnUrl="
					+ URLEncoder.encode(returnUrl, "utf-8");
			String aString = weixinService.getUserInfoRedirectUrl(redirectUrl);
			LoggerUtil.info("[Weixin auth redirectUrl=]" + redirectUrl);
			LoggerUtil.info("[Weixin auth url=]" + aString);
			return "redirect:" + weixinService.getUserInfoRedirectUrl(redirectUrl);
		} catch (Exception e) {
			logger.error("[Weixin auth error]", e);
		}
		return null;
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, String returnUrl, String code, String domainToken,
			HttpServletResponse response) {
		try {
			User sessionUser = getSessionUser(request);

			OauthGetTokenResponse token = weixinService.getTokenResponse(code);
			LoggerUtil.info("[Weixin login token=]" + token);
			if (StringUtils.isNotBlank(code) && token != null) {
				String unionId = token.getUnionid();
				if (sessionUser != null && StringUtils.isNotBlank(unionId)) {
					sessionUser.setWeixinId(unionId);
					userService.updateUser(sessionUser);
					request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, sessionUser);
					LoggerUtil.info("[Weixin code=]" + code + "[Weixin token=]" + token);
				} else if (StringUtils.isNotBlank(unionId)) {
					User account = userService.selectUserByWeixinId(unionId);
					if (account != null) {
						LoginToken loginToken = tokenService.generateLoginToken(WeixinService.CONFIG_SERVICE,
								String.valueOf(account.getUserId()), JSPHelper.getRemoteAddr(request));
						loginToken.setUser(account);
						request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, account);
						request.getSession().setAttribute("loginToken", loginToken);
						LoggerUtil.info("[Weixin loginToken=]" + loginToken);
						CookieUtils.setSessionCookieValue(response, String.valueOf(account.getUserId()),
								loginToken.getToken());
						LoggerUtil.loginInfo("type=weixinLogin, userId=" + String.valueOf(account.getUserId())
								+ ", mobile=" + String.valueOf(account.getMobile()));
					} else {
						User user = new User();
						if (StringUtils.isNotBlank(unionId)) {
							user.setWeixinId(unionId);
						}
						user.setStatus(UserStatus.NORMAL);
						user.setLevel(UserLevel.MANAGER);
						// if (inviteUserId != null) {
						// user.setInviteUserId(Long.valueOf(inviteUserId));
						// }
						userService.insertUser(user);
					}
				}

				request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_CHECK, "true");
				request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN, unionId);
				if (StringUtils.isNotBlank(domainToken)) {
					redisCacheService.set(domainToken, unionId);
				}
				request.getSession().setAttribute("token", token);
			} else {
				Object weixinTry = request.getSession().getAttribute(SessionConstants.SESSION_WEIXIN_TRY);
				LoggerUtil.info("[Weixin login weixinTry=]" + weixinTry);

				if (weixinTry != null && weixinTry.equals("true")) {
					request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_CHECK, "true");
				}
				LoggerUtil.info("[Weixin login code=]" + code + "token" + token);
				request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_TRY, "true");
			}
			return "redirect:" + returnUrl;
		} catch (Exception e) {
			LoggerUtil.error("[Weixin login error]", e);
		}
		return "";
	}

	private void getUserInfo(HttpServletRequest request, User sessionUser, OauthGetTokenResponse tokenResponse) {
		try {
			LoggerUtil.info("[Weixin Login] getUserInfo");
			GetUserInfoResponse userInfo = weixinService.getUserInfo(tokenResponse.getAccessToken(),
					tokenResponse.getOpenid());
			LoggerUtil.info("[Weixin Login] userInfo=" + userInfo.toJsonString());
			BaseInfo baseInfo = new BaseInfo();
			baseInfo.setAvatar(userInfo.getHeadimgurl());
			baseInfo.setName(userInfo.getNickname());
			Integer sex = userInfo.getSex();
			if (sex != null && sex != 0) {
				baseInfo.setGender((long) sex);
			}
			baseInfo.setUserId(String.valueOf(sessionUser.getUserId()));
			userService.insertOrUpdateBaseInfo(baseInfo);
			sessionUser = userService.selectUserById(sessionUser.getUserId());
			request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, sessionUser);
		} catch (Exception e) {
			LoggerUtil.error("GetUserInfo", e);
		}
	}

	@RequestMapping("/verifyLogin")
	public Object verifyLogin(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SessionConstants.SESSION_ACCOUNT);
		if (user == null) {
			return null;
		}
		return user;
	}

	@RequestMapping("/devLogin")
	public String devLogin(HttpServletRequest request, String returnUrl) {
		String openId = "4567";
		User account = userService.selectUserByWeixinId(openId);
		if (account != null) {
			request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, account);
			LoggerUtil.loginInfo("type=weixinLogin, userId=" + String.valueOf(account.getUserId()) + ", mobile="
					+ String.valueOf(account.getMobile()));
		}
		request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_CHECK, "true");
		request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN, openId);
		return "redirect:" + returnUrl;
	}
}
