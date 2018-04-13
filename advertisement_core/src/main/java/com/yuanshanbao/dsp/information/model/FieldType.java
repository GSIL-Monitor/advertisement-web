package com.yuanshanbao.dsp.information.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FieldType {

	public static final String FIELD_VALUE_SPLIT = "~!@";
	
	/*public static final String FIELD_NAME_EDUCATION = "Education_";
	public static final String FIELD_NAME_RESUME = "Resume_";
	public static final String FIELD_NAME_BASE_INFO = "BaseInfo_";
	*/
	public static final String FIELD_NAME_MOBILE = "BaseInfo_mobile";
	public static final String FIELD_NAME_MOBILE_DESC = "手机号";

	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int TEXT_FIELD = 1;
	public static final String TEXT_FIELD_DESCRIPTION = "单行文本框";
	public static final int TEXT_NUMBER = 2;
	public static final String TEXT_NUMBER_DESCRIPTION = "数字输入框";
	public static final int TEXT_AREA = 3;
	public static final String TEXT_AREA_DESCRIPTION = "多行文本框";
	public static final int DATE_SELECT = 4;
	public static final String DATE_SELECT_DESCRIPTION = "选择日期";
	public static final int MONTH_SELECT = 5;
	public static final String MONTH_SELECT_DESCRIPTION = "选择年月";
	public static final int SELECT = 6;
	public static final String SELECT_DESCRIPTION = "单选下拉框";
	public static final int CHECK_BOX = 7;
	public static final String CHECK_BOX_DESCRIPTION = "多选下拉框";
	public static final int RADIO_BUTTON = 8;
	public static final String RADIO_BUTTON_DESCRIPTION = "单项按钮";
	public static final int CHOICE_BUTTON = 9;
	public static final String CHOICE_BUTTON_DESCRIPTION = "双项按钮";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(TEXT_FIELD, TEXT_FIELD_DESCRIPTION);
		codeDescriptionMap.put(TEXT_AREA, TEXT_AREA_DESCRIPTION);
		codeDescriptionMap.put(DATE_SELECT, DATE_SELECT_DESCRIPTION);
		codeDescriptionMap.put(MONTH_SELECT, MONTH_SELECT_DESCRIPTION);
		codeDescriptionMap.put(SELECT, SELECT_DESCRIPTION);
		codeDescriptionMap.put(CHECK_BOX, CHECK_BOX_DESCRIPTION);
		codeDescriptionMap.put(TEXT_NUMBER, TEXT_NUMBER_DESCRIPTION);
		codeDescriptionMap.put(RADIO_BUTTON, RADIO_BUTTON_DESCRIPTION);
		codeDescriptionMap.put(CHOICE_BUTTON, CHOICE_BUTTON_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
	
}
