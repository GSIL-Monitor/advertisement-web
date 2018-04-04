package com.yuanshanbao.ad.user.dao;

import java.util.List;

import com.yuanshanbao.ad.base.shard.TableShard;
import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.user.model.LoginToken;
import com.yuanshanbao.ad.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

@TableShard(tableName = CommonConstant.USER_TABLE_NAME, shardType = CommonConstant.USER_SHARD_TYPE, shardBy = {
		CommonConstant.USER_SHARD_BY_ID, CommonConstant.USER_SHARD_BY_MOBILE })
public interface UserDao {

	public int insertUser(User user);

	public int insertUserWithCreateTime(User user);

	public User selectUser(User user);

	public int updateUser(User user);

	public List<User> selectUser(User user, PageBounds pageBounds);

	public User login(String mobile);

	public void saveLoginToken(LoginToken loginToken);

	public LoginToken getLoginToken(LoginToken loginToken);

}