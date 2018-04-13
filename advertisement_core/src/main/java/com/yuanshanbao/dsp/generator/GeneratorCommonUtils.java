package com.yuanshanbao.dsp.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;

import freemarker.template.Template;

public class GeneratorCommonUtils {
	
	private static final char UNDERLINE = '_';
	private static final char SPLIT = '/';
	private static final String REPLACE_PARAM = "${1}";

	/**
	 * @param s
	 *            String
	 * @return 首字母大写
	 */
	public static String firstCharUpperCase(String s) {
		if (s == null || "".equals(s)) {
			return ("");
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * @param s
	 *            String
	 * @return 首字母小写
	 */
	public static String firstCharLowerCase(String s) {
		if (s == null || "".equals(s)) {
			return ("");
		}
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static String camelToUnderline(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (Character.isUpperCase(c)){  
	               sb.append(UNDERLINE);  
	               sb.append(Character.toLowerCase(c));  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
		return sb.toString();
	}

	public static Connection getConnection(String driver, String url,
			String username, String password) {
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username,
					password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 生成文件
	 * 
	 * @param object
	 * @param savePath
	 * @param fileName
	 * @param template
	 */
	public static void createFile(Object object, String savePath, String fileName,
			Template template) {
		String realFileName = savePath + "/" + fileName;

		String realSavePath = savePath;

		File newsDir = new File(realSavePath);
		if (!newsDir.exists()) {
			newsDir.mkdirs();
		}
		// 如果已經存在不创建
		File realFile = new File(realFileName);
		if (realFile.exists()) {
			return;
		}
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("defaultTemplate", object);
		rootMap.put("fileName", fileName);
		try {
			// SYSTEM_ENCODING = "UTF-8";
			Writer out = new OutputStreamWriter(new FileOutputStream(
					realFileName), "UTF-8");
			template.process(rootMap, out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param path
	 * @return 根据包的路径得到package XXX中的XXX
	 */
	public static String getImportPath(String path) {
		StringBuffer result = new StringBuffer();
		String[] strings = path.split("/");
		for (int i = 3, size = strings.length; i < size; i++) {
			result.append(".").append(strings[i]);
		}
		return result.substring(1);
	}
	
	/**
	 * @param path
	 * @return 根据包的路径得到package XXX中的XXX
	 */
	public static String getRealPath(String path, String pkgName) {
		StringBuffer result = new StringBuffer(path);
		if (result.indexOf(REPLACE_PARAM) > 0) {
			int start = result.indexOf(REPLACE_PARAM);
			result.replace(start, start+4, pkgName);
		}
		return result.toString();
	}
	
	public static String splitToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == SPLIT) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	

}
