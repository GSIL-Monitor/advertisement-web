package com.yuanshanbao.dsp.channel.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChannelIndependentStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int NOTINDEPENDENT = 0;
	public static final String NOTINDEPENDENT_DESCRIPTION = "不需要";
	public static final int INDEPENDENT = 1;
	public static final String INDEPENDENT_DESCRIPTION = "需要";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(NOTINDEPENDENT, NOTINDEPENDENT_DESCRIPTION);
		codeDescriptionMap.put(INDEPENDENT, INDEPENDENT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
