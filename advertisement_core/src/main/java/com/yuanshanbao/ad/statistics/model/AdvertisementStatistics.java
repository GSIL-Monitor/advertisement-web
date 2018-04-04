package com.yuanshanbao.ad.statistics.model;

import java.sql.Timestamp;

import com.yuanshanbao.ad.advertisement.model.Advertisement;

public class AdvertisementStatistics {

	private Long advertisementStatisticsId;
	private String date;
	private Long advertisementId;
	private String channel;
	private Integer welfareCount;
	private Integer bannerCount;
	private Integer tagsCount;
	private Integer downloadCount;
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Integer total;
	
	private Advertisement advertisement;

	public AdvertisementStatistics(Long advertisementId) {
		super();
		this.advertisementId = advertisementId;
		this.welfareCount = 0;
		this.bannerCount = 0;
		this.tagsCount = 0;
		this.downloadCount = 0;
		this.total = 0;
	}
	
	public AdvertisementStatistics() {
		super();
		this.welfareCount = 0;
		this.bannerCount = 0;
		this.tagsCount = 0;
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

	public Integer getWelfareCount() {
		return welfareCount;
	}

	public void setWelfareCount(Integer welfareCount) {
		this.welfareCount = welfareCount;
	}

	public void addWelfareCount(Integer welfareCount) {
		this.welfareCount = this.welfareCount + welfareCount;
	}

	public Integer getBannerCount() {
		return bannerCount;
	}

	public void setBannerCount(Integer bannerCount) {
		this.bannerCount = bannerCount;
	}

	public void addBannerCount(Integer bannerCount) {
		this.bannerCount = this.bannerCount + bannerCount;
	}

	public Integer getTagsCount() {
		return tagsCount;
	}

	public void setTagsCount(Integer tagsCount) {
		this.tagsCount = tagsCount;
	}

	public void addTagsCount(Integer tagsCount) {
		this.tagsCount = this.tagsCount + tagsCount;
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
	
}
