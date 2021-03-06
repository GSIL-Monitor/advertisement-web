package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementStrategyType {

	protected static Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, String> strategyKeyMap = new LinkedHashMap<Integer, String>();

	public static final Integer REGION = 0;
	public static final String REGION_DESCRIPTION = "地域";

	public static final Integer AGE = 1;
	public static final String AGE_DESCRIPTION = "年龄";
	public static final String AGE_KEY = "ageStrategy";

	public static final Integer DEVICETYPE = 2;
	public static final String DEVICETYPE_DESCRIPTION = "设备类型";
	public static final String DEVICETYPE_KEY = "deviceTypeStrategy";

	public static final Integer IP_REGION = 3;
	public static final String IP_REGION_DESCRIPTION = "IP地域";
	public static final String IP_REGION_KEY = "ipRegionStrategy";

	public static final Integer NETWORK_WAY = 4;
	public static final String NETWORK_WAY_DESCRIPTION = "联网方式";
	public static final String NETWORK_WAY_KEY = "netWorkStrategy";

	public static final Integer CARRIER = 5;
	public static final String CARRIER_DESCRIPTION = "运营商";
	public static final String CARRIER_KEY = "carrierStrategy";

	public static final Integer GENDER = 6;
	public static final String GENDER_DESCRIPTION = "性别";
	public static final String GENDER_KEY = "genderStrategy";

	static {
		initCodeDescriptionMap();
		initStragegyKeyMap();
	}

	public static void initCodeDescriptionMap() {
		typeDescriptionMap.put(REGION, REGION_DESCRIPTION);
		typeDescriptionMap.put(AGE, AGE_DESCRIPTION);
		typeDescriptionMap.put(DEVICETYPE, DEVICETYPE_DESCRIPTION);
		typeDescriptionMap.put(IP_REGION, IP_REGION_DESCRIPTION);
		typeDescriptionMap.put(CARRIER, CARRIER_DESCRIPTION);
	}

	private static void initStragegyKeyMap() {
		strategyKeyMap.put(IP_REGION, IP_REGION_KEY);
		strategyKeyMap.put(DEVICETYPE, DEVICETYPE_KEY);
		strategyKeyMap.put(AGE, AGE_KEY);
		strategyKeyMap.put(NETWORK_WAY, NETWORK_WAY_KEY);
		strategyKeyMap.put(CARRIER, CARRIER_KEY);
		strategyKeyMap.put(GENDER, GENDER_KEY);
	}

	public static String getDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}

	public static Map<Integer, String> getStrategyKeyMap() {
		return strategyKeyMap;
	}

}