package com.yuanshanbao.dsp.channel.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.tags.model.Tags;

public class Channel {

	/**
	 * 渠道ID
	 */
	private Long channelId;
	private Long appId;
	/**
	 * 渠道key值
	 */
	private String key;

	private Long activityId;

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
	 * 状态
	 */
	private Integer status;

	/**
	 * 状态
	 */
	private Integer allocateType;
	private Integer independent;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	private String deliverOrderUrl;
	private Double unitPrice;

	private Timestamp updateTime;
	private Activity activity;

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

	public String getDeliverOrderUrl() {
		return deliverOrderUrl;
	}

	public void setDeliverOrderUrl(String deliverOrderUrl) {
		this.deliverOrderUrl = deliverOrderUrl;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getAllocateType() {
		return allocateType;
	}

	public void setAllocateType(Integer allocateType) {
		this.allocateType = allocateType;
	}

	public String getAllocateTypeValue() {
		return ChannelAllocateStatus.getDescription(allocateType);
	}

	public Integer getIndependent() {
		return independent;
	}

	public void setIndependent(Integer independent) {
		this.independent = independent;
	}

	public boolean checkIndependent() {
		if (independent == null) {
			return false;
		}
		if (independent.equals(0)) {
			return false;
		} else {
			return true;
		}
	}

}
