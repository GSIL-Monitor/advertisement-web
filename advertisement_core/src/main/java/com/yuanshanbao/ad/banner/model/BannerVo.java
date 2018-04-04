package com.yuanshanbao.ad.banner.model;

/**
 * Created by Ynght on 2016/10/24.
 */
public class BannerVo {
	private Integer bannerId;
	private String title;
	private String imgUrl;
	private String detailUrl;
	private Integer weight;
	private Integer actionType;

	public BannerVo(Integer bannerId, String title, String imgUrl, String detailUrl, Integer weight, Integer actionType) {
		this.bannerId = bannerId;
		this.title = title;
		this.imgUrl = imgUrl;
		this.detailUrl = detailUrl;
		this.weight = weight;
		this.actionType = actionType;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

}
