package com.yuanshanbao.dsp.user.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserLevel {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ONLEVEL = 0;
	public static final String ONLEVEL_DESCRIPTION = "经理";
	public static final int OFFLEVEL = 1;
	public static final String OFFLEVEL_DESCRIPTION = "总监";
	public static final int LEVEL = 2;
	public static final String LEVEL_DESCRIPTION = "行长";
	public static final int NULL = -1;
	public static final String NULL_DESCRIPTION = "暂无";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONLEVEL, ONLEVEL_DESCRIPTION);
		codeDescriptionMap.put(OFFLEVEL, OFFLEVEL_DESCRIPTION);
		codeDescriptionMap.put(LEVEL, LEVEL_DESCRIPTION);
		codeDescriptionMap.put(NULL, NULL_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
