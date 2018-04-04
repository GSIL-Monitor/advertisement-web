package com.yuanshanbao.ad.sms.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.sms.model.SmsVerifyCode;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface VerifyCodeDao {

	/**
	 * 添加短信
	 */
	public boolean addObject(SmsVerifyCode smsVerifyCode);

	public boolean querySmsCodeValidate(SmsVerifyCode smsVerifyCode);

	public List<SmsVerifyCode> queryAllValidSmsCode(String mobile);
	
	public List<SmsVerifyCode> selectMobileVerifyCode(Map<String, Object> map, PageBounds pageBounds);

}
