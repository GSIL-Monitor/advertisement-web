package com.yuanshanbao.dsp.user.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserLevel {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int MANAGER = 0;
	public static final String MANAGER_DESCRIPTION = "经理";
	public static final int MAJORDOMO = 1;
	public static final String MAJORDOMO_DESCRIPTION = "总监";
	public static final int BAILLIFF = 2;
	public static final String BAILLIFF_DESCRIPTION = "首席执行官";
	public static final int NULL = -1;
	public static final String NULL_DESCRIPTION = "暂无";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(MANAGER, MANAGER_DESCRIPTION);
		codeDescriptionMap.put(MAJORDOMO, MAJORDOMO_DESCRIPTION);
		codeDescriptionMap.put(BAILLIFF, BAILLIFF_DESCRIPTION);
		codeDescriptionMap.put(NULL, NULL_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
