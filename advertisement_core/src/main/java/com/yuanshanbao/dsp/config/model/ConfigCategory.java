package com.yuanshanbao.dsp.config.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory {

	private int configCategory;
	private String categoryName;
	private List<Function> functionList = new ArrayList<Function>();

	public int getConfigCategory() {
		return configCategory;
	}

	public void setConfigCategory(int configCategory) {
		this.configCategory = configCategory;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
