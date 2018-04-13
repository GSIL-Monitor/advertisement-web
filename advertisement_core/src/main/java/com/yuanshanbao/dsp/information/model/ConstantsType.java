package com.yuanshanbao.dsp.information.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantsType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int MAX = 1;
	public static final String MAX_DESCRIPTION = "最大值";
	public static final int MIN = 2;
	public static final String MIN_DESCRIPTION = "最小值";
	public static final int NAME = 3;
	public static final String NAME_DESCRIPTION = "姓名";
	public static final int REGEX = 4;
	public static final String REGEX_DESCRIPTION = "正则限制";
	public static final int TYPE = 5;
	public static final String TYPE_DESCRIPTION = "内容类型";
	public static final int INCLUDING = 6;
	public static final String INCLUDING_DESCRIPTION = "包含XX";
	public static final int EXCLUDING = 7;
	public static final String EXCLUDING_DESCRIPTION = "不包含XX";
	public static final int MAX_LENGTH = 8;
	public static final String MAX_LENGTH_DESCRIPTION = "最大长度";
	public static final int MIN_LENGTH = 9;
	public static final String MIN_LENGTH_DESCRIPTION = "最小长度";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(MAX, MAX_DESCRIPTION);
		codeDescriptionMap.put(MIN, MIN_DESCRIPTION);
		codeDescriptionMap.put(NAME, NAME_DESCRIPTION);
		codeDescriptionMap.put(REGEX, REGEX_DESCRIPTION);
		codeDescriptionMap.put(TYPE, TYPE_DESCRIPTION);
		codeDescriptionMap.put(INCLUDING, INCLUDING_DESCRIPTION);
		codeDescriptionMap.put(EXCLUDING, EXCLUDING_DESCRIPTION);
		codeDescriptionMap.put(MAX_LENGTH, MAX_LENGTH_DESCRIPTION);
		codeDescriptionMap.put(MIN_LENGTH, MIN_LENGTH_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
