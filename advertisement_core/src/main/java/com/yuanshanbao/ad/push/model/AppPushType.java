package com.yuanshanbao.ad.push.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AppPushType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, String> codeFlagDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int IOS = 1; 
	public static final String IOS_PREFIX = "ios_";
	public static final String IOS_DESCRIPTION = "IOS";
	public static final int ANDROID = 2; 
	public static final String ANDROID_PREFIX = "android_";
	public static final String ANDROID_DESCRIPTION = "Android";
	public static final int ALL = 0; 
	public static final String ALL_PREFIX = "";
	public static final String ALL_DESCRIPTION = "全部";
	
	public static final int YES = 1; 
	public static final String YES_DESCRIPTION = "是";
	public static final int NO = 0; 
	public static final String NO_DESCRIPTION = "否";
	
	static {
		initCodeDescriptionMap();
		initFlagDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(IOS, IOS_DESCRIPTION);
		codeDescriptionMap.put(ANDROID, ANDROID_DESCRIPTION);
		codeDescriptionMap.put(ALL, ALL_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
	
	public static void initFlagDescriptionMap() {
		codeFlagDescriptionMap.put(YES, YES_DESCRIPTION);
		codeFlagDescriptionMap.put(NO, NO_DESCRIPTION);
	}

	public static String getFlagDescription(Integer code) {
		return codeFlagDescriptionMap.get(code);
	}

	public static Map<Integer, String> getFlagDescriptionMap() {
		return codeFlagDescriptionMap;
	}

}
