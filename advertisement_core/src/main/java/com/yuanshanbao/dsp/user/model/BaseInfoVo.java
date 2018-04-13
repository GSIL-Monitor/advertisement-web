package com.yuanshanbao.dsp.user.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BaseInfoVo implements Serializable {

	private static final long serialVersionUID = -8631837291951256767L;

	private String userId;
	private String mobile;
	private String avatar;
	private String name;
	private String identityCard;
	private Long gender;
	private Timestamp birthday;
	private String email;
	private String province;
	private String city;

	public BaseInfoVo() {

	}

	public BaseInfoVo(BaseInfo baseInfo) {
		if (baseInfo != null) {
			this.userId = baseInfo.getUserId();
			this.mobile = baseInfo.getMobile();
			this.avatar = baseInfo.getAvatar();
			this.name = baseInfo.getName();
			this.gender = baseInfo.getGender();
			this.identityCard = baseInfo.getIdentityCard();
			this.birthday = baseInfo.getBirthday();
			this.email = baseInfo.getEmail();
			this.province = baseInfo.getProvince();
			this.city = baseInfo.getCity();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
