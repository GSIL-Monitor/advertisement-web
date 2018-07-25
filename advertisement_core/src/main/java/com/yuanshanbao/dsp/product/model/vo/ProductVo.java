package com.yuanshanbao.dsp.product.model.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class ProductVo {

	private Long productId;
	private String name;
	private String title;
	private String description;
	private String imageUrl;
	private String smallImageUrl;
	private Integer minAge;
	private Integer maxAge;
	private Integer status;
	private Long productCount;
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
		this.featureTagsList = product.getFetureTags();
		this.recommendTagsList = getRecommendTagsList(product.getRecommendTags());
		this.productCount = product.getProductCount();
		this.minAge = product.getMinAge();
		this.maxAge = product.getMaxAge();
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

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private List<TagsVo> getRecommendTagsList(String recommendTags) {
		if (StringUtils.isBlank(recommendTags)) {
			return null;
		}
		String[] ids = recommendTags.split(",");
		if (ids == null || ids.length == 0) {
			return null;
		}
		List<TagsVo> result = new ArrayList<TagsVo>();
		for (String element : ids) {
			if (!ValidateUtil.isNumber(element)) {
				continue;
			}
			Tags exist = ConstantsManager.getTags(Long.valueOf(element));
			if (exist == null) {
				continue;
			}
			TagsVo vo = new TagsVo(exist);
			result.add(vo);
		}
		return result;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}
}
