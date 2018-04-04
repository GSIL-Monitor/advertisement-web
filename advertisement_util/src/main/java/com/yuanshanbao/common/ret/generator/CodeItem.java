package com.yuanshanbao.common.ret.generator;

public class CodeItem {

	private String key;
	private String description;
	private int index;
	private String content;

	public CodeItem(String key, String description, int index, String content) {
		super();
		this.key = key;
		this.description = description;
		this.index = index;
		this.content = content;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
