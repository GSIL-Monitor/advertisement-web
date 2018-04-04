package com.yuanshanbao.ad.tags.model;

import java.sql.Timestamp;
import java.util.List;

public class TagsType {

	private Long typeId;
	private String typeName;
	private String typeDescription;
	private Long parentId;
	private Integer level;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private List<TagsType> children;

	public TagsType() {

	}

	public TagsType(Long typeId, String typeName, String typeDescription) {
		this.typeId = typeId;
		this.typeName = typeName;
		this.typeDescription = typeDescription;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	public List<TagsType> getChildren() {
		return children;
	}

	public void setChildren(List<TagsType> children) {
		this.children = children;
	}

}
