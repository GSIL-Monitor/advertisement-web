package com.yuanshanbao.dsp.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.DataFormat;
import com.yuanshanbao.common.util.HttpsUtil;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.common.util.RandomUtil;
import com.yuanshanbao.common.util.StringUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.common.util.VerifyFormatUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyType;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.core.http.HttpServletRequestWrapper;
import com.yuanshanbao.dsp.sms.service.VerifyCodeService;
import com.yuanshanbao.dsp.user.model.BaseInfo;
import com.yuanshanbao.dsp.user.model.InviteCode;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.model.UserStatus;
import com.yuanshanbao.dsp.user.model.UserVo;
import com.yuanshanbao.dsp.user.service.AESUtils;
import com.yuanshanbao.dsp.user.service.InviteCodeService;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.dsp.weixin.service.WeixinService;

@Controller
@RequestMapping({ "/i/user", "/m/user" })
public class UserController extends BaseController {

	public static String VERIFY_CODE = "SMS_33485108";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AppService appService;

	@Autowired
	private UserService userService;

	@Autowired
	private VerifyCodeService smsCodeService;

	@Autowired
	private InviteCodeService inviteCodeService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private RedisService redisService;

	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, String appId,
			String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String username = parameterMap.get("mobile");
			String password = parameterMap.get("password");
			// 4.校验短信
			try {
				if (!VerifyFormatUtil.verifyPasswdFormat(password)) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PASSOWRD);
					return resultMap;
				}
				LoginToken loginToken = userService.appLogin(appId, username, password,
						JSPHelper.getRemoteAddr(request));
				if (loginToken == null) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.LOGIN_FAIL);
					return resultMap;
				}
				User user = userService.selectUserByMobile(username);
				if (user.getStatus() != null && user.getStatus() == UserStatus.LOCK) {
					throw new BusinessException(ComRetCode.USER_LOCKED);
				}
				setSession(request, loginToken.getToken(), user);
				CookieUtils.setSessionCookieValue(response, SessionConstants.COOKIE_SESSION_ID, loginToken.getToken());
				resultMap.put("loginToken", loginToken);
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			} catch (BusinessException e) {
				InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
				return resultMap;
			}
		} catch (Exception e) {
			LoggerUtil.error("[login] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	private void setSession(HttpServletRequest request, String token, User user) {
		request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, user);
		HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(token, request);
		requestWrapper.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, user);
	}

	@ResponseBody
	@RequestMapping("/tokenLogin")
	public Map<String, Object> tokenLogin(HttpServletRequest request, HttpServletResponse response, String appId,
			String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String token = parameterMap.get("token");
			User user = getSessionUser(request);
			if (user == null) {
				user = tokenService.verifyLoginToken(token);
				if (user != null) {
					if (user.getStatus() != null && user.getStatus() == UserStatus.LOCK) {
						throw new BusinessException(ComRetCode.USER_LOCKED);
					}
					setSession(request, token, user);
				} else {
					throw new BusinessException(ComRetCode.NOT_LOGIN);
				}
			}
			LoginToken loginToken = new LoginToken();
			loginToken.setToken(token);
			loginToken.setUser(user);
			resultMap.put("loginToken", loginToken);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[tokenLogin] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/smsLogin")
	public Map<String, Object> smsLogin(HttpServletRequest request, HttpServletResponse response, String appId,
			String params, String token) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String mobile = parameterMap.get("mobile");
			String smsCode = parameterMap.get("smsCode");
			String inviteCode = parameterMap.get("inviteCode");
			String registerFrom = parameterMap.get("from");
			String userIp = JSPHelper.getRemoteAddr(request);
			// 4.校验短信
			try {
				smsCodeService.validateSmsCode(mobile, smsCode, "", userIp);
				User user = userService.selectUserByMobile(mobile);
				User tokenUser = differentiateTokenUser(request, token);
				LoginToken loginToken = null;
				if (user != null) {
					if (user.getStatus() != null && user.getStatus() == UserStatus.LOCK) {
						throw new BusinessException(ComRetCode.USER_LOCKED);
					}
					Boolean UserBoolean = userService.changeMobile(tokenUser, user, mobile);
					if (UserBoolean) {
						user = tokenUser;
					}
				} else if (tokenUser != null) {
					tokenUser.setMobile(mobile);
					user = tokenUser;
					userService.updateUser(tokenUser);
				} else {
					user = new User();
					user.setMobile(mobile);
					user.setRegisterFrom(registerFrom);
					user.setStatus(UserStatus.NORMAL);
					user.setLevel(UserLevel.MANAGER);
					String password = RandomUtil.generateNumberString(8);
					generateUser(user, password, inviteCode);
				}
				loginToken = tokenService.generateLoginToken(appId, user.getUserId() + "", userIp);
				if (loginToken == null) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.LOGIN_FAIL);
					return resultMap;
				}
				loginToken.setUser(user);
				setSession(request, loginToken.getToken(), user);
				// CookieUtils.setSessionCookieValue(response,
				// SessionConstants.COOKIE_SESSION_ID, loginToken.getToken());
				resultMap.put("loginToken", loginToken);
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			} catch (BusinessException e) {
				InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
				return resultMap;
			}
		} catch (Exception e) {
			LoggerUtil.error("[login] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/weixinLogin")
	public Map<String, Object> weixinLogin(HttpServletRequest request, HttpServletResponse response, String appId,
			String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String code = parameterMap.get("code");
			String from = parameterMap.get("from");

			OauthGetTokenResponse tokenResponse = weixinService.getTokenResponse(WeixinService.CONFIG_APP, code);
			if (tokenResponse == null) {
				throw new BusinessException(ComRetCode.WEIXIN_LOGIN_FAIL);
			}
			String unionId = tokenResponse.getUnionid();
			boolean register = false;
			User user = userService.selectUserByWeixinId(unionId);
			if (user == null) {
				user = new User();
				user.setWeixinId(unionId);
				user.setRegisterFrom(from);
				user.setStatus(UserStatus.NORMAL);
				userService.insertUser(user);
				GetUserInfoResponse userInfo = weixinService.getUserInfo(WeixinService.CONFIG_APP,
						tokenResponse.getAccessToken(), unionId);
				user = userService.insertBaseInfoFromWeixin(request, user, userInfo);
				register = true;
			}
			LoginToken loginToken = tokenService.generateLoginToken(appId, user.getUserId() + "",
					JSPHelper.getRemoteAddr(request));
			loginToken.setRegister(register);
			loginToken.setUser(user);
			setSession(request, loginToken.getToken(), user);
			resultMap.put("loginToken", loginToken);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[weixinLogin] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/xcxLogin")
	public Map<String, Object> xcxLogin(HttpServletRequest request, HttpServletResponse response, String appId,
			String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String code = parameterMap.get("code");
			String from = parameterMap.get("from");
			String inviteUserId = parameterMap.get("inviteUserId");
			String result = HttpsUtil.doGet(
					"https://api.weixin.qq.com/sns/jscode2session",
					"appid=" + weixinService.getAppId(WeixinService.CONFIG_WZXCX) + "&secret="
							+ weixinService.getAppSecret(WeixinService.CONFIG_WZXCX) + "&js_code=" + code
							+ "&grant_type=authorization_code", "UTF-8", 30000, 30000);

			OauthGetTokenResponse tokenResponse = JacksonUtil.json2pojo(result, OauthGetTokenResponse.class);
			if (tokenResponse == null) {
				throw new BusinessException(ComRetCode.WEIXIN_LOGIN_FAIL);
			}
			JSONObject jsonObject = JSONObject.fromObject(result);
			String sessionKey = (String) jsonObject.get("session_key");

			String unionId = tokenResponse.getUnionid();
			LoggerUtil.info("[xcxLogin unionId = ] " + unionId);

			if (StringUtils.isBlank(unionId)) {
				unionId = tokenResponse.getOpenid();
			}
			String openId = tokenResponse.getOpenid();
			LoggerUtil.info("[xcxLogin openId = ] " + openId);

			userService.updateWeiXinId(unionId, openId);
			boolean register = false;
			User user = userService.selectUserByWeixinId(unionId);
			if (StringUtils.isNotBlank(unionId)) {
				if (user == null) {
					user = new User();
					user.setWeixinId(unionId);
					user.setRegisterFrom(from);
					user.setStatus(UserStatus.NORMAL);
					user.setLevel(UserLevel.MANAGER);
					if (inviteUserId != null) {
						user.setInviteUserId(Long.valueOf(inviteUserId));
					}
					userService.insertUser(user);
					register = true;
					User wxUser = userService.selectUserByWeixinId(unionId);
					Agency agency = new Agency();
					if (wxUser != null && inviteUserId != null) {
						agency.setUserId(wxUser.getUserId());
						if (!StringUtil.isEmpty(wxUser.getNickName()) && !("undefined".equals(wxUser.getNickName()))) {
							agency.setAgencyName(wxUser.getNickName());
						} else {
							agency.setAgencyName("");
						}
						agency.setInviteUserId(Long.valueOf(inviteUserId));
						agency.setType(AgencyType.INVITE);
						agencyService.insertAgency(agency);
					}
				}
			} else {
				throw new BusinessException(ComRetCode.WEIXIN_LOGIN_FAIL);
			}

			LoginToken loginToken = tokenService.generateLoginToken(appId, user.getUserId() + "",
					JSPHelper.getRemoteAddr(request));
			loginToken.setRegister(register);
			setSession(request, loginToken.getToken(), user);
			redisCacheService.set(RedisConstant.SESSION_KEY + user.getUserId(), sessionKey);
			resultMap.put("loginToken", loginToken);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[weixinLogin] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/xcxUpdateUserInfo")
	public Map<String, Object> xcxUpdateUserInfo(HttpServletRequest request, HttpServletResponse response,
			String appId, String token, String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = getLoginUser(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String name = parameterMap.get("name");
			String avatar = parameterMap.get("avatar");
			String gender = parameterMap.get("gender");

			userService.updateUserBaseInfoIfNotExists(user, name, avatar, gender);

			User updateUser = new User();
			updateUser.setUserId(user.getUserId());
			if (StringUtils.isNotBlank(name) && !("undefined".equals(name))) {
				updateUser.setNickName(name);
			} else {
				if (StringUtils.isNotBlank(user.getNickName()) && !("undefined".equals(user.getNickName()))) {
					updateUser.setNickName(user.getNickName());
				}
			}
			if (StringUtils.isNotBlank(avatar) && !("undefined".equals(avatar))) {
				updateUser.setAvatar(avatar);
			} else {
				if (StringUtils.isNotBlank(user.getAvatar()) && !("undefined".equals(user.getAvatar()))) {
					updateUser.setAvatar(user.getAvatar());
				}
			}
			userService.updateUser(updateUser);
			request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, user);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[weixinLogin] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@RequestMapping("/decryptUnionId")
	public void decryptWeiXinUnionId(HttpServletRequest request, HttpServletResponse response, String token,
			String encryptedData, String iv) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = getLoginUser(token);

			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			String sessionKey = redisCacheService.get(RedisConstant.SESSION_KEY + user.getUserId());
			if (StringUtils.isNotBlank(sessionKey) && StringUtils.isNotBlank(encryptedData)
					&& StringUtils.isNotBlank(iv)) {
				byte[] decyptUserInfo = AESUtils.decryptWeiXinUnionId(Base64.decodeBase64(encryptedData),
						Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));

				if (decyptUserInfo != null) {
					String result = new String(decyptUserInfo);
					if (StringUtils.isNotBlank(result)) {
						JSONObject jsonObject = JSONObject.fromObject(result);
						String unionId = (String) jsonObject.get("unionId");

						if (StringUtils.isNotBlank(unionId)) {
							if (unionId.equals(user.getWeixinId())) {
								LoggerUtil.info("[decryptUnionId unionId =]" + unionId);
								return;
							} else {
								user.setWeixinId(unionId);
								userService.updateUser(user);
								LoggerUtil.info("[userWeixinId weixinId =]" + user.getWeixinId()
										+ "==> decryptUnionId]" + unionId);
							}
							resultMap.put("result", result);
							InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
						}
					}
				}
			} else {
				LoggerUtil.info("session_key = " + sessionKey + ",encryptedData=" + encryptedData + ",iv=" + iv);
			}
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());

		} catch (Exception e) {
			LoggerUtil.error("[getTempToken] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
	}

	@ResponseBody
	@RequestMapping("/getTempToken")
	public Map<String, Object> transferToken(HttpServletRequest request, HttpServletResponse response, String appId,
			String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String token = parameterMap.get("token");

			User user = tokenService.verifyLoginToken(token);
			if (user != null) {
				setSession(request, token, user);
			}
			if (user != null) {
				String tempToken = tokenService.generateTempToken(appId, user.getUserId() + "");
				resultMap.put("tempToken", tempToken);
			} else {
				resultMap.put("tempToken", "");
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[getTempToken] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/exist")
	public Map<String, Object> exist(HttpServletRequest request, String appId, String username) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (!StringUtil.isEmpty(username) || !ValidateUtil.isPhoneNo(username)) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PARAMETER);
		}

		User account = userService.selectUserByMobile(username);
		if (account == null) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} else {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.USER_EXIST);
		}

		return resultMap;
	}

	/**
	 * @param request
	 * @param appId
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register")
	public Map<String, Object> register(HttpServletRequest request, String appId, String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String mobile = parameterMap.get("mobile"); // 手机
			String password = parameterMap.get("password");
			String smsCode = parameterMap.get("smsCode"); // 验证码
			String inviteCode = parameterMap.get("inviteCode");
			String inviteUserId = parameterMap.get("inviteUserId");
			String registerFrom = parameterMap.get("from");
			String userIp = JSPHelper.getRemoteAddr(request);

			// 4.校验短信
			try {
				/*
				 * if (StringUtils.isBlank(password)) { password =
				 * RandomUtil.generateNumberString(8); } if
				 * (!VerifyFormatUtil.verifyPasswdFormat(password)) {
				 * InterfaceRetCode.setAppCodeDesc(resultMap,
				 * ComRetCode.WRONG_PASSOWRD); return resultMap; }
				 */
				User user = userService.selectUserByMobile(mobile);
				if (user != null) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.USER_EXIST);
					return resultMap;
				}
				smsCodeService.validateSmsCode(mobile, smsCode, "", userIp);
				user = new User();
				user.setMobile(mobile);
				user.setRegisterFrom(registerFrom);
				user.setStatus(UserStatus.NORMAL);
				generateUser(user, password, inviteCode);
				LoginToken loginToken = tokenService.generateLoginToken(appId, user.getUserId() + "", userIp);
				user = userService.selectUserById(user.getUserId());
				loginToken.setUser(user);
				setSession(request, loginToken.getToken(), user);
				resultMap.put("loginToken", loginToken);
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			} catch (BusinessException e) {
				InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
				return resultMap;
			}
		} catch (Exception e) {
			LoggerUtil.error("[decryptUnionId] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}

	}

	/**
	 * 添加手机号
	 */
	@ResponseBody
	@RequestMapping("/mobile")
	public Object addMobile(HttpServletRequest request, String appId, String params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String mobile = parameterMap.get("mobile");
			String smsCode = parameterMap.get("smsCode");
			String userIp = JSPHelper.getRemoteAddr(request);
			smsCodeService.validateSmsCode(mobile, smsCode, "", userIp);
			if (StringUtil.isEmpty(mobile) && !ValidateUtil.isPhoneNo(mobile)) {
				throw new BusinessException(ComRetCode.WRONG_MOBILE);
			}
			User user = new User();
			user.setMobile(mobile);
			userService.insertUser(user);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 添加身份证信息
	 */
	@ResponseBody
	@RequestMapping("/identity")
	public Map<String, Object> identity(HttpServletRequest request, String appId, String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String identityNumber = parameterMap.get("identityNumber");
			String userName = parameterMap.get("userName");
			if (!ValidateUtil.isIdentityNo(identityNumber)) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.IDNO_FORMAT_ERROR);
				return resultMap;
			}

		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 提交意见
	 */
	@RequestMapping("/commitOpinion")
	@ResponseBody
	public Object commitOpinion(HttpServletRequest request, String token, @RequestParam(" opinion") String opinion) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User loginToken = tokenService.verifyLoginToken(token);
			if (loginToken == null)
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			if (StringUtil.isEmpty(opinion))
				throw new BusinessException(ComRetCode.NO_OPIOION);
			User user = new User();
			user.setUserId(loginToken.getUserId());
			userService.updateUser(user);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (Exception e) {
			LoggerUtil.error("[register] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	/**
	 * 渠道注册
	 *
	 * @param request
	 * @param agent
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/channelRegister")
	public Map<String, Object> channel(HttpServletRequest request, String agent) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Channel channel = ConfigManager.getChannel(agent);
			if (channel == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			String key = null;

			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");
			String smsCode = request.getParameter("smsCode");
			String inviteCode = request.getParameter("inviteCode");
			String ip = request.getParameter("userIp");
			String userAgent = request.getParameter("userAgent");

			try {
				mobile = AESUtils.decrypt(key, mobile);
				password = AESUtils.decrypt(key, password);
				smsCode = AESUtils.decrypt(key, smsCode);
				inviteCode = AESUtils.decrypt(key, inviteCode);
				ip = AESUtils.decrypt(key, ip);
				userAgent = AESUtils.decrypt(key, userAgent);
				LoggerUtil.info("[channel_register] channel={}, mobile={}, userIp={}, userAgent={}", agent, mobile, ip,
						userAgent);
			} catch (BusinessException e) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PARAMETER);
				return resultMap;
			}

			String userIp = JSPHelper.getRemoteAddr(request);
			if (StringUtils.isNotBlank(ip)) {
				userIp = ip;
			}
			// 4.校验短信
			try {
				if (!VerifyFormatUtil.verifyPasswdFormat(password)) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PASSOWRD);
					return resultMap;
				}
				User user = userService.selectUserByMobile(mobile);
				if (user != null) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.USER_EXIST);
					return resultMap;
				}
				smsCodeService.validateSmsCode(mobile, smsCode, "", userIp);
				user = new User();
				user.setMobile(mobile);
				user.setRegisterFrom(agent);
				user.setStatus(UserStatus.NORMAL);
				generateUser(user, password, inviteCode);
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			} catch (BusinessException e) {
				InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
				return resultMap;
			}
		} catch (Exception e) {
			LoggerUtil.error("[channel register] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	private void generateUser(User user, String password, String inviteCode) {
		InviteCode invite = inviteCodeService.validateInviteCode(inviteCode);
		if (invite != null) {
			user.setInviteType(invite.getInviteType());
		}
		userService.insertOrUpdateUser(user);
		user.setPassword(MD5Util.encryptPassword(password, user.getUserId() + ""));
		userService.updateUser(user);
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setUserId(user.getUserId() + "");
		baseInfo.setName(DataFormat.hiddenMobile(user.getMobile()));
		baseInfo.setStatus(CommonStatus.ONLINE);
		// userService.insertOrUpdateBaseInfo(baseInfo);
		user.setBaseInfo(baseInfo);
	}

	@ResponseBody
	@RequestMapping("/password/forget")
	public Map<String, Object> forget(HttpServletRequest request, String appId, String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String mobile = parameterMap.get("mobile"); // 手机
			String password = parameterMap.get("password");
			String smsCode = parameterMap.get("smsCode"); // 验证码
			String userIp = JSPHelper.getRemoteAddr(request);

			// 4.校验短信
			try {
				if (!VerifyFormatUtil.verifyPasswdFormat(password)) {
					InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PASSOWRD);
					return resultMap;
				}
				smsCodeService.validateSmsCode(mobile, smsCode, "", userIp);
				User user = userService.selectUserByMobile(mobile);
				user.setPassword(MD5Util.encryptPassword(password, user.getUserId() + ""));
				userService.updateUser(user);
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			} catch (BusinessException e) {
				InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
				return resultMap;
			}
		} catch (Exception e) {
			LoggerUtil.error("[forgetPassword] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/password/change")
	public Map<String, Object> change(HttpServletRequest request, String appId, String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameterMap = appService.decryptParameters(appId, params);
			String token = parameterMap.get("token");
			User user = tokenService.verifyLoginToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			String password = parameterMap.get("password");
			if (!VerifyFormatUtil.verifyPasswdFormat(password)) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PASSOWRD);
				return resultMap;
			}
			user.setPassword(MD5Util.encryptPassword(password, user.getUserId() + ""));
			userService.updateUser(user);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[changePassword] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/avatar")
	public Object uploadAvadar(HttpServletRequest request, HttpServletResponse response, String token,
			MultipartFile file) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			token = request.getHeader("token");
			User user = tokenService.verifyLoginToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			if (file == null) {
				throw new BusinessException(ComRetCode.UPLOAD_AVATAR_ERROR);
			}
			String originalPath = user.getAvatar();
			String path = UploadUtils.uploadFile(file, "test/img");
			user.setAvatar(path);
			userService.insertOrUpdateUser(user);

			UploadUtils.deleteFile(originalPath);
			resultMap.put("avatarUrl", path);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

			User currentUser = userService.selectUserById(user.getUserId());
			request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, currentUser);

			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[avatar] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	@ResponseBody
	@RequestMapping("/commitInfo")
	public Object commitBaseInfo(HttpServletRequest request, String token, User userInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = tokenService.verifyLoginToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.TOKEN_INVALID);
			}
			userInfo.setMobile(user.getMobile());
			userInfo.setUserId(user.getUserId());
			userService.insertOrUpdateUser(userInfo);
			User currentUser = userService.selectUserById(user.getUserId());
			resultMap.put("user", new UserVo(currentUser));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, currentUser);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[commitInfo] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println(buildUser("3f7aec7e68f3c8cd", "jiaduobao", "13051678321", "123456", "2427"));
	}

	private static String buildUser(String key, String agent, String mobile, String password, String smsCode)
			throws Exception {
		System.out.println(Base64.encodeBase64String(AESUtils.getRawKey(key.getBytes())));
		StringBuffer result = new StringBuffer();
		String encrypt = AESUtils.encrypt(key, mobile);
		result.append("agent=" + agent);
		result.append("&mobile=" + encrypt);
		String passwordAes = AESUtils.encrypt(key, password);
		result.append("&password=" + passwordAes);
		String smsCodeAes = AESUtils.encrypt(key, smsCode);
		result.append("&smsCode=" + smsCodeAes);
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/weixinServiceLogin")
	public Map<String, Object> weixinServiceLogin(HttpServletRequest request, HttpServletResponse response,
			String appId, String code, String params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(code)) {
				return resultMap;
			}
			OauthGetTokenResponse tokenResponse = weixinService.getTokenResponse(null, code);
			if (tokenResponse == null) {
				throw new BusinessException(ComRetCode.WEIXIN_LOGIN_FAIL);
			}
			String openId = tokenResponse.getOpenid();
			resultMap.put("openId", openId);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
			return resultMap;
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("[weixinLogin] exception: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}
}
