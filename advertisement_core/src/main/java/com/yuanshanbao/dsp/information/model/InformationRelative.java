package com.yuanshanbao.dsp.information.model;

import java.sql.Timestamp;

/**
 * 
 * @description
 *
 * @author 
 */
public class InformationRelative {

	// ~ Field
	// =============================================================================================
	
	private Long relativeInsurantId;

	private String name;

	private String infos;

	private String recommends;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;

	
	// ~ Get and Set Methods
	// =================================================================================
	
	public Long getRelativeInsurantId() {
		return relativeInsurantId;
	}

	public void setRelativeInsurantId(Long relativeInsurantId) {
		this.relativeInsurantId = relativeInsurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public String getRecommends() {
		return recommends;
	}

	public void setRecommends(String recommends) {
		this.recommends = recommends;
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