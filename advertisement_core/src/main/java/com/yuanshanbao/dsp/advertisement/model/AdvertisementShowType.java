package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementShowType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, String> dynamicAdMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, String> staticAdMap = new LinkedHashMap<Integer, String>();

	public static final int ALWAYS = 1; 
	public static final String ALWAYS_DESCRIPTION = "每次启动都展示";
	public static final int PERIOD = 2; 
	public static final String PERIOD_DESCRIPTION = "周期性展示";
	public static final int UPDATE = 3; 
	public static final String UPDATE_DESCRIPTION = "更新展示";
	
	public static final int LIST = 4; 
	public static final String LIST_DESCRIPTION = "列表格式显示";
	public static final int NET = 5; 
	public static final String NET_DESCRIPTION = "网络格式显示";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ALWAYS, ALWAYS_DESCRIPTION);
		codeDescriptionMap.put(PERIOD, PERIOD_DESCRIPTION);
		codeDescriptionMap.put(UPDATE, UPDATE_DESCRIPTION);
		codeDescriptionMap.put(LIST, LIST_DESCRIPTION);
		codeDescriptionMap.put(NET, NET_DESCRIPTION);
		
		dynamicAdMap.put(ALWAYS, ALWAYS_DESCRIPTION);
		dynamicAdMap.put(PERIOD, PERIOD_DESCRIPTION);
		dynamicAdMap.put(UPDATE, UPDATE_DESCRIPTION);
		
		staticAdMap.put(LIST, LIST_DESCRIPTION);
		staticAdMap.put(NET, NET_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
	
	public static Map<Integer, String> getDynamicDescriptionMap() {
		return dynamicAdMap;
	}
	
	public static Map<Integer, String> getStaticDescriptionMap() {
		return staticAdMap;
	}

}
