package com.yuanshanbao.ms.model.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yuanshanbao.common.util.ValidateUtil;

public class User implements UserDetails {
	private org.springframework.security.core.userdetails.User userDetails;

	public User() {
	}

	public User(org.springframework.security.core.userdetails.User userDetails) {
		this.userDetails = userDetails;
	}

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "用户名不能为空")
	private String username;

	@NotBlank(message = "用密码不能为空")
	private String password;

	private String role;

	private String history;

	private String note;

	private Timestamp last_time;

	private int first_login_force = 0;

	private int login_count;

	private Timestamp last_chgpwd_time;

	private List<Group> groups;

	@NotBlank(message = "邮箱不能为空")
	private String email;

	private int pwd_keep_time = 0;

	@NotBlank(message = "姓名不能为空")
	private String name;

	private String departmentName;

	private String user_level;

	private String bind_user_ids;

	private String bindCompanyId;

	private String bindChannel;

	private String companyName;

	private Long projectId;

	private Long departmentId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getLast_time() {
		return last_time;
	}

	public void setLast_time(Timestamp last_time) {
		this.last_time = last_time;
	}

	public int getFirst_login_force() {
		return first_login_force;
	}

	public void setFirst_login_force(int first_login_force) {
		this.first_login_force = first_login_force;
	}

	public int getLogin_count() {
		return login_count;
	}

	public void setLogin_count(int login_count) {
		this.login_count = login_count;
	}

	public Timestamp getLast_chgpwd_time() {
		return last_chgpwd_time;
	}

	public void setLast_chgpwd_time(Timestamp last_chgpwd_time) {
		this.last_chgpwd_time = last_chgpwd_time;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPwd_keep_time() {
		return pwd_keep_time;
	}

	public void setPwd_keep_time(int pwd_keep_time) {
		this.pwd_keep_time = pwd_keep_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUser_level() {
		return user_level;
	}

	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}

	public String getBind_user_ids() {
		return bind_user_ids;
	}

	public void setBind_user_ids(String bind_user_ids) {
		this.bind_user_ids = bind_user_ids;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public List<Long> getBindUserIdList() {
		List<Long> list = new ArrayList<Long>();
		if (StringUtils.isNotBlank(bind_user_ids)) {
			String[] segs = bind_user_ids.split(",");
			for (String seg : segs) {
				if (ValidateUtil.isNumber(seg)) {
					list.add(Long.parseLong(seg));
				}
			}
		}
		return list;
	}

	public org.springframework.security.core.userdetails.User getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(
			org.springframework.security.core.userdetails.User userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (userDetails == null) {
			return new ArrayList<GrantedAuthority>();
		}

		return userDetails.getAuthorities();
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@Override
	public boolean isAccountNonExpired() {
		if (userDetails == null) {
			return true;
		}

		return userDetails.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		if (userDetails == null) {
			return true;
		}

		return userDetails.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (userDetails == null) {
			return true;
		}

		return userDetails.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		if (userDetails == null) {
			return false;
		}

		return userDetails.isEnabled();
	}

	public String getBindCompanyId() {
		return bindCompanyId;
	}

	public void setBindCompanyId(String bindCompanyId) {
		this.bindCompanyId = bindCompanyId;
	}

	public String getBindChannel() {
		return bindChannel;
	}

	public void setBindChannel(String bindChannel) {
		this.bindChannel = bindChannel;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
