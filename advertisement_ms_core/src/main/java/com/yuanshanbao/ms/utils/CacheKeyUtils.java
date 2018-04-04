package com.yuanshanbao.ms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yuanshanbao.common.util.CommonUtil;

public class CacheKeyUtils {
	public static final String USER_FAIL_LOGIN_PREFIX = "USER_FAIL_LOGIN_PREFIX_";

	public static final String ICON_PREFIX = "ICON_PREFIX_";

	public static String getLoginFailCountKey(String userName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String key = USER_FAIL_LOGIN_PREFIX + userName + sdf.format(new Date());

		return CommonUtil.getCacheKey(key);
	}

	public static String getIconnameKey(String iconName) {
		return ICON_PREFIX + iconName;
	}
}
