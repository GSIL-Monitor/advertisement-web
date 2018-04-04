package com.yuanshanbao.ad.article.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArticleStatus {

	protected static Map<Integer, String> buttonLinkDescriptionMap = new LinkedHashMap<Integer, String>();
	
	protected static Map<Integer, String> contentTypeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	protected static Map<Integer, String> categoryDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int FLY_CLICK = 1;
	public static final String FLY_CLICK_DESCRIPTION = "悬浮可点";
	public static final int FLY_NO_CLICK = 2;
	public static final String FLY_NO_CLICK_DESCRIPTION = "悬浮不可点";
	public static final int  NO_FLY_CLICK = 3;
	public static final String NO_FLY_CLICK_DESCRIPTION = "不悬浮可点";
	public static final int ALL_NO = 4;
	public static final String ALL_NO_DESCRIPTION = "不悬浮不可点";

	
	public static final int SIMPLE = 1;
	public static final String SIMPLE_DESCRIPTION = "简单版";
	
	public static final int COMPLEX = 2;
	public static final String COMPLEX_DESCRIPTION = "复杂版";
	
	public static final int NEWS = 1;
	public static final String NEWS_DESCRIPTION = "最新资讯";
	
	public static final int HOT = 2;
	public static final String HOT_DESCRIPTION = "热门问题";
	

	static {
		initButtonDescriptionMap();
	}

	public static void initButtonDescriptionMap() {
		buttonLinkDescriptionMap.put(FLY_CLICK, FLY_CLICK_DESCRIPTION);
		buttonLinkDescriptionMap.put(FLY_NO_CLICK, FLY_NO_CLICK_DESCRIPTION);
		buttonLinkDescriptionMap.put(NO_FLY_CLICK, NO_FLY_CLICK_DESCRIPTION);
		buttonLinkDescriptionMap.put(ALL_NO, ALL_NO_DESCRIPTION);
		
		contentTypeDescriptionMap.put(SIMPLE, SIMPLE_DESCRIPTION);
		contentTypeDescriptionMap.put(COMPLEX, COMPLEX_DESCRIPTION);
		
		categoryDescriptionMap.put(NEWS, NEWS_DESCRIPTION);
		categoryDescriptionMap.put(HOT, HOT_DESCRIPTION);
	}

	public static String getButtonDescription(Integer code) {
		return buttonLinkDescriptionMap.get(code);
	}

	public static Map<Integer, String> getButtonDescriptionMap() {
		return buttonLinkDescriptionMap;
	}
	
	public static String getContentTypeDescription(Integer code) {
		return contentTypeDescriptionMap.get(code);
	}
	
	public static Map<Integer, String> getContentTypeDescription() {
		return contentTypeDescriptionMap;
	}
	
	public static String getCategoryDescription(Integer code) {
		return categoryDescriptionMap.get(code);
	}
	
	public static Map<Integer, String> getCategoryDescription() {
		return categoryDescriptionMap;
	}
	
}
