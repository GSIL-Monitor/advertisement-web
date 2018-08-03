package com.yuanshanbao.dsp.advertisement.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdvertisementDisplayType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ADD = 1;
	public static final String ADD_DESCRIPTION = "新增";
	public static final int COVER = 0;
	public static final String COVER_DESCRIPTION = "覆盖";
	public static final int DELETE = -1;
	public static final String DELETE_DESCRIPTION = "删除";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ADD, ADD_DESCRIPTION);
		codeDescriptionMap.put(COVER, COVER_DESCRIPTION);
		codeDescriptionMap.put(DELETE, DELETE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
