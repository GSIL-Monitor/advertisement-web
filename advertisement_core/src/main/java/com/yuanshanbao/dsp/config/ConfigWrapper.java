package com.yuanshanbao.dsp.config;

import org.apache.commons.lang3.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;

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
}
