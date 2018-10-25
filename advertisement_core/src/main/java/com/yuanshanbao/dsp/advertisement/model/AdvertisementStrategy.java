package com.yuanshanbao.dsp.advertisement.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.core.CommonStatus;

public class AdvertisementStrategy {

	private Long advertisementStrategyId;
	private Long projectId;
	private Long functionId;
	private Long advertisementId;
	private Long advertiserId;
	private Long probabilityId;
	private Long planId;
	private String key;
	private String value;
	private String description;
	private Integer type;
	private Double percent;
	private Integer sort;
	private Integer status;
	private Integer flag;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Advertisement advertisement;
	private Function function;

	public Long getAdvertisementStrategyId() {
		return advertisementStrategyId;
	}

	public void setAdvertisementStrategyId(Long advertisementStrategyId) {
		this.advertisementStrategyId = advertisementStrategyId;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getTypeValue() {
		return AdvertisementStrategyType.getDescription(type);
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

	@Override
	public String toString() {
		return "AdvertisementStrategy [strategyId=" + advertisementStrategyId + "]";
	}

	public boolean judge(Long activityId, String channel) {
		/*
		 * if (activityId == null && StringUtil.isBlank(channel)) { return true;
		 * } try { if (type != null &&
		 * type.equals(AdvertisementStrategyType.AGE)) { String[] ages =
		 * value.split("-"); if (ages.length < 2) { return false; } int min =
		 * Integer.valueOf(ages[0]); int max = Integer.valueOf(ages[1]); int age
		 * = insurant.getAge(); if (age < min || age > max) { return false; } }
		 * if (type != null && type.equals(AdvertisementStrategyType.REGION) &&
		 * StringUtils.isNotBlank(value)) { // 如果省不为空，并且value不包含省的话，判断市是否包含 if
		 * (StringUtils.isNotBlank(insurant.getProvince()) &&
		 * !value.contains(insurant.getProvince())) { //
		 * 如果市为空，省不相等就返回false，如果市不为空，但value不包含市，也返回错误 if
		 * (StringUtils.isBlank(insurant.getCity()) ||
		 * !value.contains(insurant.getCity())) { return false; } } } return
		 * true; } catch (BusinessException e) {
		 * LoggerUtil.error("selectAdvertisementStrategy error :", e); return
		 * true; }
		 */
		return true;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProbabilityId() {
		return probabilityId;
	}

	public void setProbabilityId(Long probabilityId) {
		this.probabilityId = probabilityId;
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

}
