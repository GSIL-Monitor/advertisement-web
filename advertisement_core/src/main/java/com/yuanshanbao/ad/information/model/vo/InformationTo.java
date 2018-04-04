package com.yuanshanbao.ad.information.model.vo;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 接收接口信息传输数据
 *
 * @since 2018/02/07
 * @author java
 *
 */
public class InformationTo {
	private Long informationTaskId;
	private String mobile;
	private String name;
	private Long gender;
	private String identityCard;
	private Timestamp birthday;
	private Long education;
	private String email;
	private String location;
	private String address;
	private Long position;
	private String province;
	private String city;
	private Integer salary;
	private Long marriage;
	private Long hasChild;
	private String channel;
	private String channelDescription;
	private Map<String, String> extendInfoMap;

	public Long getInformationTaskId() {
		return informationTaskId;
	}

	public void setInformationTaskId(Long informationTaskId) {
		this.informationTaskId = informationTaskId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getEducation() {
		return education;
	}

	public void setEducation(Long education) {
		this.education = education;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
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

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Long getMarriage() {
		return marriage;
	}

	public void setMarriage(Long marriage) {
		this.marriage = marriage;
	}

	public Long getHasChild() {
		return hasChild;
	}

	public void setHasChild(Long hasChild) {
		this.hasChild = hasChild;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public Map<String, String> getExtendInfoMap() {
		return extendInfoMap;
	}

	public void setExtendInfoMap(Map<String, String> extendInfoMap) {
		this.extendInfoMap = extendInfoMap;
	}
}
