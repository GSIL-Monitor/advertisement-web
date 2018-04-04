package com.yuanshanbao.ad.tags.model.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.ad.information.model.vo.InformationFieldVo;

public class TagsFieldVo {

	private Long tagsId;
	private String name;
	private String value;
	private String description;
	private String bigImage;
	private String image;
	private boolean hasSelected;

	private List<InformationFieldVo> referenceList = new ArrayList<InformationFieldVo>();

	public TagsFieldVo(TagsVo tagsVo) {
		if (tagsVo == null) {
			return;
		}
		this.tagsId = tagsVo.getTagsId();
		this.name = tagsVo.getName();
		this.value = tagsVo.getValue();
		this.description = tagsVo.getDescription();
		this.bigImage = tagsVo.getBigImage();
		this.image = tagsVo.getImage();
	}

	public TagsFieldVo() {
		super();
	}

	public TagsFieldVo(String name, String value) {
		this.name = name;
		this.value = value;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getTagsId() {
		return tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
	}

	public boolean isHasSelected() {
		return hasSelected;
	}

	public void setHasSelected(boolean hasSelected) {
		this.hasSelected = hasSelected;
	}

	public List<InformationFieldVo> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<InformationFieldVo> referenceList) {
		this.referenceList = referenceList;
	}

	@Override
	public String toString() {
		return "TagsVo [name=" + name + ", value=" + value + "]";
	}

	public String getDescriptionValue(String descriptionKey) {
		if (StringUtils.isNotBlank(description)) {
			String[] segs = this.description.split(",");
			for (String seg : segs) {
				String[] params = seg.split(":");
				if (params.length == 2 && params[0].equals(descriptionKey)) {
					return params[1];
				}
			}
		}
		return null;
	}
}
