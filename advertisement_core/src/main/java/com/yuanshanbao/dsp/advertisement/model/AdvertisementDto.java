package com.yuanshanbao.dsp.advertisement.model;

import java.sql.Timestamp;

import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.quota.model.Quota;

public class AdvertisementDto {
	private Long advertisementId;
	private Long projectId;
	private Long advertiserId;
	private String title;
	private String subTitle;
	private String link;
	private String imageUrl;
	private String bigImageUrl;
	private String description;
	private Integer status;

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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

	public String getBigImageUrl() {
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Probability getProbability() {
		return probability;
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

	private Integer type;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Probability probability;
	private Quota quota;
}
