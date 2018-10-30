package com.yuanshanbao.dsp.advertisement.model.vo;

import com.yuanshanbao.dsp.creative.model.Creative;

public class AdvertisementDetails {

	private String pId;
	private String title;
	private String description;
	private String imageUrl;
	private String clickUrl;

	public AdvertisementDetails() {
		super();
	}

	public AdvertisementDetails(String planKey, Creative creative, String channel) {
		this.pId = planKey;
		this.title = creative.getTitle();
		this.description = creative.getDescription();
		this.imageUrl = creative.getImageUrl();
		this.clickUrl = setUrlByKey(planKey, channel);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	private String setUrlByKey(String planKey, String channel) {
		return "t.huhad.com/i/j/common.html?id=" + planKey + "&channel=" + channel;
	}
}
