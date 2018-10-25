package com.yuanshanbao.dsp.plan.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlanStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "投放中";
	public static final int OFFLINE = 2;
	public static final String OFFLINE_DESCRIPTION = "未投放";
	public static final int INVALID = 0;
	public static final String INVALID_DESCRIPTION = "失效";
	public static final int UNREVIEWED = 3;
	public static final String UNREVIEWED_DESCRIPTION = "未审核";
	public static final int NOTFUNDS = 4;
	public static final String NOTFUNDS_DESCRIPTION = "余额不足";
	public static final int UNDERREVIEWED = 5;
	public static final String UNDERREVIEWED_DESCRIPTION = "审核中";
	public static final int DELETE = -1;
	public static final String DELETE_DESCRIPTION = "已删除";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		codeDescriptionMap.put(OFFLINE, OFFLINE_DESCRIPTION);
		codeDescriptionMap.put(INVALID, INVALID_DESCRIPTION);
		codeDescriptionMap.put(UNREVIEWED, UNREVIEWED_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
