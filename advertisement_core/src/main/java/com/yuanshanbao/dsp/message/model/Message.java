package com.yuanshanbao.dsp.message.model;

import com.yuanshanbao.common.util.DateUtils;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/26.
 */
public class Message {
	private Long messageId;
	private Long userId;
	private String description;
	private Timestamp createTime;
	private Long productId;
	private String title;
	private String bigTitle;
	private String details;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBigTitle() {
		return bigTitle;
	}

	public void setBigTitle(String bigTitle) {
		this.bigTitle = bigTitle;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		messageId = messageId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public  String getCreateTimeValue(){
		return DateUtils.format(createTime,DateUtils.DEFAULT_DATE_FORMAT);
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
