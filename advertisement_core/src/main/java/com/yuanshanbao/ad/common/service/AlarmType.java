package com.yuanshanbao.ad.common.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class AlarmType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int SERVER = 1;
	public static final String SERVER_DESCRIPTION = "服务器报警";

	public static final int STATISTICS_ALERT = 2;
	public static final String STATISTICS_ALERT_DESCRIPTION = "数据报警";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(SERVER, SERVER_DESCRIPTION);
		codeDescriptionMap.put(STATISTICS_ALERT, STATISTICS_ALERT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
