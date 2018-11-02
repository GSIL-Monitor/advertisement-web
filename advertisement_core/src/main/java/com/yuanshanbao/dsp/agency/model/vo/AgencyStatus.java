package com.yuanshanbao.dsp.agency.model.vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class AgencyStatus {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ONCHECK = 0;
	public static final String ONCHECK_DESCRIPTION = "待审核";
	public static final int OFFCHECK = 1;
	public static final String OFFCHECK_DESCRIPTION = "审核通过";
	public static final int NOCKECK = 2;
	public static final String NOCKECK_DESCRIPTION = "审核未通过";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONCHECK, ONCHECK_DESCRIPTION);
		codeDescriptionMap.put(OFFCHECK, OFFCHECK_DESCRIPTION);
		codeDescriptionMap.put(NOCKECK, NOCKECK_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
