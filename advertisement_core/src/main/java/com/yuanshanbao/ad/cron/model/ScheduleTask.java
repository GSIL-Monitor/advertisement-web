package com.yuanshanbao.ad.cron.model;

import java.sql.Timestamp;

public class ScheduleTask {
	private Long taskId;
	private String keyInfo;
	private Integer taskType;// TaskTypeEnum
	private Integer status;// CommonStatusEnum
	private String remark;
	private Timestamp createTime;
	private Timestamp updateTime;

	public ScheduleTask() {
		super();
	}

	public ScheduleTask(String keyInfo, Integer taskType, Integer status, Timestamp createTime) {
		this.keyInfo = keyInfo;
		this.taskType = taskType;
		this.status = status;
		this.createTime = createTime;
	}

	public ScheduleTask(String keyInfo, Integer taskType, Integer status, String remark, Timestamp createTime) {
		this.keyInfo = keyInfo;
		this.taskType = taskType;
		this.status = status;
		this.remark = remark;
		this.createTime = createTime;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
