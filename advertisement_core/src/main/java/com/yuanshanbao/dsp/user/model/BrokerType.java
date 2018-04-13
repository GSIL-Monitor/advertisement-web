package com.yuanshanbao.dsp.user.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BrokerType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int INSURANCE_BROKER = 1;
	public static final String INSURANCE_BROKER_DESCRIPTION = "保险专员";
	public static final int LOAN_BROKER = 2;
	public static final String LOAN_BROKER_DESCRIPTION = "信贷专员";
	
	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(LOAN_BROKER, LOAN_BROKER_DESCRIPTION);
		codeDescriptionMap.put(INSURANCE_BROKER, INSURANCE_BROKER_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
