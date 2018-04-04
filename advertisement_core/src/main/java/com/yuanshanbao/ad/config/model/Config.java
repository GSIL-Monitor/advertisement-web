package com.yuanshanbao.ad.config.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.merchant.model.Merchant;

public class Config {

	private Long configId;
	private Long activityId;
	private Long merchantId;
	private Long productId;
	private String appKey;
	private String channel;
	private Long functionId;
	private Boolean decision;
	private String action;

	private Integer sort;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Function function;
	private Activity activity;
	private Merchant merchant;

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public Boolean getDecision() {
		return decision;
	}

	public void setDecision(Boolean decision) {
		this.decision = decision;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public String getFunctionKey() {
		if (function != null) {
			return function.getKey();
		}
		return null;
	}

	public boolean isMatch(Long activityId, String channel, String appKey, Long merchantId, Long productId,
			Long functionId) {
		return isNullOrEquals(this.activityId, activityId) && isNullOrEquals(this.channel, channel)
				&& isNullOrEquals(this.merchantId, merchantId) && isNullOrEquals(this.productId, productId)
				&& isNullOrEquals(this.functionId, functionId) && isNullOrEquals(this.appKey, appKey);
	}

	public boolean isMatch(Long activityId, String channel, String appKey, Long merchantId, Long productId) {
		return isMatch(activityId, channel, appKey, merchantId, productId, null);
	}

	public boolean isMatch(Long activityId, String channel, String appKey, Long merchantId) {
		return isMatch(activityId, channel, appKey, merchantId, null, null);
	}

	public boolean isMatch(Long activityId, String channel, String appKey) {
		return isMatch(activityId, channel, appKey, null, null, null);
	}

	public boolean isMatchWithoutFunction(Long activityId, String channel, String appKey, Long merchantId,
			Long productId) {
		return isNullOrEquals(this.activityId, activityId) && isNullOrEquals(this.channel, channel)
				&& isNullOrEquals(this.merchantId, merchantId) && isNullOrEquals(this.productId, productId)
				&& isNullOrEquals(this.appKey, appKey);
	}

	public boolean isMatchWithoutFunction(Long activityId, String channel, String appKey, Long merchantId) {
		return isMatchWithoutFunction(activityId, channel, appKey, merchantId, null);
	}

	public boolean isMatchWithoutFunction(Long activityId, String channel, String appKey) {
		return isMatchWithoutFunction(activityId, channel, appKey, null, null);
	}

	private boolean isNullOrEquals(Long value1, Long value2) {
		if (value1 != null) {
			return value1.equals(value2);
		} else {
			return true;
		}
	}

	private boolean isNullOrEquals(String content1, String content2) {
		if (StringUtils.isNotBlank(content1)) {
			return content1.equals(content2);
		} else {
			return true;
		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void calculateSort() {

		int sort = 0;

		if (this.activityId != null) {
			sort++;
		}
		if (this.merchantId != null) {
			sort++;
		}
		if (this.productId != null) {
			sort++;
		}
		if (this.appKey != null) {
			sort++;
		}
		if (StringUtils.isNotBlank(this.channel)) {
			sort++;
		}
		this.sort = sort;
	}

}
