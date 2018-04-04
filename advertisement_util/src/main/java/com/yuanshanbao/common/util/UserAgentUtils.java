package com.yuanshanbao.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class UserAgentUtils {

	public static String android = "Android";
	public static String iphone = "iPhone";
	public static String ipad = "iPad";
	public static String iOS = "iOS";
	public static String noDevice = "未知设备";

	//获取用户UA信息  
	public static String getUAInfo(HttpServletRequest request) {
		if (null == request)
			return "";
		return request.getHeader("User-Agent");
	}

	//获取用户Browser信息  
	public static String getBrowser(String ua) {

		if (null == ua)
			return "";
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		Browser browser = userAgent.getBrowser();
		return browser.toString();
	}

	//获取用户os信息  
	public static String getOS(String ua) {

		if (null == ua)
			return noDevice;
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		OperatingSystem os = userAgent.getOperatingSystem();
		return os.toString();
	}

	//获取移动用户操作系统    
	public static String getMobileOS(String userAgent) {
		if (userAgent.contains(android)) {
			return android;
		} else if (userAgent.contains(iphone)) {
			return iphone;
		} else if (userAgent.contains(ipad)) {
			return ipad;
		} else {
			return "others";
		}
	}

	//获取用户手机型号    
	public static String getDeviceType(String userAgent) {

		if (null == userAgent || "" == userAgent)
			return noDevice;

		String OS = UserAgentUtils.getMobileOS(userAgent);
		if (OS.equals(android)) {
			String rex = "[()]+";
			String[] str = userAgent.split(rex);
			str = str[1].split("[;]");
			String[] res = null;
			for (String split : str) {
				if (split.contains("Build/")) {
					res = split.split("Build/");
				}
			}

			return res[0].trim();
		} else if (OS.equals(iphone)) {
			return "iPhone_iOS";
		} else if (OS.equals(ipad)) {
			return ipad;
		} else {
			return getOS(userAgent);
		}

	}

	//获取用户手机型号    
	public static String getSystemVersion(String userAgent) {

		if (null == userAgent || "" == userAgent)
			return noDevice;

		String OS = UserAgentUtils.getMobileOS(userAgent);
		if (OS.equals(android)) {
			String rex = "[()]+";
			String[] str = userAgent.split(rex);
			str = str[1].split("[;]");
			String[] res = str[1].trim().split(" ");
			return res[1].trim();
		} else if (OS.equals(iphone)) {
			String[] str = userAgent.split("[()]+");
			String res = str[1].split("OS")[1].split("like")[0].trim().replace("_", ".");
			return res;
		} else if (OS.equals(ipad)) {
			return ipad;
		} else {
			return getOS(userAgent);
		}

	}

	//获取用户系统名称
	public static String getSystemName(String userAgent) {
		if (userAgent.contains(android)) {
			return android;
		} else if (userAgent.contains(iphone)) {
			return iOS;
		} else if (userAgent.contains(ipad)) {
			return iOS;
		} else {
			return "others";
		}
	}

	//获取用户系统名称
	public static boolean isMobile(String userAgent) {
		String[] deviceArray = new String[] { "android", "iphone", "symbianos", "windows phone", "ipad", "ipod", "ios" };
		if (StringUtils.isBlank(userAgent)) {
			return false;
		}
		for (int i = 0; i < deviceArray.length; i++) {
			if (userAgent.toLowerCase().indexOf(deviceArray[i]) > 0) {
				return true;
			}
		}
		return false;
	}
}
