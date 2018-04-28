package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int COMMON = 1;
	public static final String COMMON_DESCRIPTION = "普通广告";
	public static final int RAFFLE = 2;
	public static final String RAFFLE_DESCRIPTION = "抽奖广告";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(COMMON, COMMON_DESCRIPTION);
		codeDescriptionMap.put(RAFFLE, RAFFLE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
