package com.yuanshanbao.dsp.advertisement.model.vo;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;

public class AdvertisementVo {

	private Long advertisementId;
	private String title;
	private String subTitle;
	private String imageUrl;
	private String link;
	public AdvertisementVo() {
		super();
	}

	public AdvertisementVo(Advertisement advertisement) {
		this.advertisementId = advertisement.getAdvertisementId();
		this.title = advertisement.getTitle();
		this.subTitle = advertisement.getSubTitle();
		this.imageUrl = advertisement.getImageUrl();
		this.link = advertisement.getLink();

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
}
