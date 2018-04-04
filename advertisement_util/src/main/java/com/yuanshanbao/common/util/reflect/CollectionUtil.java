package com.yuanshanbao.common.util.reflect;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author dingmingliang
 * 
 */
public final class CollectionUtil {

	private CollectionUtil() {
	}

	private static Logger logger = Logger.getLogger(CollectionUtil.class);

	@SuppressWarnings("rawtypes")
	private static Class[] numberClass = new Class[] { Integer.class, int.class, Long.class, long.class };

	/**
	 * Field.class的fieldName和getMethod的映射关系
	 */
	private static Map<String, String> fieldClassGetMethodNameMap = new HashMap<String, String>();

	static {
		fieldClassGetMethodNameMap.put("name", "getName");
		fieldClassGetMethodNameMap.put("value", "getValue");
	}

	/**
	 * 将一个Collection对象,转换成Map
	 * 
	 * @param coll
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2> Map<T1, T2> convertCollToMap(Collection<T2> coll, String fieldName) {
		if (isEmptyOfCollection(coll) || StringUtils.isBlank(fieldName))
			return null;

		Map<T1, T2> map = null;
		for (T2 obj : coll) {
			try {
				T1 key = null;
				// 读取对象中的字段
				if (obj instanceof Field && containsKeyOfMap(fieldClassGetMethodNameMap, fieldName)) {
					// 需要对Field对象做简化处理,防止死循环
					String methodNameOfGet = getValueOfMapIgnoreCase(fieldClassGetMethodNameMap, fieldName);
					Method methodOfGet = Field.class.getMethod(methodNameOfGet);
					key = (T1) methodOfGet.invoke(obj);
				} else
					key = (T1) ObjectCompareUtil.getValueByFieldName(obj, fieldName);
				// 尝试根据Key,对Map做初始化
				if (map == null) {
					if (key != null && isInArray(numberClass, key.getClass()))
						map = new TreeMap<T1, T2>();
					else
						map = new HashMap<T1, T2>();
				}
				map.put(key, obj);
			} catch (Exception ex) {
			}
		}
		return map;
	}

	/**
	 * 将souList转换成新的List对象
	 * 
	 * @param souList
	 * @param desClazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2> List<T1> convertList(List<T2> souList, Class<T1> desClazz) {
		if (CollectionUtil.isEmptyOfCollection(souList))
			return null;

		List<T1> desList = new ArrayList<T1>();
		for (T2 obj : souList) {
			desList.add((T1) obj);
		}
		return desList;
	}

	/**
	 * 获得key对应的Value<br>
	 * (兼容Map为空的情况)
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <T1, T2> T2 getValueOfMap(Map<T1, T2> map, T1 key) {
		if (isNotEmptyOfMap(map)) {
			if (map.containsKey(key))
				return map.get(key);
		}
		return null;
	}

	/**
	 * 获得KeyValue对应的Value<br>
	 * (兼容Map为空的情况,KeyValue忽略大小写)
	 * 
	 * @param <T>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <T> T getValueOfMapIgnoreCase(Map<String, T> map, String keyValue) {
		if (isNotEmptyOfMap(map)) {
			for (String key : map.keySet()) {
				if (StringUtils.equalsIgnoreCase(key, keyValue))
					return map.get(key);
			}
		}
		return null;
	}

	/**
	 * 返回list中的第一个对象,如果有的话
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> T getFirstObjectOfList(List<T> list) {
		return isNotEmptyOfList(list) ? list.get(0) : null;
	}

	/**
	 * 返回Collection中的第一个对象,如果有的话
	 * 
	 * @param <T>
	 * @param coll
	 * @return
	 */
	public static <T> T getFirstObjectOfCollection(Collection<T> coll) {
		return isNotEmptyOfCollection(coll) ? coll.iterator().next() : null;
	}

