package com.yuanshanbao.dsp.sms.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.core.CommonStatus;

public class MarketingSms {

	private Long marketingSmsId;
	private Long marketingTaskId;
	private String mobile;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	// search
	private String createTimeStart;
	private String createTimeEnd;

	private MarketingTask marketingTask;

	public Long getMarketingSmsId() {
		return marketingSmsId;
	}

	public void setMarketingSmsId(Long marketingSmsId) {
		this.marketingSmsId = marketingSmsId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getMarketingTaskId() {
		return marketingTaskId;
	}

	public void setMarketingTaskId(Long marketingTaskId) {
		this.marketingTaskId = marketingTaskId;
	}

	public MarketingTask getMarketingTask() {
		return marketingTask;
	}

	public void setMarketingTask(MarketingTask marketingTask) {
		this.marketingTask = marketingTask;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
