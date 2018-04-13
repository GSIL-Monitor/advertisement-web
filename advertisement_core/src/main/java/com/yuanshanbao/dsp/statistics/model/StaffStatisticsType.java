package com.yuanshanbao.dsp.statistics.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class StaffStatisticsType {

	public static final Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int DAILY_DATA = 1;
	public static final String DAILY_DATA_DESCRIPTION = "按日统计";

	public static final int MONTH_DATA = 2;
	public static final String MONTH_DATA_DESCRIPTION = "按月统计";

	static {
		initParams();
	}

	public static void initParams() {
		typeDescriptionMap.put(MONTH_DATA, MONTH_DATA_DESCRIPTION);
		typeDescriptionMap.put(DAILY_DATA, DAILY_DATA_DESCRIPTION);
	}

	public static String getTypeDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}
}
