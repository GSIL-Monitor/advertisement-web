package com.yuanshanbao.ad.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int WELFARE = 1; 
	public static final String WELFARE_DESCRIPTION = "福利";
	public static final int BANNER = 2; 
	public static final String BANNER_DESCRIPTION = "轮播图";
	public static final int TAGS = 3; 
	public static final String TAGS_DESCRIPTION = "标签";
	public static final int POPUP = 4; 
	public static final String POPUP_DESCRIPTION = "弹窗";
	public static final int CONNER = 5; 
	public static final String CONNER_DESCRIPTION = "角标";
	public static final int PRODUCT = 6; 
	public static final String PRODUCT_DESCRIPTION = "产品";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(WELFARE, WELFARE_DESCRIPTION);
		codeDescriptionMap.put(BANNER, BANNER_DESCRIPTION);
		codeDescriptionMap.put(TAGS, TAGS_DESCRIPTION);
		codeDescriptionMap.put(POPUP, POPUP_DESCRIPTION);
		codeDescriptionMap.put(CONNER, CONNER_DESCRIPTION);
		codeDescriptionMap.put(PRODUCT, PRODUCT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
