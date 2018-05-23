package com.yuanshanbao.dsp.channel.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.app.model.App;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.tags.model.Tags;

public class Channel {

	/**
	 * 渠道ID
	 */
	private Long channelId;
	/**
	 * 渠道key值
	 */
	private String key;

	private String activityId;
	/**
	 * 软件ID
	 */
	private Long appId;

	private String appKey;

	private String appName;
	/**
	 * 类型，用于分类使用
	 */
	private String type;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 描述
	 */
	private String activityDescription;
	/**
	 * 标记（预留）
	 */
	private String mark;
	/**
	 * 余量
	 */
	private Double bonus;
	/**
	 * 图片链接
	 */
	private String imageUrl;

	/**
	 * 单价
	 */
	private Double unitPrice;

	/**
	 * 统计展示类型
	 */
	private Integer showType;
	/**
	 * 后台管理系统，该渠道查看后台统计 需要的展示字段
	 */
	private String showFields;
	/**
	 * 通知接口
	 */
	private String notifyHandler;
	/**
	 * 接口加密秘钥
	 */
	private String encryptKey;
	/**
	 * 安卓下载链接
	 */
	private String androidDownloadUrl;
	/**
	 * ios下载链接
	 */
	private String iosDownloadUrl;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	private String deliverOrderUrl;

	private Timestamp updateTime;

	private Activity activity;

	private App app;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (ValidateUtil.isNumber(type)) {
			Long tagsId = Long.parseLong(type);
			Tags tags = ConstantsManager.getTags(tagsId);
			if (tags != null) {
				type = tags.getValue();
			}
		}
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getShowFields() {
		return showFields;
	}

	public void setShowFields(String showFields) {
		this.showFields = showFields;
	}

	public String getNotifyHandler() {
		return notifyHandler;
	}

	public void setNotifyHandler(String notifyHandler) {
		this.notifyHandler = notifyHandler;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
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

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAndroidDownloadUrl() {
		return androidDownloadUrl;
	}

	public void setAndroidDownloadUrl(String androidDownloadUrl) {
		this.androidDownloadUrl = androidDownloadUrl;
	}

	public String getIosDownloadUrl() {
		return iosDownloadUrl;
	}

	public void setIosDownloadUrl(String iosDownloadUrl) {
		this.iosDownloadUrl = iosDownloadUrl;
	}

	public String getDeliverOrderUrl() {
		return deliverOrderUrl;
	}

	public void setDeliverOrderUrl(String deliverOrderUrl) {
		this.deliverOrderUrl = deliverOrderUrl;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
