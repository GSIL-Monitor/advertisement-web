package com.yuanshanbao.dsp.config;

import org.apache.commons.lang3.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.information.model.Information;

public class ConfigWrapper extends ConfigManager {

	public static boolean hasIndexNoticeCode(String appKey, String channel) {
		return ConfigConstants.TRUE.equals(ConfigManager.getConfigValue(channel, appKey,
				ConfigConstants.HAS_INDEX_NOTICE_CONFIG));
	}

	public static int getPickChance(Long activityId, String channel, Object object, Object object2) {
		String chance = ConfigManager
				.getConfigValue(activityId, channel, null, null, ConfigConstants.PICK_PRIZE_CHANCE);
		if (ValidateUtil.isNumber(chance)) {
			return Integer.parseInt(chance);
		}
		return 1;
	}

	public static String getRedirectUrl(Long activityId, String channel, Object object, Object object2) {
		String redirectUrl = ConfigManager
				.getConfigValue(activityId, channel, null, null, ConfigConstants.REDIRECT_URL);
		if (StringUtils.isNotBlank(redirectUrl)) {
			return redirectUrl;
		}
		return null;
	}

	public static boolean hasVerifyCode(Long activityId, String channel) {
		return ConfigConstants.TRUE.equals(ConfigManager.getConfigValue(activityId, channel,
				ConfigConstants.HAS_VERIFY_CODE));
	}

	public static boolean isSurveyIndex(Long activityId, String channel) {
		return ConfigConstants.INDEX.equals(ConfigManager.getConfigValue(activityId, channel,
				ConfigConstants.SURVEY_POSITION));
	}

	public static int getSurveyCount(Long activityId, String channel, Long merchantId, Long insuranceId) {
		int result = 0;
		String value = ConfigManager.getConfigValue(activityId, channel, merchantId, null, SURVEY_COUNT);
		if (ValidateUtil.isNumber(value)) {
			result = Integer.parseInt(value);
		}
		return result;
	}

	public static Object isSurveyPopup(Long activityId, String channel, Long merchantId, Long insuranceId) {
		return ConfigConstants.POPUP.equals(ConfigManager.getConfigValue(activityId, channel, merchantId, insuranceId,
				ConfigConstants.SURVEY_POSITION));
	}

	public static String getThirdStatCodeConfig(Long activityId, String channel) {
		return ConfigManager.getConfigValue(activityId, channel, ConfigConstants.THIRD_STAT_CODE_CONFIG);
	}

	public static boolean noIdentityCard(Long activityId, String channel, Long merchantId, Long insuranceId) {
		return ConfigConstants.NO.equals(ConfigManager.getConfigValue(activityId, channel, merchantId, insuranceId,
				ConfigConstants.HAS_IDENTITY_CARD));
	}

	public static boolean hasIdentityCard(Long activityId, String channel, Long merchantId, Long insuranceId) {
		return ConfigConstants.TRUE.equals(ConfigManager.getConfigValue(activityId, channel, merchantId, insuranceId,
				ConfigConstants.HAS_IDENTITY_CARD));
	}

	public static boolean isEmailOption(Long activityId, String channel, Long merchantId, Long insuranceId) {
		return ConfigConstants.TRUE.equals(ConfigManager.getConfigValue(activityId, channel, merchantId, insuranceId,
				ConfigConstants.EMAIL_OPTION));
	}

	public static String getSmsTemplate(Information information) {
		return ConfigManager.getConfigValue(information.getActivityId(), information.getChannel(),
				information.getMerchantId(), null, ConfigConstants.SMS_TEMPLATE);
	}

	public static boolean isSendSms(Long activityId, String channel, Long merchantId, Long insuranceId) {
		return ConfigConstants.TRUE.equals(ConfigManager.getConfigValue(activityId, channel, merchantId, insuranceId,
				ConfigConstants.SEND_SMS));
	}
}
