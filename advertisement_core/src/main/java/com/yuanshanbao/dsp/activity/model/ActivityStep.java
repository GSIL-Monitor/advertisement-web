package com.yuanshanbao.dsp.activity.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.information.model.InformationField;

public class ActivityStep {

	private Long activityStepId;
	private Integer activityStepIndex;
	private Long activityId;
	private String name;
	private String description;
	private String fields;
	private Integer extendAmount;
	private String handler;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private List<InformationField> informationFieldList;

	public Long getActivityStepId() {
		return activityStepId;
	}

	public void setActivityStepId(Long activityStepId) {
		this.activityStepId = activityStepId;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getFieldIds() {
		List<Long> fieldIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(fields)) {
			String[] fieldStrs = fields.split(CommonConstant.COMMA_SPLIT_STR);
			for (String field : fieldStrs) {
				if (ValidateUtil.isNumber(field)) {
					fieldIds.add(Long.parseLong(field));
				}
			}
			return fieldIds;
		}
		return null;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public Integer getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(Integer extendAmount) {
		this.extendAmount = extendAmount;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public List<InformationField> getInformationFieldList() {
		return informationFieldList;
	}

	public void setInformationFieldList(List<InformationField> informationFieldList) {
		this.informationFieldList = informationFieldList;
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

	public Integer getActivityStepIndex() {
		return activityStepIndex;
	}

	public void setActivityStepIndex(Integer activityStepIndex) {
		this.activityStepIndex = activityStepIndex;
	}

}
