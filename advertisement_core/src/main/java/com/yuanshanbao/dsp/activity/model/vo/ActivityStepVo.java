package com.yuanshanbao.dsp.activity.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.ActivityStep;
import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.dsp.information.model.vo.InformationFieldVo;
import com.yuanshanbao.dsp.tags.model.vo.TagsFieldVo;

public class ActivityStepVo {

	private Long activityStepId;
	private Integer activityStepIndex;
	private Long activityId;
	private String name;
	private Integer extendAmount;

	private List<InformationFieldVo> informationFieldVoList;

	public ActivityStepVo() {
		super();
	}

	public ActivityStepVo(ActivityStep step) {
		this.activityStepId = step.getActivityStepId();
		this.activityId = step.getActivityId();
		this.name = step.getName();
		this.extendAmount = step.getExtendAmount();
		this.informationFieldVoList = getInformationFieldVoList(step);
		this.activityStepIndex = step.getActivityStepIndex();
	}

	private List<InformationFieldVo> getInformationFieldVoList(ActivityStep step) {
		informationFieldVoList = new ArrayList<InformationFieldVo>();
		Map<Long, InformationField> map = new HashMap<Long, InformationField>();
		List<InformationField> list = step.getInformationFieldList();
		for (InformationField field : list) {
			map.put(field.getFieldId(), field);
		}
		for (InformationField field : list) {
			InformationField parent = map.get(field.getRelativeField());
			if (parent != null) {
				parent.getReferenceList().add(new InformationFieldVo(field));
			}
			if (field.getRelativeField() == null) {
				informationFieldVoList.add(new InformationFieldVo(field));
			}
		}
		for (InformationFieldVo vo : informationFieldVoList) {
			List<InformationFieldVo> needRemove = new ArrayList<InformationFieldVo>();
			for (InformationFieldVo informationFieldVo : vo.getReferenceList()) {
				List<TagsFieldVo> fieldVos = vo.getValueFieldTags();
				for (TagsFieldVo fieldVo : fieldVos) {
					if (ValidateUtil.isNumber(informationFieldVo.getRelativeValue())
							&& fieldVo.getTagsId().equals(Long.parseLong(informationFieldVo.getRelativeValue()))) {
						fieldVo.getReferenceList().add(informationFieldVo);
					}
					needRemove.addAll(fieldVo.getReferenceList());
				}
			}
			vo.getReferenceList().removeAll(needRemove);
		}
		return informationFieldVoList;
	}

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

	public Integer getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(Integer extendAmount) {
		this.extendAmount = extendAmount;
	}

	public List<InformationFieldVo> getInformationFieldVoList() {
		return informationFieldVoList;
	}

	public void setInformationFieldVoList(List<InformationFieldVo> informationFieldVoList) {
		this.informationFieldVoList = informationFieldVoList;
	}

	public Integer getActivityStepIndex() {
		return activityStepIndex;
	}

	public void setActivityStepIndex(Integer activityStepIndex) {
		this.activityStepIndex = activityStepIndex;
	}

}
