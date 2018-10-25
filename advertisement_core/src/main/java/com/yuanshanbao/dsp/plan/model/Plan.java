package com.yuanshanbao.dsp.plan.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.order.model.Order;

public class Plan {

	private Long planId;
	private Long projectId;
	private Long orderId;
	private Long advertiserId;
	private BigDecimal spend;
	private String name;
	private String url;
	private String creative;
	private Integer chargeType;
	private BigDecimal bestBid;
	private String allowChannelCategory;
	private String forbidChannelCategory;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Activity activity;
	private Channel channelObject;
	private Order order;
	private Advertiser advertiser;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setStartTimeValue(String startTimeValue) {
		this.startTime = DateUtils.formatToTimestamp(startTimeValue, "yyyy-MM-dd HH:mm");
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setEndTimeValue(String endTimeValue) {
		this.endTime = DateUtils.formatToTimestamp(endTimeValue, "yyyy-MM-dd HH:mm");
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

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Channel getChannelObject() {
		return channelObject;
	}

	public void setChannelObject(Channel channelObject) {
		this.channelObject = channelObject;
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getSpend() {
		return spend;
	}

	public void setSpend(BigDecimal spend) {
		this.spend = spend;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public String getCreative() {
		return creative;
	}

	public void setCreative(String creative) {
		this.creative = creative;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public String getAllowChannelCategory() {
		return allowChannelCategory;
	}

	public void setAllowChannelCategory(String allowChannelCategory) {
		this.allowChannelCategory = allowChannelCategory;
	}

	public String getForbidChannelCategory() {
		return forbidChannelCategory;
	}

	public void setForbidChannelCategory(String forbidChannelCategory) {
		this.forbidChannelCategory = forbidChannelCategory;
	}

	public BigDecimal getBestBid() {
		return bestBid;
	}

	public void setBestBid(BigDecimal bestBid) {
		this.bestBid = bestBid;
	}
}
