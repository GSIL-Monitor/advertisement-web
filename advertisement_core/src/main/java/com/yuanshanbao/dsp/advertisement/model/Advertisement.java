package com.yuanshanbao.dsp.advertisement.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;

public class Advertisement {

	public static final String JUMPER_URL = "/i/common";
	public static final String APP_URL_PREFIX = "xiaoketang://";
	public static final String HOST = PropertyUtil.getProperty("loan.host");

	public static final String WAP_CONTENT_KEY = "wap";
	public static final String APP_CONTENT_KEY = "app";

	public static final int IS_ANDROID = 0;
	public static final int IS_IOS = 1;
	public static final int IS_WAP = 4;

	private String appUrl;
	private Integer showType;
	private Long cycleTime;
	private Long count;
	private String position;

	private Long advertisementId;
	private Long projectId;
	private Long advertiserId;
	private String title;
	private String subTitle;
	private String link;
	private String imageUrl;
	private String bigImageUrl;
	private String description;
	private String buttonName;
	private Long category;
	private Integer status;
	private Integer type;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Advertiser advertiser;

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBigImageUrl() {
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
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
		return AdvertisementStatus.getDescription(status);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeContent() {
		return AdvertisementType.getDescription(type);
	}

	public void addChannelToLink(String channel) {
		if (channel == null) {
			channel = "";
		}
		if (link.contains("?")) {
			link += "&channel=" + channel;
		} else {
			link += "?channel=" + channel;
		}
	}

	public String getJumperLink(String position, String channel) {
		String link = JUMPER_URL + "?position=" + position + "&id=" + advertisementId;
		if (StringUtils.isNotBlank(channel)) {
			link = link + "&channel=" + channel;
		}
		return link;
	}

	public String getAppLink(String position, String channel) {
		if (appUrl == null || !appUrl.contains(APP_URL_PREFIX)) {
			return HOST + getJumperLink(position, channel);
		}
		String link = appUrl + "&position=" + position + "&adStat=true&id=" + advertisementId;
		if (StringUtils.isNotBlank(channel)) {
			link = link + "&channel=" + channel;
		}
		return link;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(Long cycleTime) {
		this.cycleTime = cycleTime;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

}
