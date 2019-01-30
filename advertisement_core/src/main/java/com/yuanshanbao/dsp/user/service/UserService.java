package com.yuanshanbao.dsp.user.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.user.model.BaseInfo;
import com.yuanshanbao.dsp.user.model.CropImage;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;

public interface UserService {

	public User selectUserByWeixinId(String openId);

	public void insertUser(User user);

	public void updateUser(User user);

	public User selectUserByMobile(String mobile);

	public User login(String username, String password);

	public User selectUserById(Long userId);

	public User selectUserById(String userId);

	public User selectUserByToken(String token);

	public void uploadAvatar(User user, CropImage avatar) throws Exception;

	public LoginToken appLogin(String appId, String username, String password, String remoteAddr);

	public void insertOrUpdateUser(User user);

	public void insertBaseInfo(BaseInfo baseInfo);

	public void insertOrUpdateBaseInfo(BaseInfo baseInfo);

	public void updateBaseInfo(BaseInfo baseInfo);

	public BaseInfo selectBaseInfo(String userId);

	public void registerWithoutPassword(User user);

	public void insertUserWithCreateTime(User user);

	public User insertBaseInfoFromWeixin(HttpServletRequest request, User user, GetUserInfoResponse userInfo);

	public Map<String, BaseInfo> selectBaseInfoByIds(List<String> userIdList);

	public void updateUserBaseInfoIfNotExists(User user, String name, String avatar, String gender);

	/**
	 * 当前用户等级详情
	 * 
	 * @param userId
	 * @return
	 */
	public void updateLevelDetails(Long userId);

	/**
	 * 查询用户等级人个数
	 * 
	 * @param inviteUserId
	 * @return
	 */
	public int queryUserLevelCount(Long inviteUserId, Integer levelManager, Integer levelMajoromdo, Integer bailliff);

	public void updateUserMobile(User user);

	public Boolean changeMobile(User tokenUser, User user, String mobile);

	public int getUserLevelMajordomo(Long userId);

	public void createQRCodeURL(User user, String h5Url, Map<String, Object> resultMap);

	public void updateWeiXinId(String unionId, String openId);

	public BigDecimal getReconciliationBrokerage(String money, String subsidyMoney, Agency agency);

}