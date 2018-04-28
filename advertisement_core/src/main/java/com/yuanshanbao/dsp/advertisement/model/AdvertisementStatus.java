package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementStatus {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "投放中";
	public static final int OFFLINE = 2;
	public static final String OFFLINE_DESCRIPTION = "未投放";
	public static final int INVALID = 0;
	public static final String INVALID_DESCRIPTION = "失效";
	public static final int DELETE = -1;
	public static final String DELETE_DESCRIPTION = "已删除";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		codeDescriptionMap.put(OFFLINE, OFFLINE_DESCRIPTION);
		codeDescriptionMap.put(INVALID, INVALID_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
