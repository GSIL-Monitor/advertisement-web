package com.yuanshanbao.ad.app.model;

import java.sql.Timestamp;

public class AppInfo {

	private String appId;
	private String uniqueId;
	private String deviceType;
	private String systemName;
	private String systemVersion;
	private String productVersion;
	private String appKey;
	private String sourceChannel;

	private String key;

	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String ip;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[id=" + appId + "]");
		buffer.append("[uniqueId=" + uniqueId + "]");
		buffer.append("[deviceType=" + deviceType + "]");
		buffer.append("[systemName=" + systemName + "]");
		buffer.append("[systemVersion=" + systemVersion + "]");
		buffer.append("[productVersion=" + productVersion + "]");
		buffer.append("[sourceChannel=" + sourceChannel + "]");

		return buffer.toString();
	}
}