	/**
	 * 返回Map中的第一个Key值
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @return
	 */
	public static <T1, T2> T1 getFirstKeyOfMap(Map<T1, T2> map) {
		return isNotEmptyOfMap(map) ? map.keySet().iterator().next() : null;
	}

	/**
	 * 返回Map中的第一个Value值
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @return
	 */
	public static <T1, T2> T2 getFirstValueOfMap(Map<T1, T2> map) {
		return isNotEmptyOfMap(map) ? map.values().iterator().next() : null;
	}

	/**
	 * 返回array中的第一个对象,如果有的话
	 * 
	 * @param array
	 * @return
	 */
	public static String getFirstObjectOfArray(String[] array) {
		return isNotEmptyOfArray(array) ? array[0] : null;
	}

	/**
	 * 返回array中的第一个对象,如果有的话
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> T getFirstObjectOfArray(T[] array) {
		return isNotEmptyOfArray(array) ? array[0] : null;
	}

	/**
	 * 判断Array中是否至少有一个对象在Set中存在
	 * 
	 * @param array
	 * @param set
	 * @return
	 */
	public static boolean isOneArrayInSet(String[] array, Set<String> set) {
		// 1. 参数判断
		if (isEmptyOfArray(array) || isEmptyOfSet(set))
			return false;
		// 2. 结果判断
		for (String arrayItem : array) {
			if (set.contains(arrayItem))
				return true;
		}
		// 3. 默认结果
		return false;
	}

	/**
	 * 判断Set1中是否至少有一个对象在Set2中存在
	 * 
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static <T> boolean isOneSetInSet(Set<T> set1, Set<T> set2) {
		// 1. 参数判断
		if (isEmptyOfCollection(set1) || isEmptyOfCollection(set2))
			return false;
		// 2. 结果判断
		for (T item : set1) {
			if (set2.contains(item))
				return true;
		}
		// 3. 默认结果
		return false;
	}

	/**
	 * 判断Set1的对象,全部都在Set2中存在
	 * 
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static <T> boolean isAllSetInSet(Set<T> set1, Set<T> set2) {
		// 1. 参数判断
		if (isEmptyOfCollection(set1) || isEmptyOfCollection(set2))
			return false;
		// 2. 结果判断
		for (T item : set1) {
			if (!set2.contains(item))
				return false;
		}
		// 3. 默认结果
		return true;
	}

	/**
	 * 判断Array1中是否至少有一个对象在Array2中存在
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T> boolean isOneArrayInArray(T[] array1, T[] array2) {
		// 1. 参数判断
		if (isEmptyOfArray(array1) || isEmptyOfArray(array2))
			return false;
		// 2. 结果判断
		for (T arrayItem : array1) {
			if (isInArray(array2, arrayItem))
				return true;
		}
		// 3. 默认结果
		return false;
	}

	/**
	 * 查看Map是否包含某个Key<br>
	 * (兼容Map为空的情况)
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean containsKeyOfMap(Map map, Object key) {
		return isNotEmptyOfMap(map) && map.containsKey(key);
	}

	/**
	 * 判断Array是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmptyOfArray(int[] array) {
		return array == null || array.length <= 0;
	}

	/**
	 * 判断Array是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmptyOfArray(long[] array) {
		return array == null || array.length <= 0;
	}

	/**
	 * 判断Array是否不为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmptyOfArray(long[] array) {
		return !isEmptyOfArray(array);
	}

	/**
	 * 判断Array是否为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> boolean isEmptyOfArray(T[] array) {
		return array == null || array.length <= 0;
	}

	/**
	 * 
	 * 判断Collection是否为空
	 * 
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmptyOfCollection(Collection<T> collection) {
		return collection == null || collection.size() <= 0;
	}

	/**
	 * 判断Array是否不为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmptyOfArray(int[] array) {
		return !isEmptyOfArray(array);
	}

	/**
	 * 判断Array是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> boolean isNotEmptyOfArray(T[] array) {
		return !isEmptyOfArray(array);
	}

	/**
	 * 判断Collection是否不为空
	 * 
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public static <T> boolean isNotEmptyOfCollection(Collection<T> collection) {
		return !isEmptyOfCollection(collection);
	}

	/**
	 * 判断Set是否为空
	 * 
	 * @param set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmptyOfSet(Set set) {
		return isEmptyOfCollection(set);
	}

	/**
	 * 判断Set是否不为空
	 * 
	 * @param set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNotEmptyOfSet(Set set) {
		return !isEmptyOfSet(set);
	}

	/**
	 * 判断Map是否为空
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmptyOfMap(Map map) {
		return map == null || map.size() <= 0;
	}

	/**
	 * 判断Map是否不为空
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNotEmptyOfMap(Map map) {
		return !isEmptyOfMap(map);
	}

	/**
	 * 返回list的内容是否是空
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmptyOfList(List list) {
		return isEmptyOfCollection(list);
	}

	/**
	 * 返回list的内容是否不为空
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNotEmptyOfList(List list) {
		return !isEmptyOfList(list);
	}

	/**
	 * 检查value是否在数组array中
	 * 
	 * @param <T>
	 * @param array
	 * @param value
	 * @return
	 */
	public static <T> boolean isInArray(T[] array, T value) {
		if (array != null && array.length > 0) {
			for (T item : array) {
				if ((item == null && item == value) || (item != null && item.equals(value)))
					return true;
			}
		}
		return false;
	}

