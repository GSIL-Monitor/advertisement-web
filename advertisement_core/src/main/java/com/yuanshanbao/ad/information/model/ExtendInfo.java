package com.yuanshanbao.ad.information.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.tags.model.Tags;

public class ExtendInfo {

	private Long extendInfoId;
	private String userId;
	private Long informationId;
	private Long fieldId;
	/**
	 * 扩展信息key
	 */
	private String key;
	/**
	 * 扩展信息字段描述
	 */
	private String name;
	/**
	 * 扩展信息value
	 */
	private String value;
	private Long tagsId;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Information information;
	private InformationField informationField;

	public Long getExtendInfoId() {
		return extendInfoId;
	}

	public void setExtendInfoId(Long extendInfoId) {
		this.extendInfoId = extendInfoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getInformationId() {
		return informationId;
	}

	public void setInformationId(Long informationId) {
		this.informationId = informationId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getTagsId() {
		return tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
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

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setValueAndTags(InformationField field, String value) {
		Tags valueTags = field.getTagsByValue(value);
		if (valueTags != null) {
			this.tagsId = valueTags.getTagsId();
			this.value = valueTags.getValue();
		} else {
			this.value = value;
		}
	}

	public InformationField getInformationField() {
		return informationField;
	}

	public void setInformationField(InformationField informationField) {
		this.informationField = informationField;
	}

}
