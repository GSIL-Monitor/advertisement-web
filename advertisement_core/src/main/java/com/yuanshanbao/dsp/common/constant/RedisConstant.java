package com.yuanshanbao.dsp.common.constant;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.cache.IniCache;

public class RedisConstant {
	// redis
	public static String REDIS_DEFAULT_CHARSET = IniCache.getIniValue(IniConstant.REDIS_DEFAULT_CHARSET, "UTF-8");
	public final static String REDIS_DEFAULT_OK = "ok";
	public final static Integer REDIS_DEFAULT_RESULT = 1;
	public final static Long REDIS_LAST_INDEX = -1L;

	private static final String COMMON_REDIS_PREFIX = "_$_";

	public final static String PREFIX_LOGIN_TOKEN = "advertisement_loginToken:";// token缓存
	public final static String PREFIX_TEMP_TOKEN = "advertisement_tempToken:";// token缓存
	public final static String PREFIX_USER_ID = "advertisement_user_userid:";// userId缓存
	public final static String PREFIX_MOBILE = "advertisement_user_mobile:";// mobile缓存
	public final static String PREFIX_OPEN_ID = "advertisement_user_weixinid:";// weixinId缓存
	public final static String PREFIX_UNIQUE_TOKEN = "advertisement_unique_token:";// 防止重复提交的token
	public final static String PREFIX_LOG_START = "advertisement_log:start:";
	public final static String PREFIX_LOG_HITS = "advertisement_log:hits:";

	public static final String ADVERTISEMENT_CLICK_PV_COUNT = "advertisement_click_count_pv" + COMMON_REDIS_PREFIX;
	public static final String UV_COUNT = "uv_stat_count" + COMMON_REDIS_PREFIX;
	public static final String RESULT_PAGE_CHANNEL = "result_page_channel" + COMMON_REDIS_PREFIX;

	public static final String QUOTA_COUNT = "quota_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_SHOW_COUNT = "advertisement_show_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT = "advertisement_click_count" + COMMON_REDIS_PREFIX;

	public static final String ADVERTISEMENT_CHANNEL = "advertisement_channel" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CHANNEL_AND_ID = "advertisement_channel_and_id" + COMMON_REDIS_PREFIX;

	public static final String ADVERTISEMENT_SHOW_COUNT_PV = "advertisement_show_count_pv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_SHOW_COUNT_UV = "advertisement_show_count_uv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT_PV = "advertisement_click_count_pv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT_UV = "advertisement_click_count_uv" + COMMON_REDIS_PREFIX;

	public static final String ADVERTISEMENT_ACTIVITY_SHOW_COUNT_PV = "advertisement_activity_show_count_pv"
			+ COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_ACTIVITY_SHOW_COUNT_UV = "advertisement_activity_show_count_uv"
			+ COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_ACTIVITY_CLICK_COUNT_PV = "advertisement_activity_click_count_pv"
			+ COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_ACTIVITY_CLICK_COUNT_UV = "advertisement_activity_click_count_uv"
			+ COMMON_REDIS_PREFIX;

	// token过期时间
	public static int EXPIRE_LOGIN_TOKEN = IniCache.getIniIntValue(IniConstant.TOKEN_CACHE_TIME, 24 * 60 * 60);

	public static void refresh() {
	}

	public static String getLoginTokenCacheKey(String token) {
		return new StringBuffer().append(RedisConstant.PREFIX_LOGIN_TOKEN).append(token).toString();
	}

	public static String getTempTokenCacheKey(String token) {
		return new StringBuffer().append(RedisConstant.PREFIX_TEMP_TOKEN).append(token).toString();
	}

	public static String getUserCacheUserIdKey(String userId) {
		return new StringBuffer().append(RedisConstant.PREFIX_USER_ID).append(userId).toString();
	}

	public static String getUserCacheMobileKey(String mobile) {
		return new StringBuffer().append(RedisConstant.PREFIX_MOBILE).append(mobile).toString();
	}

	public static String getUserCacheOpenIdKey(String weixinId) {
		return new StringBuffer().append(RedisConstant.PREFIX_OPEN_ID).append(weixinId).toString();
	}

	public static String getUniqueTokenKey(String uniqueToken) {
		return new StringBuffer().append(RedisConstant.PREFIX_UNIQUE_TOKEN).append(uniqueToken).toString();
	}

	/**
	 * 获取key
	 * 
	 * @return
	 */
	private static String getCachePrefix(String prefix, String key) {
		return prefix + key;
	}

