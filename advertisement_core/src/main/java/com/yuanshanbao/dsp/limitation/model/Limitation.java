package com.yuanshanbao.dsp.limitation.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.quota.model.Quota;

public class Limitation {

	private Long limitationId;
	private Long merchantId;
	private Long insuranceId;
	private Long productId;
	private String channel;
	private String locationCode;
	private Integer type;
	private Integer quota;
	private Integer stock;
	private String description;
	private Integer status;
	private Timestamp createTime;

	private Timestamp updateTime;

	private Merchant merchant;
	private Product product;

	// 已完成
	private Integer completedCount = 0;

	public Long getLimitationId() {
		return limitationId;
	}

	public void setLimitationId(Long limitationId) {
		this.limitationId = limitationId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(Long insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String location) {
		this.locationCode = location;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public boolean equalsQuotaCondition(Quota quota) {
		if (!equalsContent(channel, quota.getChannel())) {
			return false;
		}
		if (productId != null && !productId.equals(quota.getProductId())) {
			return false;
		}
		if (quota.getLocation() == null || !quota.getLocation().contains(locationCode)) {
			return false;
		}
		return true;
	}

	private boolean equalsContent(String content1, String content2) {
		if (StringUtils.isNotBlank(content1)) {
			if (content1.equals(content2)) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	public String getTypeValue() {
		return LimitationType.getDescription(type);
	}

	public String getStatusValue() {
		return LimitationType.getStatusDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
