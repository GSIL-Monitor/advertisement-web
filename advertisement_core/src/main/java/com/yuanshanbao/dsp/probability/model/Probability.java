package com.yuanshanbao.dsp.probability.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.core.CommonStatus;

public class Probability {

	private Long probabilityId;
	private Long projectId;
	private Long activityId;
	private String channel;
	private Long positionId;
	private Long advertismentId;
	private Double probability;
	private Integer sort;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Activity activity;
	private Channel channelObject;

	public Long getProbabilityId() {
		return probabilityId;
	}

	public void setProbabilityId(Long probabilityId) {
		this.probabilityId = probabilityId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getAdvertismentId() {
		return advertismentId;
	}

	public void setAdvertismentId(Long advertismentId) {
		this.advertismentId = advertismentId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
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
}
