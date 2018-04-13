package com.yuanshanbao.dsp.tags.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;

public class Tags {

	private Long tagsId;
	private Long parentId;
	private Long type;
	private String name;
	private String value;
	private String description;
	private String bigImage;
	private String image;
	private Integer showOrder;
	private Long searchType;
	private Integer score;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private List<Tags> children;

	public Long getTagsId() {
		return tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTypeValue() {
		return ConstantsManager.getTypeDescription(tagsId);
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
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

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
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

	public Long getSearchType() {
		return searchType;
	}

	public void setSearchType(Long searchType) {
		this.searchType = searchType;
	}

	@Override
	public String toString() {
		return "[type=" + type + ", name=" + name + "]";
	}

	public List<Tags> getChildren() {
		return children;
	}

	public void setChildren(List<Tags> children) {
		this.children = children;
	}

	public void addChild(Tags province) {
		if (children == null) {
			children = new ArrayList<Tags>();
		}
		children.add(province);
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
