package com.yuanshanbao.dsp.apply.model;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.product.model.vo.ProductVo;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class ApplyVo {

	/**
	 * 申请ID，分表
	 */
	private Long applyId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品logo
	 */
	private String productLogo;
	private Long productId;
	private Long userId;
	private String repayDaysStr;
	private String loanAmountStr;
	private String applyTimeStr;

	private TagsVo statusTags;

	public ApplyVo(Apply apply) {
		ProductVo vo = new ProductVo(apply.getProduct());
		this.setApplyId(apply.getApplyId());
		this.setUserId(apply.getUserId());
		this.productId = apply.getProductId();
		this.productName = vo.getName();
		// this.productLogo = vo.getProductLogo();
		// this.repayDaysStr = vo.getRepayDays();
		// this.loanAmountStr = vo.getLoanAmount();
		this.applyTimeStr = "活动时间    " + DateUtils.format(apply.getCreateTime(), "yyyy.MM.dd HH:mm");
		if (apply.getStatus() != null) {
			this.statusTags = new TagsVo(ConstantsManager.getTags(ApplyStatus.getTagID(apply.getStatus())));
		}

	}

	public ApplyVo() {
		super();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLogo() {
		return productLogo;
	}

	public void setProductLogo(String productLogo) {
		this.productLogo = productLogo;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public String getRepayDaysStr() {
		return repayDaysStr;
	}

	public void setRepayDaysStr(String repayDaysStr) {
		this.repayDaysStr = repayDaysStr;
	}

	public String getLoanAmountStr() {
		return loanAmountStr;
	}

	public void setLoanAmountStr(String loanAmountStr) {
		this.loanAmountStr = loanAmountStr;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public TagsVo getStatusTags() {
		return statusTags;
	}

	public void setStatusTags(TagsVo statusTags) {
		this.statusTags = statusTags;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

}
