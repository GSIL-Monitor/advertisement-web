package com.yuanshanbao.dsp.activity.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int DAZHUANPAN = 1;
	public static final String DAZHUANPAN_DESCRIPTION = "大转盘";
	public static final int GUAJIANG = 2;
	public static final String GUAJIANG_DESCRIPTION = "刮奖";
	public static final int FANPAI = 3;
	public static final String FANPAI_DESCRIPTION = "翻牌";
	public static final int ZAJINDAN = 4;
	public static final String ZAJINDAN_DESCRIPTION = "砸金蛋";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(DAZHUANPAN, DAZHUANPAN_DESCRIPTION);
		codeDescriptionMap.put(GUAJIANG, GUAJIANG_DESCRIPTION);
		codeDescriptionMap.put(FANPAI, FANPAI_DESCRIPTION);
		codeDescriptionMap.put(ZAJINDAN, ZAJINDAN_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
