package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementPosition {

	protected static Map<String, String> typeDescriptionMap = new LinkedHashMap<String, String>();
	protected static Map<String, String> configMap = new LinkedHashMap<String, String>();
	protected static Map<String, String> sectionMap = new LinkedHashMap<String, String>();

	public static final String WELFARE = "welfare";
	public static final String WELFARE_CONFIG = "WelfareConfig";
	public static final String WELFARE_DESCRIPTION = "福利页面";
	public static final String ADVERTISEMENT_WELFARE = "advertisement";

	public static final String BANNER = "banner";
	public static final String BANNER_CONFIG = "BannerConfig";
	public static final String BANNER_DESCRIPTION = "轮播图";

	public static final String TAGS = "tags";
	public static final String TAGS_CONFIG = "TagsConfig";
	public static final String TAGS_DESCRIPTION = "TAGS";
	
	public static final String DOWNLOAD = "download";
	public static final String DOWNLOAD_DESCRIPTION = "下载页";
	
	public static final String POPUP = "popup";
	public static final String POPUP_CONFIG = "PopupConfig";
	public static final String POPUP_DESCRIPTION = "弹窗红包";
	
	public static final String CONNER = "conner";
	public static final String CONNER_CONFIG = "ConnerConfig";
	public static final String CONNER_DESCRIPTION = "角标红包";
	
	public static final String PROMOTION = "promotion";
	public static final String PROMOTION_CONFIG = "PromotionConfig";
	public static final String PROMOTION_DESCRIPTION = "产品促销红包";
	
	/**
	 * 常量
	 */
	public static final String INDEX = "index";
	public static final String INDEX_DESCRIPTION = "首页页面";
	public static final String ADVERTISEMENT_INDEX = "advertisementIndex";
	
	public static final String USER = "user";
	public static final String USER_DESCRIPTION = "用户页面";
	public static final String ADVERTISEMENT_USER = "advertisementUser";
	
	public static final String CATEGORY = "category";
	public static final String CATEGORY_DESCRIPTION = "广告分类配置";
	public static final String ADVERTISEMENT_CATEGORY = "advertisementCategory";
	
	
	static {
		initCodeDescriptionMap();
		initConfigMap();
		initSectionMap();
	}

	public static void initCodeDescriptionMap() {
		typeDescriptionMap.put(WELFARE, WELFARE_DESCRIPTION);
		typeDescriptionMap.put(BANNER, BANNER_DESCRIPTION);
		typeDescriptionMap.put(TAGS, TAGS_DESCRIPTION);
		typeDescriptionMap.put(DOWNLOAD, DOWNLOAD_DESCRIPTION);
		typeDescriptionMap.put(POPUP, POPUP_DESCRIPTION);
		typeDescriptionMap.put(CONNER, CONNER_DESCRIPTION);
		typeDescriptionMap.put(ADVERTISEMENT_INDEX, INDEX_DESCRIPTION);
		typeDescriptionMap.put(ADVERTISEMENT_USER, USER_DESCRIPTION);
		typeDescriptionMap.put(ADVERTISEMENT_WELFARE, WELFARE_DESCRIPTION);
		typeDescriptionMap.put(ADVERTISEMENT_CATEGORY, CATEGORY_DESCRIPTION);
	}

	private static void initConfigMap() {
		configMap.put(POPUP, POPUP_CONFIG);
		configMap.put(CONNER, CONNER_CONFIG);
		configMap.put(BANNER, BANNER_CONFIG);
		configMap.put(TAGS, TAGS_CONFIG);
		configMap.put(WELFARE, WELFARE_CONFIG);
		configMap.put(PROMOTION, PROMOTION_CONFIG);
	}
	
	private static void initSectionMap() {
		sectionMap.put(WELFARE, ADVERTISEMENT_WELFARE);
		sectionMap.put(INDEX, ADVERTISEMENT_INDEX);
		sectionMap.put(USER, ADVERTISEMENT_USER);
		sectionMap.put(CATEGORY, ADVERTISEMENT_CATEGORY);
	}
	
	public static Map<String, String> getSectionMap() {
		return sectionMap;
	}
	
	public static String getConfig(String code) {
		return configMap.get(code);
	}
	
	public static Map<String, String> getConfigMap() {
		return configMap;
	}

	public static String getDescription(String code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<String, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}

}