	public static String getAdvertisementClickCountKey(String function, String id) {
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT, DateUtils.format(new Date()) + function + id);
	}

	public static String getAdvertisementChannelClickCountKey(String function, String id, String channel) {
		return getAdvertisementChannelClickCountKey(DateUtils.format(new Date()), function, id, channel);
	}

	public static String getAdvertisementChannelClickCountKey(String date, String function, String id, String channel) {
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT, DateUtils.format(new Date()) + function + id + "_" + channel);
	}

	public static String getAdvertisementClickPVCountKey(String function, String id) {
		return getAdvertisementClickPVCountKey(DateUtils.format(new Date()), function, id);
	}

	public static String getAdvertisementClickPVCountKey(String date, String function, String id) {
		return getCachePrefix(ADVERTISEMENT_CLICK_PV_COUNT, date + "_" + function + "_" + id);
	}

	public static String getAdvertisementChannelClickPVCountKey(String function, String id, String channel) {
		return getAdvertisementChannelClickPVCountKey(DateUtils.format(new Date()), function, id, channel);
	}

	public static String getAdvertisementChannelClickPVCountKey(String date, String function, String id, String channel) {
		return getAdvertisementClickPVCountKey(date, function, id) + "_" + channel;
	}

	public static String getAdvertisementChannelKey() {
		return getAdvertisementChannelKey(DateUtils.format(new Date()));
	}

	public static String getAdvertisementChannelKey(String date) {
		return getCachePrefix(ADVERTISEMENT_CHANNEL, date);
	}

	public static String getResultPageUVCountKey(String position, String channel) {
		return getResultPageUVCountKey(position, DateUtils.format(new Date()), channel);
	}

	public static String getResultPageUVCountKey(String position, String date, String channel) {
		return getCachePrefix(getCachePrefix(position, UV_COUNT), date + "_" + channel);
	}

	public static String getResultPageChannelKey() {
		return getResultPageChannelKey(DateUtils.format(new Date()));
	}

	public static String getResultPageChannelKey(String date) {
		return getCachePrefix(RESULT_PAGE_CHANNEL, date);
	}

	public static String getQuotaCount(Long quotaId) {
		return getCachePrefix(QUOTA_COUNT, quotaId + "");
	}

	public static String getAdvertisementShowCountKey(String date, Long advertisementId, Long positionId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT, date + "_" + advertisementId + "_" + positionId);
	}

	public static String getAdvertisementClickCountKey(String date, Long advertisementId, Long positionId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_CLICK_COUNT, date + "_" + advertisementId + "_" + positionId);
	}

	public static String getAdvertisementShowCountKey(Long advertisementId, Long positionId) {
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT, DateUtils.format(new Date()) + "_" + advertisementId + "_"
				+ positionId);
	}

	public static String getAdvertisementClickCountKey(Long advertisementId, Long positionId) {
		return getCachePrefix(ADVERTISEMENT_CLICK_COUNT, DateUtils.format(new Date()) + "_" + advertisementId + "_"
				+ positionId);
	}

	// -------------------------------------------------------------------------------------------------------------------------------
	public static String getAdvertisementShowCountPVKey(String date, Long advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT_PV, date + "_" + advertisementId + "_" + channel);
	}

	public static String getAdvertisementClickCountPVKey(String date, Long advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_CLICK_COUNT_PV, date + "_" + advertisementId + "_" + channel);
	}

	public static String getAdvertisementShowCountUVKey(String date, Long advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT_UV, date + "_" + advertisementId + "_" + channel);
	}

	public static String getAdvertisementClickCountUVKey(String date, Long advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_CLICK_COUNT_UV, date + "_" + advertisementId + "_" + channel);
	}

	// 渠道下广告
	public static String getAdvertisementChannelAndIdKey() {
		return getAdvertisementChannelAndIdKey(DateUtils.format(new Date()));
	}

	public static String getAdvertisementChannelAndIdKey(String date) {
		return getCachePrefix(ADVERTISEMENT_CHANNEL_AND_ID, date);
	}

	public static String getAdvertisementActivityClickCountUVKey(String date, Long advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_CLICK_COUNT_UV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getAdvertisementActivityClickCountPVKey(String date, Long advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_CLICK_COUNT_PV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getAdvertisementActivityShowCountUVKey(String date, Long advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_SHOW_COUNT_UV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getAdvertisementActivityShowCountPVKey(String date, Long advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_SHOW_COUNT_PV, date + "_" + advertisementId + "_" + activityKey);
	}
}
