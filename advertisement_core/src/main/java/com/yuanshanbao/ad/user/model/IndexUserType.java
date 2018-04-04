package com.yuanshanbao.ad.user.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class IndexUserType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int WEIXIN = 1;
	public static final String WEIXIN_DESCRIPTION = "微信登录";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(WEIXIN, WEIXIN_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
