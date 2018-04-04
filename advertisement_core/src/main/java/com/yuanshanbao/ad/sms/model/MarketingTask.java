package com.yuanshanbao.ad.sms.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;

public class MarketingTask {

	private Long marketingTaskId;
	private String uploadUrl;
	private String downloadUrl;
	private String rejectProvince;
	private String rejectCity;
	private String downloadMobileType;
	private Integer exportCount;
	private String productName;
	private String smsPlatform;
	private String smsContent;
	private String progress;
	private Integer status;
	private Timestamp sendTime;
	private Timestamp createTime;
	private Timestamp updateTime;

	private String sendTimeStr;

	public Long getMarketingTaskId() {
		return marketingTaskId;
	}

	public void setMarketingTaskId(Long marketingTaskId) {
		this.marketingTaskId = marketingTaskId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSmsPlatform() {
		return smsPlatform;
	}

	public void setSmsPlatform(String smsPlatform) {
		this.smsPlatform = smsPlatform;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
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

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public String getStatusValue() {
		return MarketingTaskType.getStatusDescription(status);
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getRejectProvince() {
		return rejectProvince;
	}

	public void setRejectProvince(String rejectProvince) {
		this.rejectProvince = rejectProvince;
	}

	public String getRejectCity() {
		return rejectCity;
	}

	public void setRejectCity(String rejectCity) {
		this.rejectCity = rejectCity;
	}

	public String getDownloadMobileType() {
		return downloadMobileType;
	}

	public void setDownloadMobileType(String downloadMobileType) {
		this.downloadMobileType = downloadMobileType;
	}

	public Integer getExportCount() {
		return exportCount;
	}

	public void setExportCount(Integer exportCount) {
		this.exportCount = exportCount;
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getSendTimeContent() {
		return DateUtils.format(sendTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

}
