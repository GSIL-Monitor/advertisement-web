package com.yuanshanbao.dsp.statistics.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticsStatus {

	public static final Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	
	public static final int INIT = 0;
	public static final String INIT_DESCRIPTION = "初始化";
	
	public static final int ALREADY = 1;
	public static final String ALREADY_DESCRPTION = "已审核";
	
	public static final int DELETE = -1;
	public static final String DELETE_DESCRIPTION = "已删除";
	
	static {
		initParams();
	}
	
	public static void initParams() {
		typeDescriptionMap.put(INIT, INIT_DESCRIPTION);
		typeDescriptionMap.put(ALREADY, ALREADY_DESCRPTION);
		typeDescriptionMap.put(DELETE, DELETE_DESCRIPTION);
	}
	
	public static String getTypeDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
	}
}
