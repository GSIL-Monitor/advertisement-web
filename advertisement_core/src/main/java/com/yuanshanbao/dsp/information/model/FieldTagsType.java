package com.yuanshanbao.dsp.information.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FieldTagsType {

	protected static Map<Long, String> codeDescriptionMap = new LinkedHashMap<Long, String>();

	public static final long HAS_TAGS = 1;
	public static final String HAS_TAGS_DESCRIPTION = "有标签";
	public static final long NONE_TAGS = 2;
	public static final String NONE_TAGS_DESCRIPTION = "无标签";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(HAS_TAGS, HAS_TAGS_DESCRIPTION);
		codeDescriptionMap.put(NONE_TAGS, NONE_TAGS_DESCRIPTION);
	}

	public static String getDescription(Long code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Long, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
	
}
