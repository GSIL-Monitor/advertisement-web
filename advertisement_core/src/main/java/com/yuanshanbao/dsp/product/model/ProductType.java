package com.yuanshanbao.dsp.product.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProductType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int INSURANCE = 1;
	public static final String INSURANCE_DESCRIPTION = "保险";

	public static final int LOAN = 2;
	public static final String LOAN_DESCRIPTION = "贷款";

	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "常规";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(INSURANCE, INSURANCE_DESCRIPTION);
		codeDescriptionMap.put(LOAN, LOAN_DESCRIPTION);
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
	}

	public static String getTypeDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return codeDescriptionMap;
	}

}
