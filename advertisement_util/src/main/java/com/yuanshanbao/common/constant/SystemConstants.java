package com.yuanshanbao.common.constant;

import java.util.HashMap;
import java.util.Map;

public class SystemConstants {
	public static String SYS_ROOT = "";

	public static String REPORT_FOLDER = "";

	public static String HTML_SUCCESS = "";

	public static String HTML_FAILURE = "";

	public static String ICON_FOLDER = "/resources/css/images/icon/";

	public static String SCODE_SPLIT_STR = ":";

	public static String SCODE_LOGIN = "login";

	public static String SCODE_SESSION_LOGIN = "login";

	public static final Map<String, String> SCODE_MAP = new HashMap<String, String>();

	static {
		SCODE_MAP.put(SCODE_LOGIN, SCODE_SESSION_LOGIN);
	}
}
