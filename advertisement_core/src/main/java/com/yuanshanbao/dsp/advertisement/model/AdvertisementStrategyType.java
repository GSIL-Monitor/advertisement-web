package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementStrategyType {

	protected static Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<String, String> strategyKeyMap = new LinkedHashMap<String, String>();

	public static final Integer REGION = 0;
	public static final String REGION_DESCRIPTION = "地域";

	public static final Integer AGE = 1;
	public static final String AGE_DESCRIPTION = "年龄";

	public static final Integer DEVICETYPE = 2;
	public static final String DEVICETYPE_DESCRIPTION = "设备型号";
	public static final String DEVICETYPE_KEY = "deviceTypeStrategy";

	public static final Integer IP_REGION = 3;
	public static final String IP_REGION_DESCRIPTION = "IP地域";
	public static final String IP_REGION_KEY = "ipRegionStrategy";

	static {
		initCodeDescriptionMap();
		initStragegyKeyMap();
	}

	public static void initCodeDescriptionMap() {
		typeDescriptionMap.put(REGION, REGION_DESCRIPTION);
		typeDescriptionMap.put(AGE, AGE_DESCRIPTION);
		typeDescriptionMap.put(DEVICETYPE, DEVICETYPE_DESCRIPTION);
		typeDescriptionMap.put(IP_REGION, IP_REGION_DESCRIPTION);
	}

	private static void initStragegyKeyMap() {
		strategyKeyMap.put(IP_REGION_KEY, "");
		strategyKeyMap.put(DEVICETYPE_KEY, "");
	}

	public static String getDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}

	public static Map<String, String> getStrategyKeyMap() {
		return strategyKeyMap;
	}

}