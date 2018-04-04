package com.yuanshanbao.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据格式转换。以后会有独立的包，包含若干个类，实现统一接口。
 * 
 * @author Administrator
 *
 */
public class Formatter {

	/**
	 * object转化为HTTP参数。性能瓶颈在网络通信上，反射影响不大
	 * 
	 * @param object
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String objectToHttpParam(Object object, boolean isRemoveNull) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Field[] fieldArray = object.getClass().getDeclaredFields();
		String result = "?";// 返回值

		String methodName = null;// get方法名
		String fieldValue = null;// field取值
		Object fieldObject = null;
		for (int i = 0; i < fieldArray.length; i++) {
			// 生成方法名 getXxxx.
			methodName = fieldArray[i].getName();
			methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
			methodName = "get" + methodName;

			// 获取方法
			Method method = null;
			try {
				method = object.getClass().getMethod(methodName);
				fieldObject = null;
				if (method != null) {
					// 调用方法，取得参数值
					fieldObject = method.invoke(object);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (null == fieldObject) {
				continue;
			}
			fieldValue = fieldObject.toString();

			if (isRemoveNull && StringUtil.isEmpty(fieldValue)) {
				continue;
			} else {
				// 拼接url参数
				result += fieldArray[i].getName().toLowerCase();
				result += "=";
				result += CodecUtil.urlEncode(fieldValue, "GBK");
				result += "&";
			}

		}

		// 去掉最后一个&
		result = result.substring(0, result.length() - 1);

		return result;
	}

	public static String objectToHttpParam(Object object) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return objectToHttpParam(object, false);
	}

	/**
	 * url参数字符串转化为Map
	 * 
	 * @param condition
	 * @return
	 */
	public static Map<String, String> urlParam2Map(String urlParam) {
		Map<String, String> result = new HashMap();
		// 1. p1=a&p2=3&p3=4 => p1=a; p2=3; p3=4;
		String[] urlParams = urlParam.split("&");

		// 2.p1=a => key=p1; value=a;
		for (int i = 0; i < urlParams.length; i++) {
			String[] params = urlParams[i].split("\\=");
			if (params.length != 2) {
				continue;
			}
			result.put(params[0], params[1]);
		}

		return result;
	}
}