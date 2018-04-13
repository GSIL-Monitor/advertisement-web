package com.yuanshanbao.dsp.sms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.sms.model.SmsVerifyCode;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 */
@Repository
public class VerifyCodeDaoImpl extends BaseDaoImpl implements VerifyCodeDao {

	/**
	 * 添加消息
	 */
	@Override
	public boolean addObject(SmsVerifyCode smsVerifyCode) {
		int num = this.getSqlSession().insert("verifyCode.addObject", smsVerifyCode);
		if (num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 验证短信验证码
	 */
	@Override
	public boolean querySmsCodeValidate(SmsVerifyCode smsVerifyCode) {
		long current = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", smsVerifyCode.getMobile());
		map.put("validateTime", current - smsVerifyCode.getEffectiveTime());
		map.put("randomCode", smsVerifyCode.getRandomCode());

		int count = this.getSqlSession().selectOne("verifyCode.queryMobCheckCode", map);
		if (count > 0) {
			this.getSqlSession().update("verifyCode.queryMobCheckCodeUpdate", map);
			return true;
		}
		return false;
	}

	@Override
	public List<SmsVerifyCode> queryAllValidSmsCode(String mobile) {
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", mobile);
		map.put("time", System.currentTimeMillis() - 10 * 60 * 1000);
		return getSqlSession().selectList("verifyCode.queryValidSmsCode", map);
	}
	
	@Override
	public List<SmsVerifyCode> selectMobileVerifyCode(Map<String, Object> map, PageBounds pageBounds) {
		return this.getSqlSession().selectList("verifyCode.selectMobileVerify", map, pageBounds);
	}

}
