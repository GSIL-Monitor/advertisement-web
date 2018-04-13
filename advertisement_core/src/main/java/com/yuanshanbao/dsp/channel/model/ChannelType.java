package com.yuanshanbao.dsp.channel.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ChannelType {

	protected static Map<String, String> codeDescriptionMap = new LinkedHashMap<String, String>();

	protected static Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();

	protected static Map<Integer, String> deliverTypeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final String ZHIFU = "zhifu";
	public static final String ZHIFU_DESCRIPTION = "金融支付类";
	public static final String ZIXUN = "zixun";
	public static final String ZIXUN_DESCRIPTION = "新闻资讯类";
	public static final String CHEZHU = "chezhu";
	public static final String CHEZHU_DESCRIPTION = "车主类";

	public static final Integer TOMMOROW_SHOW_AND_NOT_CONFIRM = 1;
	public static final String TOMMOROW_SHOW_AND_NOT_CONFIRM_DESCRIPTION = "第二天展示数据不需要确认";

	public static final Integer SHOW_DATA_ALWAYS = 2;
	public static final String SHOW_DATA_ALWAYS_DESCRIPTION = "实时显示统计";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ZHIFU, ZHIFU_DESCRIPTION);
		codeDescriptionMap.put(ZIXUN, ZIXUN_DESCRIPTION);
		codeDescriptionMap.put(CHEZHU, CHEZHU_DESCRIPTION);

		typeDescriptionMap.put(TOMMOROW_SHOW_AND_NOT_CONFIRM, TOMMOROW_SHOW_AND_NOT_CONFIRM_DESCRIPTION);
		typeDescriptionMap.put(SHOW_DATA_ALWAYS, SHOW_DATA_ALWAYS_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<String, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

	public static String getType(String description) {
		for (Entry<String, String> entry : codeDescriptionMap.entrySet()) {
			if (entry.getValue().equals(description)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static String getShowTypeDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getShowTypeDescriptionMap() {
		return typeDescriptionMap;
	}

	public static String getDeliverTypeDescription(Integer code) {
		return deliverTypeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getDeliverTypeDescriptionMap() {
		return deliverTypeDescriptionMap;
	}
}
