package com.yuanshanbao.ad.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.user.model.LoginToken;
import com.yuanshanbao.ad.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	public int insertUser(User user) {
		user.setCreateTime(DateUtils.getCurrentTime());
		user.setUpdateTime(DateUtils.getCurrentTime());
		return getSqlSession().insert("User.insertUser", user);
	}

	public int insertUserWithCreateTime(User user) {
		return getSqlSession().insert("User.insertUserWithCreateTime", user);
	}

	public User selectUser(User user) {
		List<User> list = getSqlSession().selectList("User.selectUser", user);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<User> selectUser(User user, PageBounds pageBounds) {
		return getSqlSession().selectList("User.selectUser", user, pageBounds);
	}

	public int updateUser(User user) {
		user.setUpdateTime(DateUtils.getCurrentTime());
		return getSqlSession().update("User.updateUser", user);
	}

	@Override
	public User login(String mobile) {
		return getSqlSession().selectOne("User.login", mobile);
	}

	@Override
	public void saveLoginToken(LoginToken loginToken) {
		getSqlSession().insert("User.saveLoginToken", loginToken);
	}

	@Override
	public LoginToken getLoginToken(LoginToken token) {
		return getSqlSession().selectOne("User.selectLoginToken", token);
	}

}
