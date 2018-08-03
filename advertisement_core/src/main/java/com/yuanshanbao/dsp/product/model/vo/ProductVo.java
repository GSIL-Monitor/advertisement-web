package com.yuanshanbao.dsp.product.model.vo;

import java.math.BigDecimal;
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
	private String detailImageUrl;
	private String bigImageUrl;
	private String schoolTime;
	private String brandFeature;
	private Integer minAge;
	private Integer maxAge;
	private BigDecimal totalAmount;
	private BigDecimal discountAmount;
	private Integer status;
	private Long applyCount;
	private List<TagsVo> featureTagsList;
	/**
	 * 产品推荐标签
	 */
	private List<TagsVo> recommendTagsList;
	private List<TagsVo> detailTagsList;
	private List<TagsVo> detailImageTagsList;

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
		this.bigImageUrl = product.getBigImageUrl();
		this.detailImageUrl = product.getDetailImageUrl();
		this.featureTagsList = product.getFetureTags();
		this.recommendTagsList = getRecommendTagsList(product.getRecommendTags());
		this.detailTagsList = getRecommendTagsList(product.getDetailTags());
		this.detailImageTagsList = getRecommendTagsList(product.getDetailImageTags());
		this.detailImageTagsList = getRecommendTagsList(product.getDetailImageTags());
		this.setApplyCount(product.getApplyCount());
		this.minAge = product.getMinAge();
		this.maxAge = product.getMaxAge();
		this.setTotalAmount(product.getTotalAmount());
		this.discountAmount = product.getDiscountAmount();
		this.schoolTime = getDivideDetail(product.getSchoolTime());
		this.brandFeature = getDivideDetail(product.getBrandFeature());
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

	public Long getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Long applyCount) {
		this.applyCount = applyCount;
	}

	public String getBigImageUrl() {
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}

	public List<TagsVo> getDetailTagsList() {
		return detailTagsList;
	}

	public void setDetailTagsList(List<TagsVo> detailTagsList) {
		this.detailTagsList = detailTagsList;
	}

	public List<TagsVo> getDetailImageTagsList() {
		return detailImageTagsList;
	}

	public void setDetailImageTagsList(List<TagsVo> detailImageTagsList) {
		this.detailImageTagsList = detailImageTagsList;
	}

	public String getSchoolTime() {
		return schoolTime;
	}

	public void setSchoolTime(String schoolTime) {
		this.schoolTime = schoolTime;
	}

	public String getBrandFeature() {
		return brandFeature;
	}

	public void setBrandFeature(String brandFeature) {
		this.brandFeature = brandFeature;
	}

	private String getDivideDetail(String value) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		String[] args = value.split(",");
		StringBuffer sb = new StringBuffer();
		for (String s : args) {
			sb.append(s + "\r\n");
		}
		return sb.toString();
	}

	public String getDetailImageUrl() {
		return detailImageUrl;
	}

	public void setDetailImageUrl(String detailImageUrl) {
		this.detailImageUrl = detailImageUrl;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
