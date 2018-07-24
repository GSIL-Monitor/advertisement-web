package com.yuanshanbao.dsp.apply.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApplyStatus {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, Long> tagMap = new LinkedHashMap<Integer, Long>();

	public static final int NOT = -1;
	public static final String NOT_DESCRIPTION = "未申请";
	public static final int VIEW = 0;
	public static final String VIEW_DESCRIPTION = "已查看";
	public static final int APPLING = 1;
	public static final String APPLING_DESCRIPTION = "申请中";
	public static final Long APPLING_TAGID = 57L;
	public static final int APPLIED = 2;
	public static final String APPLIED_DESCRIPTION = "已申请";
	public static final Long APPLIED_TAGID = 71L;
	public static final int REVIEWING = 3;
	public static final String REVIEWING_DESCRIPTION = "审核中";
	public static final int REJECT = 4;
	public static final String REJECT_DESCRIPTION = "未通过";
	public static final Long REJECT_TAGID = 68L;
	public static final int APPROVAL = 5;
	public static final String APPROVAL_DESCRIPTION = "已批贷";
	public static final Long APPROVAL_TAGID = 69L;
	public static final int DISBURSEMENT = 6;
	public static final String DISBURSEMENT_DESCRIPTION = "已放款";
	public static final Long DISBURSEMENT_TAGID = 70L;
	public static final int REPAYING = 7;
	public static final String REPAYING_DESCRIPTION = "还款中";
	public static final int REPAID = 8;
	public static final String REPAID_DESCRIPTION = "已还款";
	public static final Long REPAID_TAGID = 72L;
	public static final int OVERDUE = 9;
	public static final String OVERDUE_DESCRIPTION = "逾期";
	public static final Long OVERDUE_TAGID = 67L;
	public static final int DELETE = -1;
	public static final String DELETE_DESCRIPTION = "已删除";

	static {
		initCodeDescriptionMap();
		initTagMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(NOT, NOT_DESCRIPTION);
		codeDescriptionMap.put(VIEW, VIEW_DESCRIPTION);
		codeDescriptionMap.put(APPLING, APPLING_DESCRIPTION);
		codeDescriptionMap.put(APPLIED, APPLIED_DESCRIPTION);
		codeDescriptionMap.put(REVIEWING, REVIEWING_DESCRIPTION);
		codeDescriptionMap.put(APPROVAL, APPROVAL_DESCRIPTION);
		codeDescriptionMap.put(DISBURSEMENT, DISBURSEMENT_DESCRIPTION);
		codeDescriptionMap.put(REPAYING, REPAYING_DESCRIPTION);
		codeDescriptionMap.put(REPAID, REPAID_DESCRIPTION);
		codeDescriptionMap.put(OVERDUE, OVERDUE_DESCRIPTION);
		codeDescriptionMap.put(DELETE, DELETE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

	public static void initTagMap() {
		tagMap.put(APPLING, APPLING_TAGID);
		tagMap.put(APPLIED, APPLIED_TAGID);
		tagMap.put(REJECT, REJECT_TAGID);
		tagMap.put(APPROVAL, APPROVAL_TAGID);
		tagMap.put(DISBURSEMENT, DISBURSEMENT_TAGID);
		tagMap.put(REPAID, REPAID_TAGID);
		tagMap.put(OVERDUE, OVERDUE_TAGID);
	}

	public static Long getTagID(Integer code) {
		return tagMap.get(code);
	}

	public static Map<Integer, Long> getTagMap() {
		return tagMap;
	}
}
