package com.yuanshanbao.dsp.agency.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;

/**
 * Created by Administrator on 2018/10/22.
 */
public class Agency implements Serializable {
	private static final long serialVersionUID = 4165445835251317169L;
	private Long id;
	private Long inviteUserId;
	private Long userId;
	private String agencyName;
	private String name;
	private String mobile;
	private Long productId;
	private String productName;
	private BigDecimal brokerage;
	private Integer type;
	private Integer status; // 0:待审核 1:审核通过 2:审核未通过
	private Timestamp inviteTime;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public String getMobile() {
		return mobile;
	}

	public String getHideMobile() {
		if (mobile != null) {
			return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public String getUpdateTimeValue() {
		return DateUtils.format(updateTime, DateUtils.DEFAULT_DATE_FORMAT);
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
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

	public String getHideName() {
		if (name != null) {
			return name.replaceAll(name.substring(1, name.length()), "****");
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInviteTime(Timestamp inviteTime) {
		this.inviteTime = inviteTime;
	}

	public String getInviteTimeValue() {
		return DateUtils.format(inviteTime, DateUtils.DEFAULT_DATE_FORMAT);
	}

	public String getStatusValue() {
		return AgencyStatus.getDescription(status);
	}

	public String getBrokerageValue() {
		return String.valueOf(brokerage);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
