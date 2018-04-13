package com.yuanshanbao.dsp.user.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BrokerStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int COMMIT = 0;
	public static final String COMMIT_DESCRIPTION = "已提交";
	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "上线";
	public static final int AUDITING = 2;
	public static final String AUDITING_DESCRIPTION = "审核中";
	public static final int FAILED = 3;
	public static final String FAILED_DESCRIPTION = "审核未通过";
	
	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(COMMIT, COMMIT_DESCRIPTION);
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		codeDescriptionMap.put(AUDITING, AUDITING_DESCRIPTION);
		codeDescriptionMap.put(FAILED, FAILED_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
