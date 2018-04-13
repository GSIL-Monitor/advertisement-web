package com.yuanshanbao.dsp.product.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.merchant.model.Merchant;

/**
 * 
 * @description
 *
 * @author
 */
public class InsuranceProduct extends Product {

	// ~ Field
	// =============================================================================================

	private Long insuranceProductId;

	private Long productId;

	private Long calculatorId;

	private String payway;

	private String premium;
	
	private String ageBracket;

	private String applicableTargets;

	private String insuredDuration;

	private Integer dailyAllowance;

	private String productFeatures;

	private String guaranteeContent;

	private String reservationProcess;

	private String insuranceStatements;

	private String productIncludeUrl;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;
	
	private Merchant merchant;

	// ~ Constructors Methods
	// =================================================================================

	public InsuranceProduct() {
		super();
	}

	// ~ Get and Set Methods
	// =================================================================================

	public Long getInsuranceProductId() {
		return insuranceProductId;
	}

	public void setInsuranceProductId(Long insuranceProductId) {
		this.insuranceProductId = insuranceProductId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCalculatorId() {
		return calculatorId;
	}

	public void setCalculatorId(Long calculatorId) {
		this.calculatorId = calculatorId;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getApplicableTargets() {
		return applicableTargets;
	}

	public void setApplicableTargets(String applicableTargets) {
		this.applicableTargets = applicableTargets;
	}

	public String getInsuredDuration() {
		return insuredDuration;
	}

	public void setInsuredDuration(String insuredDuration) {
		this.insuredDuration = insuredDuration;
	}

	public Integer getDailyAllowance() {
		return dailyAllowance;
	}

	public void setDailyAllowance(Integer dailyAllowance) {
		this.dailyAllowance = dailyAllowance;
	}

	public String getProductFeatures() {
		return productFeatures;
	}

	public void setProductFeatures(String productFeatures) {
		this.productFeatures = productFeatures;
	}

	public String getGuaranteeContent() {
		return guaranteeContent;
	}

	public void setGuaranteeContent(String guaranteeContent) {
		this.guaranteeContent = guaranteeContent;
	}

	public String getReservationProcess() {
		return reservationProcess;
	}

	public void setReservationProcess(String reservationProcess) {
		this.reservationProcess = reservationProcess;
	}

	public String getInsuranceStatements() {
		return insuranceStatements;
	}

	public void setInsuranceStatements(String insuranceStatements) {
		this.insuranceStatements = insuranceStatements;
	}

	public String getProductIncludeUrl() {
		return productIncludeUrl;
	}

	public void setProductIncludeUrl(String productIncludeUrl) {
		this.productIncludeUrl = productIncludeUrl;
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
	
	public String getAgeBracket() {
		return ageBracket;
	}

	public void setAgeBracket(String ageBracket) {
		this.ageBracket = ageBracket;
	}
	
	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	
	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd hh:mm:ss");
	}

	public void addProduct(Product product) {
		if (product != null) {
			this.setName(product.getName());
			this.setProductKey(product.getProductKey());
			this.setTitle(product.getTitle());
			this.setDescription(product.getDescription());
			this.setImageUrl(product.getImageUrl());
			this.setMerchantId(product.getMerchantId());
			this.setRecommendTags(product.getRecommendTags());
			this.setPositiveRate(product.getPositiveRate());
			this.setCategory(product.getCategory());
			this.setSmallImageUrl(product.getSmallImageUrl());
		}
	}

}