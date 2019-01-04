package com.yuanshanbao.dsp.product.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

/**
 * 
 * @description
 *
 * @author
 */
public class Product {

	// ~ Field
	// =============================================================================================

	private Long productId;

	private Long merchantId;

	private Long activityId;

	private String productKey;

	private String name;

	private String title;

	private String description;

	private String imageUrl;

	private String smallImageUrl;

	private String bigImageUrl;

	private String queryUrl;

	private String detailImageUrl;

	private String featureTags;

	private String recommendTags;

	private String detailTags;

	private String detailImageTags;

	private String authorizeTags;

	private String schoolTime;

	private String brandFeature;

	private BigDecimal totalAmount;

	private BigDecimal discountAmount;

	private Integer minAge;

	private Integer maxAge;

	private String requirements;

	private String applyFlow;

	private String advantage;

	private String applyInterface;

	private String guide;

	private String deliverOrderUrl;

	private Integer type;

	private Integer sort;

	private Long applyCount;

	private Integer positiveRate;

	private String workOrderImageUrl;

	private Integer category;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;

	private Merchant merchant;

	private BigDecimal brokerage;
	/**
	 * 查询排序条件
	 */
	private Integer sortCondition;
	private List<Long> authorize;
	private List<Long> feature;

	private Integer queryAge;

	// ~ Get and Set Methods
	// =================================================================================

	public String getQueryUrl() {
		return queryUrl;
	}

	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public String getBrokerageValue() {
		if (brokerage != null) {
			NumberFormat nt = NumberFormat.getPercentInstance();
			if (brokerage.compareTo(BigDecimal.valueOf(0.1)) == -1) {
				nt.setMinimumFractionDigits(2);
				LoggerUtil.info(" getBrokerageValue = " + nt.format(brokerage));
				return nt.format(brokerage);
			} else if (brokerage.compareTo(BigDecimal.valueOf(1)) == -1) {
				return nt.format(brokerage);
			}
			return brokerage.stripTrailingZeros().toPlainString();
		}
		return String.valueOf(brokerage);
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public Long getProductId() {
		return productId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
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
		if (StringUtils.isNotBlank(imageUrl)) {
			return imageUrl + "?time=" + Long.toString(System.currentTimeMillis() / 1000);
		}
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFeatureTags() {
		return featureTags;
	}

	public void setFeatureTags(String featureTags) {
		this.featureTags = featureTags;
	}

	public String getRecommendTags() {
		return recommendTags;
	}

	public void setRecommendTags(String recommendTags) {
		this.recommendTags = recommendTags;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getAuthorizeTags() {
		return authorizeTags;
	}

	public void setAuthorizeTags(String authorizeTags) {
		this.authorizeTags = authorizeTags;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getApplyFlow() {
		return applyFlow;
	}

	public void setApplyFlow(String applyFlow) {
		this.applyFlow = applyFlow;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
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

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public List<TagsVo> getFetureTags() {
		if (StringUtils.isBlank(featureTags)) {
			return null;
		}
		String[] ids = featureTags.split(",");
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

	public String getApplyInterface() {
		return applyInterface;
	}

	public void setApplyInterface(String applyInterface) {
		this.applyInterface = applyInterface;
	}

	public Long getActivityId() {
		return activityId;
	}

	public String getActivityIdValue() {
		if (activityId != null) {
			return ConfigManager.getActivityById(activityId).getName();
		}
		return null;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Integer getSortCondition() {
		return sortCondition;
	}

	public void setSortCondition(Integer sortCondition) {
		this.sortCondition = sortCondition;
	}

	public List<Long> getAuthorize() {
		if (StringUtils.isNotBlank(authorizeTags)) {
			String[] ids = authorizeTags.split(",");
			if (ids == null || ids.length == 0) {
				return null;
			}
			for (String id : ids) {
				authorize = new ArrayList<Long>();
				authorize.add(Long.parseLong(id));
			}
		}
		return authorize;
	}

	public List<Long> getFeature() {
		if (StringUtils.isNotBlank(featureTags)) {
			String[] ids = featureTags.split(",");
			if (ids == null || ids.length == 0) {
				return null;
			}
			for (String id : ids) {
				feature = new ArrayList<Long>();
				feature.add(Long.parseLong(id));
			}
		}
		return feature;
	}

	public Long getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Long applyCount) {
		this.applyCount = applyCount;
	}

	public Integer getQueryAge() {
		return queryAge;
	}

	public void setQueryAge(Integer queryAge) {
		this.queryAge = queryAge;
	}

	public String getBigImageUrl() {
		if (StringUtils.isNotBlank(bigImageUrl)) {
			return bigImageUrl + "?bigTime=" + Long.toString(System.currentTimeMillis() / 1000);
		}
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}

	public String getDetailTags() {
		return detailTags;
	}

	public void setDetailTags(String detailTags) {
		this.detailTags = detailTags;
	}

	public String getDetailImageTags() {
		return detailImageTags;
	}

	public void setDetailImageTags(String detailImageTags) {
		this.detailImageTags = detailImageTags;
	}

	public String[] getSchoolTimeValue() {
		if (schoolTime != null) {
			String[] getValue = schoolTime.split(",");
			return getValue;
		}
		return null;
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

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getDeliverOrderUrl() {
		return deliverOrderUrl;
	}

	public void setDeliverOrderUrl(String deliverOrderUrl) {
		this.deliverOrderUrl = deliverOrderUrl;
	}

	public String getSchoolTime() {
		return schoolTime;
	}
}