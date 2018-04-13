package com.yuanshanbao.dsp.common.model;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;

public class ServerLog {

	private Long logId;
	private Integer type;
	private String title;
	private String content;
	private Integer count;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private AtomicInteger atomicCount = new AtomicInteger(1);

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeValue() {
		if (this.type == null) {
			return LoggerUtil.LEVEL_ERROR_DESCRIPTION;
		}
		switch (this.type) {
		case LoggerUtil.LEVEL_DEBUG:
			return LoggerUtil.LEVEL_DEBUG_DESCRIPTION;
		case LoggerUtil.LEVEL_INFO:
			return LoggerUtil.LEVEL_INFO_DESCRIPTION;
		case LoggerUtil.LEVEL_WARN:
			return LoggerUtil.LEVEL_WARN_DESCRIPTION;
		case LoggerUtil.LEVEL_ERROR:
			return LoggerUtil.LEVEL_ERROR_DESCRIPTION;
		default:
			break;
		}
		return LoggerUtil.LEVEL_ERROR_DESCRIPTION;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return atomicCount.get();
	}

	public void setCount(Integer count) {
		this.count = count;
		this.atomicCount.set(count);
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

	public String getCreateTimeValue() {
		return DateUtils.format(createTime, null);
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

	public int incrementAndGet() {
		return this.atomicCount.incrementAndGet();
	}

}
