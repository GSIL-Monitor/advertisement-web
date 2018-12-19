package com.yuanshanbao.dsp.partner.agent.feifan.model;

import java.util.List;

public class FeiFanRequest {
	private String id;
	private Integer mid;
	private FeiFanDevice device;
	private List<Impression> imp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FeiFanDevice getDevice() {
		return device;
	}

	public void setDevice(FeiFanDevice device) {
		this.device = device;
	}

	public List<Impression> getImp() {
		return imp;
	}

	public void setImp(List<Impression> imp) {
		this.imp = imp;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

}
