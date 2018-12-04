package com.yuanshanbao.dsp.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;
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

	@Override
	public int queryUserLevelCount(Long inviteUserId, Integer levelManager, Integer levelMajoromdo, Integer bailliff) {
		Map<String, Object> map = new HashMap<>();
		map.put("inviteUserId", inviteUserId);
		Integer[] levels = { levelManager, levelMajoromdo, bailliff };
		map.put("levels", levels);
		return getSqlSession().selectOne("User.selectUserCount", map);
	}

	@Override
	public int updateUserMobile(User user) {
		user.setUpdateTime(DateUtils.getCurrentTime());
		return getSqlSession().update("User.updateUserMobile", user);
	}

	@Override
	public int getUserLevelMajordomo(Long userId) {
		return getSqlSession().selectOne("User.selectUserLevelManagerCount", userId);
	}

}
