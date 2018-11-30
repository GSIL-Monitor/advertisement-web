package com.yuanshanbao.dsp.bankcard.model.vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class BankCardStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int NULL = 0;
	public static final String NUll_DESCRIPTION = "暂无";
	public static final int APPROVING = 1;
	public static final String APPROVING_DESCRIPTION = "审批中";
	public static final int CANCEL = 2;
	public static final String CANCEL_DESCRIPTION = "取消";
	public static final int REFESEND_TO = 3;
	public static final String REFESEND_TO_DESCRIPTION = "拒绝";
	public static final int APPROVED = 4;
	public static final String APPROVED_DESCRIPTION = "批核";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(NULL, NUll_DESCRIPTION);
		codeDescriptionMap.put(APPROVING, APPROVING_DESCRIPTION);
		codeDescriptionMap.put(CANCEL, CANCEL_DESCRIPTION);
		codeDescriptionMap.put(REFESEND_TO, REFESEND_TO_DESCRIPTION);
		codeDescriptionMap.put(APPROVED, APPROVED_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
