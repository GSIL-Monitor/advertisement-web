package com.yuanshanbao.common.ret.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CodeType {

	private int startIndex;
	private String name;
	private List<CodeItem> codeList = new ArrayList<CodeItem>();
	private Map<String, Integer> fieldMap;
	private Map<Integer, String> valueMap;
	private LinkedList<Integer> queue = new LinkedList<Integer>();

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CodeItem> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CodeItem> codeList) {
		this.codeList = codeList;
	}

	public void setFieldMap(Map<String, Integer> fieldMap) {
		this.fieldMap = fieldMap;
		this.valueMap = new HashMap<Integer, String>();
		for (Entry<String, Integer> entry : fieldMap.entrySet()) {
			valueMap.put(entry.getValue(), entry.getKey());
		}
		for (int i = startIndex; i < startIndex + 200; i++) {
			if (valueMap.get(i) == null) {
				queue.add(i);
			}
		}
	}

	public void addItem(CodeItem item) {
		Integer value = fieldMap.get(item.getKey());
		if (value != null) {
			item.setIndex(value);
		} else {
			item.setIndex(queue.pop());
		}
		codeList.add(item);
	}
}
