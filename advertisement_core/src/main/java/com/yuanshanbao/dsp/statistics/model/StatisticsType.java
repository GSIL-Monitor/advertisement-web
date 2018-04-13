package com.yuanshanbao.dsp.statistics.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticsType {

	public static final Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int CHANNEL_ORIGINAL_DATA = 1;
	public static final String CHANNEL_ORIGINAL_DATA_DESCRIPTION = "原始数据";

	public static final int CHANNEL_WORKING_DATA = 2;
	public static final String CHANNEL_WORKING_DATA_DESCRPTION = "计算数据";

	public static final int PRODUCT_DATA = 3;
	public static final String PRODUCT_DATA_DESCRPTION = "产品数据";

	static {
		initParams();
	}

	public static void initParams() {
		typeDescriptionMap.put(CHANNEL_ORIGINAL_DATA, CHANNEL_ORIGINAL_DATA_DESCRIPTION);
		typeDescriptionMap.put(CHANNEL_WORKING_DATA, CHANNEL_WORKING_DATA_DESCRPTION);
		typeDescriptionMap.put(PRODUCT_DATA, PRODUCT_DATA_DESCRPTION);
	}

	public static String getTypeDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}
}
