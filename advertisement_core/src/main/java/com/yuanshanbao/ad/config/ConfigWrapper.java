package com.yuanshanbao.ad.config;

public class ConfigWrapper extends ConfigManager {

	public static boolean hasIndexNoticeCode(String appKey, String channel) {
		return ConfigConstants.TRUE.equals(ConfigManager.getConfigValue(channel, appKey,
				ConfigConstants.HAS_INDEX_NOTICE_CONFIG));
	}
}
