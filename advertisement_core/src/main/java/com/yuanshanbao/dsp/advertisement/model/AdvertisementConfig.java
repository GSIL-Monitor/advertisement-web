package com.yuanshanbao.dsp.advertisement.model;

import java.util.List;

import com.yuanshanbao.dsp.config.model.Function;



public class AdvertisementConfig {

	private String name;
	private List<Function> functions;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
}
