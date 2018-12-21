package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementProjectType {
protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	public static final int EDUCATION = 107;
	public static final String EDUCATION_DESCRIPTION = "教育app";
	
	public static final int VIPKID = 108;
	public static final String VIPKID_DESCRIPTION = "vipKid";
	
	public static final int  WANGZHUAN= 117;
	public static final String WANGZHUAN_DESCRIPTION = "网赚";
	
	public static final int WZAGENT = 118;
	public static final String WZAGENT_DESCRIPTION = "网赚代理版";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(EDUCATION, EDUCATION_DESCRIPTION);
		codeDescriptionMap.put(VIPKID, VIPKID_DESCRIPTION);
		codeDescriptionMap.put(WANGZHUAN, WANGZHUAN_DESCRIPTION);
		codeDescriptionMap.put(WZAGENT, WZAGENT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
