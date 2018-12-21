package com.yuanshanbao.dsp.user.model;

import java.io.Serializable;

import com.yuanshanbao.common.util.CommonUtil;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 4165445835251317169L;

	private Long userId;
	private String mobile;
	private String weixinId;
	private String name;
	private String nickName;
	private String avatar;
	private Long gender;
	private String channel;
	private String bindAlias;
	private String inviteUserId;
	private String status;
	private String level;

	public UserVo() {
		super();
	}

	public UserVo(User user) {
		if (user != null) {
			this.setUserId(user.getUserId());
			this.name = user.getName();
			this.avatar = user.getAvatar();
			this.gender = user.getGender();
			this.channel = user.getRegisterFrom();
			this.bindAlias = CommonUtil.getBindPrefix() + user.getUserId();
		}
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLevelValue() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
