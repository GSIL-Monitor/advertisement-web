package com.yuanshanbao.dsp.page.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageType {

	protected static Map<Integer, String> typeMap = new LinkedHashMap<Integer, String>();

	public static final Integer COMMON = 1;
	public static final String COMMON_DESCRIPTION = "普通";

	public static final Integer IMPORT_TASK = 2;
	public static final String IMPORT_TASK_DESCRIPTION = "数据任务导入模板";
	
	public static final Integer EXPORT_TASK = 3;
	public static final String EXPORT_TASK_DESCRIPTION = "数据任务导出模板";

	static {
		initMap();
	}

	public static void initMap() {
		typeMap.put(COMMON, COMMON_DESCRIPTION);
		typeMap.put(IMPORT_TASK, IMPORT_TASK_DESCRIPTION);
		typeMap.put(EXPORT_TASK, EXPORT_TASK_DESCRIPTION);
	}

	public static String getLevelValue(Integer code) {
		return typeMap.get(code);
	}

	public static Map<Integer, String> getLevelMap() {
		return typeMap;
	}
}
