package com.yuanshanbao.ad.banner.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;

/**
 * Created by Ynght on 2016/10/24.
 */
public class Banner {
	private Integer bannerId;
	private String title;
	private String imgUrl;
	private String detailUrl;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer weight;
	private Integer actionType;// 0 : wap //1:native
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Banner() {

	}

	public Banner(Integer bannerId, String title, String imgUrl, String detailUrl, Timestamp startTime,
			Timestamp endTime, Integer weight, Integer actionType, Integer type, Integer status) {
		this.bannerId = bannerId;
		this.title = title;
		this.imgUrl = imgUrl;
		this.detailUrl = detailUrl;
		this.startTime = startTime;
		this.endTime = endTime;
		this.weight = weight;
		this.actionType = actionType;
		this.type = type;
		this.status = status;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
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

	public String getStartTimeContent() {
		return DateUtils.format(startTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getEndTimeContent() {
		return DateUtils.format(endTime, "yyyy-MM-dd HH:mm:ss");
	}
}
