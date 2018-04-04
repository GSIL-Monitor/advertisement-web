package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface UserMapper {
	public List<User> queryUsers(User user, PageBounds pageBounds);
	
	public List<User> queryUserByName(String name);

	public int insertUser(User user);

	public int deleteUser(String userName);
	
	public int updateUser(User user);
	
	public int updateUserPwd(User user);

	public int updateUserLoginInfo(User user);

	public List<User> queryUserByUsername(PageBounds pageBounds, String userName);
}