	/**
	 * 检查value是否在数组array中<br>
	 * (忽略大小写)
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	public static boolean isInArrayIgnoreCase(String[] array, String value) {
		if (array != null && array.length > 0) {
			for (String item : array) {
				if (StringUtils.equalsIgnoreCase(item, value))
					return true;
			}
		}
		return false;
	}

	/**
	 * 检查value是否在数组array中
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	public static boolean isInArray(int[] array, int value) {
		if (array != null && array.length > 0) {
			for (int item : array) {
				if (item == value)
					return true;
			}
		}
		return false;
	}

	/**
	 * 检查value是否在数组array中
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	public static boolean isInArray(long[] array, long value) {
		if (array != null && array.length > 0) {
			for (long item : array) {
				if (item == value)
					return true;
			}
		}
		return false;
	}

	/**
	 * 添加Value到List中
	 * 
	 * @param <T>
	 * @param list
	 * @param value
	 * @return 添加Value后的List对象
	 */
	public static <T> List<T> addOfList(List<T> list, T value) {
		if (list == null)
			list = new ArrayList<T>();
		if (value != null)
			list.add(value);
		return list;
	}

	/**
	 * 将souList的对象添加到destList中
	 * 
	 * @param <T>
	 * @param destList
	 *            目标List
	 * @param souList
	 *            源List
	 * @return 添加Value后的List对象
	 */
	public static <T> List<T> addAllOfList(List<T> destList, Collection<T> souList) {
		if (destList == null)
			destList = new ArrayList<T>();
		if (isNotEmptyOfCollection(souList))
			destList.addAll(souList);

		return destList;
	}

	/**
	 * 将souSet的对象添加到destSet中
	 * 
	 * @param <T>
	 * @param destSet
	 *            目标Sett
	 * @param souSet
	 *            源Set
	 * @return 添加Value后的Set对象
	 */
	public static <T> Set<T> addAllOfSet(Set<T> destSet, Collection<T> souSet) {
		if (destSet == null)
			destSet = checkIsNumberOfCollection(souSet) ? new TreeSet<T>() : new HashSet<T>();
		if (isNotEmptyOfCollection(souSet))
			destSet.addAll(souSet);

		return destSet;
	}

