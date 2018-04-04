package com.yuanshanbao.common.util.reflect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * JAVA反射相关的Util
 * 
 * @author dingmingliang
 * 
 */
public final class ReflectUtil {

	private ReflectUtil() {
	}

	private static Logger logger = Logger.getLogger(ReflectUtil.class);

	/**
	 * Key: class<br>
	 * Value: class对应的所有Field字段
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Map<Class, SoftReference<List<Field>>> CLASS_FIELDLIST_MAP = new HashMap<Class, SoftReference<List<Field>>>();

	/**
	 * Key: class<br>
	 * Value: class对应的所有Field字段的Map
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Map<Class, SoftReference<Map<String, Field>>> CLASS_FIELDMAP_MAP = new HashMap<Class, SoftReference<Map<String, Field>>>();

	/**
	 * 拿到objClass对应Class所有的Field集合(包括父类的Field)
	 * 
	 * @param objClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Field> getFieldListByClass(Class objClass) {
		// 0.参数判断
		if (objClass == Object.class)
			return null;

		// 1.尝试先从缓存中获取
		List<Field> fieldListOfRet = CollectionUtil.getValueBySoftMap(CLASS_FIELDLIST_MAP, objClass);
		if (fieldListOfRet != null) {
			return fieldListOfRet;
		}
		fieldListOfRet = new ArrayList<Field>();

		// 2.尝试实时解析(需要将子类作为最后更新者)
		Field[] fieldArray = objClass.getDeclaredFields();
		Map<String, Field> fieldNameAndFieldMap = new HashMap<String, Field>();
		// 递归调用父类的解析
		Class objSuperClass = objClass.getSuperclass();
		Map<String, Field> mapOfTmp = CollectionUtil.convertCollToMap(getFieldListByClass(objSuperClass), "name");
		CollectionUtil.putAllOfMap(fieldNameAndFieldMap, mapOfTmp);
		mapOfTmp = CollectionUtil.convertCollToMap(Arrays.asList(fieldArray), "name");
		CollectionUtil.putAllOfMap(fieldNameAndFieldMap, mapOfTmp);
		fieldListOfRet.addAll(fieldNameAndFieldMap.values());

		// 3.更新缓存数据
		CollectionUtil.putValueBySoftMap(CLASS_FIELDLIST_MAP, objClass, fieldListOfRet);

		return fieldListOfRet;
	}

	/**
	 * @param objClass
	 * @param fieldName
	 * @return
	 */
	public static <T> Field getField(Class<T> objClass, String fieldName) {
		// 0.参数判断
		if (objClass == Object.class)
			return null;

		// 1.尝试先从缓存中获取
		Map<String, Field> fieldMapOfRet = CollectionUtil.getValueBySoftMap(CLASS_FIELDMAP_MAP, objClass);
		if (fieldMapOfRet != null) {
			return fieldMapOfRet.get(fieldName);
		}

		// 2.尝试实时解析
		List<Field> fieldList = getFieldListByClass(objClass);
		if (CollectionUtil.isEmptyOfCollection(fieldList))
			return null;
		Map<String, Field> mapOfTmp = CollectionUtil.convertCollToMap(fieldList, "name");
		if (mapOfTmp == null)
			return null;

		// 3.更新缓存
		CollectionUtil.putValueBySoftMap(CLASS_FIELDMAP_MAP, objClass, mapOfTmp);

		// 4.返回结果
		return mapOfTmp.get(fieldName);
	}

	/**
	 * 根据Field,返回JAVA BEAN 格式的Method
	 * 
	 * @param objClass
	 * @param field
	 * @param isGetMethod
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Method genMethod(Class objClass, Field field, boolean isGetMethod) {
		Class fieldTypeClass = field.getType();
		// 生成需要比较字段的读取方法名称
		String methodName = isGetMethod ? getGetMethodNameByField(field) : getSetMethodNameByField(field);
		Method method = null;
		try {
			method = isGetMethod ? objClass.getMethod(methodName) : objClass.getMethod(methodName, fieldTypeClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * 生成Set方法的Name
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String getSetMethodNameByField(Field field) {
		Class fieldTypeClass = field.getType();
		String fieldName = field.getName();
		String preffix = "set";
		if ((fieldTypeClass == Boolean.class || fieldTypeClass == boolean.class)
				&& fieldName.toLowerCase().startsWith("is"))
			fieldName = fieldName.substring(2);

		return getMethodNameByFieldName(fieldName, preffix);
	}

	/**
	 * 生成Get方法的Name
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String getGetMethodNameByField(Field field) {
		Class fieldTypeClass = field.getType();
		String fieldName = field.getName();
		String preffix = "get";
		if (CollectionUtil.isInArray(ConstValue.booleanClassArray, fieldTypeClass))
			preffix = fieldName.toLowerCase().startsWith("is") ? "" : "is";

		return getMethodNameByFieldName(fieldName, preffix);
	}

	/**
	 * @param fieldName
	 * @param preffix
	 * @return
	 */
	private static String getMethodNameByFieldName(String fieldName, String preffix) {
		StringBuilder methodName = new StringBuilder(16);
		methodName.append(preffix);
		methodName.append(PrintStringUtils.setFirstCharToUpperCase(fieldName));

		return PrintStringUtils.setFirstCharToLowerCase(methodName.toString());
	}

