package com.yuanshanbao.dsp.advertisement.model.vo;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.dsp.plan.model.Plan;

public class AdvertisementDetails {

	private String pId;
	private String title;
	private String description;
	private String imageUrl;
	private String clickUrl;
	public AdvertisementDetails() {
		super();
	}

	public AdvertisementDetails(Plan plan,Creative creative) {
		this.pId = String.valueOf(plan.getPlanId());
		
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
}
