package com.yuanshanbao.common.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 签名加解密(我来贷)
 */
public class EncryptUtil {

	public static final String RSA_ALGORITHM_NAME = "RSA";
	public static final String SHA1WITHRSA_ALGORITHM_NAME = "SHA1WithRSA";

	// foxconn public key
	public static String STR_PUBLIC_KEY = "publickeydemo";

	//foxconn private key
	public static String STR_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJSvtzT/ivu+L7INmKZN/BPImLfm"
			+ "A4k1kO6swAYSbYgderwGL0H0eJQk8wxxLHGIUz4XZ5TaopuHBfjNrbeySuxn2ZW/iQ/IfH7rtucd"
			+ "ORuswwxtoCtz4T9qCgxLiWvGLqKUz5HKEXZHmewsjlqnC9owSEtypTiqrFNsnpT5jxLrAgMBAAEC"
			+ "gYBi59w8Af0m5uV9ULQJdHjJhDeqpPQXMq29RubZYyq/T7TUHF0VR5VqHoYa8bG5uBB/jjwV9bYT"
			+ "NSdQ0SjMuGpDgcsu81LjwAfiZYpV8CekhiuiS8xDy2OeGONrEN8ErgIOpira6XZCF9UJvATIXUSr"
			+ "FlHDQg6nTCBF+4QQ8PH+2QJBANDZeaqDO63/45W1K39zVoQAfS0dJtzeqave42GkSJDuay1FLlyZ"
			+ "4JRgtC2LRLNc7hafhC6P91NWeKeo8SXc8ZcCQQC2QRfkadIK83JPX2pphJudQJ83UAUdUsFz6UML"
			+ "G6DtLrabgcL5DJJhulcGUegPYeZkenFtPHTIgG75We9chOvNAkAsQ4t3yiyh6jQQDL/VWVqiy3+8"
			+ "SgtskHyhgrHqhekjXoS0qY7z7WEd0Ykehf/fi/iiLtjTDYw7y33y+ulxPuytAkEAn74aFOfzZAnR"
			+ "LWSyPl4ujvwvESof8X2xdckELF5BBOKmx1JK9ENioinTUQTFY4Ypi/QOKvKMYZjAq+l59RvEGQJB"
			+ "AKfaBgWLG6VaH3wjZc/XVQuJki1oNyuer3lCFVUfZAWfU/ypbsmYpQP2lUNYXKt7gLhg9gMtF7eQ" + "txJOtB2pM6Q=";

	public static final String ENCODE_UTF8 = "utf-8";

	private static PrivateKey privateKey;
	private static PublicKey publicKey;

	static {
		init();
	}

	private EncryptUtil() {
	}

	public static void init() {
		try {
			privateKey = processPrivateKey();
			//publicKey = processPublicKey();
		} catch (Exception e) {
			throw new RuntimeException("加载RSA密钥对失败!");
		}
	}

	private static PrivateKey processPrivateKey() throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decryptBASE64(STR_PRIVATE_KEY));
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NAME);
		return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	}

	private static PublicKey processPublicKey() throws Exception {
		X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(decryptBASE64(STR_PUBLIC_KEY));
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NAME);
		return keyFactory.generatePublic(bobPubKeySpec);
	}

	/**
	 * 加签
	 * @param src 所需加签的字符串
	 * @return
	 * @throws Exception
	 */
	public static String sign(String src) throws Exception {
		Signature signature = Signature.getInstance(SHA1WITHRSA_ALGORITHM_NAME);
		signature.initSign(privateKey);
		signature.update(src.getBytes(ENCODE_UTF8));
		String result = encryptBASE64(signature.sign());
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(result);
		return m.replaceAll("");
	}

	/**
	 * 比对
	 * @param src 加签前的字符串
	 * @param sign 加签后的字符串
	 * @return  true 成功 <br/>
	 *          false 失败
	 * @throws Exception
	 */
	public static boolean check(String src, String sign) throws Exception {
		byte[] signed = decryptBASE64(sign);
		Signature signCheck = Signature.getInstance(SHA1WITHRSA_ALGORITHM_NAME);
		signCheck.initVerify(publicKey);
		signCheck.update(src.getBytes(ENCODE_UTF8));
		return signCheck.verify(signed);
	}

	public static byte[] decryptBASE64(String key) {
		try {
			key = key.replaceAll("\r\n", "");
			return Base64.decodeBase64(key.getBytes(ENCODE_UTF8));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String encryptBASE64(byte[] key) {
		try {
			return new String(Base64.encodeBase64(key, true), ENCODE_UTF8).replaceAll("\r\n", "");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
