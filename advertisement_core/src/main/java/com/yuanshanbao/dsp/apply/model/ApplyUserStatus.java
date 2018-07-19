package com.yuanshanbao.dsp.apply.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApplyUserStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int NEW = 1;
	public static final String NEW_DESCRIPTION = "新用户";
	public static final int BLACK = 2;
	public static final String BLACK_DESCRIPTION = "黑名单";
	public static final int OLD = 3;
	public static final String OLD_DESCRIPTION = "老用户";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(NEW, NEW_DESCRIPTION);
		codeDescriptionMap.put(BLACK, BLACK_DESCRIPTION);
		codeDescriptionMap.put(OLD, OLD_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
