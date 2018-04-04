package com.yuanshanbao.ad.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementCategoryType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int LIST = 1; 
	public static final String LIST_DESCRIPTION = "列表格式显示";
	public static final int NET = 2; 
	public static final String NET_DESCRIPTION = "网络格式显示";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(LIST, LIST_DESCRIPTION);
		codeDescriptionMap.put(NET, NET_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
