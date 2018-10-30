package com.yuanshanbao.dsp.material.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MaterialStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ONLINE = 1;
	public static final String ONLINE_DESCRIPTION = "投放中";
	public static final int UNREVIEWED = 2;
	public static final String UNREVIEWED_DESCRIPTION = "未审核";
	public static final int UNDERREVIEWED = 3;
	public static final String UNDERREVIEWED_DESCRIPTION = "审核中";
	public static final int DENIED = 4;
	public static final String DENIEDD_DESCRIPTION = "驳回";
	public static final int DELETE = -1;
	public static final String DELETE_DESCRIPTION = "已删除";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ONLINE, ONLINE_DESCRIPTION);
		codeDescriptionMap.put(UNREVIEWED, UNREVIEWED_DESCRIPTION);
		codeDescriptionMap.put(UNDERREVIEWED, UNDERREVIEWED_DESCRIPTION);
		codeDescriptionMap.put(DENIED, DENIEDD_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
