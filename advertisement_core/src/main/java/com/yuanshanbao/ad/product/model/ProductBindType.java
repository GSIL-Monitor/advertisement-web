package com.yuanshanbao.ad.product.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProductBindType {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	
	public static final int INIT = 0;
	public static final String INIT_DESCRIPTION = "审核中";
	
	public static final int BIND = 1;
	public static final String BIND_DESCRIPTION = "已绑定";

	public static final int UNBIND = 2;
	public static final String UNBIND_DESCRIPTION = "未绑定";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(INIT, INIT_DESCRIPTION);
		codeDescriptionMap.put(BIND, BIND_DESCRIPTION);
		codeDescriptionMap.put(UNBIND, UNBIND_DESCRIPTION);
	}

	public static String getTypeDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return codeDescriptionMap;
	}

}
