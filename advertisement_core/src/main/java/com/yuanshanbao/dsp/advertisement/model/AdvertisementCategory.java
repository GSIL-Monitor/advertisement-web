package com.yuanshanbao.dsp.advertisement.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.core.CommonStatus;

public class AdvertisementCategory {

	private Long categoryId;
	private Long type;
	private String name;
	private String value;
	private String description;
	private String image;
	private Integer showOrder;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	
	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public String toString() {
		return "[type=" + type + ", name=" + name + "]";
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
