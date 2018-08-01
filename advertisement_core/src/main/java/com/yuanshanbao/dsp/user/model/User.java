package com.yuanshanbao.dsp.user.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DataFormat;
import com.yuanshanbao.common.util.DateUtils;

public class User implements Serializable {

	private static final long serialVersionUID = 4165445835251317169L;

	private Long userId;
	private String mobile;
	private String weixinId;
	private String username;
	private String password;
	private String registerFrom;
	private String name;
	private String avatar;
	private Integer inviteType;
	private Integer status;
	private Integer userType;
	private Timestamp createTime;
	private Timestamp updateTime;

	private BaseInfo baseInfo;

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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

	public String getRegisterFrom() {
		return registerFrom;
	}

	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}

	public Integer getInviteType() {
		return inviteType;
	}

	public void setInviteType(Integer inviteType) {
		this.inviteType = inviteType;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusValue() {
		return UserStatus.getDescription(status);
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd");
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		if (StringUtils.isNotBlank(getName())) {
			return getName();
		}
		return DataFormat.hiddenMobile(mobile);
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getAvatar() {
		return avatar;
	}

	public Long getGender() {
		if (baseInfo != null) {
			return baseInfo.getGender();
		}
		return null;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
