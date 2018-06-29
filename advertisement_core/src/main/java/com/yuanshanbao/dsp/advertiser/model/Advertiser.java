package com.yuanshanbao.dsp.advertiser.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;

public class Advertiser {

	private Long advertiserId;
	private Long projectId;
	private String name;
	private String companyName;
	private String description;
	private String phone;
	private String address;
	private String wechat;
	private String bindUserName;
	private String businessNumber;
	private String businessPicture;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
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

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getStatusValue() {
		// return AdvertiserStatus.getDescription(status);
		return "";
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getBindUserName() {
		return bindUserName;
	}

	public void setBindUserName(String bindUserName) {
		this.bindUserName = bindUserName;
	}

	public String getBusinessPicture() {
		return businessPicture;
	}

	public void setBusinessPicture(String businessPicture) {
		this.businessPicture = businessPicture;
	}

}
