package com.yuanshanbao.ad.location.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocationType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int PROVINCE = 1;
	public static final String PROVINCE_DESCRIPTION = "省";
	public static final int CITY = 2;
	public static final String CITY_DESCRIPTION = "市";
	public static final int DISTRICT = 3;
	public static final String DISTRICT_DESCRIPTION = "区";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(PROVINCE, PROVINCE_DESCRIPTION);
		codeDescriptionMap.put(CITY, CITY_DESCRIPTION);
		codeDescriptionMap.put(DISTRICT, DISTRICT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
