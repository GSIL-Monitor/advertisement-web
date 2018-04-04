package com.yuanshanbao.common.util.reflect;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 提供各种对象的特定值
 * 
 * @author dingmingliang
 * 
 */
public final class ConstValue {

	private ConstValue() {
	}

	public static final Integer INTEGER_ZERO = Integer.valueOf(0);

	public static final Integer INTEGER_ONE = Integer.valueOf(1);

	public static final Integer INTEGER_TWO = Integer.valueOf(2);

	public static final BigDecimal BIGDECIMAL_2 = new BigDecimal("2");

	public static final BigDecimal BIGDECIMAL_10 = new BigDecimal("10");

	public static final BigDecimal BIGDECIMAL_100 = new BigDecimal("100");

	public static final BigDecimal BIGDECIMAL_P01 = new BigDecimal("0.01");

	public static final String STRING_EMPTY = "";

	public static final MathContext MC = new MathContext(10);

	public static final int INT_INVALID = 0;

	public static final int INT_VALID = 1;

	public static final int INT_FAIL = 0;

	public static final int INT_SUCCESS = 1;

	/**
	 * 第一个分隔符
	 */
	public static final String SPLIT_FIRST = ",";

	/**
	 * 第二个分隔符
	 */
	public static final String SPLIT_SECOND = ";";

	/**
	 * 第三个分隔符
	 */
	public static final String SPLIT_THIRD = "#";

	/**
	 * 分割Key和Value的符号
	 */
	public static final String SPLIT_KEY_VALUE = ":";

	/**
	 * 中间的or分隔符
	 */
	public static final String SPLIT_OR = "or";

	/**
	 * 中间的and分隔符
	 */
	public static final String SPLIT_AND = "and";

	/**
	 * 图片URL的分隔符
	 */
	public static final String SPLIT_PICURL = "@@@";

	/**
	 * 使用BASE64保存数据的内容前缀
	 */
	public static final String BASE64_PREFFIX = "##BASE64##";

	/**
	 * MemCached的SubKey的不同变量名之间的分隔符
	 */
	public static final String MC_SUBKEY_NAMESPLIT = " ";

	/**
	 * MemCached的SubKey的不同变量值之间的分隔符
	 */
	public static final String MC_SUBKEY_VALUESPLIT = ",";

	/**
	 * Class是数字的数组
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] numberClassArray = new Class[] { Integer.class, int.class, Long.class, long.class };

	/**
	 * Class是布尔值的数组
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] booleanClassArray = new Class[] { Boolean.class, boolean.class };

}
