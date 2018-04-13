package com.yuanshanbao.dsp.location.model;

import java.sql.Timestamp;

public class IpLocation {

	private Long ipLocationId;
	private String prefix;
	private String locationCode;
	private String operator;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getIpLocationId() {
		return ipLocationId;
	}

	public void setIpLocationId(Long ipLocationId) {
		this.ipLocationId = ipLocationId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

}
