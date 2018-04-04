package com.yuanshanbao.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * properties配置加载类 加载classpath下的.properties配置文件
 * 
 * @author 开发支持中心。
 */
public class PropertyUtil {

	private static Logger logger = Logger.getLogger(PropertyUtil.class);

	public static String key_config = "keys.properties";
	public static String system_config = "config.properties";
	private static Map<String, String> config_map = new HashMap<String, String>();

	static {
		auto_load(key_config);
		auto_load(system_config);
	}

	@SuppressWarnings("rawtypes")
	private static void auto_load(String name) {

		try {
			String pre = CommonUtil.getEnvironment();
			Resource resource = new ClassPathResource("config" + File.separator + pre + File.separator + name);
			if (resource == null || !resource.exists()) {
				return;
			}
			Properties p = PropertiesLoaderUtils.loadProperties(resource);
			for (Map.Entry e : p.entrySet()) {
				config_map.put((String) e.getKey(), (String) e.getValue());
			}
		} catch (IOException e) {
			logger.fatal("load property file failed. file name: " + name, e);
		}
	}

	public static String getProperty(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return config_map.get(key);
	}

	public static void main(String[] args) {
		System.out.println(getProperty("image.temp.dir"));
	}
}
