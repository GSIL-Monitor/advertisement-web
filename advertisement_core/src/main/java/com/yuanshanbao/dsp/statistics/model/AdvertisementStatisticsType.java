package com.yuanshanbao.dsp.statistics.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementStatisticsType {
	public static final Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int PV_DATA = 1;
	public static final String PV_DATA_DESCRIPTION = "PV数据";

	public static final int UV_DATA = 2;
	public static final String UV_DATA_DESCRPTION = "UV数据";


	static {
		initParams();
	}

	public static void initParams() {
		typeDescriptionMap.put(PV_DATA, PV_DATA_DESCRIPTION);
		typeDescriptionMap.put(UV_DATA, UV_DATA_DESCRPTION);
	}

	public static String getTypeDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}
}
