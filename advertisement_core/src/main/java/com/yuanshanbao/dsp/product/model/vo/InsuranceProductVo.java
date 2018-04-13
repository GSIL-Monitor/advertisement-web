package com.yuanshanbao.dsp.product.model.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.product.model.InsuranceProduct;
import com.yuanshanbao.dsp.product.model.ProductBindType;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class InsuranceProductVo {

	private static final String DETAIL_URL = "www.sohu.com";

	// 产品ID
	private Long productId;
	// 产品名称
	private String name;
	// 产品标题
	private String title;
	// 产品描述
	private String description;
	// 产品Logo
	private String imageUrl;
	// 产品推荐标签
	private List<TagsVo> recommendTagsList;
	// 产品好评率
	private Integer positiveRate;
	// 产品状态
	private Integer status;
	// 产品绑定标签
	private TagsVo bindTags;
	// 产品包含图片
	private String productIncludeUrl;
	private int width;
	private int height;
	// 详情跳转链接
	private String detailUrl;

	public InsuranceProductVo(InsuranceProduct product) {
		if (product == null) {
			return;
		}
		this.productId = product.getProductId();
		this.name = product.getName();
		this.title = product.getTitle();
		this.description = product.getDescription();
		Merchant merchant = product.getMerchant();
		if (merchant != null) {
			this.imageUrl = merchant.getImageUrl();
		}
		this.positiveRate = product.getPositiveRate();
		this.status = product.getStatus();
		this.productIncludeUrl = getProductIncludeUrl(product.getProductIncludeUrl());
		this.detailUrl = getDetailUrl();
		this.bindTags = getBindTags(product.getBindStatus());
		this.recommendTagsList = getRecommendTagsList(product.getRecommendTags());
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

	public List<TagsVo> getRecommendTagsList() {
		return recommendTagsList;
	}

	public void setRecommendTagsList(List<TagsVo> recommendTagsList) {
		this.recommendTagsList = recommendTagsList;
	}

	public Integer getPositiveRate() {
		return positiveRate;
	}

	public void setPositiveRate(Integer positiveRate) {
		this.positiveRate = positiveRate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public TagsVo getBindTags() {
		return bindTags;
	}

	public void setBindTags(TagsVo bindTags) {
		this.bindTags = bindTags;
	}

	private TagsVo getBindTags(Integer bindStatus) {
		if (bindStatus != null) {
			if (bindStatus.equals(ProductBindType.BIND)) {
				this.bindTags = new TagsVo(ConstantsManager.getTags(101L));
			} else if (bindStatus.equals(ProductBindType.UNBIND)) {
				this.bindTags = new TagsVo(ConstantsManager.getTags(102L));
			}
		}
		return bindTags;
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

	public String getProductIncludeUrl(String productIncludeUrl) {
		if (StringUtils.isNotBlank(productIncludeUrl)) {
			String[] str = productIncludeUrl.split("_");
			if (str.length > 1) {
				String[] measurements = str[1].split("X");
				if (ValidateUtil.isNumber(measurements[0])) {
					width = Integer.parseInt(measurements[0]);
				}
				measurements[1] = measurements[1].replace(".png", "");
				if (ValidateUtil.isNumber(measurements[1])) {
					height = Integer.parseInt(measurements[1]);
				}
			}
			this.productIncludeUrl = productIncludeUrl;
		}
		return this.productIncludeUrl;
	}

	public String getProductIncludeUrl() {
		return productIncludeUrl;
	}

	public void setProductIncludeUrl(String productIncludeUrl) {
		this.productIncludeUrl = productIncludeUrl;
	}

	public String getDetailUrl() {
		if (StringUtils.isBlank(detailUrl)) {
			detailUrl = DETAIL_URL + "?id=" + productId;
		}
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
