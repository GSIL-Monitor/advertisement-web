package com.yuanshanbao.dsp.config.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.core.CommonStatus;

public class ConfigAction {

	private Long configActionId;
	private Long functionId;
	private String actionValue;
	private String description;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Function function;

	public Long getConfigActionId() {
		return configActionId;
	}

	public void setConfigActionId(Long configActionId) {
		this.configActionId = configActionId;
	}

	public String getActionValue() {
		return actionValue;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
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

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

}
