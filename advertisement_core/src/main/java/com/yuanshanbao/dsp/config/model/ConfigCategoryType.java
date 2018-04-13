package com.yuanshanbao.dsp.config.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigCategoryType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int COMMON = 1;
	public static final String COMMON_DESCRIPTION = "通用配置";
	public static final int APPROVEL = 4;
	public static final String APPROVEL_DESCRIPTION = "审核相关";
	public static final int PRODUCT = 5;
	public static final String PRODUCT_DESCRIPTION = "产品相关";
	public static final int RUIDAI = 6;
	public static final String RUIDAI_DESCRIPTION = "瑞贷相关";
	public static final int WELFARE = 7;
	public static final String WELFARE_DESCRIPTION = "福利页";
	public static final int INDEX = 8;
	public static final String INDEX_DESCRIPTION = "首页";
	public static final int USER = 9;
	public static final String USER_DESCRIPTION = "用户页";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(COMMON, COMMON_DESCRIPTION);
		codeDescriptionMap.put(APPROVEL, APPROVEL_DESCRIPTION);
		codeDescriptionMap.put(PRODUCT, PRODUCT_DESCRIPTION);
		codeDescriptionMap.put(RUIDAI, RUIDAI_DESCRIPTION);
		codeDescriptionMap.put(WELFARE, WELFARE_DESCRIPTION);
		codeDescriptionMap.put(INDEX, INDEX_DESCRIPTION);
		codeDescriptionMap.put(USER, USER_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
