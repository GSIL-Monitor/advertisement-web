package com.yuanshanbao.ad.article.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArticleSectionStatus {

	protected static Map<Integer, String> sectionButtonDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int  NO_FLY_CLICK = 1;
	public static final String NO_FLY_CLICK_DESCRIPTION = "可点";
	public static final int ALL_NO = 2;
	public static final String ALL_NO_DESCRIPTION = "不可点";


	static {
		initButtonDescriptionMap();
	}

	public static void initButtonDescriptionMap() {
		sectionButtonDescriptionMap.put(NO_FLY_CLICK, NO_FLY_CLICK_DESCRIPTION);
		sectionButtonDescriptionMap.put(ALL_NO, ALL_NO_DESCRIPTION);
	}

	public static String getButtonDescription(Integer code) {
		return sectionButtonDescriptionMap.get(code);
	}

	public static Map<Integer, String> getButtonDescriptionMap() {
		return sectionButtonDescriptionMap;
	}
	
}
