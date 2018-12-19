package com.yuanshanbao.dsp.partner.agent.feifan.model;

import java.math.BigDecimal;

import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.material.model.Material;

public class FeiFanAdvertisement {
	private String impid;
	private String cid;
	private String pm;
	private String cm;
	private Integer price;

	public FeiFanAdvertisement() {
		super();
	}

	public FeiFanAdvertisement(String impId, String planKey, String probabilityKey, Material material, String channel,
			BigDecimal price) {
		this.impid = impId;
		this.cid = material.getMaterialId().toString();
		this.pm = getCollectUrl(planKey, probabilityKey, channel, "show");
		this.cm = getCollectUrl(planKey, probabilityKey, channel, "click");
		this.setPrice(price.multiply(new BigDecimal(100)).intValue());
	}

	public String getImpid() {
		return impid;
	}

	public void setImpid(String impid) {
		this.impid = impid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getCm() {
		return cm;
	}

	public void setCm(String cm) {
		this.cm = cm;
	}

	private String getCollectUrl(String planKey, String probabilityKey, String channel, String type) {
		StringBuffer sb = new StringBuffer();
		sb.append(CommonConstant.advertisement_HOST);
		sb.append("/" + type);
		sb.append("?");
		sb.append("pid=");
		sb.append(planKey);
		sb.append("&");
		sb.append("key=");
		sb.append(probabilityKey);
		sb.append("&");
		sb.append("channel=");
		sb.append(channel);
		return sb.toString();
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
