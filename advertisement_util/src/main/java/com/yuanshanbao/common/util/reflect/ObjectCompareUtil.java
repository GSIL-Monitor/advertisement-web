package com.yuanshanbao.common.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 内部类(旧代码迁移过来,做兼容的类)
 * 
 * @author dingmingliang
 * 
 */
final class ObjectCompareUtil {
	
	/**
	 * 获取指定类下面,某个字段的值(通过反射实现)
	 * 
	 * @param <T>
	 * @param dataSource
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Object getValueByFieldName(T dataSource, String fieldName) {
		Class objClass = dataSource.getClass();
		Field field;
		Object value = null;
		try {
			field = ReflectUtil.getField(objClass, fieldName);
			if (field == null) {
				return null;
			}
			String methodNameOfGet = ReflectUtil.getGetMethodNameByField(field);			
			Method methodOfGet = objClass.getMethod(methodNameOfGet);
			// 设置对象中的字段
			value = methodOfGet.invoke(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}