/*
 * 创建日期 2006-12-26
 * 
 */
package com.yuanshanbao.common.util;

/**
 * NeteaseSignUtil
 * Version 1.0
 * Copyright 2006-2007.
 * All Rights Reserved.
 * <p>本程序是个签名的工具类。
 * 用于各个产品和网银系统传递数据时使用。
 * 包含的功能包括产生需要的公私钥；产生签名和验证签名的过程。
 * @author Shaoqing Fu
 * 
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.log4j.Logger;

public class RSAUtil {
	private static final Logger logger = Logger.getLogger(RSAUtil.class);
	private static String priKey_Hex_Str = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100aa3c2f6639f859da75345acfcb573ffdc3293ae21381f6386a264d2138e3aeef18a3d2d42c1e8c00eab5a9cdef56e1d8da21506cb5eacf6d988cb91aba224b2da94174eb2fa4e02145e631ff01af98ad81e20d18fe7eb108d04c8eb884e5be6c9bd4ef0746f305027abda6f56d92890dd4c546d09f4953419ced9c723715c2db02030100010281803436937f32469720a71430ca4d1eecb4a39850c428a192e888be48a7e8f004a6b71300462d660d20f6dc5341a3a82d4b3ad43efba8ec6544c3265e5bf13d357b408c74d9c920d2b9ceb9722d883f576a25d6e800a834bf2762c7233e64c8c0f87e66cd2cd007597e67715933f898676054d3566e0362d30b636c083ef961e709024100fb3c53c1ab2387db26c158b906cc1a2ad1b802396e8c2e9983c35f2b2e1289f635dcb9c7f815d64bd931f7d24695893c840b8f3b16140eae057ff04e30580957024100ad769ff7e30c16d7b916f490f31894b098d8313698660e489bdc1b865c11fb745494e345c9b5d918332e6cdb535ac904f4ba2257c8715cb1f9114ac038b46c1d024018d8496c20917df0e52f3fa7e48d309545720dfe41f968ee53827199e28f80c731626798e7e6b8ef7d461cbcd5b01a073b03ada16938888f1b0a20e88a8d5a37024100833f354c8733577943e77586d318af306b8570bf21525ece5b77902ffa2bf4f99121d250b48d6e449c3d2d4a49e8701edad897319a4d03d8f95e3713f813b3950240726de3b19966a012728b4ef71061d1d6c2673f11a987ace163c20f7ba4f7a6c8f6c1de25fca8a27ff7cb83507326ae32a6626de09283f191b1b2403ef2677ee7";
	private static String pubKey_Hex_Str = "30819f300d06092a864886f70d010101050003818d0030818902818100aa3c2f6639f859da75345acfcb573ffdc3293ae21381f6386a264d2138e3aeef18a3d2d42c1e8c00eab5a9cdef56e1d8da21506cb5eacf6d988cb91aba224b2da94174eb2fa4e02145e631ff01af98ad81e20d18fe7eb108d04c8eb884e5be6c9bd4ef0746f305027abda6f56d92890dd4c546d09f4953419ced9c723715c2db0203010001";
	private static String cp_priKey_Hex_Str = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100da94223d9a88df55feda2d1df4b47a436d48f2ef05a9676620d3d1fb60d05ba84fe1f8bf33cc0d1ba25681d35e891c0da9773de41df17ab55bf6aac34b9d77a283de4fc58768f74d30526e8a61e7bd97a9275aa28cdc48d1a7d97f4af88d770cb5e1a85cbae3cb214a734fc3477540c4cc486f350deeb497df0888c6b0f8440b02030100010281805cc22895c412f9d460c6ee069c664acc7a804eb828caef5a63ec005c91855aa05ee622cde18962b6ab79d2b68493e317d9270558dd6c310aedf00c1fbce32275482dd69fee7a78d990e6848c0f5b6ab883a3414831f2fe0078b35cc035529b68b8fbb2eab93d321daa976fbcebec0a76179cd3756392993387c20626c68651e1024100fa90aaacb15cca6baec9ff43986e3008cb1a22ed723e8bcc50ce8d25bff533ea96ae8bb522dbfc359be360b911c036a3f1bb4a2b301226d2da0bc80e626ef20f024100df51da6e51a027d61ce8265a14b33a885d97f118273732eb48cba42adf3f30f3781372e60001d0128e4c88c120bf9f13b0bc3e8891895f94561f0cda814c9a45024100b8327565b29516318732f3b68b6e255db1812b40070637db2164569af3bc9c47ce31ce7e807bdbb2c540a83024fe7aac96de2a2e098eae7fbecf7e6824b08ebd02401701f7df25c9ba50f4e9760a11d7e54fe754a0223ce812f6eaa91060da5f484bcd3333c33e7cf152f8162a80bb3f02c4a490c060919df59af2c4802e82e3c18102401619ae4508e53d01cd09a69f68a0723f0587813cf322e4aa54b5529c7ad77c8aed0a8810aa0f2590e8f309d091efd144c56351adcda608d7e35866666ba7ac8a";
	private static String cp_pubKey_Hex_Str = "30819f300d06092a864886f70d010101050003818d0030818902818100da94223d9a88df55feda2d1df4b47a436d48f2ef05a9676620d3d1fb60d05ba84fe1f8bf33cc0d1ba25681d35e891c0da9773de41df17ab55bf6aac34b9d77a283de4fc58768f74d30526e8a61e7bd97a9275aa28cdc48d1a7d97f4af88d770cb5e1a85cbae3cb214a734fc3477540c4cc486f350deeb497df0888c6b0f8440b0203010001";

	/**
	 * 本方法使用SHA1withRSA签名算法产生签名
	 * 
	 * @param String
	 *            priKey 签名时使用的私钥(16进制编码)
	 * @param String
	 *            src 签名的原字符串
	 * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
	 */
	public static String generateSHA1withRSASigature(String priKey, String src) {
		try {

			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pribyte = hexStrToBytes(priKey.trim());

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");

			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes());

			byte[] signature = sigEng.sign();
			return bytesToHexStr(signature);
		} catch (Exception e) {
			e.printStackTrace();
			// LogMan.log("[NeteaseSignUtil][generateSHA1withRSASigature]"+e);
			return null;
		}
	}

	/**
	 * 本方法使用SHA1withRSA签名算法产生签名
	 * 
	 * @param String
	 *            priKey 签名时使用的私钥(16进制编码)
	 * @param String
	 *            src 签名的原字符串
	 * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
	 */
	public static String generateSHA1withRSASigatureBase64(String priKey, String src) {
		try {

			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pribyte = Base64.decode(priKey);

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");

			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes("UTF-8"));

			byte[] signature = sigEng.sign();
			return Base64.encodeS(signature);
		} catch (Exception e) {
			e.printStackTrace();
			// LogMan.log("[NeteaseSignUtil][generateSHA1withRSASigature]"+e);
			return null;
		}
	}

	/**
	 * 本方法使用SHA1withRSA签名算法验证签名
	 * 
	 * @param String
	 *            pubKey 验证签名时使用的公钥(16进制编码)
	 * @param String
	 *            sign 签名结果(16进制编码)
	 * @param String
	 *            src 签名的原字符串
	 * @return String 签名的返回结果(16进制编码)
	 */
	public static boolean verifySHA1withRSASigature(String pubKey, String sign, String src) {
		try {
			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pubbyte = CommonUtil.hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);

			sigEng.initVerify(rsaPubKey);
			sigEng.update(src.getBytes());

			byte[] sign1 = CommonUtil.hexStrToBytes(sign);
			return sigEng.verify(sign1);

		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 本方法使用SHA1withRSA签名算法验证签名
	 * 
	 * @param String
	 *            pubKey 验证签名时使用的公钥(16进制编码)
	 * @param String
	 *            sign 签名结果(16进制编码)
	 * @param String
	 *            src 签名的原字符串
	 * @return String 签名的返回结果(16进制编码)
	 */
	public static boolean verifySHA1withRSASigature(String pubKey, String sign, String src, String encoding) {
		try {
			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pubbyte = CommonUtil.hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);

			sigEng.initVerify(rsaPubKey);
			sigEng.update(src.getBytes(encoding));

			byte[] sign1 = CommonUtil.hexStrToBytes(sign);
			return sigEng.verify(sign1);

		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 本方法用于产生1024位RSA公私钥对。
	 * 
	 */
	public static void genRSAKeyPair() {
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		try {
			System.out.println("Generating a pair of RSA key ... ");
			rsaKeyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			String tempStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			int length = tempStr.length();
			String temp = "";
			for (int i = 0; i < 50; i++) {
				int randomInt = random.nextInt(length);
				temp += tempStr.substring(randomInt, randomInt + 1);
			}
			random.setSeed(temp.getBytes());

			rsaKeyGen.initialize(1024, random);
			rsaKeyPair = rsaKeyGen.genKeyPair();
			PublicKey rsaPublic = rsaKeyPair.getPublic();
			PrivateKey rsaPrivate = rsaKeyPair.getPrivate();

			System.out.println("公钥：" + CommonUtil.bytesToHexStr(rsaPublic.getEncoded()));
			System.out.println("私钥：" + CommonUtil.bytesToHexStr(rsaPrivate.getEncoded()));
			System.out.println("1024-bit RSA key GENERATED.");
		} catch (Exception e) {
			System.out.println("genRSAKeyPair��" + e);
		}
	}

	/**
	 * 将字节数组转换为16进制字符串的形式.
	 */
	private static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * 将16进制字符串还原为字节数组.
	 */
	private static final byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public static void main(String[] args) {
		genRSAKeyPair();
		// String privateKey =
		// "30820276020100300d06092a864886f70d0101010500048202603082025c020100028181008b7b246677b797f76d2afb57140c114372ee20cb95283634a0d732d2e2e01e7473f39021327f5c719a3428839769e2d44d66ccdb1e4e1b3f195e74c5781c5899fa9a1bcd5b03c8c4530d70738b7a574729da31ce74f082a22957f1dae3c9c4654a604732c9b45b6c699c14c88aafc121192c98d80500d13dba2afbd1bce8cc6b020301000102818046ceceddcdecc012f5b29a41cc858bc607f46765b264e58fb60f4957d1a12114d140566ce3ebd00c43d232b70b08d00422ff0b0439d040f8b9724029771e3cc3be6412c5840da1ab8f5e92298c175d1516a3a54370d4982926af181edaa6c7a3fad789beeb875d985b6ea0dc2278529cef72d257fb09fa02fa165308a1a52d21024100c6736beff8073e84ed9eec01d933b7bcf65d1c80eb49a3211cfed54e75d8a654c0c3b17e5ec16d87a95a48c36f6175e8e327d670b2635b9cdb7edc7a99158ce3024100b3edec29b9f13736a99ae35d322b38a8c81cbb600cdc384d4d98b97527ad6afd53fbded4b5c0fb87d2a7abe5905a55d416909a5eb991c55e3bd03b7ade5820d90241009e3db719d86ade5d5ca4d687facc147ce6ee042680776d4ea0324ba08eb3bc9de43fa72e9f1c19dc8d1be2dd83d8f10e4ce39d48abd6325cf7614729b31bce05024022814600da7fbfaee3e7a8227f347ab1236f1f1fd45787148c04f5cbd9c80ff474dea064fa55ffe694274377caaf08369bfd2f1d6b48e128a91fcdc84a4b6c41024041fcb72fe2abe04b603226b10b62fc503a2cf88301bd1f1710e2e13690332d4ad9bb584fdb73b89b7860bf312a852b28236c4a50a2bc159597477ab8b8872666";
		// String source =
		// "80advertisement2009750sdD11805tenpay.weixin1000110000120170428203611advertisementadvertisement127.0.0.1";
		// System.out.println(generateSHA1withRSASigature(privateKey, source));
	}
}
