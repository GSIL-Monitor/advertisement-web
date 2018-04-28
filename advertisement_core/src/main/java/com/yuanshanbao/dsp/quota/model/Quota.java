package com.yuanshanbao.dsp.quota.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.yuanshanbao.common.util.DateUtils;

public class Quota {

	private Long quotaId;
	private Long projectId;
	private Long activityId;
	private Long positionId;
	private String channel;
	private Long advertisementId;
	private Integer type;
	private Integer count;
	private Timestamp startTime;
	private Timestamp endTime;
	private BigDecimal unitPrice;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private List<Long> advertisementIdList;

	public Long getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(Long quotaId) {
		this.quotaId = quotaId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setStartTimeValue(String startTimeValue) {
		this.startTime = DateUtils.formatToTimestamp(startTimeValue, "yyyy-MM-dd HH:mm");
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setEndTimeValue(String endTimeValue) {
		this.endTime = DateUtils.formatToTimestamp(endTimeValue, "yyyy-MM-dd HH:mm");
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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

	public List<Long> getAdvertisementIdList() {
		return advertisementIdList;
	}

	public void setAdvertisementIdList(List<Long> advertisementIdList) {
		this.advertisementIdList = advertisementIdList;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

}
