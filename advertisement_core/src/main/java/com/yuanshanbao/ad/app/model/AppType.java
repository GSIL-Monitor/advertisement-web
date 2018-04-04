package com.yuanshanbao.ad.app.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AppType {

	protected static Map<String, String> codeDescriptionMap = new LinkedHashMap<String, String>();
	protected static Map<Long, String> keyMap = new LinkedHashMap<Long, String>();
	protected static Map<Long, String> descriptionMap = new LinkedHashMap<Long, String>();

	public static final String XINGDAI = "xingdai";
	public static final Long XINGDAI_ID = 1L;
	public static final String XINGDAI_SHORT = "x";
	public static final String XINGDAI_DESCRIPTION = "兴贷";

	public static final String RUIDAI = "ruidai";
	public static final Long RUIDAI_ID = 2L;
	public static final String RUIDAI_SHORT = "r";
	public static final String RUIDAI_DESCRIPTION = "瑞贷";

	static {
		initCodeDescriptionMap();
		initKeyMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(XINGDAI, XINGDAI_DESCRIPTION);
		codeDescriptionMap.put(RUIDAI, RUIDAI_DESCRIPTION);

		descriptionMap.put(XINGDAI_ID, XINGDAI_DESCRIPTION);
		descriptionMap.put(RUIDAI_ID, RUIDAI_DESCRIPTION);
	}

	public static void initKeyMap() {
		keyMap.put(XINGDAI_ID, XINGDAI);
		keyMap.put(RUIDAI_ID, RUIDAI);
	}

	public static String getDescription(String code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Long, String> getKeyMap() {
		return keyMap;
	}

	public static String getkey(Long id) {
		return keyMap.get(id);
	}

	public static Map<String, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

	public static String getDescription(Long code) {
		return descriptionMap.get(code);
	}

	public static Map<Long, String> getDescriptionMap() {
		return descriptionMap;
	}
}
