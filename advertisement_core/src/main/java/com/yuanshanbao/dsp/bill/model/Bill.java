package com.yuanshanbao.dsp.bill.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;

public class Bill {
	private Long billId;
	private Long advertiserId;
	private Long probabilityId;
	private Long planId;
	private Long orderId;
	private String channel;
	private String date;
	private BigDecimal amount;
	private BigDecimal nowCount;
	private BigDecimal lastCount;
	private String description;
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private String queryStartTime;
	private String queryEndTime;
	private Advertiser advertiser;

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public Long getProbabilityId() {
		return probabilityId;
	}

	public void setProbabilityId(Long probabilityId) {
		this.probabilityId = probabilityId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public String getTypeContent() {
		return BillType.getDescription(type);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getNowCount() {
		return nowCount;
	}

	public void setNowCount(BigDecimal nowCount) {
		this.nowCount = nowCount;
	}

	public BigDecimal getLastCount() {
		return lastCount;
	}

	public void setLastCount(BigDecimal lastCount) {
		this.lastCount = lastCount;
	}

}
