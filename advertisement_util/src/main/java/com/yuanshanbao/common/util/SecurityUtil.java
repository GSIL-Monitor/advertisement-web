package com.yuanshanbao.common.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public class SecurityUtil
{
	public static String byte2hex(byte abyte0[])
	{
		StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
		for (int i = 0; i < abyte0.length; i++)
		{
			if ((abyte0[i] & 0xff) < 16)
			{
				stringbuffer.append("0");
			}
			stringbuffer.append(Long.toString((long) abyte0[i] & (long) 255, 16));
		}

		return stringbuffer.toString().toUpperCase();
	}

	public static String getMd5(String s)
	{
		byte abyte0[] = null;
		MessageDigest messagedigest;
		try
		{
			messagedigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException nosuchalgorithmexception)
		{
			throw new IllegalArgumentException("no md5 support");
		}
		messagedigest.update(s.getBytes());
		abyte0 = messagedigest.digest();
		return byte2hex(abyte0);
	}

	public static String hmacmd5(String ssn, int seed) throws Exception
	{
		try
		{
			byte[] plainText = ssn.getBytes();

			KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
			SecureRandom sr = new SecureRandom();

			//			LoggerUtil.debug("seed=" + seed, "SecurityUtil", "hmacmd5");

			sr.setSeed(seed);
			keyGen.init(64, sr);
			SecretKey MD5key = keyGen.generateKey();
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(MD5key);
			mac.update(plainText);

			return URLEncoder.encode(new String(mac.doFinal()), "UTF-8");
		}
		catch (Exception e)
		{
			//			LoggerUtil.warn(e.getMessage(), SecurityUtil.class.getName(), "hmacmd5");
			throw new Exception(e);
		}
	}
}
