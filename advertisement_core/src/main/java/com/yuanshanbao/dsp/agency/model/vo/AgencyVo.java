package com.yuanshanbao.dsp.agency.model.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/15.
 */
public class AgencyVo {
	private Long inviteUserId;
	private Long userId;
	private String agencyName;
	private String name;
	private String mobile;
	private Long productId;
	private String productName;
	private BigDecimal brokerage;
	private String status; // 0:待审核 1:审核通过 2:审核未通过
	private String userLevel;
	private String inviteUserLevel;
	private String inviteTime;
	private String createTime;
	private String updateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getInviteTimeValue() {
		return inviteTime;
	}

	public void setInviteTime(String inviteTime) {
		this.inviteTime = inviteTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTimeValue() {
		return updateTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getInviteTime() {
		return inviteTime;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public String getBrokerageValue() {
		return String.valueOf(brokerage);
	}

	public String getStatus() {
		return status;
	}

	public String getStatusValue() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getInviteUserLevel() {
		return inviteUserLevel;
	}

	public void setInviteUserLevel(String inviteUserLevel) {
		this.inviteUserLevel = inviteUserLevel;
	}

}
