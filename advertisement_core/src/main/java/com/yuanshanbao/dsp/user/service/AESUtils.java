package com.yuanshanbao.dsp.user.service;

import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.yuanshanbao.common.util.MD5Util;

/**
 * Created by zhangyiqun on 2016/10/28.
 */
public class AESUtils {
	public static final String CHARSET = "UTF-8";
	public static final String ALGORITHM_AES = "AES";
	static final String HEX = "0123456789ABCDEF";
	static final String IV = "24d77bb6b61c8fc1";
	public static boolean initialized = false;

	public AESUtils() {
	}

	public static String encrypt(String seed, String text) {
		try {
			byte[] e = Base64.decodeBase64(seed);
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
			byte[] e = Base64.decodeBase64(seed);
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(e, enc);
			return new String(result);
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

	public static byte[] decryptWeiXinUnionId(byte[] content, byte[] keyByte, byte[] ivByte) throws Exception {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void initialize() {
		if (initialized)
			return;
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

	// 生成iv
	public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}

	public static void main(String[] args) throws Exception {
		String earatedata = "SroFYr0xh0Ihti2ZM73YXx6yBbLhnuPP/rZ9yTghByj5pxWDPHknETbGhzxXH/S1FNu2i3vYNTf/AwWghu1lBB8WOpKqAaePY9ZPgKdRFDqHdy9ED7QEr9SMgLL9Y0b2sLrIW7xsdQqv/9xs/TjzdoTgYC3xVHsM1BGn0FqNwoEgRuOPHjhFZ8lkEq1rnw2fq/TOWMEq88/Aki7dWibYJV4hCGIWZX5Bd9SHn1U3mYXiMIcCQvf+yCKkXWVLjmtDLk1e5ugijVqmN4WzzmtWCgggRaBtylQUlu5EGRKWIqjjQSVpaZAt38tC90Hkyk6BLMAJZ4FZvQ9oc1ifu+4IKAWUp4j53our0yCBh/qJx0YX6FUyKyzkglN7vnlgpDqgvZs8nkFUxUoib3CMvyvylAimSggFvE4srkCY2IRtu1UBwIsFbJZB9PE3zGAog5HRAz6/dEQFrXf2jxOzn7kwOBSp4UX1zwkZV9EEIkhA7BU7u1jEvKILuViusITmgS5g4c5WBkisKpBt6d4lcmiUSw==";
		String sessionKey = "dXlDHi0QttFzmReZhBe2fw==";
		String ivKey = "40JVmL4F24KvI5PFHQhbWg==";
		byte[] resultValue = decryptWeiXinUnionId(Base64.decodeBase64(earatedata), Base64.decodeBase64(sessionKey),
				Base64.decodeBase64(ivKey));
		String result = new String(resultValue);
		System.out.print(result);
		System.out.println(Base64.encodeBase64String(MD5Util.get("sddz").substring(0, 16).getBytes()));
		String e = encrypt(
				"NjUyNWIwNGRkZjQ1M2Q4Yg==",
				"{\"mobile\":\"18888888888\",\"name\":\"测试\",\"partnerId\":\"12313123\",\"advertisementAmount\":10000,\"repayDays\":7,\"repayMonths\":null,\"monthRepayDate\":20,\"monthRepayAmount\":500.67,\"poundage\":11,\"monthRate\":0.02,\"monthServiceRate\":0.001,\"firstRepayDate\":1510295153,\"realRepayDate\":null,\"realRepayAmount\":null,\"overdueAmount\":520.67,\"userIp\":null,\"userAgent\":null,\"status\":null,\"partnerStatus\":null}");
		System.out.println(e);
		System.out
				.println(decrypt(
						"NjUyNWIwNGRkZjQ1M2Q4Yg==",
						"29F8F42BDAB4FC1C37AC86786F4E3622211ECEDDE7465F0D53E385C5450A4474CAF74FBA6371E71F93A7606CA1F75C38E80BF4A50B620941D47AC55641208225AE7A1FFDED098B069CC512AD84FE3BB7C1D0413F189C3B0D44A6E5B830899D1FB63D233745E71B822D9A9085704F053FEBD6B526DC0B9FA86583ED561A0C27A7BFE1279C09BB41F4CE246E34658A28E509247BD3249083AF9F356CA81411C5FAAF43E038C2D7ED3F01B4664D52B29211"));
	}
}