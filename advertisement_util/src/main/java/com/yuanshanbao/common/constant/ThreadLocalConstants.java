package com.yuanshanbao.common.constant;

import javax.servlet.http.HttpServletRequest;

public class ThreadLocalConstants {
	// 用户请求对象
	public static ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();

}
