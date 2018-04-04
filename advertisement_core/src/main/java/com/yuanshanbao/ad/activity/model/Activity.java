package com.yuanshanbao.ad.activity.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.core.CommonStatus;

public class Activity {

	private Long activityId;
	private String name;
	private String key;
	private String entranceUrl;
	private Integer baseAmount;
	private Double bonus;
	private String steps;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;
	
	private List<ActivityStep> activitySteps;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEntranceUrl() {
		return entranceUrl;
	}

	public void setEntranceUrl(String entranceUrl) {
		this.entranceUrl = entranceUrl;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
	public List<ActivityStep> getActivitySteps() {
		return activitySteps;
	}

	public void setActivitySteps(List<ActivityStep> activitySteps) {
		this.activitySteps = activitySteps;
	}

	public List<Long> getStepIds() {
		List<Long> stepIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(steps)) {
			String[] stepStrs = steps.split(CommonConstant.COMMA_SPLIT_STR);
			for (String step : stepStrs) {
				if (ValidateUtil.isNumber(step)) {
					stepIds.add(Long.parseLong(step));
				}
			}
			return stepIds;
		}
		return null;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
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

	public Integer getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(Integer baseAmount) {
		this.baseAmount = baseAmount;
	}
	
}