	/**
	 * 将souCollection的对象添加到destCollection中
	 * 
	 * @param <T>
	 * @param destCollection
	 *            目标Collection
	 * @param souCollection
	 *            源Collection(如果为空,则不添加)
	 * @param newCollection
	 *            当目标Collection为空的时候,赋值使用
	 * @return
	 */
	public static <T> Collection<T> addAllOfCollection(Collection<T> destCollection, Collection<T> souCollection,
			Collection<T> newCollection) {
		if (destCollection == null)
			destCollection = newCollection;
		if (isNotEmptyOfCollection(souCollection))
			destCollection.addAll(souCollection);

		return destCollection;
	}

	/**
	 * 将value对象添加到destCollection中
	 * 
	 * @param <T>
	 * @param destCollection
	 *            目标Collection
	 * @param value
	 *            添加的值
	 * @param newCollection
	 *            当目标Collection为空的时候,赋值使用
	 * @return
	 */
	public static <T> Collection<T> addOfCollection(Collection<T> destCollection, T value, Collection<T> newCollection) {
		if (destCollection == null)
			destCollection = newCollection;
		destCollection.add(value);

		return destCollection;
	}

	/**
	 * 添加Value到List中(过滤value是null的情况)
	 * 
	 * @param <T>
	 * @param list
	 * @param value
	 * @return 添加Value后的List对象
	 */
	public static <T> List<T> addOfListFilterNull(List<T> list, T value) {
		if (value == null)
			return list;
		return addOfList(list, value);
	}

	/**
	 * 设置Array中指定的index值为Value
	 * 
	 * @param <T>
	 * @param array
	 * @param value
	 * @param index
	 */
	public static <T> void setValueOfArray(T[] array, T value, int index) {
		if (isNotEmptyOfArray(array) && array.length > index)
			array[index] = value;
	}

	/**
	 * 设置数组的某个index的value
	 * 
	 * @param array
	 * @param index
	 * @param value
	 */
	public static void setValueOfArrayByIndex(int[] array, int index, int value) {
		if (array != null && array.length >= index + 1)
			array[index] = value;
	}

	/**
	 * 设置数组的某个index的value
	 * 
	 * @param array
	 * @param index
	 * @param value
	 */
	public static <T> void setValueOfArrayByIndex(T[] array, int index, T value) {
		if (array != null && array.length >= index + 1)
			array[index] = value;
	}

	/**
	 * 合并多个数组为一个数组
	 * 
	 * @param arrays
	 * @return
	 */
	public static long[] combineArray(long[]... arrays) {
		if (arrays == null || arrays.length <= 0)
			return null;
		// 获得数组的总长度
		int arrayTotalLength = 0, index = 0;
		for (long[] arrayItem : arrays)
			if (arrayItem != null)
				arrayTotalLength += arrayItem.length;
		// 合并数组内容
		long[] arrayTotal = new long[arrayTotalLength];
		for (long[] arrayItem : arrays) {
			if (arrayItem != null && arrayItem.length > 0) {
				System.arraycopy(arrayItem, 0, arrayTotal, index, arrayItem.length);
				index += arrayItem.length;
			}
		}
		return arrayTotal;
	}

	/**
	 * 联合多个一维数组对象为一个一维数组对象<br>
	 * 同时根据参数决定是否需要过滤重复值
	 * 
	 * @param isFilterDulp
	 *            是否过滤重复的值
	 * @param arrays
	 * @return
	 */
	public static Integer[] combineArray(boolean isFilterDulp, Integer[]... arrays) {
		if (arrays == null || arrays.length <= 0)
			return null;

		Collection<Integer> c = isFilterDulp ? new HashSet<Integer>() : new ArrayList<Integer>();
		for (Integer[] arrayItem : arrays) {
			if (isEmptyOfArray(arrayItem))
				continue;
			c.addAll(Arrays.asList(arrayItem));
		}
		return c.toArray(new Integer[0]);
	}

