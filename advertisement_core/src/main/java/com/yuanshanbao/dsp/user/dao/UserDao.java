package com.yuanshanbao.dsp.user.dao;

import java.util.List;

import com.yuanshanbao.dsp.base.shard.TableShard;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface UserDao {

	public int insertUser(User user);

	public int insertUserWithCreateTime(User user);

	public User selectUser(User user);

	public int updateUser(User user);

	public List<User> selectUser(User user, PageBounds pageBounds);

	public User login(String mobile);

	public void saveLoginToken(LoginToken loginToken);

	public LoginToken getLoginToken(LoginToken loginToken);

	public int queryUserLevelCount(Long inviteUserId,Integer levelManager,Integer levelMajoromdo,Integer bailliff);


	public int updateUserMobile(User user);


}