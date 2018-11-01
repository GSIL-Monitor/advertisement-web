package com.yuanshanbao.dsp.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConnectionType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ETHERNET = 1;
	public static final String ETHERNET_DESCRIPTION = "以太网";
	public static final int WIFI = 2;
	public static final String WIFI_DESCRIPTION = "wifi";
	public static final int CELLULAR = 3;
	public static final String CELLULAR_DESCRIPTION = "蜂窝网络";
	public static final int SECOND = 4;
	public static final String SECOND_DESCRIPTION = "2G";
	public static final int THIRD = 5;
	public static final String THIRD_DESCRIPTION = "3G";
	public static final int FORTH = 6;
	public static final String FORTH_DESCRIPTION = "4G";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ETHERNET, ETHERNET_DESCRIPTION);
		codeDescriptionMap.put(WIFI, WIFI_DESCRIPTION);
		codeDescriptionMap.put(CELLULAR, CELLULAR_DESCRIPTION);
		codeDescriptionMap.put(SECOND, SECOND_DESCRIPTION);
		codeDescriptionMap.put(THIRD, THIRD_DESCRIPTION);
		codeDescriptionMap.put(FORTH, FORTH_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
