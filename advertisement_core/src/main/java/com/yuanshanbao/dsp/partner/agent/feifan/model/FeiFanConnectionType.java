package com.yuanshanbao.dsp.partner.agent.feifan.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeiFanConnectionType {
	protected static Map<Integer, Integer> codeDescriptionMap = new LinkedHashMap<Integer, Integer>();

	public static final int UNKNOWN = 0;
	public static final int UNKNOWN_DESCRIPTION = -1;
	public static final int WIFI = 1;
	public static final int WIFI_DESCRIPTION = 2;
	public static final int SECOND = 2;
	public static final int SECOND_DESCRIPTION = 4;
	public static final int THIRD = 3;
	public static final int THIRD_DESCRIPTION = 5;
	public static final int FORTH = 4;
	public static final int FORTH_DESCRIPTION = 6;

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(UNKNOWN, UNKNOWN_DESCRIPTION);
		codeDescriptionMap.put(WIFI, WIFI_DESCRIPTION);
		codeDescriptionMap.put(SECOND, SECOND_DESCRIPTION);
		codeDescriptionMap.put(THIRD, THIRD_DESCRIPTION);
		codeDescriptionMap.put(FORTH, FORTH_DESCRIPTION);
	}

	public static Integer getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, Integer> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
