package com.yuanshanbao.dsp.advertisement.model.vo;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;


public class AdvertisementVo {

	private Long advertisementId;
	private String title;
	private String subTitle;
	private String imageUrl;
	private Long tagsId;
	private String buttonName;
	private Long category;
	private String categoryDesc;
	private String link;
	private Long count;
	private Integer showType;
	private Long cycleTime;
	private Long positionId;
	
	public AdvertisementVo() {
		super();
	}
	
	public AdvertisementVo(Advertisement advertisement) {
		this.advertisementId = advertisement.getAdvertisementId();
		this.title = advertisement.getTitle();
		this.subTitle = advertisement.getSubTitle();
		this.imageUrl = advertisement.getImageUrl();
		this.buttonName = advertisement.getButtonName();
		this.category = advertisement.getCategory();
		this.categoryDesc = advertisement.getCategoryDesc();
		this.link = advertisement.getLink();
		this.count = advertisement.getCount();
		this.showType = advertisement.getShowType();
		this.cycleTime = advertisement.getCycleTime();
		this.positionId = advertisement.getPositionId();
	}
	
	public Long getAdvertisementId() {
		return advertisementId;
	}
	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getTagsId() {
		return tagsId;
	}
	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public Long getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(Long cycleTime) {
		this.cycleTime = cycleTime;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	
}
