package com.yuanshanbao.dsp.activity.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int DAZHUANPAN = 1;
	public static final String DAZHUANPAN_DESCRIPTION = "大转盘";
	public static final int GUAJIANG = 2;
	public static final String GUAJIANG_DESCRIPTION = "礼包";
	public static final int FANPAI = 3;
	public static final String FANPAI_DESCRIPTION = "翻牌";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(DAZHUANPAN, DAZHUANPAN_DESCRIPTION);
		codeDescriptionMap.put(GUAJIANG, GUAJIANG_DESCRIPTION);
		codeDescriptionMap.put(FANPAI, FANPAI_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
