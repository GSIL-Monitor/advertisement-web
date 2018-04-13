package com.yuanshanbao.dsp.sms.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MarketingTaskType {

	protected static Map<Integer, String> statusDescriptionMap = new LinkedHashMap<Integer, String>();

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final Integer INIT = 1;
	public static final String INIT_DESCRIPTION = "初始化";

	public static final Integer RUNNING = 2;
	public static final String RUNNING_DESCRIPTION = "运行中";

	public static final Integer END = 3;
	public static final String END_DESCRIPTION = "结束任务";

	public static final Integer DELETE = -1;
	public static final String DELETE_DESCRIPTION = "已删除";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(1, "");

		statusDescriptionMap.put(INIT, INIT_DESCRIPTION);
		statusDescriptionMap.put(RUNNING, RUNNING_DESCRIPTION);
		statusDescriptionMap.put(END, END_DESCRIPTION);
		statusDescriptionMap.put(DELETE, DELETE_DESCRIPTION);
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