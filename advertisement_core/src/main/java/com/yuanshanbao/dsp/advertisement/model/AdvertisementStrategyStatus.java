package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementStrategyStatus {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "上线";
	public static final int UNREVIEWED = 3;
	public static final String UNREVIEWED_DESCRIPTION = "未审核";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		codeDescriptionMap.put(UNREVIEWED, UNREVIEWED_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
