package com.yuanshanbao.dsp.plan.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.order.model.Order;

public class Plan {

	private Long planId;
	private Long projectId;
	private Long orderId;
	private Long advertiserId;
	private BigDecimal spend;
	private BigDecimal dayLimit;
	private String consumed;
	private String name;
	private String link;
	private String material;
	private Integer chargeType;
	private BigDecimal bestBid;
	private String allowChannelCategory;
	private String forbidChannelCategory;
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp dayStartTime;
	private Timestamp dayEndTime;
	private Integer status;
	private String remark;
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
		return PlanStatus.getDescription(status);
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

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public String getConsumed() {
		return consumed;
	}

	public void setConsumed(String consumed) {
		this.consumed = consumed;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public BigDecimal getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(BigDecimal dayLimit) {
		this.dayLimit = dayLimit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getDayStartTime() {
		return dayStartTime;
	}

	public void setDayStartTime(Timestamp dayStartTime) {
		this.dayStartTime = dayStartTime;
	}

	public void setDayStartTimeValue(String dayStartTimeValue) {
		this.dayStartTime = DateUtils.formatToTimestamp(dayStartTimeValue, "yyyy-MM-dd HH:mm");
	}

	public Timestamp getDayEndTime() {
		return dayEndTime;
	}

	public void setDayEndTime(Timestamp dayEndTime) {
		this.dayEndTime = dayEndTime;
	}

	public void setDayEndTimeValue(String dayEndTimeValue) {
		this.dayEndTime = DateUtils.formatToTimestamp(dayEndTimeValue, "yyyy-MM-dd HH:mm");
	}
}
