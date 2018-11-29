package com.yuanshanbao.dsp.bill.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BillStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ORIGINAL_BILL = 1;
	public static final String ORIGINAL_BILL_DESCRIPTION = "原始数据";
	public static final int MERGE_BILL = 2;
	public static final String MERGE_BILL_DESCRIPTION = "合并数据";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ORIGINAL_BILL, ORIGINAL_BILL_DESCRIPTION);
		codeDescriptionMap.put(MERGE_BILL, MERGE_BILL_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
