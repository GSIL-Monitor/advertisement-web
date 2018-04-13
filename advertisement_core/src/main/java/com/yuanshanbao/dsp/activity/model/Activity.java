package com.yuanshanbao.dsp.activity.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.core.CommonStatus;

/**
 * @author Administrator
 *
 */
public class Activity {

	private Long activityId;
	private String name;
	private String key;
	private String entranceUrl;
	private String imageUrl;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEntranceUrl() {
		return entranceUrl;
	}

	public void setEntranceUrl(String entranceUrl) {
		this.entranceUrl = entranceUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

}
