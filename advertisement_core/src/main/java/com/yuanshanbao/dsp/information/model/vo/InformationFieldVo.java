package com.yuanshanbao.dsp.information.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.dsp.tags.model.vo.TagsFieldVo;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class InformationFieldVo {

	private Long fieldId;
	private String name;
	private String fieldName;
	private Integer type;
	private Long tagsType;
	private String values;
	private String defaultValue;
	private String placeholder;
	private Integer required;
	private String relativeValue;

	private List<TagsFieldVo> valueFieldTags;

	private List<InformationFieldVo> referenceList = new ArrayList<InformationFieldVo>();

	public InformationFieldVo() {
		super();
	}

	public InformationFieldVo(InformationField field) {
		if (field == null) {
			return;
		}
		this.fieldId = field.getFieldId();
		this.name = field.getName();
		this.fieldName = field.getFieldName();
		this.type = field.getType();
		this.tagsType = field.getTagsType();
		this.values = field.getValues();
		this.defaultValue = field.getDefaultValue();
		this.required = field.getRequired();
		this.referenceList = field.getReferenceList();
		this.valueFieldTags = getValueFieldTags(field);
		this.placeholder = field.getPlaceholder();
		this.relativeValue = field.getRelativeValue();
	}

	private List<TagsFieldVo> getValueFieldTags(InformationField field) {
		List<TagsVo> list = field.getValueTags();
		List<TagsFieldVo> result = new ArrayList<TagsFieldVo>();
		for (TagsVo tagsVo : list) {
			if (tagsType != null) {
				TagsFieldVo vo = new TagsFieldVo(tagsVo);
				if (tagsVo.getTagsId().equals(field.getDefaultValue())) {
					vo.setHasSelected(true);
				}
				if (tagsVo.getName().equals(field.getDefaultValue())) {
					vo.setHasSelected(true);
				}
				result.add(vo);
			}
		}
		return result;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTagsType() {
		return tagsType;
	}

	public void setTagsType(Long tagsType) {
		this.tagsType = tagsType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public List<TagsFieldVo> getValueFieldTags() {
		return valueFieldTags;
	}

	public void setValueFieldTags(List<TagsFieldVo> valueFieldTags) {
		this.valueFieldTags = valueFieldTags;
	}

	public String getRelativeValue() {
		return relativeValue;
	}

	public void setRelativeValue(String relativeValue) {
		this.relativeValue = relativeValue;
	}

	public List<InformationFieldVo> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<InformationFieldVo> referenceList) {
		this.referenceList = referenceList;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

}