	/**
	 * 生成对象对应的toString值(显示全部字段)
	 * 
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> String genToString(T obj) {
		return genToString(obj, null);
	}

	/**
	 * 生成对象对应的toString值
	 * 
	 * @param <T>
	 * @param obj
	 * @param fieldNameArray
	 * @return
	 */
	public static <T> String genToString(T obj, String[] fieldNameArray) {
		StringBuilder sb = new StringBuilder(256);
		Map<String, String> map = genToStringOfMap(obj, fieldNameArray);
		if (CollectionUtil.isNotEmptyOfMap(map)) {
			for (String name : map.keySet()) {
				String value = map.get(name);
				PrintStringUtils.appendValueForToString(sb, name, value);
			}
		}
		return sb.toString();
	}

	/**
	 * 生成对象对应的toString值
	 * 
	 * @param <T>
	 * @param obj
	 * @param fieldNameArray
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static <T> Map<String, String> genToStringOfMap(T obj, String[] fieldNameArray) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Class objClass = obj.getClass();
		// 1.检查并尝试重新生成fieldNameArray
		if (CollectionUtil.isEmptyOfArray(fieldNameArray)) {
			Field[] fieldArray = objClass.getDeclaredFields();
			List<String> fieldNameList = new ArrayList<String>();
			for (Field field : fieldArray)
				fieldNameList.add(field.getName());
			fieldNameArray = fieldNameList.toArray(new String[0]);
		}
		// 2.批量添加字段对应的值到显示对象中
		for (String fieldName : fieldNameArray) {
			if (StringUtils.isBlank(fieldName))
				continue;
			try {
				Field field = objClass.getDeclaredField(fieldName);
				String methodNameOfGet = getGetMethodNameByField(field);
				Method methodOfGet = objClass.getMethod(methodNameOfGet);
				// 设置对象中的字段
				Object value = methodOfGet.invoke(obj);
				map.put(fieldName, String.valueOf(value));
			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}
		}
		return map;
	}

	/**
	 * 深度拷贝一份对象(通过内存拷贝)
	 * 
	 * @param <T>
	 * @param objSrc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cloneObj(T objSrc) {
		T objRet = null;
		if (objSrc == null)
			return objRet;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(objSrc);
			out.flush();
			out.close();

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(in);
			objRet = (T) oi.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRet;
	}

	/**
	 * 将sou对象转换成des对象
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param des
	 * @param sou
	 * @param isFilterEnum
	 */
	@SuppressWarnings("rawtypes")
	public static <T1, T2> void convertObj(T1 des, T2 sou, boolean isFilterEnum) {
		if (des == null || sou == null)
			return;
		Class desClass = des.getClass(), souClass = sou.getClass();
		List<Field> fieldList = getFieldListByClass(souClass);
		if (CollectionUtil.isEmptyOfCollection(fieldList))
			return;

		for (Field field : fieldList) {
			Class fieldTypeClass = field.getType();
			// 判断是否需要过滤枚举类型(js无法处理)
			if ((isFilterEnum && fieldTypeClass.isEnum()) || Modifier.isFinal(field.getModifiers()))
				continue;
			try {
				// 生成需要比较字段的读取方法名称
				String methodNameOfGet = getGetMethodNameByField(field), methodNameOfSet = getSetMethodNameByField(field);
				// 得到需要调用的Method
				Method methodOfGet = souClass.getMethod(methodNameOfGet), methodOfSet = desClass.getMethod(
						methodNameOfSet, fieldTypeClass);
				// 调用Mehtod的invoke方法获得返回值
				Object value = methodOfGet.invoke(sou);
				methodOfSet.invoke(des, value);
			} catch (Exception ex) {
				if (logger.isDebugEnabled())
					logger.debug(ex);
			}
		}
	}

	/**
	 * 初始化对象，同时初始化内向内非基本类型对象
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T createBean(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					if (!field.getType().isPrimitive() && field.getType() != String.class) {
						Object obj = field.getType().newInstance();
						t.getClass().getMethod(getSetMethodNameByField(field), field.getType()).invoke(t, obj);
					}
				}
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}
}