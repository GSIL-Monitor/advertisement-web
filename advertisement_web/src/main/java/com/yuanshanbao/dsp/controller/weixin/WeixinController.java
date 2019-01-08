package com.yuanshanbao.dsp.controller.weixin;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.HttpsUtil;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.service.AgencyService;
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

	@Autowired
	private AgencyService agencyService;

	@RequestMapping("/auth")
	public String auth(HttpServletRequest request, String returnUrl, String inviteUserId, String productId)
			throws UnsupportedEncodingException {
		try {
			String host = request.getHeader("Host");
			String redirectUrl = "https://" + host + "/i/weixin/oauth/login?returnUrl="
					+ URLEncoder.encode(returnUrl, "utf-8") + "&inviteUserId=" + inviteUserId + "&productId="
					+ productId;
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
			String productId, String inviteUserId, HttpServletResponse response) {
		try {
			User sessionUser = (User) request.getSession().getAttribute(SessionConstants.SESSION_USER);

			String avatar = null;
			GetUserInfoResponse userInfo = null;
			String headimgurl = null;
			String nickname = null;
			OauthGetTokenResponse token = weixinService.getTokenResponse(code);
			LoggerUtil.info("[Weixin login code=]" + code);
			// String h5AccessToken = HttpsUtil.doGet(
			// "https://api.weixin.qq.com/sns/oauth2/access_token",
			// "appid=" + weixinService.getAppId(WeixinService.CONFIG_SERVICE) +
			// "&secret="
			// + weixinService.getAppSecret(WeixinService.CONFIG_SERVICE) +
			// "&code=" + code
			// + "&grant_type=authorization_code", "UTF-8", 30000, 30000);
			// LoggerUtil.info("[Weixin login h5AccessToken =]" +
			// h5AccessToken);
			// GetUserInfoResponse userInfo =
			// weixinService.getUserInfo("16_Vg-cwKjMjlJi9atX4vGhtFKYBj_MynNvFBT2",
			// "o-9_s0VyIKePYPsYzzDi3WuchzBA");
			// GetUserInfoResponse userInfo =
			// weixinService.getUserInfo("o-9_s0VyIKePYPsYzzDi3WuchzBA");
			String openId = token.getOpenid();
			if (StringUtils.isNotBlank(openId)) {
				userInfo = weixinService.getUserInfo(token.getOpenid());
			}
			LoggerUtil.info("[Weixin login userInfo.getHeadimgurl=]" + userInfo.getHeadimgurl());
			if (userInfo != null) {
				if (StringUtils.isNotBlank(userInfo.getHeadimgurl())) {
					URL url = new URL(userInfo.getHeadimgurl());
					URLConnection con = url.openConnection();
					InputStream headimgIs = con.getInputStream();
					avatar = UploadUtils.uploadBytes(headimgIs, headimgIs.available(),
							"test/image/avatar" + System.nanoTime() + (int) (Math.random() * 10000) + ".png");
				} else {
					String info = HttpsUtil.doGet("https://api.weixin.qq.com/sns/userinfo",
							"access_token=" + token.getAccessToken() + "&openid=" + token.getOpenid() + "&lang=zh_CN",
							"UTF-8", 30000, 30000);
					if (StringUtils.isNotBlank(info)) {
						JSONObject jsonObject = JSONObject.fromObject(info);
						if (jsonObject != null) {
							headimgurl = (String) jsonObject.get("headimgurl");
							nickname = (String) jsonObject.get("nickname");
						}
						LoggerUtil.info("[Weixin login headimgurl===]" + headimgurl);
						LoggerUtil.info("[Weixin login nickname=====]" + nickname);

						if (StringUtils.isNotBlank(headimgurl)) {
							URL url = new URL(headimgurl);
							URLConnection con = url.openConnection();
							InputStream headimgIs = con.getInputStream();
							avatar = UploadUtils.uploadBytes(headimgIs, headimgIs.available(), "test/image/avatar"
									+ System.nanoTime() + (int) (Math.random() * 10000) + ".png");
						}
					}
				}

				LoggerUtil.info("[Weixin login avatar=]" + avatar);

			}

			LoggerUtil.info("[Weixin login userInfo=]" + userInfo);
			LoggerUtil.info("[Weixin login token=]" + token);
			LoggerUtil.info("[Weixin login getAccessToken=]" + token.getAccessToken());
			LoggerUtil.info("[Weixin login getOpenid=]" + token.getOpenid());

			String unionId = token.getUnionid();
			// String unionId = "ollV61EDX3GEfpoaiBqgOVghh7Og";
			LoggerUtil.info("[Weixin login unionId=]" + unionId);
			if (sessionUser != null && StringUtils.isNotBlank(unionId)) {
				User sUser = new User();
				sUser.setUserId(sessionUser.getUserId());
				if (StringUtils.isNotBlank(avatar) && !("undefined".equals(avatar))) {
					sUser.setAvatar(avatar);
				}
				if (StringUtils.isNotBlank(sessionUser.getNickName())
						&& !("undefined".equals(sessionUser.getNickName()))) {
					sUser.setNickName(sessionUser.getNickName());
				} else {
					if (StringUtils.isNotBlank(nickname)) {
						sUser.setNickName(nickname);
					}
				}
				sUser.setWeixinId(unionId);
				userService.updateUser(sUser);
				request.getSession().setAttribute(SessionConstants.SESSION_USER, sessionUser);
				LoginToken loginToken = tokenService.generateLoginToken(WeixinService.CONFIG_SERVICE,
						String.valueOf(sessionUser.getUserId()), JSPHelper.getRemoteAddr(request));
				request.getSession().setAttribute(SessionConstants.SESSION_TOKEN, loginToken.getToken());
				LoggerUtil.info("[Weixin login sessionUser !=SESSION_TOKEN == ]" + loginToken.getToken());

				// LoggerUtil.info("[Weixin code=]" + code +
				// "[Weixin token=]" + token.toJsonString());
			} else if (StringUtils.isNotBlank(unionId)) {
				User account = userService.selectUserByWeixinId(unionId);
				if (account == null) {
					account = new User();
					account.setWeixinId(unionId);
					account.setStatus(UserStatus.NORMAL);
					account.setLevel(UserLevel.MANAGER);
					if (userInfo != null) {
						if (StringUtils.isNotBlank(userInfo.getNickname())) {
							account.setNickName(userInfo.getNickname());
						} else {
							account.setNickName(nickname);
						}
					}
					if (StringUtils.isNotBlank(avatar)) {
						account.setAvatar(avatar);
					}
					if (StringUtils.isNotBlank(inviteUserId) && ValidateUtil.isNumber(inviteUserId)) {
						account.setInviteUserId(Long.valueOf(inviteUserId));
					}
					userService.insertUser(account);
					User wxUser = userService.selectUserByWeixinId(unionId);
					Agency agency = new Agency();
					if (wxUser != null && inviteUserId != null) {
						agency.setUserId(wxUser.getUserId());
						if (!StringUtils.isBlank(wxUser.getNickName()) && !("undefined".equals(wxUser.getNickName()))) {
							agency.setAgencyName(wxUser.getNickName());
						} else {
							if (StringUtils.isNotBlank(nickname)) {
								agency.setAgencyName(nickname);
							}
						}
						if (ValidateUtil.isNumber(inviteUserId)) {
							agency.setInviteUserId(Long.valueOf(inviteUserId));
						}
						agencyService.insertAgency(agency);
					}
				} else {
					if (StringUtils.isNotBlank(avatar)) {
						if (StringUtils.isNotBlank(avatar)) {
							account.setAvatar(avatar);
						}
						if (StringUtils.isNotBlank(userInfo.getNickname())) {
							account.setNickName(userInfo.getNickname());
						} else {
							if (StringUtils.isNotBlank(nickname)) {
								account.setNickName(nickname);
							}
						}
						userService.updateUser(account);
					}
				}
				LoginToken loginToken = tokenService.generateLoginToken(WeixinService.CONFIG_SERVICE,
						String.valueOf(account.getUserId()), JSPHelper.getRemoteAddr(request));
				loginToken.setUser(account);
				request.getSession().setAttribute(SessionConstants.SESSION_USER, sessionUser);

				request.getSession().setAttribute(SessionConstants.SESSION_TOKEN, loginToken.getToken());
				LoggerUtil.info("[Weixin login : SESSION_TOKEN == ]" + loginToken.getToken());

				LoggerUtil.info("type=weixinLogin, userId=" + String.valueOf(account.getUserId()) + ", mobile="
						+ String.valueOf(account.getMobile()));

				request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_CHECK, "true");
				request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN, unionId);
				if (StringUtils.isNotBlank(domainToken)) {
					redisCacheService.set(domainToken, unionId);
				}
				// request.getSession().setAttribute("token", token);
			} else {
				throw new BusinessException(ComRetCode.WEIXIN_LOGIN_FAIL);
				//
				// Object weixinTry =
				// request.getSession().getAttribute(SessionConstants.SESSION_WEIXIN_TRY);
				// LoggerUtil.info("[Weixin login weixinTry=]" + weixinTry);
				//
				// if (weixinTry != null && weixinTry.equals("true")) {
				// request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_CHECK,
				// "true");
				// }
				// // LoggerUtil.info("[Weixin login code=]" + code + "token" +
				// // token);
				// request.getSession().setAttribute(SessionConstants.SESSION_WEIXIN_TRY,
				// "true");
			}
			if (StringUtils.isNotBlank(productId) && ValidateUtil.isNumber(productId)) {
				return "redirect:" + returnUrl + "?fromPage=goods&scene=scanning&productId=" + productId;
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
