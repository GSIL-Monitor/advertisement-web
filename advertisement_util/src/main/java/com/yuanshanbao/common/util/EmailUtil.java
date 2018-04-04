package com.yuanshanbao.common.util;

import org.apache.commons.lang.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class EmailUtil {

	public static String EMAIL_MERCHANT_NETEASE = "netease";
	public static String EMAIL_MERCHANT_TENCENT = "tencent";
	public static String EMAIL_MERCHANT_TELECOM = "telecom";
	public static String EMAIL_MERCHANT_OTHER = "other";

	public static String ACCESS_KEY = PropertyUtil.getProperty("aliyun.accessKeyId");
	public static String ACCESS_SECRET = PropertyUtil.getProperty("aliyun.accessKeySecret");

	private static final String ACCOUNT_NAME = "service@send.lanchouzp.com";
	private static final String FROM_ALIAS = "远山保险";

	public static final String POLICY_EMAIL_SUBJECT = "【%s】恭喜您成功领取%s";
	public static final String CALCULATE_EMAIL_SUBJECT = "【%s】平安鸿运随行返还型意外保障试算结果";

	public static final String POLICY_LINK = PropertyUtil.getProperty("host.web") + "/internal/email/policy.html";
	public static final String CALCULATE_LINK = PropertyUtil.getProperty("host.web") + "/internal/email/calculate.html";

	public static void send(String email, String merchantName, String subject, String body) {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY, ACCESS_SECRET);
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName(ACCOUNT_NAME);
			request.setFromAlias(merchantName);
			request.setAddressType(1);
			request.setReplyToAddress(true);
			request.setToAddress(email);
			request.setSubject(subject);
			request.setHtmlBody(body);
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			LoggerUtil.sendMessageInfo(JacksonUtil.obj2json(httpResponse));
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	public static String getEmailMerchant(String email) {
		if (StringUtils.isBlank(email)) {
			return null;
		}
		if (email.endsWith("@163.com") || email.endsWith("@126.com") || email.endsWith("@yeah.net")
				|| email.endsWith("@188.com") || email.endsWith("@vip.163.com") || email.endsWith("@vip.126.com")) {
			return EMAIL_MERCHANT_NETEASE;
		}
		if (email.endsWith("@qq.com") || email.endsWith("@vip.qq.com") || email.endsWith("@foxmail.com")) {
			return EMAIL_MERCHANT_TENCENT;
		}
		if (email.endsWith("@189.com")) {
			return EMAIL_MERCHANT_TELECOM;
		}
		return EMAIL_MERCHANT_OTHER;
	}

}