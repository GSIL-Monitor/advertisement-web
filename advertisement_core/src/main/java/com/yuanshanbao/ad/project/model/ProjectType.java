package com.yuanshanbao.ad.project.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProjectType {

	protected static Map<Integer, String> typeMap = new LinkedHashMap<Integer, String>();

	public static final Integer INSURANCE = 1;
	public static final String INSURANCE_DESCRIPTION = "保险";

	public static final Integer LOAN = 2;
	public static final String LOAN_DESCRIPTION = "贷款";

	static {
		initMap();
	}

	public static void initMap() {
		typeMap.put(INSURANCE, INSURANCE_DESCRIPTION);
		typeMap.put(LOAN, LOAN_DESCRIPTION);
	}

	public static String getLevelValue(Integer code) {
		return typeMap.get(code);
	}

	public static Map<Integer, String> getLevelMap() {
		return typeMap;
	}
}