	/**
	 * 联合多个一维数组对象为一个一维数组对象<br>
	 * 同时根据参数决定是否需要过滤重复值
	 * 
	 * @param isFilterDulp
	 *            是否过滤重复的值
	 * @param arrays
	 * @return
	 */
	public static int[] combineArray(boolean isFilterDulp, int[]... arrays) {
		if (arrays == null || arrays.length <= 0)
			return null;

		Collection<Integer> c = isFilterDulp ? new HashSet<Integer>() : new ArrayList<Integer>();
		for (int[] arrayItem : arrays) {
			if (isEmptyOfArray(arrayItem))
				continue;
			for (int item : arrayItem)
				c.add(item);
		}
		int[] retArr = new int[c.size()];
		int index = 0;
		for (int item : c) {
			retArr[index] = item;
			index++;
		}

		return retArr;
	}

	/**
	 * 联合多个一维数组对象为一个一维数组对象<br>
	 * 同时根据参数决定是否需要过滤重复值
	 * 
	 * @param isFilterDulp
	 *            是否过滤重复的值
	 * @param arrays
	 * @return
	 */
	public static Object[] combineArray(boolean isFilterDulp, Object[]... arrays) {
		if (arrays == null || arrays.length <= 0)
			return null;

		Collection<Object> c = isFilterDulp ? new HashSet<Object>() : new ArrayList<Object>();
		for (Object[] arrayItem : arrays) {
			if (isEmptyOfArray(arrayItem))
				continue;
			c.addAll(Arrays.asList(arrayItem));
		}
		return c.toArray(new Object[0]);
	}

	/**
	 * 联合多个一维数组对象为一个一维数组对象<br>
	 * (不过滤相同的对象)
	 * 
	 * @param arrays
	 * @return
	 */
	public static Integer[] combineArray(Integer[]... arrays) {
		return combineArray(false, arrays);
	}

	/**
	 * 联合多个一维数组对象为一个一维数组对象<br>
	 * (不过滤相同的对象)
	 * 
	 * @param arrays
	 * @return
	 */
	public static Object[] combineArray(Object[]... arrays) {
		return combineArray(false, arrays);
	}

	/**
	 * 合并多个数组为一个数组
	 * 
	 * @param arrays
	 * @return
	 */
	public static String[] combineArray(String[]... arrays) {
		if (arrays == null || arrays.length <= 0)
			return null;
		// 获得数组的总长度
		int arrayTotalLength = 0, index = 0;
		for (String[] arrayItem : arrays)
			if (arrayItem != null)
				arrayTotalLength += arrayItem.length;
		// 合并数组内容
		String[] arrayTotal = new String[arrayTotalLength];
		for (String[] arrayItem : arrays) {
			if (arrayItem != null && arrayItem.length > 0) {
				System.arraycopy(arrayItem, 0, arrayTotal, index, arrayItem.length);
				index += arrayItem.length;
			}
		}
		return arrayTotal;
	}

	/**
	 * 添加一个值到Map中
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @param key
	 * @param value
	 * @param autoNew
	 * @return
	 */
	public static <T1, T2> Map<T1, T2> putValueOfMap(Map<T1, T2> map, T1 key, T2 value, boolean autoNew) {
		// 1.尝试自动初分配一个空的Map
		if (map == null && autoNew) {
			Map<T1, T2> mapOfTmp = new HashMap<T1, T2>();
			mapOfTmp.put(key, value);
			map = checkKeyIsNumberOfMap(mapOfTmp) ? new TreeMap<T1, T2>() : new HashMap<T1, T2>();
		}
		// 2.尝试设置值
		if (map != null)
			map.put(key, value);
		// 3.返回结果
		return map;
	}

