package com.yuanshanbao.ms.service.admin.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuanshanbao.ms.mapper.admin.GroupMapper;
import com.yuanshanbao.ms.mapper.admin.GroupUserMapper;
import com.yuanshanbao.ms.mapper.admin.UserMapper;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private GroupUserMapper guMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public boolean isUserExits(String username) {
		List<User> users = userMapper.queryUserByName(username);

		return users != null && users.size() > 0;
	}

	@Override
	public List<User> queryUsers(User user, PageBounds pageBounds) {
		return userMapper.queryUsers(user, pageBounds);
	}

	@Override
	public User queryUsers(Long projectId, String username) {
		if (projectId == null || StringUtils.isBlank(username)) {
			return null;
		}
		User user = new User();
		user.setProjectId(projectId);
		user.setUsername(username);
		List<User> list = queryUsers(user, new PageBounds());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean insertUser(User user) {
		int result = -1;

		result = userMapper.insertUser(user);

		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteUser(String userName) {
		int result = -1;

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			result = userMapper.deleteUser(userName);

			result = guMapper.deleteRecordsByUsername(userName);
		} catch (DataAccessException ex) {
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);

		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUserLoginInfo(User user) {
		int result = -1;

		result = userMapper.updateUserLoginInfo(user);

		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUser(User user) {
		int result = -1;

		result = userMapper.updateUser(user);

		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUserPwd(User user) {
		int result = -1;

		result = userMapper.updateUserPwd(user);

		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

}
