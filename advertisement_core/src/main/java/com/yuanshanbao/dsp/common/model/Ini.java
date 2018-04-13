package com.yuanshanbao.dsp.common.model;

import java.sql.Timestamp;

public class Ini {
	private String iniName; // Ini英文
	private String iniNameCn; // Ini中文
	private String iniValue; // Ini值
	private String iniDesc; // Ini描述
	private String iniCategory; // Ini分类
	private int status; // 状�?�? 有效中；2 已失�?

	private Timestamp createTime;
	private Timestamp updateTime;

	/**
	 * tb_ini.ini_category 表示属于通用INI
	 */
	public static final String INI_GATEGORY_GENERAL = "general";

	/*****************
	 * tb_ini.status 1 有效�? 2 已失�?
	 */
	public static final int INI_STATUS_VALID = 1;
	public static final int INI_STATUS_INVALID = 2;

	public String getIniName() {
		return iniName;
	}

	public void setIniName(String iniName) {
		this.iniName = iniName;
	}

	public String getIniNameCn() {
		return iniNameCn;
	}

	public void setIniNameCn(String iniNameCn) {
		this.iniNameCn = iniNameCn;
	}

	public String getIniValue() {
		return iniValue;
	}

	public void setIniValue(String iniValue) {
		this.iniValue = iniValue;
	}

	public String getIniDesc() {
		return iniDesc;
	}

	public void setIniDesc(String iniDesc) {
		this.iniDesc = iniDesc;
	}

	public String getIniCategory() {
		return iniCategory;
	}

	public void setIniCategory(String iniCategory) {
		this.iniCategory = iniCategory;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "INI [iniName=" + iniName + ", iniValue=" + iniValue + "]";
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
