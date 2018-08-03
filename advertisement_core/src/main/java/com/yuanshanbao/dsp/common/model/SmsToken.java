package com.yuanshanbao.dsp.common.model;

public class SmsToken {

	private String key;
	private String operation;
	private int value;
	private boolean enable;

	public SmsToken() {
		super();
	}

	public SmsToken(String key, String operation, int value, boolean enable) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
		this.enable = enable;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
