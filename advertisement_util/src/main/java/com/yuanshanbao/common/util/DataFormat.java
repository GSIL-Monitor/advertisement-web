package com.yuanshanbao.common.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFormat {

	private static Logger logger = LoggerFactory.getLogger(DataFormat.class);

	private static Set<String> paraMD5 = new HashSet<String>(); // 需要md5的参数

	static {
		paraMD5.add("TOKEN");
		paraMD5.add("TRADEPASS");
		paraMD5.add("OLDPWD");
		paraMD5.add("NEWPWD");
		paraMD5.add("CUSTMONEYPWD");
		paraMD5.add("NCUSTMONEYPWD");
		paraMD5.add("CUSTBANKPASS");
	}

	private static String hidden(int i) {
		String ret = "";
		if (i > 0) {
			StringBuffer tmp = new StringBuffer();
			for (int j = 0; j < i; j++) {
				tmp.append("*");
			}
			ret = tmp.toString();
		}
		return ret;
	}

	/**
	 * 隐藏手机号码 手机号隐藏前8位，显示最后3位
	 * 
	 * @param mobile
	 * @return
	 */
	public static String hiddenMobile(String mobile) {
		String ret = "";
		if (mobile != null && mobile.length() > 3) {
			ret = mobile.substring(0, 3) + hidden(4) + mobile.substring(mobile.length() - 4);
		}
		return ret;
	}

	/**
	 * 隐藏姓名，显示第一个字符
	 * 
	 * @param mobile
	 * @return
	 */
	public static String hiddenName(String name) {
		String ret = "";
		if (name != null && name.length() > 1) {
			ret = name.substring(0, 1) + hidden(name.length() - 1);
		}
		return ret;
	}

	/**
	 * 隐藏身份证号 身份证显示前两位和后两位
	 * 
	 * @param mobile
	 * @return
	 */
	public static String hiddenIdentityCard(String identityCard) {
		String ret = "";
		if (identityCard != null && identityCard.length() > 3) {
			ret = identityCard.substring(0, 2) + hidden(identityCard.length() - 4)
					+ identityCard.substring(identityCard.length() - 2);
		}
		return ret;
	}

	/**
	 * 请求URL处理-将敏感词做md5处理
	 * 
	 * @param requestUrl
	 * @return
	 */
	public static String requestUrlMd5code(String requestUrl) {

		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(requestUrl.substring(0, requestUrl.indexOf("?") + 1));
			buffer.append(paramMd5code(requestUrl.substring(requestUrl.indexOf("?") + 1)));
			return buffer.toString();
		} catch (Exception e) {
			logger.error("[requestUrlMd5code] " + e.getMessage());
			return requestUrl;
		}

	}

	private static String paramMd5code(String str) {
		StringBuffer tmp = new StringBuffer();
		String[] params = str.split("&");
		if (params != null) {
			for (String param : params) {
				String[] kv = param.split("=", -1);
				if (paraMD5.contains(kv[0])) {
					tmp.append(kv[0]).append("=").append(MD5Util.get(kv[1] + " ", "UTF-8"));
				} else {
					tmp.append(kv[0]).append("=").append(kv[1]);
				}
				tmp.append("&");
			}

			if (tmp.length() > 0)
				tmp.delete(tmp.length() - 1, tmp.length());
		}

		return tmp.toString();
	}

	/**
	 * TCP请求json字符串处理-将敏感词做md5处理
	 * 
	 * @param requestUrl
	 * @return
	 */
	public static String jsonStrMd5code(String jsonStr) {

		try {
			JSONObject object = JSONObject.fromObject(jsonStr);
			// jsonArray 中不会包含密码,并未对其进行处理
			Set<Map.Entry<String, Object>> set = object.entrySet();
			for (Map.Entry<String, Object> entry : set) {
				if (paraMD5.contains(entry.getKey())) {
					object.put(entry.getKey(), MD5Util.get(entry.getValue() + " ", "UTF-8"));
				}
			}
			return object.toString();
		} catch (Exception e) {
			logger.error("[jsonStrMd5code] " + e.getMessage());
			return jsonStr;
		}

	}

	/**
	 * 隐藏帐号-用户短信发送等. 域名全部显示，域名前的用户名部分： 3位及以下全部显示，4-5位显示第1位和最后2位，6位及以上显示第1位和最后3位
	 * 
	 * @param username
	 * @return
	 */
	public static String hiddenUsername(String username) {
		String ssn = "";
		String domain = "";
		int pos = username.lastIndexOf("@");
		ssn = username.substring(0, pos);
		domain = username.substring(pos);

		if (ssn.length() > 3) {
			ssn = ssn.substring(0, 1) + "*" + ssn.substring(ssn.length() - 3);
		} else {
			ssn = ssn.substring(0, 1) + "*" + ssn.substring(ssn.length() - 2);
		}

		return ssn + domain;
	}
}
