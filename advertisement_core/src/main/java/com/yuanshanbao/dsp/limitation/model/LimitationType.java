package com.yuanshanbao.dsp.limitation.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitationType {
	
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	protected static Map<Integer, String> statusDescriptionMap = new LinkedHashMap<Integer, String>();
	
	public static final int DAY = 1;
	public static final String DAY_DESCRIPTION = "日限额";
	public static final int MONTH = 2;
	public static final String MONTH_DESCRIPTION = "月限额";
	public static final int YEAR = 3;
	public static final String YEAR_DESCRIPTION = "年限额";
	
	
	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "上线";
	public static final int REJECT = 3;
	public static final String REJECT_DESCRIPTION = "剔除当前渠道";
	public static final int OFFLINE = -1;
	public static final String OFFLINE_DESCRIPTION = "下线";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(DAY, DAY_DESCRIPTION);
		codeDescriptionMap.put(MONTH, MONTH_DESCRIPTION);
		codeDescriptionMap.put(YEAR, YEAR_DESCRIPTION);
		
		statusDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		statusDescriptionMap.put(REJECT, REJECT_DESCRIPTION);
		statusDescriptionMap.put(OFFLINE, OFFLINE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
	
	public static String getStatusDescription(Integer code) {
		return statusDescriptionMap.get(code);
	}

	public static Map<Integer, String> getStatusDescriptionMap() {
		return statusDescriptionMap;
	}
}
