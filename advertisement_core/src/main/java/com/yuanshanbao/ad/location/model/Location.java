package com.yuanshanbao.ad.location.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Location {

	private Long locationId;
	private String code;
	private String name;
	private String shortName;
	private Integer type;
	private String parentCode;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Location parent;
	private List<Location> children = new ArrayList<Location>();

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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

	public Location getParent() {
		return parent;
	}

	public void setParent(Location parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public List<Location> getChildren() {
		return children;
	}

	public void setChildren(List<Location> children) {
		this.children = children;
	}

	public String getShortName() {
		if (StringUtils.isBlank(shortName)) {
			shortName = simple(name);
		}
		return shortName;
	}

	private String simple(String shortName) {
		if (shortName.contains("市") && shortName.length() > 2) {
			shortName = shortName.replace("市", "");
		}
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean contains(String locationCode) {
		Location current = this;
		while (current != null) {
			if (current.getCode().equals(locationCode)) {
				return true;
			}
			current = current.getParent();
		}
		return false;
	}

}
