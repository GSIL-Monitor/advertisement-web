package com.yuanshanbao.common.util;

import java.io.UnsupportedEncodingException;

public class VerifyFormatUtil {
	/**
	 * Verifies the format of a password.
	 * 
	 * @param pass
	 *            a password.
	 * @return OK if valid;PASSWD_NULL if pass=null; PASSWD_SHORT if len(pass)
	 *         <6;PASSWD_LONG if len(pass)>16.
	 *  
	 */
	public static boolean verifyPasswdFormat(String pass) {
		if (StringUtil.isEmpty(pass))
			return false;
		if (compareStringLen(pass, 6) == -1)
			return false;
		if (compareStringLen(pass, 16) == 1)
			return false;
		else
			return true;
	}
	
	/**
	 * Compare the length of the 'str' parameter with the 'lenvalue' parameter.
	 * 
	 * @param str
	 *            a string.
	 * @param lenvalue
	 *            a integer.
	 * @return 0 if len(str) = lenvalue; 1 if len(str) > lenvalue ; 2 if
	 *         len(str) < lenvalue.
	 */
	private static int compareStringLen(String str, int lenvalue) {
		try {
			if (str.getBytes("GBK").length == lenvalue)
				return 0;
			if (str.getBytes("GBK").length > lenvalue)
				return 1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
