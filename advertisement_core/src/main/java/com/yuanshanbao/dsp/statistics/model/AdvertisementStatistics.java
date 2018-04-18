package com.yuanshanbao.dsp.statistics.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;

public class AdvertisementStatistics {

	private Long advertisementStatisticsId;
	private String date;
	private Long advertisementId;
	private Long positionId;
	private String positionName;
	private String channel;
	private Integer downloadCount;
	private Integer exposureCount;
	private String clickRate;
	private Integer clickCount;
	private BigDecimal totalAmount;
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private BigDecimal avgPrice;
	private Integer total;
	
	private String queryStartTime;
	private String queryEndTime;
	
	
	private Advertisement advertisement;

	public AdvertisementStatistics(Long advertisementId) {
		super();
		this.advertisementId = advertisementId;
		this.exposureCount = 0;
		this.setTotalAmount(new BigDecimal(0));
		this.clickCount = 0;
		this.downloadCount = 0;
		this.total = 0;
	}
	
	public AdvertisementStatistics() {
		super();
		this.exposureCount = 0;
		this.setTotalAmount(new BigDecimal(0));
		this.clickCount = 0;
		this.downloadCount = 0;
		this.total = 0;
	}

	public Long getAdvertisementStatisticsId() {
		return advertisementStatisticsId;
	}

	public void setAdvertisementStatisticsId(Long advertisementStatisticsId) {
		this.advertisementStatisticsId = advertisementStatisticsId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}



	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void addDownloadCount(Integer downloadCount) {
		this.downloadCount = this.downloadCount + downloadCount;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
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

	public Integer getExposureCount() {
		return exposureCount;
	}

	public void setExposureCount(Integer exposureCount) {
		this.exposureCount = exposureCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}
	
}
