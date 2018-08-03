package com.yuanshanbao.dsp.common.model;

import java.util.ArrayList;
import java.util.List;

import com.yuanshanbao.common.util.LoggerUtil;

public class SmsTokenList {

	private List<SmsToken> list;
	private boolean enable = false;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<SmsToken> getList() {
		return list;
	}

	public void setList(List<SmsToken> list) {
		this.list = list;
	}

	public List<SmsToken> generateRandomSmsTokenList() {
		list = new ArrayList<SmsToken>();
		try {
			int count = getRandom(5);
			for (int i = 0; i < count; i++) {
				String key = String.valueOf((char) ('a' + getRandom(26) - 1)) + getRandom(100000) + "";
				String operation = "+";
				if (getRandom(2) > 1) {
					operation = "-";
				}
				int value = getRandom(100000);
				boolean enable = getRandom(2) > 1;
				list.add(new SmsToken(key, operation, value, enable));
			}
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		return list;
	}

	private int getRandom(int max) {
		return (int) (Math.random() * max) + 1;
	}
}
