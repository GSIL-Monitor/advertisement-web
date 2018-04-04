package com.yuanshanbao.ad.sms.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.sms.model.SmsVerifyCode;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * 验证码 interface service
 * 
 * @author wangfeng
 * 
 */
public interface VerifyCodeService {

	/**
	 * 发送验证码到用户手机.
	 * 
	 * @param mobile
	 *            手机号.
	 * @param tip
	 * @param effectiveTime
	 *            有效时间,以分为单位,默认30分钟.
	 * @return
	 */
	public SmsVerifyCode sendSmsCode(String signature, String mobile, String channel, String userIp, boolean voice);

	/**
	 * 
	 * @param username
	 *            账号
	 * @param mobile
	 *            手机
	 * @param userVerificationCode
	 *            验证码
	 * @param expiredTime
	 *            过期时间
	 * @param effectiveTime
	 *            有效时间(分钟)
	 * @param sign
	 *            签名
	 * @param userIp
	 *            用户ip
	 * @return
	 */
	public boolean validateSmsCode(String mobile, String smsCode, String sign, String userIp);
	
	public List<SmsVerifyCode> selectMobileVerify(Map<String, Object> map, PageBounds pageBounds);

}
