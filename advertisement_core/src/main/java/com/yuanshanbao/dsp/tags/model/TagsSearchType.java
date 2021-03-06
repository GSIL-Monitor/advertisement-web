package com.yuanshanbao.dsp.tags.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class TagsSearchType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final Integer NORMAL = 1;
	public static final String NORMAL_DESCRIPTION = "常规";
	public static final Integer FEATURE = 2;
	public static final String FEATURE_DESCRIPTION = "产品特征";
	public static final Integer CREDIT = 4;
	public static final String CREDIT_DESCRIPTION = "信用证明";
	public static final Integer REPAYMENT = 20;
	public static final String REPAYMENT_DESCRIPTION = "还款方式";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(NORMAL, NORMAL_DESCRIPTION);
		codeDescriptionMap.put(FEATURE, FEATURE_DESCRIPTION);
		codeDescriptionMap.put(CREDIT, CREDIT_DESCRIPTION);
		codeDescriptionMap.put(REPAYMENT, REPAYMENT_DESCRIPTION);
	}

	public static String getDescription(Long code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
