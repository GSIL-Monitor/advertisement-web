package com.yuanshanbao.dsp.partner.agent.feifan.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeiFanOsType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int IOS = 1;
	public static final String IOS_DESCRIPTION = "iOS";
	public static final int ANDROID = 2;
	public static final String ANDROID_DESCRIPTION = "Android";
	public static final int WINDOWS = 3;
	public static final String WINDOWS_DESCRIPTION = "PC";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(IOS, IOS_DESCRIPTION);
		codeDescriptionMap.put(ANDROID, ANDROID_DESCRIPTION);
		codeDescriptionMap.put(WINDOWS, WINDOWS_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
