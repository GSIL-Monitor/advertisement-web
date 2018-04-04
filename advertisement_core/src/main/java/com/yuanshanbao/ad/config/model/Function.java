package com.yuanshanbao.ad.config.model;

import java.sql.Timestamp;
import java.util.List;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;

public class Function {

	private Long functionId;
	private String key;
	private String name;
	private Boolean defaultDecision;
	private String defaultAction;
	private Integer category;
	private Integer type;

	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private List<ConfigAction> configActionList;

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDefaultDecision() {
		return defaultDecision;
	}

	public void setDefaultDecision(Boolean defaultDecision) {
		this.defaultDecision = defaultDecision;
	}

	public String getDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(String defaultAction) {
		this.defaultAction = defaultAction;
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

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public List<ConfigAction> getConfigActionList() {
		return configActionList;
	}

	public void setConfigActionList(List<ConfigAction> configActionList) {
		this.configActionList = configActionList;
	}

}
