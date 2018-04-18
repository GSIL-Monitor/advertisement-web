package com.yuanshanbao.dsp.quota.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class QuotaType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final Integer CPC = 1;
	public static final String CPC_DESCRIPTION = "CPC";
	public static final Integer CPM = 2;
	public static final String CPM_DESCRIPTION = "CPM";
	public static final Integer CPT = 3;
	public static final String CPT_DESCRIPTION = "CPT";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(CPC, CPC_DESCRIPTION);
		codeDescriptionMap.put(CPM, CPM_DESCRIPTION);
		codeDescriptionMap.put(CPT, CPT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
