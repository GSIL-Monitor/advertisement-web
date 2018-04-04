package com.yuanshanbao.ad.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementStrategyType {
	
	protected static Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	
	public static final Integer REGION = 0;
	public static final String REGION_DESCRIPTION = "地域";

	public static final Integer AGE = 1;
	public static final String AGE_DESCRIPTION = "年龄";
	

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		typeDescriptionMap.put(REGION, REGION_DESCRIPTION);
		typeDescriptionMap.put(AGE, AGE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}
	
}