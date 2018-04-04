package com.yuanshanbao.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class JSPHelper {
	
	/**
	 * 获得用户最初ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static final String getRemoteAddr(HttpServletRequest request) {

		String rip = request.getRemoteAddr();
		try {
			String xff = request.getHeader("X-Forwarded-For");
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("Proxy-Client-IP");
			}
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("WL-Proxy-Client-IP");
			}
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("HTTP_CLIENT_IP");
			}
			if (xff == null || xff.length() == 0 || "unknown".equalsIgnoreCase(xff)) {
				xff = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			String ip;
			if (xff != null && xff.length() != 0) {
				int px = xff.indexOf(',');
				if (px != -1) {
					ip = xff.substring(0, px);
				} else {
					ip = xff;
				}
			} else {
				ip = rip;
			}
			return ip.trim();
		} catch (Exception e) {
			LoggerUtil.error("[getRemoteAddr]", e);
		}
		return rip.trim();
	}
	
	/**
	 * 获取最后一跳服务器IP
	 * @param request
	 * @return
	 */
	public static final String getServerAddr(HttpServletRequest request) {
		String xRealIP = request.getHeader("X-Real-IP");
		if(StringUtils.isNotBlank(xRealIP)){
			xRealIP = xRealIP.trim();
		}
		return xRealIP;
	}
	
	
	
	
	
	/**
	 * 调用shell取服务器的ip地址。多个内外网地址之间用,分隔。
	 * 
	 * @return 用分隔符,分隔的多个ip地址。
	 */
	public static String getServerIp() {

		StringBuffer ips = new StringBuffer();
		try {
			String[] command = { "/bin/sh", "-c", "/sbin/ifconfig | grep 'Bcast' | awk '{print $2}' | sed -e 's/addr.//g'" };
			Process process = Runtime.getRuntime().exec(command);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			BufferedReader input = new BufferedReader(ir);
			String line = "";
			while ((line = input.readLine()) != null) {
				ips.append(line).append(",");
			}
			input.close();
			ir.close();
			input = null;
			ir = null;
			process.getInputStream().close();
			process.getOutputStream().close();
			process.getErrorStream().close();
			process.destroy();
			process = null;
		} catch (Exception e) {
			LoggerUtil.error("获取IP异常：", e);
//			e.printStackTrace();
		}
		return ips.toString();
	}
	
	/**
	 * 获取linux机器的内网ip地址。
	 * 
	 * @return
	 */
	public static String getIP() {

		String serverIP = JSPHelper.getServerIp();
		if (serverIP != null) {
			String ipArray[] = serverIP.split(",");
			// 取最后一个IP地址 , 内网IP地址。
			if (ipArray != null && ipArray.length > 0) {
				return ipArray[ipArray.length - 1];
			}
			return null;
		}
		return null;
	}
	
	/**
	 * 将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
	 * 
	 * @param strIP
	 * @return
	 */
	public static long ipToLong(String strIP) {

		String[] ip = strIP.split("\\x2e");
		if (ip.length == 4) {
			return (Long.valueOf(ip[0]).longValue() << 24) + (Long.valueOf(ip[1]).longValue() << 16) + (Long.valueOf(ip[2]).longValue() << 8) + Long.valueOf(ip[3]).longValue(); // ip1*256*256*256+ip2*256*256+ip3*256+ip4
		} else {
			return 0;
		}
	}
	
	
	public static boolean isWindows() {
		String osName = System.getProperty("os.name");
		if (osName.matches("[Ww][Ii][Nn][Dd][Oo][Ww][Ss].*")
				&& !File.separator.equals("/")) {
			return true;
		}
		return false;
	}

}
