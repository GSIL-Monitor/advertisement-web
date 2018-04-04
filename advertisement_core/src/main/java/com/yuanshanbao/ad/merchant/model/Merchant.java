package com.yuanshanbao.ad.merchant.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;

public class Merchant {
	/**
	 * id
	 */
	private Long merchantId;
	private Long projectId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 商家全名
	 */
	private String fullName;
	/**
	 * 商家英文简称
	 */
	private String shortName;
	/**
	 * 用于自动生成路径名
	 */
	private String englishName;
	/**
	 * 商家logo
	 */
	private String imageUrl;
	/**
	 * 厂商代码
	 */
	private String merchantCode;
	/**
	 * 联系电话
	 */
	private String telephone;
	/**
	 * 接口方式
	 */
	private Integer interfaceType;
	/**
	 * 出单接口
	 */
	private String deliverInterface;

	/**
	 * 状态(0 停用 1启用)
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	private Timestamp updateTime;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(Integer interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getDeliverInterface() {
		return deliverInterface;
	}

	public void setDeliverInterface(String deliverInterface) {
		this.deliverInterface = deliverInterface;
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
