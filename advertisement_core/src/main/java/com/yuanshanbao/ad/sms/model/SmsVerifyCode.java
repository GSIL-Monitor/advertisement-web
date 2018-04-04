package com.yuanshanbao.ad.sms.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import com.yuanshanbao.common.util.reflect.ReflectUtil;

/**
 * 短信验证码
 * 
 * @author bjsxfeng
 *
 */
public class SmsVerifyCode implements Serializable {

	private static final long serialVersionUID = 4842461296232495094L;

	private static final String OWNER_KEY = "gIvEmE5_TOT";

	// 用户名
	private String userName;
	// 手机号
	private String mobile;
	// 短信发送时间
	private long smsReceiveTime;
	// 短信验证码
	private int randomCode;
	// 用户ip
	private String userIp;
	// type
	private String type;
	// channel
	private String channel;
	// 有效时间
	private long effectiveTime;
	// 截止时间
	private long expiredTime;
	// 签名
	private String sign;

	private Timestamp createTime;
	private Timestamp updateTime;

	public SmsVerifyCode() {
	}

	public SmsVerifyCode(String moblie, int verificationCode, long receiveTime, String channel, String userIp,
			int effectiveMin) {
		this.mobile = moblie;
		this.randomCode = verificationCode;
		this.smsReceiveTime = receiveTime;
		this.userIp = userIp;
		this.channel = channel;
		this.effectiveTime = effectiveMin * 60 * 1000;
		this.expiredTime = receiveTime + effectiveTime;
		this.sign = genSignature();
	}

	public String genSignature() {
		String[] arrays = new String[] { this.mobile, String.valueOf(this.randomCode) };
		Arrays.sort(arrays);
		StringBuilder verifyCode = new StringBuilder(256);
		for (Object parameter : arrays) {
			verifyCode.append(parameter);
			verifyCode.append("&");
		}
		String md5Value = DigestUtils.md5Hex(verifyCode.append(OWNER_KEY).toString());
		return md5Value;
	}

	public long getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getSmsReceiveTime() {
		return smsReceiveTime;
	}

	public void setSmsReceiveTime(long smsReceiveTime) {
		this.smsReceiveTime = smsReceiveTime;
	}

	public int getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(int randomCode) {
		this.randomCode = randomCode;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return ReflectUtil.genToString(this);
	}
}
