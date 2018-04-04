package com.yuanshanbao.common.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyIdcard {

	static Pattern pattern = Pattern.compile("\\d{17}");
	// 第i位置上的加权因子，其数值依据公式Wi=2 的 （i-1）次幂 (mod 11)计算得出
	static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// 校验码表，i = ∑(ai×Wi)(mod 11) ，从下表中查询到校验位
	static char[] vi = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	static HashMap<String, String> areaCodes = new HashMap<String, String>(3415);
	static {
		areaCodes.put("11", "");// 北京市
		areaCodes.put("12", "");// 天津市
		areaCodes.put("13", "");// 河北省
		areaCodes.put("14", "");// 山西省
		areaCodes.put("15", "");// 内蒙古
		areaCodes.put("21", "");// 辽宁省
		areaCodes.put("22", "");// 吉林省
		areaCodes.put("23", "");// 黑龙江
		areaCodes.put("31", "");// 上海市
		areaCodes.put("32", "");// 江苏省
		areaCodes.put("33", "");// 浙江省
		areaCodes.put("34", "");// 安徽省
		areaCodes.put("35", "");// 福建省
		areaCodes.put("36", "");// 江西省
		areaCodes.put("37", "");// 山东省
		areaCodes.put("41", "");// 河南省
		areaCodes.put("42", "");// 湖北省
		areaCodes.put("43", "");// 湖南省
		areaCodes.put("44", "");// 广东省
		areaCodes.put("45", "");// 广西
		areaCodes.put("46", "");// 海南省
		areaCodes.put("50", "");// 重庆市
		areaCodes.put("51", "");// 四川省
		areaCodes.put("52", "");// 贵州省
		areaCodes.put("53", "");// 云南省
		areaCodes.put("54", "");// 西藏自治区
		areaCodes.put("61", "");// 陕西省
		areaCodes.put("62", "");// 甘肃省
		areaCodes.put("63", "");// 青海省
		areaCodes.put("64", "");// 宁夏
		areaCodes.put("65", "");// 新疆
		areaCodes.put("71", "");// 台湾省
		areaCodes.put("81", "");// 香港
		areaCodes.put("82", "");// 澳门
	}

	// 跟网易宝保持一致，只允许输入 汉字 以及"·" ，并且要以 汉字 开头 。
	public static final Pattern NAME_PATTERN = Pattern.compile("^([\u0391-\uFFE5])([\u0391-\uFFE5\u00B7])*$");

	/**
	 * 
	 * verifyIdCard
	 * 
	 * 
	 * 
	 * @param idcard
	 *            String
	 * 
	 * @return boolean
	 * 
	 */

	public static boolean verifyIdCard(String idcard) {

		Matcher matcher = null;

		String verifyCode = null; // 最后一位校验位

		if (idcard == null) {
			return false;
		}

		if (idcard.length() == 15) {
			idcard = convertToEighteen(idcard);
		}

		if (idcard.length() != 18) {
			return false;
		}
		try {
			//
			matcher = pattern.matcher(idcard);

			if (!matcher.find()) {
				return false;
			}

			String area = idcard.substring(0, 2);
			if (!validArea(area)) {
				return false;
			}

			String birth = idcard.substring(6, 14);

			// 校验年份
			String year = birth.substring(0, 4);
			if (!DateUtils.isYear(year)) {
				return false;
			}

			// 校验月份
			String month = birth.substring(4, 6);
			int tmp = Integer.parseInt(month);
			if (tmp < 1 || tmp > 12) {
				return false;
			}

			// 校验日期
			String days = birth.substring(6, 8);
			tmp = Integer.parseInt(days);
			if (tmp < 1 || tmp > 31) {
				return false;
			}

			SimpleDateFormat birthDateFormat = new SimpleDateFormat("yyyyMMdd");

			Date birthD = birthDateFormat.parse(birth);
			String birth2 = birthDateFormat.format(birthD);

			// 日期部分不合法
			if (!birth.equals(birth2)) {
				return false;
			}

			// 小于等于当天
			if (new Date().compareTo(birthD) <= 0) {
				return false;
			}

			// 校验位
			verifyCode = idcard.substring(17);

			if (verifyCode != null
					&& verifyCode.equalsIgnoreCase(String.valueOf(getCheckCode(idcard.substring(0, 17))))) {
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 校验身份证号年龄是否年满18周岁
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean verifyIdCardIsAdult(String idcard) {

		Matcher matcher = null;

		String verifyCode = null; // 最后一位校验位

		if (idcard == null) {
			return false;
		}

		if (idcard.length() == 15) {
			idcard = convertToEighteen(idcard);
		}

		if (idcard.length() != 18) {
			return false;
		}
		try {
			//
			matcher = pattern.matcher(idcard);

			if (!matcher.find()) {
				return false;
			}

			String area = idcard.substring(0, 2);
			if (!validArea(area)) {
				return false;
			}

			String birth = idcard.substring(6, 14);

			// 校验年份
			String year = birth.substring(0, 4);
			if (!DateUtils.isYear(year)) {
				return false;
			}

			// 校验月份
			String month = birth.substring(4, 6);
			int tmp = Integer.parseInt(month);
			if (tmp < 1 || tmp > 12) {
				return false;
			}

			// 校验日期
			String days = birth.substring(6, 8);
			tmp = Integer.parseInt(days);
			if (tmp < 1 || tmp > 31) {
				return false;
			}

			SimpleDateFormat birthDateFormat = new SimpleDateFormat("yyyyMMdd");

			Date birthD = birthDateFormat.parse(birth);
			String birth2 = birthDateFormat.format(birthD);

			// 日期部分不合法
			if (!birth.equals(birth2)) {
				return false;
			}

			// 小于等于当天
			if (new Date().compareTo(birthD) <= 0) {
				return false;
			}

			// 未年满18岁
			if (DateUtils.getNextYear(birthD, 18).getTime() > System.currentTimeMillis()) {
				return false;
			}

			// 校验位
			verifyCode = idcard.substring(17);

			if (verifyCode != null && verifyCode.equals(String.valueOf(getCheckCode(idcard.substring(0, 17))))) {
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * 计算校验位
	 * 
	 * @param eightcardid
	 *            String
	 * 
	 * @return String
	 * 
	 */

	public static char getCheckCode(String eightcardid) {
		int checkCodeIndex = 0;
		char checkCode = ' ';
		try {
			eightcardid = eightcardid.substring(0, 17);
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * (1 * eightcardid.charAt(i) - 48);
			}
			checkCodeIndex = sum % 11;
			checkCode = vi[checkCodeIndex];

		} catch (RuntimeException ex) {
			checkCode = ' ';
		}
		return checkCode;

	}

	/**
	 * 15位身份证升级到18位，生日年份前增加19，末尾增加校验位
	 * 
	 * @param fifteenCardId
	 *            String
	 * 
	 * @return String
	 * 
	 */

	public static String convertToEighteen(String fifteenCardId) {
		StringBuffer sb = new StringBuffer(fifteenCardId);
		sb.insert(6, "19");
		sb.append(getCheckCode(sb.toString()));
		return sb.toString();

	}

	/**
	 * 判断身份证的生日串（7-14位）是否合法日期
	 * 
	 * @param date
	 * @return
	 */
	public static boolean validDate(String date) {
		try {
			SimpleDateFormat birthDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date d = birthDateFormat.parse(date);
			String s = birthDateFormat.format(d);
			return s.equals(date);
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 根据身份证的生日位判断是否成年（满18周岁）
	 * 
	 * @param birthday
	 *            pattern yyyymmdd
	 * @return
	 */
	public static boolean isAdult(String birthday) {

		String yearOfBirth = birthday.substring(0, 4);
		String monthOfBirth = birthday.substring(4, 6);
		String dayOfBirth = birthday.substring(6, 8);

		Calendar now = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.set(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth) - 1, Integer.parseInt(dayOfBirth));
		birth.add(Calendar.YEAR, 18);
		birth.add(Calendar.DAY_OF_YEAR, -1);

		return birth.before(now);
	}

	public static String getBirthday(String id) {
		if (id.length() == 15) {
			return "19" + id.substring(6, 12);
		} else {
			return id.substring(6, 14);
		}
	}

	/**
	 * 通过身份证号获取性别
	 * 
	 * @param IdNo
	 *            String
	 * @return String 1-男2-女
	 */
	public static Long getGenderFromId(String idcard) {
		if (idcard == null || (idcard.length() != 15 && idcard.length() != 18)) {
			return null;
		}
		String sex = "";
		if (idcard.length() == 15) {
			sex = idcard.substring(14, 15);
		} else {
			sex = idcard.substring(16, 17);
		}
		try {
			int gender = Integer.parseInt(sex);
			if (gender % 2 == 0) {
				return 2L;
			}
			if (gender % 2 == 1) {
				return 1L;
			}
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	/**
	 * 判断身份证的区域代码（前2位）是否合法
	 * 
	 * @param areaCode
	 * @return
	 */
	public static boolean validArea(String areaCode) {
		if (areaCodes.get(areaCode) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断输入字符串是否为汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isGB2312(String str) {
		// 首字节码位从0×81至0×FE，尾字节码位分别是0×40至0×FE
		char[] chars = str.toCharArray();
		boolean isGB2312 = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
					isGB2312 = true;
					break;
				}
			}
		}
		return isGB2312;
	}

	/**
	 * 是否有效中文名
	 * 
	 * @param realName
	 * @return
	 */
	public static boolean validChineseName(String realName) {
		if (realName != null && realName.length() > 1 && realName.length() <= 6 && isGB2312(realName)) {

			return true;
		}
		return false;
	}

	/**
	 * 是否有效中文名(对于网易宝等外部产品适用) 网易宝实名用户名会出现姓名超长（大多是新疆部分民族的姓名）的情况
	 * 
	 * @param realName
	 * @return
	 */
	public static boolean validChineseNameForProduct(String realName) {
		boolean flag = false;
		if (realName == null || "".equals(realName.trim())) {
			return false;
		}
		try {
			Matcher m = NAME_PATTERN.matcher(realName.trim());
			boolean flag1 = m.matches();
			if (flag1 && realName.length() <= 20) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 是否有可能是少数民族名字，如阿沛·阿旺晋美
	 * 
	 * @param realName
	 * @return
	 */
	public static boolean isMinorityName(String realName) {
		if (realName != null && realName.length() > 1 && realName.length() <= 20) {
			int middleDotIndex = realName.indexOf("·");
			// "·" 要在中间，既不能是开头 也不能结尾 如“阿沛·阿旺晋美”
			if (middleDotIndex > 0 && middleDotIndex < realName.length() - 1) {
				String beforeStr = realName.substring(0, middleDotIndex);
				String afterStr = realName.substring(middleDotIndex + 1);
				if (beforeStr != null && isGB2312(beforeStr) && afterStr != null && isGB2312(afterStr)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 校验实名登记输入的真实姓名是否有效 长度为2-5位点的中文字符
	 * 
	 * @param realName
	 * @return
	 */
	public static boolean verifyRealname(String realName) {
		if (realName != null && realName.length() > 1 && realName.length() < 6 && isGB2312(realName)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		String idCard = "110110198810101234";

		System.out.println("id " + idCard);

		// idCard = convertToEighteen(idCard);

		System.out.println("update to 18 ? " + idCard);

		System.out.println("check code ? " + getCheckCode(idCard));

		System.out.println("is date valid ?　" + VerifyIdcard.validDate("20080229"));

		System.out.println("id card valid ? " + VerifyIdcard.verifyIdCard(idCard));

		System.out.println(1 * '3');

		// System.out.println(verifyIdCardIsAdult("110101199212110059"));
		System.out.println(validChineseNameForProduct("阿沛·阿旺晋美"));
		System.out.println(validChineseNameForProduct("阿沛·阿旺晋美旺晋美没"));
		System.out.println(validChineseNameForProduct("阿沛阿旺晋美旺晋美没阿沛阿旺晋美旺晋美没长"));
		System.out.println(validChineseNameForProduct("阿沛阿旺晋美·"));
		System.out.println(validChineseNameForProduct("·阿沛阿旺晋美"));
		System.out.println(validChineseNameForProduct("阿沛阿.旺晋美"));
		System.out.println(validChineseNameForProduct("阿沛阿-旺晋美"));
		System.out.println(validChineseNameForProduct("阿沛阿?旺晋美"));
		System.out.println(validChineseNameForProduct("阿沛阿(旺晋美"));
		System.out.println(validChineseNameForProduct("阿沛·阿旺晋美"));
		try {
			System.out.println("阿沛阿旺晋美旺晋美没阿沛阿旺晋美旺晋美?没没没没".getBytes("UTF-8").length);
			System.out.println("阿沛阿旺晋美旺晋美没阿沛阿旺晋美旺晋美.没没没没".length());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(isMinorityName("阿沛·阿旺晋美"));
		System.out.println(isMinorityName("阿沛·阿旺晋美旺晋美没"));
		System.out.println(isMinorityName("阿沛阿旺晋美·"));
		System.out.println(isMinorityName("·阿沛阿旺晋美"));
		System.out.println(validChineseName("阿沛·阿旺晋美"));

	}

}
