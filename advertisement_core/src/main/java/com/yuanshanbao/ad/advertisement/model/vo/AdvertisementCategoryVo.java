package com.yuanshanbao.ad.advertisement.model.vo;

import java.util.List;

import com.yuanshanbao.ad.advertisement.model.AdvertisementCategory;

public class AdvertisementCategoryVo {

	private Long categoryId;

	private String name;

	private String description;

	private String image;
	
	private Long type;

	private List<AdvertisementVo> advertisements;

	public AdvertisementCategoryVo() {
		super();
	}

	public AdvertisementCategoryVo(AdvertisementCategory category) {
		this.categoryId = category.getCategoryId();
		this.name = category.getName();
		this.description = category.getDescription();
		this.image = category.getImage();
		this.type = category.getType();
	}

	public List<AdvertisementVo> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(List<AdvertisementVo> advertisements) {
		this.advertisements = advertisements;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
