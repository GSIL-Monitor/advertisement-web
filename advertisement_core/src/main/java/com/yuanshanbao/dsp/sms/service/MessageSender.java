package com.yuanshanbao.dsp.sms.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;

@Component
public class MessageSender {

	/**
	 * 通用配置
	 */
	public static String TEMPLATE_VARIABLE_NAME = "${name}";
	public static String TEMPLATE_VARIABLE_MERCHANTNAME = "<merchantName>";
	public static String TEMPLATE_VARIABLE_INSURANCENAME = "<insuranceName>";
	public static String TEMPLATE_VARIABLE_TELPHONE = "<telephone>";
	public static String TEMPLATE_VARIABLE_CODE = "${code}";
	public static String TEMPLATE_VARIABLE_TIME = "${time}";

	/**
	 * 创蓝
	 */
	private static String url = "http://sms.253.com/msg/";// 应用地址
	private static String account = "N3109257";// 账号
	private static String pswd = "N5n7Uw9p6Bea9a";// 密码

	public static String DIDI_COUPON_MESSAGE = "亲爱的%s，恭喜您成功领取一份%s，保单信息请留意%s通知。点击领滴滴红包 t.cn/RVjHN6x。";
	public static String VERIFY_CODE_MESSAGE = "您的验证码为：${code}，${time}分钟内有效，如非本人操作，请忽略本信息。";

	/**
	 * 阿里大鱼
	 */
	private static String ALI_URL = "http://gw.api.taobao.com/router/rest";
	private static String KEY = "23558637";
	private static String SECRET = "dcdb90db238b7f62f7c65b1fb225e2e2";

	public static String POLICY_SUCCESS = "SMS_33265432";
	public static String POLICY_SUCCESS_WITH_PHONE = "SMS_33360437";
	public static String POLICY_SUBMIT = "SMS_33280493";
	public static String VERIFY_CODE = "SMS_82985001";

	public String sendSmsChuangLan(String mobile, String msg) {
		LoggerUtil.sendMessageInfo("[mobile: " + mobile + "], [content: " + msg + "]");
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "send", false));
			method.setQueryString(new NameValuePair[] { new NameValuePair("un", account),
					new NameValuePair("pw", pswd), new NameValuePair("phone", mobile),
					new NameValuePair("rd", String.valueOf(true)), new NameValuePair("msg", msg),
					new NameValuePair("extno", null), });
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String content = URLDecoder.decode(baos.toString(), "UTF-8");
				LoggerUtil.sendMessageInfo("[mobile: " + mobile + "], [result: " + content + "]");
				return content;
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} catch (Exception e) {
			LoggerUtil.error("", e);
		} finally {
			method.releaseConnection();
		}
		return "";
	}

	public String sendSmsAli(String signName, String template, String mobile, String params) {
		LoggerUtil.sendMessageInfo("[mobile: " + mobile + "], [params: " + params + "]");
		try {
			TaobaoClient client = new DefaultTaobaoClient(ALI_URL, KEY, SECRET);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setSmsType("normal");
			req.setSmsFreeSignName(signName);
			req.setSmsParamString(params);
			req.setRecNum(mobile);
			req.setSmsTemplateCode(template);
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			String result = rsp.getBody();
			LoggerUtil.sendMessageInfo("[mobile: " + mobile + "], [result: " + result + "]");
			return result;
		} catch (ApiException e) {
			LoggerUtil.error("", e);
		}
		return "";
	}

	public void sendSmsAli(String template, String mobile, String name, String merchantName, String insuranceName,
			String telephone) {
		MessageSender sender = new MessageSender();
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("merchantName", merchantName);
		map.put("insuranceName", insuranceName);
		map.put("merchantName2", merchantName);
		if (StringUtils.isNotBlank(telephone)) {
			map.put("telephone", telephone);
		}
		sender.sendSmsAli("远山保险", template, mobile, JacksonUtil.obj2json(map));
	}

	/*
	 * chuanglan发验证码
	 */
	public String sendVerifyCodeChuangLan(String signature, String mobile, String code) {
		if (StringUtils.isBlank(signature)) {
			signature = "兴贷";
		}
		MessageSender sender = new MessageSender();
		return sender.sendSmsChuangLan(mobile, "【" + signature + "】" + format(VERIFY_CODE_MESSAGE, code, "10"));
	}

	public String sendVerifyCodeAli(String signature, String mobile, String code) {
		if (StringUtils.isBlank(signature)) {
			signature = "活动验证";
		}
		MessageSender sender = new MessageSender();
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("time", "10");
		return sender.sendSmsAli(signature, VERIFY_CODE, mobile, JacksonUtil.obj2json(map));
	}
	
	public String sendVerifyCodeAli(String mobile, String code, String activity, String signName) {
		MessageSender sender = new MessageSender();
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		if (StringUtils.isBlank(activity)) {
			activity = "赠险";
		}
		map.put("activity", activity);
		map.put("time", "10");
		return sender.sendSmsAli(signName, VERIFY_CODE, mobile, JacksonUtil.obj2json(map));
	}

	public String format(String template, String name, String merchantName, String insuranceName, String telephone) {
		if (name != null) {
			template = template.replace(TEMPLATE_VARIABLE_NAME, name);
		}
		if (merchantName != null) {
			template = template.replace(TEMPLATE_VARIABLE_MERCHANTNAME, merchantName);
		}
		if (insuranceName != null) {
			template = template.replace(TEMPLATE_VARIABLE_INSURANCENAME, insuranceName);
		}
		if (telephone != null) {
			template = template.replace(TEMPLATE_VARIABLE_TELPHONE, telephone);
		}
		return template;
	}

	public String format(String template, String code, String time) {
		if (code != null) {
			template = template.replace(TEMPLATE_VARIABLE_CODE, code);
		}
		if (time != null) {
			template = template.replace(TEMPLATE_VARIABLE_TIME, time);
		}
		return template;
	}

	public static void main(String[] args) {
		String result = new MessageSender()
				.sendSmsChuangLan("13717987596", new MessageSender().format(
						"【好贷科技】尊敬的${name}，您于近日申请贷款时获赠1份平安出行保险，客服人员将致电与您联系确认保险领取事宜，请放心接听，假日来临，预祝您旅途平安。", "崔坤", null,
						null, null));
		System.out.println(result);
	}

}