	/**
	 * 添加一个值到Map中(Map的Value是一个List)
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @param key
	 *            如果Map没有包含Key,则会生成一个ArrayList
	 * @param value
	 * @param autoNew
	 * @return
	 */
	public static <T1, T2> Map<T1, List<T2>> putValueOfListMap(Map<T1, List<T2>> map, T1 key, T2 value, boolean autoNew) {
		// 1.尝试自动初分配一个空的Map
		if (map == null && autoNew)
			map = new HashMap<T1, List<T2>>();
		// 2.尝试设置值
		if (map != null) {
			List<T2> list = map.get(key);
			list = list != null ? list : new ArrayList<T2>();
			list.add(value);
			map.put(key, list);
		}
		// 3.返回结果
		return map;
	}

	/**
	 * 添加一个值到Map中(Map的Value是一个List)
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @param key
	 *            如果Map没有包含Key,则会生成一个ArrayList
	 * @param valueList
	 * @param autoNew
	 * @return
	 */
	public static <T1, T2> Map<T1, List<T2>> putValueListOfListMap(Map<T1, List<T2>> map, T1 key, List<T2> valueList,
			boolean autoNew) {
		// 1.尝试自动初分配一个空的Map
		if (map == null && autoNew)
			map = new HashMap<T1, List<T2>>();
		// 2.尝试设置值
		if (map != null && CollectionUtil.isNotEmptyOfCollection(valueList)) {
			List<T2> list = map.get(key);
			list = list != null ? list : new ArrayList<T2>();
			list.addAll(valueList);
			map.put(key, list);
		}
		// 3.返回结果
		return map;
	}

	/**
	 * 添加一个值到Map中(忽略各种blank的情况)
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map<String, Object> putValueOfMap(Map<String, Object> map, String key, String value) {
		if (map == null || StringUtils.isBlank(key) || StringUtils.isBlank(value))
			return map;
		map.put(key, value);
		return map;
	}

	/**
	 * 完全浅拷贝一份Map对象
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param destMap
	 * @param sourMap
	 * @return
	 */
	public static <T1, T2> Map<T1, T2> putAllOfMap(Map<T1, T2> destMap, Map<T1, T2> sourMap) {
		if (isEmptyOfMap(sourMap))
			return destMap;
		if (destMap == null)
			destMap = checkKeyIsNumberOfMap(sourMap) ? new TreeMap<T1, T2>() : new HashMap<T1, T2>();
		destMap.putAll(sourMap);
		return destMap;
	}

	/**
	 * 根据Key移除Map中对应的对象
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <T1, T2> Map<T1, T2> removeKeyOfMap(Map<T1, T2> map, T1 key) {
		if (isNotEmptyOfMap(map))
			map.remove(key);
		return map;
	}

	/**
	 * 容器是否包含指定的对象引用
	 * 
	 * @param <T>
	 * @param c
	 * @param value
	 * @return
	 */
	public static <T> boolean contains(Collection<T> c, T value) {
		return c != null ? c.contains(value) : false;
	}

