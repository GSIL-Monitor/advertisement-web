package com.yuanshanbao.ad.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.dao.IniDao;
import com.yuanshanbao.ad.common.model.Ini;

/**
 * 系统全局配置bean
 * 
 * 
 */
public class IniBean {
	public static final String retCodeDescPrefix = "RETCODE.";
	public static final String retCodeDescForWebPrefix = "RETCODEWEB.";

	private static Map<String, Ini> iniMap = new HashMap<String, Ini>();
	private static Map<String, String> iniValueMap = new HashMap<String, String>();

	private static boolean initial = false;

	@Resource
	private IniDao iniDao;

	private IniBean() {
	}

	/**
	 * 初始化函数
	 */
	@PostConstruct
	public void init() {
		if (!initial) {
			initial = true;
			LoggerUtil.info("init InitBean");
			refresh();
		}
	}

	/**
	 * 刷新
	 */
	public synchronized void refresh() {
		Map<String, Ini> tempIniMap = new HashMap<String, Ini>();
		Map<String, String> tempIniValueMap = new HashMap<String, String>();
		List<Ini> iniList = iniDao.getAllIni();
		LoggerUtil.info(iniList);
		if (iniList != null) {
			for (Ini ini : iniList) {
				tempIniMap.put(ini.getIniName(), ini);
				tempIniValueMap.put(ini.getIniName(), ini.getIniValue());
			}
		}
		iniMap = tempIniMap;
		iniValueMap = tempIniValueMap;
		LoggerUtil.info("refresh " + (iniList == null ? 0 : iniList.size()) + " inis");
	}

	/**
	 * 根据key前缀获得整个ini对象
	 * 
	 * @param key
	 * @return
	 */
	public static List<Ini> getIniMapStartWith(String keyStart) {
		LinkedList<Ini> list = new LinkedList<Ini>();
		for (Map.Entry<String, Ini> ini : iniMap.entrySet()) {
			if (ini.getKey().startsWith(keyStart)) {
				list.add(ini.getValue());
			}
		}
		return list;
	}

	/**
	 * 根据key获得整个ini对象
	 * 
	 * @param key
	 * @return
	 */
	public static Ini getIniMap(String key) {
		return iniMap.get(key);
	}

	/**
	 * 根据key获得ini的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getIniValue(String key) {
		if (!iniValueMap.containsKey(key)) {
			return null;
		}
		return iniValueMap.get(key);
	}

	/**
	 * 根据key获得ini值，可以指定默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getIniValue(String key, String defaultValue) {
		if (iniValueMap.containsKey(key)) {
			return iniValueMap.get(key);
		} else if (defaultValue != null) {
			return defaultValue;
		} else {
			throw new RuntimeException("no ini found for :" + key);
		}
	}

	/**
	 * 直接返回int类型的ini值，可以指定默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIniIntValue(String key, String defaultValue) {
		String iniValue = getIniValue(key, defaultValue);
		int result;
		try {
			result = Integer.parseInt(iniValue);
		} catch (NumberFormatException e) {
			throw new RuntimeException("not found an int for ini :" + key);
		}
		return result;
	}

	/**
	 * 直接返回int类型的ini值，可以指定默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIniIntValue(String key, int defaultValue) {
		int result = defaultValue;
		if (iniValueMap.containsKey(key)) {
			String iniValue = getIniValue(key);
			try {
				result = Integer.parseInt(iniValue);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getIniLongValue(String key, long defaultValue) {
		long result = defaultValue;
		if (iniValueMap.containsKey(key)) {
			String iniValue = getIniValue(key);
			try {
				result = Long.parseLong(iniValue);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIniIntegerValue(String key, int defaultValue) {
		int result = defaultValue;
		if (iniValueMap.containsKey(key)) {
			String iniValue = getIniValue(key);
			try {
				result = Integer.parseInt(iniValue);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return result;
	}

	public static float getIniFloatValue(String key, float defaultValue) {
		float result = defaultValue;
		if (iniValueMap.containsKey(key)) {
			String iniValue = getIniValue(key);
			try {
				result = Float.parseFloat(iniValue);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return result;
	}

}
