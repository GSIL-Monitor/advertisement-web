package com.yuanshanbao.ms.service.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdminUserService {

	public List<User> queryUsers(User user, PageBounds pageBounds);

	public User queryUsers(Long projectId, String username);

	public boolean isUserExits(String name);

	public boolean insertUser(User user);

	public boolean deleteUser(String userName);

	public boolean updateUserLoginInfo(User user);

	public boolean updateUser(User user);

	public boolean updateUserPwd(User user);

	public List<User> queryUserByUserName(User user);
}
