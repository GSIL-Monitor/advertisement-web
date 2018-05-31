package com.yuanshanbao.dsp.activity.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;

public class ActivityCombine {
	private Long activityCombineId;
	private Long parentId;
	private Long activityId;
	private Long channelId;
	private Integer sort;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Activity activity;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Long getActivityCombineId() {
		return activityCombineId;
	}

	public void setActivityCombineId(Long activityCombineId) {
		this.activityCombineId = activityCombineId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
