package com.yuanshanbao.dsp.sms.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.constant.IniConstant;
import com.yuanshanbao.dsp.common.times.TimesLimitConstants;
import com.yuanshanbao.dsp.common.times.TimesLimitModel;
import com.yuanshanbao.dsp.common.times.TimesLimitService;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.sms.dao.VerifyCodeDao;
import com.yuanshanbao.dsp.sms.model.SmsVerifyCode;
import com.yuanshanbao.paginator.domain.PageBounds;

@Component
public class VerifyCodeServiceImpl implements VerifyCodeService {

	/** 10min **/
	public static final int DEFAULT_EFFECTIVE_TIME = 10;

	/** 24 hours **/
	public static final long DEFAULT_EMAIL_EFFECTIVE_TIME = 24 * 60;

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private VerifyCodeDao smsVerifyCodeDao;

	@Autowired
	private TimesLimitService timesLimitService;

	private AtomicInteger count = new AtomicInteger();

	private int alert = 1000;
	private int maxSend = 50000;

	private long alertTime = System.currentTimeMillis();

	/**
	 * 短信发送
	 */
	@Override
	public SmsVerifyCode sendSmsCode(String signature, String mobile, String channel, String userIp, boolean voice) {

		// 1.获取验证码有效时间
		int effectiveMin = getEffectiveTime();

		// 3.发送验证码短信
		int verificationCode = getVerificationCode();
		long receiveTime = System.currentTimeMillis();
		SmsVerifyCode smsVerifyCode = new SmsVerifyCode(mobile, verificationCode, receiveTime, channel, userIp,
				effectiveMin);

		// 3.将验证码保存到db
		try {
			if (alertCount()) {
				return smsVerifyCode;
			}
			smsVerifyCodeDao.addObject(smsVerifyCode);
			LoggerUtil.sendMessageInfo("[Send Sms]: mobile=[" + mobile + "], verificationCode=[" + verificationCode
					+ "], effectiveMin=[" + effectiveMin + "]");
			if (!"dev".equals(CommonUtil.getEnvironment()) && !"test".equals(CommonUtil.getEnvironment())) {
				String rt = "";
				if (voice) {
					// rt = messageSender.sendVoiceCode(mobile,
					// verificationCode, effectiveMin);
				} else {
					// rt = messageSender.sendVerifyCodeAli(signature, mobile,
					// verificationCode + "");
					rt = messageSender.sendVerifyCodeChuangLan(signature, mobile, verificationCode + "");
				}
				LoggerUtil.sendMessageInfo(rt);
			}

			// 4.返回
			return smsVerifyCode;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(), e);
			throw e;
		}
	}

	private boolean alertCount() {
		if ((System.currentTimeMillis() - alertTime) > (long) 1000 * 60 * 60) {
			count.set(0);
			alertTime = System.currentTimeMillis();
			alert = 100;
		}
		if (count.incrementAndGet() > alert) {
			messageSender.sendVerifyCodeAli("远山保险", "18911200686", count.get() + "");
			alert = alert * 2;
		}
		if (count.get() > maxSend) {
			LoggerUtil.sendMessageInfo("[Send sms message to much, count=" + count.get() + "]");
			return true;
		}
		return false;
	}

	/**
	 * 获取验证码.
	 * 
	 * @return
	 */
	private int getVerificationCode() {
		return (int) ((Math.random() * 9 + 1) * 1000);
	}

	/**
	 * 验证
	 */
	@Override
	public boolean validateSmsCode(String mobile, String smsCode, String sign, String userIp) {
		// 1.参数校验
		if (StringUtils.isBlank(smsCode)) {
			throw new BusinessException(ComRetCode.SMS_CODE_WRONG);
		}

		int smsCodeInt = 0;
		try {
			smsCodeInt = Integer.parseInt(smsCode.trim());
		} catch (Exception e) {
			throw new BusinessException(ComRetCode.SMS_CODE_WRONG);
		}
		// sign = sign.trim();
		long expiredTime = getEffectiveTime();
		TimesLimitModel commonTimesLimitObject = timesLimitService.getTimesLimitModel(
				TimesLimitConstants.VERIFY_SMS_RANDOM_CODE_FAIL, mobile);
		if (commonTimesLimitObject.isOverTimesLimit()) {
			throw new BusinessException(ComRetCode.SMS_VERIFY_TIME_LIMIT_OVER);
		}

		// 1.获取验证码有效时间
		int effectiveMin = getEffectiveTime();

		// 2.验证签名有效性
		SmsVerifyCode smsVerifyCode = new SmsVerifyCode();
		smsVerifyCode.setMobile(mobile);
		smsVerifyCode.setRandomCode(smsCodeInt);
		smsVerifyCode.setExpiredTime(expiredTime);
		smsVerifyCode.setEffectiveTime(effectiveMin * 60 * 1000);
		smsVerifyCode.setUserIp(userIp);

		// if (sign.equals(smsVerifyCode.genSignature())) {
		if (smsVerifyCodeDao.querySmsCodeValidate(smsVerifyCode)) {
			return true;
		}

		commonTimesLimitObject.increaseTimes();
		throw new BusinessException(ComRetCode.SMS_CODE_WRONG);
	}

	/**
	 * 获取过期时间.
	 * 
	 */
	public int getEffectiveTime() {
		return IniBean.getIniIntValue(IniConstant.SMS_CODE_TIME_OUT, DEFAULT_EFFECTIVE_TIME);
	}

	@Override
	public List<SmsVerifyCode> selectMobileVerify(Map<String, Object> map, PageBounds pageBounds) {
		return smsVerifyCodeDao.selectMobileVerifyCode(map, pageBounds);
	}
}
