package com.yuanshanbao.dsp.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Property {
	public static Map<String, String> getProperties(String fileName) {
		Map<String, String> map = new HashMap<String, String>();
		Resource resource = new ClassPathResource("config" + File.separator
				+ "generator" + File.separator + fileName);
		if (resource == null || !resource.exists()) {
			return null;
		}
		try {
			Properties p = PropertiesLoaderUtils.loadProperties(resource);
			for (Map.Entry e : p.entrySet()) {
				map.put((String)e.getKey(), (String)e.getValue());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
