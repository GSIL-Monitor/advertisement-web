package com.yuanshanbao.dsp.product.model.vo;

import java.util.List;

import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class ProductVo {

	private Long productId;
	private String name;
	private String title;
	private String description;
	private String workOrderImageUrl;
	private String imageUrl;
	private String smallImageUrl;
	private List<TagsVo> featureTagsList;
	/**
	 * 产品推荐标签
	 */
	private List<TagsVo> recommendTagsList;

	public ProductVo(Product product) {
		if (product == null) {
			return;
		}
		this.productId = product.getProductId();
		this.name = product.getName();
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.imageUrl = product.getImageUrl();
		this.smallImageUrl = product.getSmallImageUrl();
		this.workOrderImageUrl = product.getWorkOrderImageUrl();
		this.featureTagsList = product.getFetureTags();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public String getWorkOrderImageUrl() {
		return workOrderImageUrl;
	}

	public void setWorkOrderImageUrl(String workOrderImageUrl) {
		this.workOrderImageUrl = workOrderImageUrl;
	}

	public List<TagsVo> getFeatureTagsList() {
		return featureTagsList;
	}

	public void setFeatureTagsList(List<TagsVo> featureTagsList) {
		this.featureTagsList = featureTagsList;
	}

	public List<TagsVo> getRecommendTagsList() {
		return recommendTagsList;
	}

	public void setRecommendTagsList(List<TagsVo> recommendTagsList) {
		this.recommendTagsList = recommendTagsList;
	}

}
