package com.yuanshanbao.dsp.user.model;

import java.io.Serializable;

import com.yuanshanbao.common.util.CommonUtil;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 4165445835251317169L;

	private String userId;
	private String name;
	private String avatar;
	private Long gender;
	private String channel;
	private String bindAlias;

	public UserVo() {
		super();
	}

	public UserVo(User user) {
		if (user != null) {
			this.userId = user.getUserId();
			this.name = user.getName();
			this.avatar = user.getAvatar();
			this.gender = user.getGender();
			this.channel = user.getRegisterFrom();
			this.bindAlias = CommonUtil.getBindPrefix() + user.getUserId();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBindAlias() {
		return bindAlias;
	}

	public void setBindAlias(String bindAlias) {
		this.bindAlias = bindAlias;
	}

}
