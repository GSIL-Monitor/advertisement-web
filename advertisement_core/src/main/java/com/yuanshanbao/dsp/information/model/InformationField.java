package com.yuanshanbao.dsp.information.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.information.model.vo.InformationFieldVo;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class InformationField {

	private Long fieldId;
	private String name;
	private String fieldName;
	private Integer type;
	private String values;
	private Long tagsType;
	private String defaultValue;
	private String unit;
	private String placeholder;
	private Long relativeField;
	private String relativeValue;
	private Integer required;
	private Integer level;
	private Integer status;
	private String constains;
	private Timestamp createTime;
	private Timestamp updateTime;

	private String handler;

	/**
	 * 关联的TicketField对象
	 */
	private InformationField referenceField;
	/**
	 * 关联当前Field的对象
	 */
	private List<InformationField> referList = new ArrayList<InformationField>();

	private List<InformationFieldVo> referenceList = new ArrayList<InformationFieldVo>();

	private List<TagsVo> valueTags = new ArrayList<TagsVo>();

	private List<FieldConstrains> fieldConstrains;

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Long getTagsType() {
		return tagsType;
	}

	public void setTagsType(Long tagsType) {
		this.tagsType = tagsType;
	}

	public List<Tags> getTagsList() {
		return ConstantsManager.getTagsList(tagsType);
	}

	public String getDefaultTagsValue() {
		if (ValidateUtil.isNumber(defaultValue) && tagsType != null) {
			Tags tags = ConstantsManager.getTags(tagsType, Long.parseLong(defaultValue));
			if (tags != null) {
				return tags.getName();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldName() {
		if (StringUtils.isBlank(fieldName) && fieldId != null) {
			return "field" + fieldId;
		}
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTypeContent() {
		return FieldType.getDescription(type);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public List<String> getValueList() {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotBlank(values)) {
			String[] segs = values.split(CommonConstant.COMMA_SPLIT_STR);
			list.addAll(Arrays.asList(segs));
		}
		return list;
	}

	public String getRequirement() {
		if (required != null) {
			if (required.equals(1)) {
				return "必需";
			} else {
				return "非必需";
			}
		}
		return "";
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public boolean required() {
		return required != null && required == 1;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		if (defaultValue != null) {
			this.defaultValue = String.valueOf(defaultValue);
		}
	}

	public Long getRelativeField() {
		return relativeField;
	}

	public void setRelativeField(Long relativeField) {
		this.relativeField = relativeField;
	}

	public String getRelativeValue() {
		return relativeValue;
	}

	public void setRelativeValue(String relativeValue) {
		this.relativeValue = relativeValue;
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

	@JsonIgnore
	public InformationField getReferenceField() {
		return referenceField;
	}

	public void setReferenceField(InformationField referenceField) {
		this.referenceField = referenceField;
	}

	public List<InformationFieldVo> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<InformationFieldVo> referenceList) {
		this.referenceList = referenceList;
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public List<TagsVo> getValueTags() {
		List<TagsVo> valueTags = new ArrayList<TagsVo>();
		List<String> list = getValueList();
		if (list != null && list.size() > 0) {
			for (String tagId : list) {
				if (ValidateUtil.isNumber(tagId)) {
					valueTags.add(new TagsVo(ConstantsManager.getTags(Long.parseLong(tagId))));
				}
			}
		}
		return valueTags;
	}

	public void setValueTags(List<TagsVo> valueTags) {
		this.valueTags = valueTags;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getConstains() {
		return constains;
	}

	public void setConstains(String constains) {
		this.constains = constains;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<FieldConstrains> getFieldConstrains() {
		return fieldConstrains;
	}

	public void setFieldConstrains(List<FieldConstrains> fieldConstrains) {
		this.fieldConstrains = fieldConstrains;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Long> getConstrainsList() {
		List<Long> list = new ArrayList<Long>();
		if (StringUtils.isNotBlank(constains)) {
			String[] segs = constains.split(CommonConstant.COMMA_SPLIT_STR);
			for (String constain : segs) {
				if (ValidateUtil.isNumber(constain)) {
					list.add(Long.parseLong(constain));
				}
			}
		}
		return list;
	}

	public List<InformationField> getReferList() {
		return referList;
	}

	public void setReferList(List<InformationField> referList) {
		this.referList = referList;
	}

	public Tags getTagsByValue(String value) {
		for (Tags tags : getTagsList()) {
			if (String.valueOf(tags.getTagsId()).equals(value)) {
				return tags;
			}
			if (tags.getValue().equals(value)) {
				return tags;
			}
		}
		return null;
	}
}
