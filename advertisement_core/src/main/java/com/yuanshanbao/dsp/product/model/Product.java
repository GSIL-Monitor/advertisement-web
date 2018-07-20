package com.yuanshanbao.dsp.product.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
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

	private String featureTags;

	private String recommendTags;

	private String authorizeTags;

	private String requirements;

	private String applyFlow;

	private String advantage;

	private String applyInterface;

	private String guide;

	private Integer type;

	private Integer sort;

	private Long productCount;

	private Integer positiveRate;

	private String workOrderImageUrl;

	private Integer category;

	private Integer status;

	private Integer BindStatus;

	private Timestamp createTime;

	private Timestamp updateTime;

	private Merchant merchant;
	/**
	 * 查询排序条件
	 */
	private Integer sortCondition;

	// ~ Get and Set Methods
	// =================================================================================

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

	public Integer getBindStatus() {
		return BindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		BindStatus = bindStatus;
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

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public Long getActivityId() {
		return activityId;
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

}