package com.yuanshanbao.dsp.information.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class InformationStatus {

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final Integer INIT_STATE = 0;
	public static final String INIT_STATE_DESCRIPTION = "初始化";

	public static final Integer SUBMIT_STATE = 1;
	public static final String SUBMIT_STATE_DESCRIPTION = "已提交";

	public static final Integer SUBMIT_SUCCESS_STATE = 2;
	public static final String SUBMIT_SUCCESS_STATE_DESCRIPTION = "提交成功";

	public static final Integer DELIVER_PARTNER_STATE = 3;
	public static final String DELIVER_PARTNER_STATE_DESCRIPTION = "发送给第三方";

	public static final Integer DELIVER_PARTNER_ERROR_STATE = 4;
	public static final String DELIVER_PARTNER_ERROR_STATE_DESCRIPTION = "发送给第三方错误";

	public static final Integer NOT_DELIVER_PARTNER_STATE = 5;
	public static final String NOT_DELIVER_PARTNER_STATE_DESCRIPTION = "不希望提交";

	public static final Integer DELETE_STATE = -1;
	public static final String DELETE_STATE_DESCRIPTION = "已删除";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(INIT_STATE, INIT_STATE_DESCRIPTION);
		codeDescriptionMap.put(SUBMIT_STATE, SUBMIT_STATE_DESCRIPTION);
		codeDescriptionMap.put(SUBMIT_SUCCESS_STATE, SUBMIT_SUCCESS_STATE_DESCRIPTION);
		codeDescriptionMap.put(DELIVER_PARTNER_STATE, DELIVER_PARTNER_STATE_DESCRIPTION);
		codeDescriptionMap.put(DELIVER_PARTNER_ERROR_STATE, DELIVER_PARTNER_ERROR_STATE_DESCRIPTION);
		codeDescriptionMap.put(NOT_DELIVER_PARTNER_STATE, NOT_DELIVER_PARTNER_STATE_DESCRIPTION);
		codeDescriptionMap.put(DELETE_STATE, DELETE_STATE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}