package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertiserStatus {
	
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "上线";
	public static final int OFFLINE = -1;
	public static final String OFFLINE_DESCRIPTION = "已冻结";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		codeDescriptionMap.put(OFFLINE, OFFLINE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;	
	}
}
