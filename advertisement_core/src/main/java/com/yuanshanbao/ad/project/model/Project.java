package com.yuanshanbao.ad.project.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;

public class Project implements Serializable {

	private static final long serialVersionUID = 5706267456810663968L;

	private Long projectId;
	/**
	 * 项目key
	 */
	private String projectKey;
	/**
	 * 项目名称
	 */
	private String name;
	/**
	 * 项目描述
	 */
	private String description;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 中继网关
	 */
	private String tempGateway;
	/**
	 * 是否有外呼功能
	 */
	private Integer hasCallCenter;
	/**
	 * 是否有代理人功能
	 */
	private Integer hasBroker;
	/**
	 * 是否有实时分配功能
	 */
	private Integer hasRealtimeAllocate;
	/**
	 * 电话网关项目ID
	 */
	private String callCenterId;
	/**
	 * 最大外呼ID
	 */
	private String nextCallAgentId;
	/**
	 * 类型
	 */
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTempGateway() {
		return tempGateway;
	}

	public void setTempGateway(String tempGateway) {
		this.tempGateway = tempGateway;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getHasCallCenter() {
		return hasCallCenter;
	}

	public void setHasCallCenter(Integer hasCallCenter) {
		this.hasCallCenter = hasCallCenter;
	}

	public Integer getHasBroker() {
		return hasBroker;
	}

	public void setHasBroker(Integer hasBroker) {
		this.hasBroker = hasBroker;
	}

	public Integer getHasRealtimeAllocate() {
		return hasRealtimeAllocate;
	}

	public void setHasRealtimeAllocate(Integer hasRealtimeAllocate) {
		this.hasRealtimeAllocate = hasRealtimeAllocate;
	}

	public String getCallCenterId() {
		return callCenterId;
	}

	public void setCallCenterId(String callCenterId) {
		this.callCenterId = callCenterId;
	}

	public String getNextCallAgentId() {
		return nextCallAgentId;
	}

	public void setNextCallAgentId(String nextCallAgentId) {
		this.nextCallAgentId = nextCallAgentId;
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
}
