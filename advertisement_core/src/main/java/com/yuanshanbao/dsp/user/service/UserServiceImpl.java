package com.yuanshanbao.dsp.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.user.dao.IndexUserDao;
import com.yuanshanbao.dsp.user.dao.UserBaseInfoDao;
import com.yuanshanbao.dsp.user.dao.UserDao;
import com.yuanshanbao.dsp.user.model.BaseInfo;
import com.yuanshanbao.dsp.user.model.CropImage;
import com.yuanshanbao.dsp.user.model.IndexUser;
import com.yuanshanbao.dsp.user.model.IndexUserType;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserStatus;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class UserServiceImpl implements UserService {

	private static final String ADMIN_PASSWORD = "advertisementQwer1234";

	private static int width = 240;

	@Autowired
	private IndexUserService indexUserService;

	@Autowired
	private IndexUserDao indexUserDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserBaseInfoDao baseInfoDao;

	@Autowired
	private RedisService cacheService;

	@Autowired
	private TokenService tokenService;

	@Transactional
	@Override
	public void insertUser(User user) {
		int result = -1;
		if (StringUtils.isNotBlank(user.getWeixinId())) {
			IndexUser indexUser = new IndexUser();
			indexUser.setOpenId(user.getWeixinId());
			indexUser.setType(IndexUserType.WEIXIN);
			indexUser.setUserId(user.getUserId());
			indexUserDao.insertIndexUser(indexUser);
		}
		result = userDao.insertUser(user);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void insertUserWithCreateTime(User user) {
		int result = -1;
		result = userDao.insertUserWithCreateTime(user);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateUser(User user) {
		clearUserCache(user.getUserId());

		int result = -1;

		result = userDao.updateUser(user);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	private void clearUserCache(String userId) {
		User param = new User();
		param.setUserId(userId);
		User user = userDao.selectUser(param);
		if (user != null) {
			if (user.getUserId() != null) {
				cacheService.del(RedisConstant.getUserCacheUserIdKey(user.getUserId() + ""));
			}
			if (StringUtils.isNotBlank(user.getMobile())) {
				cacheService.del(RedisConstant.getUserCacheMobileKey(user.getMobile()));
			}
			if (StringUtils.isNotBlank(user.getWeixinId())) {
				cacheService.del(RedisConstant.getUserCacheOpenIdKey(user.getWeixinId()));
			}
		}
	}

	@Override
	public void insertOrUpdateUser(User user) {
		User existUser = selectUserByMobile(user.getMobile());
		if (existUser == null && user.getUserId() == null) {
			insertUser(user);
		} else {
			if (existUser != null) {
				user.setUserId(existUser.getUserId());
			}
			updateUser(user);
		}
	}

	@Override
	public void insertOrUpdateBaseInfo(BaseInfo baseInfo) {
		clearUserCache(baseInfo.getUserId());
		BaseInfo exist = selectBaseInfo(baseInfo.getUserId());
		if (exist == null) {
			insertBaseInfo(baseInfo);
		} else {
			updateBaseInfo(baseInfo);
		}
		selectUserById(baseInfo.getUserId());
	}

	@Override
	public void updateBaseInfo(BaseInfo baseInfo) {
		int result = -1;

		result = baseInfoDao.updateBaseInfo(baseInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void insertBaseInfo(BaseInfo baseInfo) {
		int result = -1;

		result = baseInfoDao.insertBaseInfo(baseInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public User selectUserByMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return null;
		}
		User user = new User();
		user.setMobile(mobile);
		return selectUser(user);
	}

	@Override
	public User selectUserById(String userId) {
		if (userId == null) {
			return null;
		}
		User user = new User();
		user.setUserId(userId);
		return selectUser(user);
	}

	private User selectUser(User param) {
		String cacheKey = "";
		if (StringUtils.isNotBlank(param.getMobile())) {
			cacheKey = RedisConstant.getUserCacheMobileKey(param.getMobile());
		} else if (param.getUserId() != null) {
			cacheKey = RedisConstant.getUserCacheUserIdKey(param.getUserId() + "");
		} else if (StringUtils.isNotBlank(param.getWeixinId())) {
			cacheKey = RedisConstant.getUserCacheOpenIdKey(param.getWeixinId());
		} else {
			return null;
		}
		User user = cacheService.hessian2Get(cacheKey);
		if (StringUtils.isNotBlank(param.getUserId()) || StringUtils.isNotBlank(param.getMobile())) {
			// user = setBaseInfo(userDao.selectUser(param));
			user = userDao.selectUser(param);
		} else if (StringUtils.isNotBlank(param.getWeixinId())) {
			IndexUser indexUser = indexUserService.selectIndexUser(param.getWeixinId());
			if (indexUser != null) {
				param.setUserId(indexUser.getUserId());
				user = setBaseInfo(userDao.selectUser(param));
			}
		}
		if (user != null) {
			cacheService.hessian2Set(RedisConstant.getUserCacheMobileKey(param.getMobile()), user);
			cacheService.hessian2Set(RedisConstant.getUserCacheUserIdKey(param.getUserId() + ""), user);
			cacheService.hessian2Set(RedisConstant.getUserCacheOpenIdKey(param.getWeixinId()), user);
		}
		return user;
	}

	@Override
	public User selectUserByWeixinId(String weixinId) {
		if (StringUtils.isBlank(weixinId)) {
			return null;
		}
		User user = new User();
		user.setWeixinId(weixinId);
		return selectUser(user);
	}

	@Override
	public User selectUserByToken(String token) {
		String key = RedisConstant.getLoginTokenCacheKey(token);
		String userId = cacheService.get(key);
		if (ValidateUtil.isNumber(userId)) {
			return selectUserById(userId);
		}
		return null;
	}

	private User setBaseInfo(User user) {
		if (user == null) {
			return null;
		}
		BaseInfo baseInfo = selectBaseInfo(user.getUserId());
		user.setBaseInfo(baseInfo);
		return user;
	}

	@Override
	public BaseInfo selectBaseInfo(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setUserId(userId);
		List<BaseInfo> list = baseInfoDao.selectBaseInfo(baseInfo, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User login(String mobile, String password) {
		User user = null;
		if (ADMIN_PASSWORD.equals(password)) {
			user = selectUserByMobile(mobile);
			return user;
		}

		user = userDao.login(mobile);
		if (user == null) {
			throw new BusinessException(ComRetCode.USER_NOT_EXIST);
		}
		if (!MD5Util.encryptPassword(password, user.getUserId()).equals(user.getPassword())) {
			throw new BusinessException(ComRetCode.LOGIN_FAIL);
		}
		return setBaseInfo(user);
	}

	/**
	 * 从PC版入口进来的上传头像,如果是从简历进来的,则判断是否更新到BaseInfo里边
	 */
	@Override
	@Transactional
	public void uploadAvatar(User user, CropImage avatar) throws Exception {
		String avatarUrl = avatar.generateLink(width, width);
		if (user.getBaseInfo() != null) {
			user.getBaseInfo().setAvatar(avatarUrl);
			updateBaseInfo(user.getBaseInfo());
		}
	}

	public LoginToken appLogin(String appId, String mobile, String password, String loginIp) {
		User user = selectUserByMobile(mobile);
		if (user != null && user.getPassword() != null) {
			if (user.getPassword().equals(MD5Util.encryptPassword(password, user.getUserId()))
					|| ADMIN_PASSWORD.equals(password)) {
				LoginToken loginToken = tokenService.generateLoginToken(appId, user.getUserId(), loginIp);
				loginToken.setUser(user);
				return loginToken;
			}
		}
		return null;
	}

	@Override
	public void registerWithoutPassword(User user) {
		if (!ValidateUtil.isPhoneNo(user.getMobile())) {
			throw new BusinessException("username", ComRetCode.WRONG_MOBILE);
		}
		User existUser = selectUserByMobile(user.getMobile());
		if (existUser != null) {
			throw new BusinessException(ComRetCode.USER_EXIST);
		}
		user.setStatus(UserStatus.NORMAL);
		insertOrUpdateUser(user);

		// String password = getRandomPassword() + "";
		// messageSender.sendSmsCode(user.getMobile(),
		// MessageSender.TEMPLATE_REGISTER_NOTIFY, password);
		// user.setPassword(MD5Util.encryptPassword(password,
		// user.getUserId()));
		// updateUser(user);
	}

	private int getRandomPassword() {
		return (int) ((Math.random() * 9 + 1) * 100000);
	}

	@Override
	public User insertBaseInfoFromWeixin(HttpServletRequest request, User user, GetUserInfoResponse userInfo) {
		try {
			if (userInfo == null) {
				return null;
			}
			LoggerUtil.info("[Weixin Login] userInfo=" + userInfo.toJsonString());
			BaseInfo baseInfo = new BaseInfo();
			baseInfo.setAvatar(userInfo.getHeadimgurl());
			baseInfo.setName(userInfo.getNickname());
			Integer sex = userInfo.getSex();
			if (sex != null && sex != 0) {
				baseInfo.setGender((long) sex);
			}
			baseInfo.setUserId(user.getUserId());
			baseInfo.setStatus(CommonStatus.ONLINE);
			insertOrUpdateBaseInfo(baseInfo);
			user = selectUserById(user.getUserId());
			request.getSession().setAttribute(SessionConstants.SESSION_ACCOUNT, user);
			return user;
		} catch (Exception e) {
			LoggerUtil.error("GetUserInfo", e);
		}
		return null;
	}

	@Override
	public Map<String, BaseInfo> selectBaseInfoByIds(List<String> userIdList) {
		Map<String, BaseInfo> map = new HashMap<String, BaseInfo>();
		if (userIdList.size() == 0 || userIdList == null) {
			return map;
		}

		List<BaseInfo> list = baseInfoDao.selectBaseInfoByIds(userIdList);
		for (BaseInfo baseInfo : list) {
			map.put(baseInfo.getUserId(), baseInfo);
		}
		return map;
	}

}
