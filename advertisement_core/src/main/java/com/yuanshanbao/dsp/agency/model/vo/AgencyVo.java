package com.yuanshanbao.dsp.agency.model.vo;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.yuanshanbao.dsp.agency.model.Agency;

/**
 * Created by Administrator on 2018/11/15.
 */
public class AgencyVo {
	private Long inviteUserId;
	private Long userId;
	private String indirectUserId;
	private String agencyName;
	private String name;
	private String mobile;
	private Long productId;
	private String productName;
	private BigDecimal brokerage;
	private String type;
	private String status; // 0:待审核 1:审核通过 2:审核未通过
	private String userLevel;
	private String inviteUserLevel;
	private String inviteTime;
	private String createTime;
	private String updateTime;

	public AgencyVo() {

	}

	public AgencyVo(Agency agency) {
		if (agency == null) {
			return;
		}

		this.inviteUserId = agency.getInviteUserId();
		this.userId = agency.getUserId();
		this.agencyName = agency.getAgencyName();
		this.name = agency.getName();
		this.mobile = agency.getMobile();
		this.productId = agency.getProductId();
		this.productName = agency.getProductName();
		this.brokerage = agency.getBrokerage();
		this.status = agency.getStatusValue();
		this.type = agency.getTypeValue();
		this.inviteTime = agency.getInviteTimeValue();
		this.updateTime = agency.getUpdateTimeValue();
	}

	public String getType() {
		if (StringUtils.isBlank(type)) {
			return "";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
		if (StringUtils.isNotBlank(name)) {
			if (name.length() > 3) {
				return name.substring(0, 3);
			}
		}
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
		if (StringUtils.isNotBlank(productName)) {
			if (productName.length() > 6) {
				return productName.substring(0, 6);
			}
		}
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

	public String getIndirectUserId() {
		return indirectUserId;
	}

	public void setIndirectUserId(String indirectUserId) {
		this.indirectUserId = indirectUserId;
	}

}
