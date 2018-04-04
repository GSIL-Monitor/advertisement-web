package com.yuanshanbao.ad.banner.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BannerType {
	
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	public static final int HOME = 1;
	public static final String HOME_DESCRIPTION = "首页Banner";
	public static final int SPLASH = 2;
	public static final String SPLASH_DESCRIPTION = "开屏Banner";
	public static final int CORNER = 3;
	public static final String CORNER_DESCRIPTION = "右下角Banner";
	public static final int AUTH = 4;
	public static final String AUTH_DESCRIPTION = "应用市场审核Banner";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(HOME, HOME_DESCRIPTION);
		codeDescriptionMap.put(SPLASH, SPLASH_DESCRIPTION);
		codeDescriptionMap.put(CORNER, CORNER_DESCRIPTION);
		codeDescriptionMap.put(AUTH, AUTH_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
