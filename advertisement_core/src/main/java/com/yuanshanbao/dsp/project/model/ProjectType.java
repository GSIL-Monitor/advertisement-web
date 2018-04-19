package com.yuanshanbao.dsp.project.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProjectType {

	protected static Map<String, String> typeMap = new LinkedHashMap<String, String>();

	public static final String SHIJIJIAYUAN = "1";
	public static final String SHIJIJIAYUAN_DESCRIPTION = "jiayuan";



	static {
		initMap();
	}

	public static void initMap() {
		typeMap.put(SHIJIJIAYUAN_DESCRIPTION, SHIJIJIAYUAN);
	}

	public static String getProjectId(String key) {
		return typeMap.get(key);
	}

	public static Map<String, String> getLevelMap() {
		return typeMap;
	}
}
