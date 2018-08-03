package com.yuanshanbao.dsp.channel.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChannelAllocateStatus {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int ALLOCATED = 1;
	public static final String ZHIFU_DESCRIPTION = "已分配";
	public static final int UNALLOCATED = 0;
	public static final String ZIXUN_DESCRIPTION = "未分配";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ALLOCATED, ZHIFU_DESCRIPTION);
		codeDescriptionMap.put(UNALLOCATED, ZIXUN_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
