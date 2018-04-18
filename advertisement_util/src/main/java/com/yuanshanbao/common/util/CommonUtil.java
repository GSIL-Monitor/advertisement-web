package com.yuanshanbao.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonUtil {
	private static final Log log = LogFactory.getLog(CommonUtil.class);
	private static String[] surname = { "李", "王", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴", "徐", "孙", "胡", "朱", "高", "林",
			"何", "郭", "马", "罗", "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧", "程", "曹", "袁", "邓", "许", "傅", "沈",
			"曾", "彭", "吕", "苏", "卢", "蒋", "蔡", "贾", "丁", "魏", "薛", "叶", "阎", "余", "潘", "杜", "戴", "夏", "锺", "汪", "田",
			"任", "姜", "范", "方", "石", "姚", "谭", "廖", "邹", "熊", "金", "陆", "郝", "孔", "白", "崔", "康", "毛", "邱", "秦", "江",
			"史", "顾", "侯", "邵", "孟", "龙", "万", "段", "雷", "钱", "汤", "尹", "黎", "易", "常", "武", "乔", "贺", "赖", "龚", "文" };

	private static String[] province = { "北京", "天津", "上海", "重庆", "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建",
			"江西", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "西藏", "广西", "内蒙", "宁夏", "新疆" };

	private static String[] call = { "先生", "女士" };

	public static boolean isInteger(String str) {
		try {
			new Integer(str);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean isLong(String str) {
		try {
			new Long(str);
		} catch (Exception e) {

			return false;
		}

		return true;
	}

	public static Long parseLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isBaseType(Object value) {
		if (value instanceof Integer) {
			return true;
		}
		if (value instanceof Boolean) {
			return true;
		}
		if (value instanceof String) {
			return true;
		}
		if (value instanceof Float) {
			return true;
		}
		if (value instanceof Double) {
			return true;
		}
		if (value instanceof Long) {
			return true;
		}
		if (value instanceof Short) {
			return true;
		}

		return false;
	}

	/*
	 * 将16进制字符串转换为字符数组
	 */
	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	/*
	 * 将字符数组转换为16进制字符串
	 */
	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * @param pass
	 * @return
	 */
	public static String convertToMd5(String passToConvert) {
		byte passToConvertByte[] = passToConvert.getBytes();
		String cryptograph = null;
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			byte gottenPassByte[] = messagedigest.digest(passToConvertByte);
			cryptograph = "";
			for (int i = 0; i < gottenPassByte.length; i++) {
				String temp = Integer.toHexString(gottenPassByte[i] & 0x000000ff);
				if (temp.length() < 2)
					temp = "0" + temp;
				cryptograph += temp;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			cryptograph = null;
		}
		return cryptograph;
	}

	/**
	 * 将数字格式化成6位,小于6位的,数字前面用0补足
	 * 
	 * @param num
	 * @return
	 */
	public static String formateNumberToSixLength(Integer num) {
		if (num == null) {
			return "";
		}
		NumberFormat nf = new DecimalFormat("000000");
		String numFormatted = nf.format(num.intValue());

		return numFormatted;
	}

	/**
	 * 将BigDecimal 格式化为 0.00的格式
	 * 
	 * @param big
	 * @return
	 */
	public static String formatBigDecimal(BigDecimal big) {
		if (big == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(big);
	}

	public static String formatBigDecimal(BigDecimal big, String format) {
		if (big == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(big);
	}

	public static String formatBonus(BigDecimal bonus) {
		if (bonus == null) {
			return null;
		}
		if (bonus.compareTo(new BigDecimal(10000)) > 0) {
			bonus = bonus.divide(new BigDecimal(10000));
			return formatBigDecimal(bonus) + "万";
		}
		return formatBigDecimal(bonus);
	}

	public static String formatIntBigDecimal(BigDecimal value) {
		if (value == null) {
			return null;
		}
		if (value.compareTo(new BigDecimal(value.intValue())) == 0) {
			DecimalFormat df = new DecimalFormat("#");
			return df.format(value);
		}
		return value.toString();
	}

	/**
	 * 校验日期格式，格式为YYYY-MM-DD或YYYY/MM/DD
	 * 
	 * @param date
	 *            日期
	 * @return 校验通过true，否则false
	 */
	public static boolean isValidDateFormat(String date) {
		boolean flag = false;
		if (date == null || "".equals(date.trim())) {
			return false;
		}
		date = date.replace('-', '/');
		String regEx = "^((\\d{2}(([02468][048])|([13579][26]))[\\/\\/\\s]?((((0?"
				+ "[13578])|(1[02]))[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))"
				+ "|(((0?[469])|(11))[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
				+ "(0?2[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
				+ "35679])|([13579][01345789]))[\\/\\/\\s]?((((0?[13578])|(1[02]))"
				+ "[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
				+ "[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/\\/\\s]?((0?[" + "1-9])|(1[0-9])|(2[0-8]))))))$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(date);
		flag = m.matches();

		return flag;
	}

	/**
	 * 检查是否是正浮点数 该方法用于判断金额是否是非0，且是否是0.00或则0.0或则 0 格式
	 * 
	 * @param srcToCheck
	 * @return
	 */
	public static boolean ifPositiveFloat(String srcToCheck) {
		try {
			String regEx = "^[1-9]*[1-9][0-9]*$|^(([0]\\.[0-9]{1,2})|([1-9]{1,}[0-9]*\\.[0-9]{1,2}))$";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(srcToCheck);
			if (!m.matches()) {
				return false;
			}

			BigDecimal big = new BigDecimal(srcToCheck);
			if (big.compareTo(new BigDecimal(0)) <= 0) {
				return false;
			}

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 将金额转化为分 并且补足需要的长度 cctao
	 * 
	 * @param num
	 * @param length
	 * @return
	 */
	public static String formateNumberToAppointLength(BigDecimal num, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		String str = sb.toString();
		NumberFormat nf = new DecimalFormat(str);
		String numFormatted = nf.format(num.intValue());
		return numFormatted;
	}

	/**
	 * 
	 * @param orgStr
	 * @param codeType
	 */
	public static String getEncodeStr(String orgStr, String orgCode, String desCode) {
		try {
			byte[] orgByte = orgStr.getBytes(orgCode);
			String encodedStr = new String(orgByte, desCode);
			return encodedStr;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 获得完整的错误信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getFullStackError(Throwable e) {

		StringWriter sw = new StringWriter();
		PrintWriter ps = new PrintWriter(sw);
		e.printStackTrace(ps);
		return sw.toString();
	}

	/**
	 * 参数名字母顺序排序
	 * 
	 * @param paraMap
	 *            参数map
	 * @param containParaName
	 *            是否包含参数
	 * @param isUrlEncode
	 *            参数值是否做urlencode编码
	 * @param valueNull
	 *            值空时，是否进行参与排序
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String formatParaMap(Map<String, Object> paraMap, boolean containParaName, boolean isUrlEncode,
			boolean paraValueNull) {

		StringBuffer buff = new StringBuffer();
		try {
			List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(paraMap.entrySet());

			Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
				public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});

			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, Object> item = infoIds.get(i);
				if (item.getKey() != "") {

					String key = item.getKey();
					String val = null;
					if (item.getValue() != null) {
						val = String.valueOf(item.getValue());
					}

					if (paraValueNull) {
						if (isUrlEncode) {
							val = EncodeUtils.urlEncode(val);
						}

						if (containParaName) {
							buff.append(key);
							buff.append("=");
						}
						buff.append(val);
						if (containParaName) {
							buff.append("&");
						}

					} else {
						if (null != val && "" != val) {
							if (isUrlEncode) {
								val = EncodeUtils.urlEncode(val);
							}

							if (containParaName) {
								buff.append(key);
								buff.append("=");
							}
							buff.append(val);
							if (containParaName) {
								buff.append("&");
							}
						}
					}
				}
			}

			if (buff.length() > 0) {
				if (containParaName) {
					buff.delete(buff.length() - 1, buff.length()); // 去掉&
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("formatParaMap exception: " + e.getMessage());
		}
		return buff.toString();
	}

	public static String formatParaMap(Object object, boolean containParaName, boolean isUrlEncode,
			boolean paraValueNull) {
		Map<String, Object> map = JacksonUtil.json2map(JacksonUtil.obj2json(object));
		return formatParaMap(map, containParaName, isUrlEncode, paraValueNull);
	}

	public static String getRequestParameters(Object object) {
		String result = "";
		Map<String, Object> map = JacksonUtil.json2map(JacksonUtil.obj2json(object));
		for (Entry<String, Object> entry : map.entrySet()) {
			result += entry.getKey() + "=" + entry.getValue() + "&";
		}
		return result.substring(0, result.length() - 1);
	}

	public static boolean isFromMobile(HttpServletRequest request) {
		String requestHeader = request.getHeader("user-agent");
		String[] deviceArray = new String[] { "android", "iphone", "symbianos", "windows phone", "ipad", "ipod" };
		if (StringUtils.isBlank(requestHeader)) {
			return false;
		}
		requestHeader = requestHeader.toLowerCase();
		for (int i = 0; i < deviceArray.length; i++) {
			if (requestHeader.indexOf(deviceArray[i]) > 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMobile(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/m/") || request.getRequestURI().startsWith("/w/");
	}

	public static boolean isApp(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/i/") || request.getRequestURI().startsWith("/recruit_quan/");
	}

	public static void main(String args[]) throws DecoderException {
		System.out.println(getEncodeStr("测试", "GBK", "GBK"));
		System.out.println(bytesToHexStr("abcdef".getBytes()));
		System.out.println(new String(hexStrToBytes("616263646566")));
		System.out.println(new String(Hex.decodeHex("616263646566".toCharArray())));
		// System.out.println(new
		// String(Hex.decodeHex("abcdefg".toCharArray())));
		System.out.println(bytesToHexStr("advertisement".getBytes()));
	}

	public static boolean isMapNull(Map<?, ?> parameters) {
		if (parameters == null) {
			return true;
		}
		for (Object value : parameters.values()) {
			if (value != null) {
				return false;
			}
		}
		return true;
	}

	public static String getEmailDomain(String email) {
		if (email == null) {
			return email;
		}
		if (email.indexOf("@") > -1) {
			return email.substring(email.indexOf("@"));
		}
		return null;
	}

	public static String format(String template, String[] values) {
		char[] chars = template.toCharArray();
		StringBuffer result = new StringBuffer();
		boolean startIndex = false;
		StringBuffer indexBuffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '{') {
				startIndex = true;
			} else if (chars[i] == '}') {
				startIndex = false;
				try {
					int index = Integer.parseInt(indexBuffer.toString());
					result.append(values[index]);
				} catch (Exception e) {
				}
			} else if (startIndex) {
				indexBuffer.append(chars[i]);
			} else {
				result.append(chars[i]);
			}
		}
		return result.toString();
	}

	public static String getRandomID() {
		return UUID.randomUUID().toString() + "-" + Math.random() * 100 + "-" + System.nanoTime();
	}

	public static String getMD5RandomID() {
		return convertToMd5(getRandomID());
	}

	public static String getEnvironment() {
		String pre = System.getProperty("spring.profiles.active");
		if (pre == null) {
			pre = System.getenv("spring.profiles.active");
		}
		return pre;
	}

	public static String getCacheKey(String key) {
		String environment = getEnvironment();
		if (environment.equals("dev")) {
			environment = "test";
		}
		if (environment.equals("confirm")) {
			environment = "online";
		}
		return "advertisement" + environment + key;
	}

	public static String getBindPrefix() {
		String environment = getEnvironment();
		if (environment.equals("dev") || environment.equals("test")) {
			environment = "test_";
		}
		if (environment.equals("confirm") || environment.equals("online")) {
			environment = "";
		}
		return environment;
	}

	public static String getIMUserId(String userId) {
		if (userId == null) {
			return null;
		}
		if ("online".equals(getEnvironment()) || "confirm".equals(getEnvironment())) {
			return "im" + userId;
		}
		return "imtest" + userId;
	}

	public static String getLiveSpace(String userId) {
		if (userId == null) {
			return null;
		}
		if ("online".equals(getEnvironment()) || "confirm".equals(getEnvironment())) {
			return "live" + userId;
		}
		return "livetest" + userId;
	}

	public static String getCurrentMachineIp() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
	}

	public static String getFileName(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		if (filePath.lastIndexOf("/") > 0) {
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			String extension = filePath.substring(filePath.lastIndexOf("."));
			String[] segs = fileName.split("_");
			if (segs.length > 2) {
				String name = "";
				for (int i = 0; i < segs.length - 2; i++) {
					name += segs[i] + "_";
				}
				return name.substring(0, name.length() - 1) + extension;
			}
			return fileName;
		}
		return filePath;
	}

	public static String getResourcePath(String relativePath) {
		String rootPath = getRootPath();
		String absolutePath = rootPath + relativePath;
		return absolutePath;
	}

	public static String getWebrootResourcePath(String relativePath) {
		String rootPath = CommonUtil.class.getResource(".").getPath();
		rootPath = rootPath.substring(0, rootPath.indexOf("WEB-INF"));
		String absolutePath = rootPath + relativePath;
		return absolutePath;
	}

	private static String getRootPath() {
		String rootPath = CommonUtil.class.getResource(".").getPath();
		rootPath = rootPath.substring(0, rootPath.indexOf("WEB-INF") - 1);
		rootPath = rootPath.substring(0, rootPath.lastIndexOf("/") + 1);
		return rootPath;
	}

	public static int convertYuanToFen(BigDecimal yuan) {
		return yuan.multiply(new BigDecimal(100)).intValue();
	}

	public static String getEnvironmentUserId(String userId) {
		if (userId == null) {
			return null;
		}
		if ("online".equals(getEnvironment()) || "confirm".equals(getEnvironment())) {
			return userId;
		}
		return "t" + userId;
	}

	public static String mergeUnionKey(Object... args) {
		if (args.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Object arg : args) {
			sb.append(arg).append("|");
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	public static String randomName() {
		return surname[(int) (Math.random() * surname.length)] + call[(int) (Math.random() * call.length)];
	}

	public static String randomProvince() {
		return province[(int) (Math.random() * province.length)];
	}

	public static String formatTemplate(String template, Map<String, Object> parameterMap) {
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			String value = "";
			if (entry.getValue() != null) {
				value = entry.getValue().toString();
			}
			template = template.replace("${" + entry.getKey() + "}", value);
		}
		return template;
	}

	public static boolean containsChannel(String[] channels, String param) {
		if (StringUtils.isBlank(param)) {
			return false;
		}
		for (String element : channels) {
			if (element.equals(param)) {
				return true;
			}
		}
		return false;
	}

	public static String formatTimeStamp(Timestamp createTime) {
		if (DateUtils.getDays(createTime) < 1) {
			return "今天 " + DateUtils.format(createTime, "HH:mm");
		} else if (DateUtils.getDays(createTime) < 2) {
			return "昨天 " + DateUtils.format(createTime, "HH:mm");
		} else {
			return DateUtils.format(createTime, "MM-dd HH:mm");
		}
	}

	/**
	 * 获取对象的所有参数
	 * 
	 * @param object
	 * @return
	 */
	public static String getValueByFieldName(String fieldName, Object object) {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					field.setAccessible(true);
					Object val = field.get(object);
					if (val != null) {
						return val.toString();
					}
				}
			}
			Type type = object.getClass().getGenericSuperclass();
			if (type instanceof Class) {
				fields = ((Class<?>) type).getDeclaredFields();
				for (Field field : fields) {
					if (field.getName().equals(fieldName)) {
						field.setAccessible(true);
						Object val = field.get(object);
						if (val != null) {
							return val.toString();
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取对象的所有参数
	 * 
	 * @param object
	 * @return
	 */
	public static List<String> getParameter(Class<?> objClass) {
		List<String> result = new ArrayList<String>();
		// 递归调用父类的解析
		Class<?> objSuperClass = objClass.getSuperclass();
		if (objSuperClass != null && !objSuperClass.equals(Object.class)) {
			result.addAll(getParameter(objSuperClass));
		}
		Field[] fieldArray = objClass.getDeclaredFields();
		for (Field field : fieldArray) {
			result.add(field.getName());
		}
		return result;

	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public static boolean isNullOrEquals(Long value1, Long value2) {
		if (value1 != null) {
			return value1.equals(value2);
		} else {
			return true;
		}
	}

	public static boolean isNullOrEquals(String content1, String content2) {
		if (StringUtils.isNotBlank(content1)) {
			return content1.equals(content2);
		} else {
			return true;
		}
	}

}
