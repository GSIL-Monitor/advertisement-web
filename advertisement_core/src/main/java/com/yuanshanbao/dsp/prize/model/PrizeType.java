package com.yuanshanbao.dsp.prize.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class PrizeType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int RED = 1;
	public static final String RED_DESCRIPTION = "红包类";
	public static final int INSURANCE = 2;
	public static final String INSURANCE_DESCRIPTION = "增险类";
	public static final int LOAN = 3;
	public static final String LOAN_DESCRIPTION = "贷款类";
	public static final int DISCOUNT = 4;
	public static final String DISCOUNT_DESCRIPTION = "优惠券";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(RED, RED_DESCRIPTION);
		codeDescriptionMap.put(INSURANCE, INSURANCE_DESCRIPTION);
		codeDescriptionMap.put(LOAN, LOAN_DESCRIPTION);
		codeDescriptionMap.put(DISCOUNT, DISCOUNT_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
