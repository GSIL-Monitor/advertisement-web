package com.yuanshanbao.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhangyiqun on 2016/10/28.
 */
public class AESUtils {
	public static final String CHARSET = "UTF-8";
	public static final String ALGORITHM_AES = "AES";
	static final String HEX = "0123456789ABCDEF";
	static final String IV = "24d77bb6b61c8fc1";

	public AESUtils() {
	}

	public static String encrypt(String seed, String text) {
		try {
			byte[] e = getRawKey(seed.getBytes());
			byte[] result = encrypt(e, text.getBytes());
			String content = toHex(result);
			return content;
		} catch (Exception var5) {
			var5.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String seed, String encrypted) {
		if (StringUtils.isBlank(encrypted)) {
			return null;
		}
		try {
			byte[] e = getRawKey(seed.getBytes());
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(e, enc);
			String coentn = new String(result);
			return coentn;
		} catch (Exception var6) {
			var6.printStackTrace();
			return null;
		}
	}

	public static String encryptWithoutRaw(String seed, String text) {
		try {
			byte[] result = encrypt(seed.getBytes(), text.getBytes(), IV.getBytes());
			System.out.println(Base64.encodeBase64String(result));
			String content = toHex(result);
			return content;
		} catch (Exception var5) {
			var5.printStackTrace();
			return null;
		}
	}

	public static String decryptWithoutRaw(String seed, String encrypted) {
		if (StringUtils.isBlank(encrypted)) {
			return null;
		}
		try {
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(seed.getBytes(), enc, IV.getBytes());
			String coentn = new String(result);
			return coentn;
		} catch (Exception var6) {
			var6.printStackTrace();
			return null;
		}
	}

	public static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr);
		SecretKey sKey = kgen.generateKey();
		byte[] raw = sKey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] bytes = new byte[cipher.getBlockSize()];
		cipher.init(1, skeySpec, new IvParameterSpec(bytes));
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(2, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear, byte[] iv) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(1, skeySpec, new IvParameterSpec(iv));
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted, byte[] iv) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(2, skeySpec, new IvParameterSpec(iv));
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];

		for (int i = 0; i < len; ++i) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}

		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null) {
			return "";
		} else {
			StringBuffer result = new StringBuffer(2 * buf.length);

			for (int i = 0; i < buf.length; ++i) {
				appendHex(result, buf[i]);
			}

			return result.toString();
		}
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append("0123456789ABCDEF".charAt(b >> 4 & 15)).append("0123456789ABCDEF".charAt(b & 15));
	}

	public static String buildAESEncrypt(String data, String key) {
		try {
			Cipher e = Cipher.getInstance("AES/ECB/PKCS5Padding");
			e.init(1, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
			return Base64.encodeBase64URLSafeString(e.doFinal(data.getBytes("UTF-8")));
		} catch (Exception var3) {
			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", var3);
		}
	}

	public static String buildAESDecrypt(String data, String key) {
		try {
			Cipher e = Cipher.getInstance("AES/ECB/PKCS5Padding");
			e.init(2, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
			return new String(e.doFinal(Base64.decodeBase64(data)), "UTF-8");
		} catch (Exception var3) {
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", var3);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(MD5Util.get("zhaoshangqinglan").substring(0, 16));
		String e = encrypt("d34362379ff2533c", "测试");
		System.out.println(e);
		System.out.println(decrypt("d34362379ff2533c", "3A86879742A9E1108C7F4831EF5F6672"));
		System.out.println(Base64.encodeBase64String(getRawKey("b097538bd62763f1".getBytes())));

		System.out.println(toHex(encrypt(Base64.decodeBase64("YbvrjfX6r98aD5XUcycjcQ=="), "18612215672".getBytes())));
	}
}