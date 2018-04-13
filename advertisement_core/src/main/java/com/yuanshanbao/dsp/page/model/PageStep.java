package com.yuanshanbao.dsp.page.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.information.model.InformationField;

/**
 * 
 * @description
 *
 * @author 
 */
public class PageStep {

	// ~ Field
	// =============================================================================================
	
	private Long pageStepId;

	private Long pageId;

	private String name;

	private String description;

	private String fields;

	private Integer extendAmount;

	private String handler;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;
	
	private List<InformationField> fieldList = new ArrayList<InformationField>();;

	
	// ~ Get and Set Methods
	// =================================================================================
	
	public Long getPageStepId() {
		return pageStepId;
	}

	public void setPageStepId(Long pageStepId) {
		this.pageStepId = pageStepId;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
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
	
	public List<InformationField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<InformationField> fieldList) {
		this.fieldList = fieldList;
	}

	public List<Long> getFieldIds() {
		if (StringUtils.isNotBlank(fields)) {
			List<Long> fieldIdList = new ArrayList<Long>();
			String[] fieldIdStrings = fields.split(CommonConstant.COMMA_SPLIT_STR);
			for (String fieldIdString : fieldIdStrings) {
				if (ValidateUtil.isNumber(fieldIdString)) {
					fieldIdList.add(Long.parseLong(fieldIdString));
				}
			}
			return fieldIdList;
		}
		return null;
	}

}