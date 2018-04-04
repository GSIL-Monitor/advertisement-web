package com.yuanshanbao.ad.product.model;

import java.sql.Timestamp;

import com.yuanshanbao.ad.user.model.User;

/**
 * 
 * @description
 *
 * @author 
 */
public class ProductBind {

	// ~ Field
	// =============================================================================================
	
	private Long bindId;

	private String userId;

	private Long productId;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;
	
	
	
	private User user;
	
	private InsuranceProduct insuranceProduct;
	
	
	// ~ Get and Set Methods
	// =================================================================================
	
	public Long getBindId() {
		return bindId;
	}

	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public InsuranceProduct getInsuranceProduct() {
		return insuranceProduct;
	}

	public void setInsuranceProduct(InsuranceProduct insuranceProduct) {
		this.insuranceProduct = insuranceProduct;
	}

}