	/**
	 * 生成一个默认的数组对象
	 * 
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static String[] genArray(int length, String defaultValue) {
		String[] array = new String[length];
		for (int i = 0; i < length; i++)
			array[i] = defaultValue;
		return array;
	}

	/**
	 * 生成一个默认的数组对象
	 * 
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static boolean[] genArray(int length, boolean defaultValue) {
		boolean[] array = new boolean[length];
		for (int i = 0; i < length; i++)
			array[i] = defaultValue;
		return array;
	}

	/**
	 * 根据输入的valueRangeArray,生成对应的Integer数组
	 * 
	 * @param valueRangeArray
	 * @return
	 */
	public static Integer[] genIntegerArray(int[][] valueRangeArray) {
		Set<Integer> set = new TreeSet<Integer>();
		if (valueRangeArray == null)
			return null;

		for (int[] valueRange : valueRangeArray) {
			try {
				int start = valueRange[0];
				int end = valueRange[1];
				while (start <= end) {
					set.add(start);
					start++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return set.toArray(new Integer[0]);
	}

	/**
	 * 根据输入的valueRangeArray,生成对应的int数组
	 * 
	 * @param valueRangeArray
	 * @return
	 */
	public static int[] genIntArray(int[][] valueRangeArray) {
		Integer[] integerArray = genIntegerArray(valueRangeArray);
		if (integerArray == null)
			return null;

		int[] intArray = new int[integerArray.length];
		int index = 0;
		for (int value : integerArray) {
			intArray[index] = value;
			index++;
		}
		return intArray;
	}

	/**
	 * Clone一份Map数据(对象的浅拷贝,主要用于包装类的Clone)
	 * 
	 * @param map
	 * @return
	 */
	public static <T1> Map<T1, Integer[]> cloneMap(Map<T1, Integer[]> map) {
		if (isEmptyOfMap(map))
			return map;

		Map<T1, Integer[]> destMap = checkKeyIsNumberOfMap(map) ? new TreeMap<T1, Integer[]>()
				: new HashMap<T1, Integer[]>();
		for (T1 key : map.keySet()) {
			Integer[] value = map.get(key), destValue = new Integer[value.length];
			System.arraycopy(value, 0, destValue, 0, value.length);
			destMap.put(key, destValue);
		}
		return destMap;
	}

	/**
	 * Clone一份Map数据(对象的浅拷贝,主要用于包装类的Clone)
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @return
	 */
	public static <T1, T2> Map<T1, List<T2>> cloneMapOfType2(Map<T1, List<T2>> map) {
		if (isEmptyOfMap(map))
			return map;

		Map<T1, List<T2>> destMap = checkKeyIsNumberOfMap(map) ? new TreeMap<T1, List<T2>>()
				: new HashMap<T1, List<T2>>();
		for (T1 key : map.keySet()) {
			List<T2> value = map.get(key), destValue = new ArrayList<T2>();
			destValue.addAll(value);
			destMap.put(key, destValue);
		}
		return destMap;
	}

	/**
	 * Clone一份Map数据(对象的浅拷贝,主要用于包装类的Clone)
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @return
	 */
	public static <T1, T2> Map<T1, T2> cloneMapOfType3(Map<T1, T2> map) {
		if (isEmptyOfMap(map))
			return map;

		Map<T1, T2> destMap = checkKeyIsNumberOfMap(map) ? new TreeMap<T1, T2>() : new HashMap<T1, T2>();
		destMap.putAll(map);
		return destMap;
	}

	/**
	 * 判断Map的Key是否是Number类型
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param map
	 * @return
	 */
	private static <T1, T2> boolean checkKeyIsNumberOfMap(Map<T1, T2> map) {
		boolean isIntegerKey = false;
		if (isNotEmptyOfMap(map)) {
			for (T1 key : map.keySet()) {
				isIntegerKey = key instanceof Integer || key instanceof Long;
				break;
			}
		}
		return isIntegerKey;
	}

	/**
	 * 判断Collection的Value是否是Number类型
	 * 
	 * @param <T>
	 * @param coll
	 * @return
	 */
	private static <T> boolean checkIsNumberOfCollection(Collection<T> coll) {
		boolean isIntegerKey = false;
		if (isNotEmptyOfCollection(coll)) {
			for (T value : coll) {
				isIntegerKey = value instanceof Integer || value instanceof Long;
				break;
			}
		}
		return isIntegerKey;
	}

	/**
	 * 转换Integer的数组为int数组
	 * 
	 * @param array
	 * @return
	 */
	public static int[] convertIntegerArrayToIntArray(Integer[] array) {
		if (isEmptyOfArray(array))
			return null;

		int[] resArray = new int[array.length];
		for (int i = 0; i < array.length; i++)
			resArray[i] = array[i];

		return resArray;
	}

	/**
	 * 转换Long的数组为long数组
	 * 
	 * @param array
	 * @return
	 */
	public static long[] convertLongObjectArrayToLongArray(Long[] array) {
		if (isEmptyOfArray(array))
			return null;

		long[] resArray = new long[array.length];
		for (int i = 0; i < array.length; i++)
			resArray[i] = array[i];

		return resArray;
	}

	/**
	 * 从Value是SoftReference的Map中读取数据
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T1, T2> T2 getValueBySoftMap(Map<T1, SoftReference<T2>> map, T1 key) {
		SoftReference<T2> softr = CollectionUtil.getValueOfMap(map, key);
		T2 value = softr != null ? softr.get() : null;
		if (value == null)
			return null;

		if (value instanceof List)
			return (T2) CollectionUtil.addAllOfList(null, (List) value);
		if (value instanceof Set)
			return (T2) CollectionUtil.addAllOfSet(null, (Set) value);
		if (value instanceof Map)
			return (T2) CollectionUtil.putAllOfMap(null, (Map) value);
		return value;
	}

	/**
	 * 更新Value是SoftReference的Map
	 * 
	 * @param map
	 * @param key
	 * @param value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T1, T2> void putValueBySoftMap(Map<T1, SoftReference<T2>> map, T1 key, T2 value) {
		if (map == null)
			return;

		logger.debug("NO MATCH IN CACHE MAP: " + key.toString());
		SoftReference<T2> valueOfMap = null;
		if (value instanceof List) {
			valueOfMap = new SoftReference<T2>((T2) CollectionUtil.addAllOfList(null, (List) value));
		} else if (value instanceof Set) {
			valueOfMap = new SoftReference<T2>((T2) CollectionUtil.addAllOfSet(null, (Set) value));
		} else if (value instanceof Map) {
			valueOfMap = new SoftReference<T2>((T2) CollectionUtil.putAllOfMap(null, (Map) value));
		} else
			valueOfMap = new SoftReference<T2>(value);
		map.put(key, valueOfMap);
	}

	/**
	 * 过滤Coll1中不在Coll2中存在的元素<br>
	 * (匹配规则: 简单的调用Collection的contains方法)
	 * 
	 * @param <T>
	 * @param coll1
	 * @param coll2
	 * @return
	 */
	public static <T> void filterColl1WhichNotInColl2(Collection<T> coll1, Collection<T> coll2) {
		if (CollectionUtil.isEmptyOfCollection(coll1) || CollectionUtil.isEmptyOfCollection(coll2))
			return;
		Collection<T> collOfRemove = new ArrayList<T>();

		for (T item1 : coll1) {
			// 1.先用Collection的contains方法去匹配
			if (coll2.contains(item1))
				continue;
			// 2.再调用对象的equals方法匹配
			boolean has = false;
			for (T item2 : coll2)
				if (item1.equals(item2)) {
					has = true;
					break;
				}
			if (!has)
				collOfRemove.add(item1);
		}
		if (collOfRemove.size() > 0)
			coll1.removeAll(collOfRemove);
	}

	/**
	 * 过滤Coll1中在Coll2中存在的元素<br>
	 * (匹配规则: 简单的调用Collection的contains方法)
	 * 
	 * @param <T>
	 * @param coll1
	 * @param coll2
	 * @return
	 */
	public static <T> void filterColl1WhichInColl2(Collection<T> coll1, Collection<T> coll2) {
		if (CollectionUtil.isEmptyOfCollection(coll1) || CollectionUtil.isEmptyOfCollection(coll2))
			return;
		Collection<T> collOfRemove = new ArrayList<T>();

		for (T item1 : coll1) {
			// 1.先用Collection的contains方法去匹配
			if (coll2.contains(item1)) {
				collOfRemove.add(item1);
				continue;
			}

			// 2.再调用对象的equals方法匹配
			boolean has = false;
			for (T item2 : coll2)
				if (item1.equals(item2)) {
					has = true;
					break;
				}
			if (has)
				collOfRemove.add(item1);
		}
		if (collOfRemove.size() > 0)
			coll1.removeAll(collOfRemove);
	}

}