package com.yuanshanbao.ms.filter.security;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.ms.utils.UserLoginInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

	private static final Logger logger = Logger.getLogger("user");

	@Autowired
	private AdminUserService userService;

	@Autowired
	private RightService rightService;
	
	@Autowired
	private GroupService groupService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		String password = null;
		
		com.yuanshanbao.ms.model.admin.User user = null;
		
		com.yuanshanbao.ms.model.admin.User query = new com.yuanshanbao.ms.model.admin.User();
		query.setUsername(username);
		List<com.yuanshanbao.ms.model.admin.User> users = userService.queryUsers(query, new PageBounds());
		if (users != null && users.size() > 0) {
			user = users.get(0);
		}
		
		if (user == null) {
			logger.info("user " + username + " not exist.");
			String message = "用户" + username + "不存在";
			throw new UsernameNotFoundException(message);
		}
		password = user.getPassword();

		StringBuffer loggerStr = new StringBuffer();
		
		loggerStr.append("user:" + user.getUsername() + ";roles:[");
		
		List<Right> rights = rightService.queryRightsByUsername(username);
		
		if (rights != null && rights.size() > 0) {
			for (int i = 0; i < rights.size(); i++) {
				Right item = rights.get(i);
				SimpleGrantedAuthority grantedAuthorityImpl = new SimpleGrantedAuthority(item.getId());
				auths.add(grantedAuthorityImpl);
				
				if (i == 0) {
					loggerStr.append(item.getId());
				} else {
					loggerStr.append(";" + item.getId());
				}
			}
		}
		
		loggerStr.append("]");
		logger.info(loggerStr.toString());
		Timestamp old = user.getLast_chgpwd_time();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int leftDays = user.getPwd_keep_time() - UserLoginInfo.getDays(now.getTime() - old.getTime());
		
		boolean accountNonExpired = true;
		
		if (leftDays < 0) {
			accountNonExpired = false;
		}
		
		User suser = new User(username, password, true, accountNonExpired, true, true, auths);
		
		user.setUserDetails(suser);
		user.setGroups(groupService.queryGroupsByUsername("in", username, new PageBounds()));
		
		return user;
	}
}
