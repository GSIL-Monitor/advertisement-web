package com.yuanshanbao.dsp.advertisement.model.vo;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.quota.model.Quota;

public class AdvertisementVo {

	private Long advertisementId;
	private String title;
	private String subTitle;
	private String imageUrl;
	private String link;
	private String buttonName;
	private Long count;
	private Long category;
	private String categoryDesc;
	private Probability probability;
	private Quota quota;

	public AdvertisementVo() {
		super();
	}

	public AdvertisementVo(Advertisement advertisement) {
		this.advertisementId = advertisement.getAdvertisementId();
		this.title = advertisement.getTitle();
		this.subTitle = advertisement.getSubTitle();
		this.imageUrl = advertisement.getImageUrl();
		this.link = advertisement.getLink();
		this.buttonName = advertisement.getButtonName();
		this.count = advertisement.getCount();
		this.setCategory(advertisement.getCategory());
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getStatusValue() {
		if (probability == null){
			return CommonStatus.getDescription(CommonStatus.ONLINE);
		}
		 return CommonStatus.getDescription(probability.getStatus());
	}
}
