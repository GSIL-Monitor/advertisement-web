package com.yuanshanbao.ad.channel.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class PromotionStatus {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final Integer INACTIVE = 0;
	public static final String INACTIVE_DESCRIPTION = "未激活";

	public static final Integer CALLBACK = 1;
	public static final String CALLBACK_DESCRIPTION = "已回调";
	
	public static final Integer CALLBACK_FAILED = 2;
	public static final String CALLBACK_FAILED_DESCRIPTION = "回调失败";
	
	public static final Integer WITHHOLD = 3;
	public static final String WITHHOLD_DESCRIPTION = "保留";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(CALLBACK, CALLBACK_DESCRIPTION);
		codeDescriptionMap.put(INACTIVE, INACTIVE_DESCRIPTION);
		codeDescriptionMap.put(CALLBACK_FAILED, CALLBACK_FAILED_DESCRIPTION);
		codeDescriptionMap.put(WITHHOLD, WITHHOLD_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
