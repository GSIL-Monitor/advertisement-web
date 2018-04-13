package com.yuanshanbao.dsp.page.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.dsp.information.model.vo.InformationFieldVo;
import com.yuanshanbao.dsp.page.model.PageStep;

public class PageStepVo {

	private Long pageStepId;
	private String name;
	private String description;
	private Integer extendAmount;
	private List<InformationFieldVo> fieldList;

	public PageStepVo(PageStep pageStep) {
		if (pageStep == null) {
			return;
		}
		this.pageStepId = pageStep.getPageStepId();
		this.name = pageStep.getName();
		this.description = pageStep.getDescription();
		this.extendAmount = pageStep.getExtendAmount();
		this.fieldList = convertInformationFieldVoList(pageStep.getFieldList());
	}

	private List<InformationFieldVo> convertInformationFieldVoList(List<InformationField> fieldList) {
		List<InformationFieldVo> voList = new ArrayList<InformationFieldVo>();
		if (fieldList == null) {
			return voList;
		}
		for (InformationField field : fieldList) {
			voList.add(new InformationFieldVo(field));
		}
		return voList;
	}

	public Long getPageStepId() {
		return pageStepId;
	}

	public void setPageStepId(Long pageStepId) {
		this.pageStepId = pageStepId;
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

	public Integer getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(Integer extendAmount) {
		this.extendAmount = extendAmount;
	}

	public List<InformationFieldVo> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<InformationFieldVo> fieldList) {
		this.fieldList = fieldList;
	}

}
