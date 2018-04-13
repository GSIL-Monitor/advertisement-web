package com.yuanshanbao.dsp.core;

import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.ret.ComRetCode;

public class InterfaceRetCode {

	public static void printAppodeDesc(PrintWriter pw, int key) {
		String desc = getAppRetDesc(key);
		if (desc == null)
			desc = "This value has no definition.";
		pw.println(key);
		pw.println(desc);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setAppCodeDesc(Map resultMap, int key) {
		if (key == 0) {
			key = ComRetCode.COMMON_FAIL;
		}
		String desc = getAppRetDesc(key);
		if (desc == null)
			desc = "This value has no definition.";
		resultMap.put(ComRetCode.RET_CODE, key);
		resultMap.put(ComRetCode.RET_DESC, desc);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setAppCodeDesc(Map resultMap, int key, String fieldName) {
		if (key == 0) {
			key = ComRetCode.COMMON_FAIL;
		}
		String desc = getAppRetDesc(key);
		if (desc == null)
			desc = "This value has no definition.";
		resultMap.put(ComRetCode.RET_CODE, key);
		resultMap.put(ComRetCode.RET_DESC, desc);
		resultMap.put(ComRetCode.RET_FIELD, fieldName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setSpecAppCodeDesc(Map resultMap, int key, String specDesc) {
		if (key == 0) {
			key = ComRetCode.COMMON_FAIL;
		}
		if (StringUtils.isBlank(specDesc))
			specDesc = getAppRetDesc(key);
		resultMap.put(ComRetCode.RET_CODE, key);
		resultMap.put(ComRetCode.RET_DESC, specDesc);
	}

	/**
	 * 获取app对应的返回编码的描述
	 * 
	 * @param key
	 * @return
	 */
	public static String getAppRetDesc(int key) {
		return IniBean.getIniValue(IniBean.retCodeDescPrefix + String.valueOf(key), ComRetCode.message(key));
	}

	/**
	 * 返回web版接口的返回值
	 * 
	 * @param resultMap
	 * @param key
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setWebCodeDesc(Map resultMap, int key) {
		String desc = getWebRetDesc(key);
		if (desc == null)
			desc = "This value has no definition.";
		resultMap.put("retCode", String.valueOf(key));
		resultMap.put("retDesc", desc);
	}

	/**
	 * 获取web对应的返回编码的描述
	 * 
	 * @param key
	 * @return
	 */
	private static String getWebRetDesc(int key) {
		return IniBean.getIniValue(IniBean.retCodeDescForWebPrefix + String.valueOf(key), ComRetCode.message(key));
	}

	@SuppressWarnings("rawtypes")
	public static boolean hasError(Map resultMap) {
		Object code = resultMap.get(ComRetCode.RET_CODE);
		if (code != null && !code.equals(ComRetCode.SUCCESS)) {
			return true;
		}
		return false;
	}
}
