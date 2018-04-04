package com.yuanshanbao.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.dom4j.DocumentException;

/**
 * 序列化为HTTP参数的类
 * 
 * @author Administrator
 *
 */
public class Persistent {

	public Map toMap() {
		Map ret = null;

		try {
			ret = XmlUtil.objectToMap(this);
		} catch (SecurityException e) {
			LogUtil.error(this.getClass().getName() + "toMap():", e);
		} catch (IllegalArgumentException e) {
			LogUtil.error(this.getClass().getName() + "toMap():", e);
		} catch (NoSuchMethodException e) {
			LogUtil.error(this.getClass().getName() + "toMap():", e);
		} catch (IllegalAccessException e) {
			LogUtil.error(this.getClass().getName() + "toMap():", e);
		} catch (InvocationTargetException e) {
			LogUtil.error(this.getClass().getName() + "toMap():", e);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ret.remove("class");
		ret.remove("signature");

		return ret;
	}

	@Override
	public String toString() {
		String ret = null;
		try {
			ret = Formatter.objectToHttpParam(this);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return ret;
	}
}