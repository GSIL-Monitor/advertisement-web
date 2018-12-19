package com.yuanshanbao.dsp.partner.agent;

import java.util.LinkedHashMap;
import java.util.Map;

public class DspAgentType {
	protected static Map<String, String> codeDescriptionMap = new LinkedHashMap<String, String>();

	public static final String MYZT = "";
	public static final String MYZT_DESCRIPTION = "蚂蚁智投";
	public static final String FEIFAN = "com.yuanshanbao.dsp.partner.agent.feifan.FeiFanDspHandler";
	public static final String FEIFAN_DESCRIPTION = "非凡";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(MYZT, MYZT_DESCRIPTION);
		codeDescriptionMap.put(FEIFAN, FEIFAN_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<String, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
