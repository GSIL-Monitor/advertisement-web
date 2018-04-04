package com.yuanshanbao.ms.utils.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class EntryptDecryptUtils {
	
	public static final String DEFAULT_AES_KEY = "4374-84dc-6cc7bc";
	
	public static final String KEY_AES_ALGORITHM = "AES";
	
	/**
	 * AES加密/解密算法/工作模式/填充方式
	 * 
	 * Java 6支持PKCS5PADDING填充方式
	 * 
	 * Bouncy Castle支持PKCS7Padding填充方式
	 */
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**
	 * 转换秘钥
	 * 
	 * @param key 二进制秘钥
	 * @return
	 * @throws Exception
	 */
	public static Key toAESKey(byte[] key) throws Exception {
		// 实例化AES秘钥材料
		SecretKey secretKey = new SecretKeySpec(key, KEY_AES_ALGORITHM);
		
		return secretKey;
	}
	
	public static String aesDecrypt2Str(String strData) throws Exception {
		return new String(aesDecrypt(hex2byte(strData)));
	}
	
	public static byte[] aesDecrypt(byte[] data) throws Exception {
		byte[] key = DEFAULT_AES_KEY.getBytes();
		
		return aesDecrypt(data, key);
	}
	
	public static byte[] aesDecrypt(byte[] data, byte[] key) throws Exception {
		// 还原秘钥
		Key k = toAESKey(key);
		
		/*
		 * 实例化
		 * 
		 * 使用PKS7PADDING填充方式
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}
	
	public static String aesEncrypt2Str(String strData) throws Exception {
		return byte2hex(aesEncrypt(strData.getBytes()));
	}
	
	public static byte[] aesEncrypt(byte[] data) throws Exception {
		byte[] key = DEFAULT_AES_KEY.getBytes();
		
		return aesEncrypt(data, key);
	}
	
	public static byte[] aesEncrypt(byte[] data, byte[] key) throws Exception {
		// 还原秘钥
		Key k = toAESKey(key);
		/*
		 * 实例化
		 * 
		 * 使用PKS7PADDING填充方式
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 数组转十六进制字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 十六进制字符串转数组
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(String s) {
		byte[] b=s.getBytes();
		if((b.length % 2)!=0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length/2];
		for (int n = 0; n < b.length; n+=2) {
			String item = new String(b,n,2);
			b2[n/2] = (byte)Integer.parseInt(item,16);
		}
		return b2;
	}
}