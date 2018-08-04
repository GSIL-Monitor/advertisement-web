package com.yuanshanbao.dsp.sms.model;

import com.yuanshanbao.dsp.common.constant.IniConstant;
import com.yuanshanbao.dsp.core.IniBean;

public class SmsConstant {

	/** 10min **/
	public static final int DEFAULT_EFFECTIVE_TIME = 10;

	/** 24 hours **/
	public static final long DEFAULT_EMAIL_EFFECTIVE_TIME = 24 * 60;

	/**
	 * 获取过期时间.
	 * 
	 */
	public static int getEffectiveTime() {
		return IniBean.getIniIntValue(IniConstant.SMS_CODE_TIME_OUT, DEFAULT_EFFECTIVE_TIME);
	}

	public static final String TEMPLATE_SMS_OPEN_SUCCESS = "恭喜您开户成功，交易商账号%s，初始交易密码%s，初始资金密码%s，电脑访问 fa.163.com/card 即可完成绑卡！仅限工作日9:00-15:00  fa.163.com/xz";
	public static final String TEMPLATE_SIGN_SUCCESS = "您已成功绑定银行卡（%s，尾号%s），点击链接fa.163.com/xz 下载网易贵金属手机客户端立即交易！";
	public static final String TEMPLATE_IN_SUCCESS = "您的账户已成功发起转账到市场%s元，5分钟内到账。如非本人操作，请致电020-83568090 fa.163.com";
	public static final String TEMPLATE_OUT_SUCCESS = "您的账户已成功发起转账到银行%s元，5分钟内到账。如非本人操作，请致电020-83568090 fa.163.com";
	public static final String TEMPLATE_OPEN_NOT_SIGN = "您已成功开户网易贵金属，电脑访问fa.163.com/card 即可完成绑卡！仅限工作日9:00-15:00  fa.163.com/xz";
	public static final String TEMPLATE_SIGN_NOT_IN = "下载网易贵金属手机客户端，转账交易快人一步。fa.163.com/xz";
	public static final String TEMPLATE_IN_5_NOT_TRAN = "您不理财，财不理您，万事俱备，下载网易贵金属手机客户端马上开始交易吧！ fa.163.com/xz";
	public static final String TEMPLATE_NOT_TRAN_IN_15_DAY_AND_50 = "白银行情动荡不定，多个点位可以实现盈利，还等什么，马上出手吧！fa.163.com/xz";
	public static final String TEMPLATE_NOT_TRAN_IN_22_DAY = "白银行情动荡不定，多个点位可以实现盈利，心动不如行动，马上出手吧！fa.163.com/xz";

	public static final String ACTIVITY_3_HAND_SILVER_SMS_TEMPLATE = "您的3手白银已经到账，下载网易贵金属手机客户端，在“交易-持仓”中即可查看。fa.163.com/xz";

	public static final String TITLE_SMS_OPEN_SUCCESS = "【网易贵金属】恭喜您开户成功！";
	public static final String TITLE_SIGN_SUCCESS = "【网易贵金属】恭喜您绑定银行卡成功！";
	public static final String TITLE_IN_SUCCESS = "【网易贵金属】转账请求提交成功！";
	public static final String TITLE_OUT_SUCCESS = "【网易贵金属】转账请求提交成功！";
	public static final String TITLE_OPEN_NOT_SIGN = "网易贵金属】绑定银行卡提醒";
	public static final String TITLE_SIGN_NOT_IN = "【网易贵金属】下载客户端，转账交易快人一步";
	public static final String TITLE_IN_5_NOT_TRAN = "网易贵金属VIP用户专享提醒";
	public static final String TITLE_NOT_TRAN_IN_15_DAY_AND_50 = "网易贵金属VIP用户专享提醒";
	public static final String TITLE_NOT_TRAN_IN_22_DAY = "网易贵金属VIP用户专享提醒";

	public static final String TEMPLATE_SMS_YUANSHAN_SIGN_NAME = "远山科技";
	public static final String TEMPLATE_SMS_QINGLAN_SIGN_NAME = "青蓝科技";
	public static final String TEMPLATE_SMS_JUBAOKEJI_SIGN_NAME = "聚宝科技";
	public static final String TEMPLATE_SMS_HANQIAN_SIGN_NAME = "瀚芊科技";

}
