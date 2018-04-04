package com.yuanshanbao.ad.location.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.merchant.model.Merchant;

public class MerchantLocation {

	private Long merchantLocationId;
	private Long merchantId;
	private String locationCode;
	private String locationName;
	private String callCenter;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Merchant merchant;

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Long getMerchantLocationId() {
		return merchantLocationId;
	}

	public void setMerchantLocationId(Long merchantLocationId) {
		this.merchantLocationId = merchantLocationId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getCallCenter() {
		return callCenter;
	}

	public void setCallCenter(String callCenter) {
		this.callCenter = callCenter;
	}
	
}
