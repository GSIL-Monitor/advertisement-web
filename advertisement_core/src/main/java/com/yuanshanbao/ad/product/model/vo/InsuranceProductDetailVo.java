package com.yuanshanbao.ad.product.model.vo;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.ad.product.model.InsuranceProduct;

public class InsuranceProductDetailVo {

	private static final String CALCULATOR_URL = "www.sohu.com";

	// 产品ID
	private Long productId;
	// 产品名称
	private String name;
	// 产品标题
	private String title;
	// 产品描述
	private String description;
	// 产品Logo
	private String imageUrl;
	// 保险期限
	private String insuredDuration;
	// 缴费年限
	private String payway;
	// 保额
	private String premium;
	// 适合人群
	private String ageBracket;
	// 产品特色
	private String featureContent;
	// 保障内容
	private String guaranteeContent;
	// 预约流程
	private String reservationProcess;
	// 投保须知
	private String insuranceStatements;
	// 产品好评率
	private Integer positiveRate;
	// 产品状态
	private Integer status;
	// 详情跳转链接
	private String calculatorUrl;
	// 产品绑定状态
	private Integer bindStatus;

	public InsuranceProductDetailVo(InsuranceProduct product, Integer bindStatus) {
		if (product == null) {
			return;
		}
		this.productId = product.getProductId();
		this.name = product.getName();
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.imageUrl = product.getSmallImageUrl();
		this.payway = product.getPayway();
		this.premium = product.getPremium();
		this.ageBracket = product.getAgeBracket();
		this.insuredDuration = product.getInsuredDuration();
		this.featureContent = product.getProductFeatures();
		this.guaranteeContent = product.getGuaranteeContent();
		this.reservationProcess = product.getReservationProcess();
		this.insuranceStatements = product.getInsuranceStatements();
		this.positiveRate = product.getPositiveRate();
		this.status = product.getStatus();
		this.calculatorUrl = getCalculatorUrl();
		this.bindStatus = bindStatus;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getPositiveRate() {
		return positiveRate;
	}

	public void setPositiveRate(Integer positiveRate) {
		this.positiveRate = positiveRate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCalculatorUrl() {
		if (StringUtils.isBlank(calculatorUrl)) {
			calculatorUrl = CALCULATOR_URL + "?id=" + productId;
		}
		return calculatorUrl;
	}

	public void setCalculatorUrl(String calculatorUrl) {
		this.calculatorUrl = calculatorUrl;
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

	public String getAgeBracket() {
		return ageBracket;
	}

	public void setAgeBracket(String ageBracket) {
		this.ageBracket = ageBracket;
	}

	public String getFeatureContent() {
		return featureContent;
	}

	public void setFeatureContent(String featureContent) {
		this.featureContent = featureContent;
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

	public String getInsuredDuration() {
		return insuredDuration;
	}

	public void setInsuredDuration(String insuredDuration) {
		this.insuredDuration = insuredDuration;
	}

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	
}
