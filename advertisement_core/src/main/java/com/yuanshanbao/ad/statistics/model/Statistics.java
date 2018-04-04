package com.yuanshanbao.ad.statistics.model;

import java.sql.Timestamp;
import java.util.List;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.merchant.model.Merchant;

public class Statistics {

	private Long statisticsId;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 商家
	 */
	private Long merchantId;
	/**
	 * 渠道
	 */
	private Long productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * pv
	 */
	private Integer pvCount = 0;
	/**
	 * uv
	 */
	private Integer uvCount = 0;
	/**
	 * 注册数
	 */
	private Integer registerCount = 0;
	/**
	 * 申请人次
	 */
	private Integer applyCount = 0;
	/**
	 * 申请人数
	 */
	private Integer applyUserCount = 0;
	/**
	 * 申请成功人数
	 */
	private Integer applySuccessCount = 0;
	/**
	 * 下载数
	 */
	private Integer downloadCount = 0;
	/**
	 * 首次登陆数
	 */
	private Integer firstLoginCount = 0;
	/**
	 * 详情uv
	 */
	private Integer productDetailUvCount = 0;
	/**
	 * 类型
	 */
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Merchant merchant;
	private String queryChannel;
	private String queryDate;
	private List<String> channelList;

	public Long getStatisticsId() {
		return statisticsId;
	}

	public void setStatisticsId(Long statisticsId) {
		this.statisticsId = statisticsId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
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

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public List<String> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}

	public String getQueryChannel() {
		return queryChannel;
	}

	public void setQueryChannel(String queryChannel) {
		this.queryChannel = queryChannel;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public Integer getPvCount() {
		return pvCount;
	}

	public void setPvCount(Integer pvCount) {
		this.pvCount = pvCount;
	}

	public Integer getUvCount() {
		return uvCount;
	}

	public void setUvCount(Integer uvCount) {
		this.uvCount = uvCount;
	}

	public Integer getFirstLoginCount() {
		return firstLoginCount;
	}

	public void setFirstLoginCount(Integer firstLoginCount) {
		this.firstLoginCount = firstLoginCount;
	}

	public Integer getApplyUserCount() {
		return applyUserCount;
	}

	public void setApplyUserCount(Integer applyUserCount) {
		this.applyUserCount = applyUserCount;
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime);
	}

	public String getStatusValue() {
		return StatisticsStatus.getTypeDescription(status);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductDetailUvCount() {
		return productDetailUvCount;
	}

	public void setProductDetailUvCount(Integer productDetailUvCount) {
		this.productDetailUvCount = productDetailUvCount;
	}

	public Integer getApplySuccessCount() {
		return applySuccessCount;
	}

	public void setApplySuccessCount(Integer applySuccessCount) {
		this.applySuccessCount = applySuccessCount;
	}

	/**
	 * 平均人数
	 * 
	 * @return
	 */
	public Double getSubmitPercent() {
		if (this.applyCount == null || this.applyUserCount == null) {
			return null;
		}
		return (double) this.applyCount / this.applyUserCount;
	}

	public void add(Statistics statistics) {
		if (statistics == null) {
			return;
		}

		applyCount += statistics.applyCount;
		registerCount += statistics.registerCount;
		downloadCount += statistics.downloadCount;
		pvCount += statistics.pvCount;
		uvCount += statistics.uvCount;
		firstLoginCount += statistics.firstLoginCount;
		applyUserCount += statistics.applyUserCount;
		applySuccessCount += statistics.applySuccessCount;
		productDetailUvCount += statistics.getProductDetailUvCount();
	}

	public void calculateBonus(Double bonus) {
		if (bonus >= 1L) {
			return;
		}
		uvCount = (int) Math.ceil(bonus * uvCount);
		applyCount = (int) Math.ceil(bonus * applyCount);
		applyUserCount = (int) Math.ceil(bonus * applyUserCount);
		applySuccessCount = (int) Math.ceil(bonus * applySuccessCount);
		registerCount = (int) Math.ceil(bonus * registerCount);
		downloadCount = (int) Math.ceil(bonus * downloadCount);
		firstLoginCount = (int) Math.ceil(bonus * firstLoginCount);
		productDetailUvCount = (int) Math.ceil(bonus * productDetailUvCount);
	}

	public void clearParameter() {
		if (pvCount == 0) {
			pvCount = null;
		}
		if (uvCount == 0) {
			uvCount = null;
		}
		if (downloadCount == 0) {
			downloadCount = null;
		}
		if (registerCount == 0) {
			registerCount = null;
		}
		if (applyCount == 0) {
			applyCount = null;
		}
		if (applyUserCount == 0) {
			applyUserCount = null;
		}
		if (firstLoginCount == 0) {
			firstLoginCount = null;
		}
		if (productDetailUvCount == 0) {
			productDetailUvCount = null;
		}
		if (applySuccessCount == 0) {
			applySuccessCount = null;
		}
	}

}
