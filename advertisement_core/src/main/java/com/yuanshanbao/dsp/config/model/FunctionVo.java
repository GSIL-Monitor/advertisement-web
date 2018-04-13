package com.yuanshanbao.dsp.config.model;

import java.util.List;
import java.util.Set;

import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementStrategyVo;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementVo;

public class FunctionVo {

	private Function function;
	
	private List<AdvertisementVo> advertisementVoList;
	
	private List<AdvertisementStrategyVo> strategyVoList;
	
	private Set<String> categorySet;

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public List<AdvertisementStrategyVo> getStrategyVoList() {
		return strategyVoList;
	}

	public void setStrategyVoList(List<AdvertisementStrategyVo> strategyVoList) {
		this.strategyVoList = strategyVoList;
	}

	public List<AdvertisementVo> getAdvertisementVoList() {
		return advertisementVoList;
	}

	public void setAdvertisementVoList(List<AdvertisementVo> advertisementVoList) {
		this.advertisementVoList = advertisementVoList;
	}

	public Set<String> getCategorySet() {
		return categorySet;
	}

	public void setCategorySet(Set<String> categorySet) {
		this.categorySet = categorySet;
	}
	
}
