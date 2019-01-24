package com.yuanshanbao.dsp.agency.model.vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class AgencyType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int INVITE = 1;
	public static final String INVITE_DESCRIPTION = "邀请";
	public static final int CREDIT_CARD = 2;
	public static final String CREDIT_CARD_DESCRIPTION = "散户办卡";

	public static final int AGENT_CREDIT_CARD = 3;
	public static final String AGENT_CREDIT_CARD_DESCRIPTION = "代理商办卡";

	public static final int NO_INVITE = 4;
	public static final String NO_INVITE_CARD_DESCRIPTION = "无邀请";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(INVITE, INVITE_DESCRIPTION);
		codeDescriptionMap.put(CREDIT_CARD, CREDIT_CARD_DESCRIPTION);
		codeDescriptionMap.put(AGENT_CREDIT_CARD, AGENT_CREDIT_CARD_DESCRIPTION);
		codeDescriptionMap.put(NO_INVITE, NO_INVITE_CARD_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
