package com.yuanshanbao.dsp.creative.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;

public class Creative {
	private Long creativeId;
	private Long advertiserId;
	private Long advertisementId;
	private Long probabilityId;
	private String name;
	private String title;
	private String description;
	private String link;
	private String imageUrl;
	private Integer size;
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(Long creativeId) {
		this.creativeId = creativeId;
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public Long getProbabilityId() {
		return probabilityId;
	}

	public void setProbabilityId(Long probabilityId) {
		this.probabilityId = probabilityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS);
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

	public String getSizeContent() {
		return CreativeSize.getDescription(size);
	}

	public String getTypeContent() {
		return CreativeType.getDescription(type);
	}
}
