package com.yuanshanbao.dsp.bill.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BillType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int DEDUCTION = 1;
	public static final String DEDUCTION_DESCRIPTION = "扣费";
	public static final int RECHARGE = 2;
	public static final String RECHARGE_DESCRIPTION = "充值";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(DEDUCTION, DEDUCTION_DESCRIPTION);
		codeDescriptionMap.put(RECHARGE, RECHARGE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
