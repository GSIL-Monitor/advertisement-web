package com.yuanshanbao.common.util.reflect;


import org.apache.commons.lang.StringUtils;

public class PrintStringUtils {

	private PrintStringUtils() {
	}

	/**
	 * 设置第一位字符为小写
	 * 
	 * @param value
	 * @return
	 */
	public static String setFirstCharToLowerCase(String value) {
		if (StringUtils.isBlank(value))
			return value;
		if (value.length() == 1)
			return value.toLowerCase();
		return value.substring(0, 1).toLowerCase() + value.substring(1);
	}

	/**
	 * 设置第一位字符为大写
	 * 
	 * @param value
	 * @return
	 */
	public static String setFirstCharToUpperCase(String value) {
		if (StringUtils.isBlank(value))
			return value;
		if (value.length() == 1)
			return value.toUpperCase();
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
	
	/**
	 * 添加数据给方法toString用
	 * 
	 * @param sb
	 * @param name
	 * @param value
	 */
	public static void appendValueForToString(StringBuilder sb, String name, String value) {
		sb.append(name);
		sb.append(":");
		sb.append(value);
		sb.append(",");
	}	
}
