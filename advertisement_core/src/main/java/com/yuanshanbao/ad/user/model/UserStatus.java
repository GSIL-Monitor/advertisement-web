package com.yuanshanbao.ad.user.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int NORMAL = 1;
	public static final String NORMAL_DESCRIPTION = "正常";
	public static final int BLACK = 2;
	public static final String BLACK_DESCRIPTION = "黑名单";
	public static final int LOCK = 3;
	public static final String LOCK_DESCRIPTION = "锁定";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(NORMAL, NORMAL_DESCRIPTION);
		codeDescriptionMap.put(BLACK, BLACK_DESCRIPTION);
		codeDescriptionMap.put(LOCK, LOCK_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
