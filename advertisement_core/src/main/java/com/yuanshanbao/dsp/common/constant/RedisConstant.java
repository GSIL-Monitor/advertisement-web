package com.yuanshanbao.dsp.common.constant;

import java.util.Date;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.cache.IniCache;

/**
 * Created by Singal
 */
public class RedisConstant {
	// redis
	public static String REDIS_DEFAULT_CHARSET = IniCache.getIniValue(IniConstant.REDIS_DEFAULT_CHARSET, "UTF-8");
	public final static String REDIS_DEFAULT_OK = "ok";
	public final static Integer REDIS_DEFAULT_RESULT = 1;
	public final static Long REDIS_LAST_INDEX = -1L;

	private static final String COMMON_REDIS_PREFIX = "_$%^_";

	public final static String PREFIX_LOGIN_TOKEN = "advertisement_loginToken:";// token缓存
	public final static String PREFIX_TEMP_TOKEN = "advertisement_tempToken:";// token缓存
	public final static String PREFIX_USER_ID = "advertisement_user_userid:";// userId缓存
	public final static String PREFIX_MOBILE = "advertisement_user_mobile:";// mobile缓存
	public final static String PREFIX_OPEN_ID = "advertisement_user_weixinid:";// weixinId缓存
	public final static String PREFIX_UNIQUE_TOKEN = "advertisement_unique_token:";// 防止重复提交的token
	public final static String PREFIX_LOG_START = "advertisement_log:start:";
	public final static String PREFIX_LOG_HITS = "advertisement_log:hits:";

	public static final String ADVERTISEMENT_SHOW_COUNT = "advertisement_show_count:";
	public static final String APPLY_SHOW_COUNT = "apply_show_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT = "advertisement_click_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_PV_COUNT = "advertisement_click_count_pv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CHANNEL = "advertisement_channel" + COMMON_REDIS_PREFIX;
	public static final String UV_COUNT = "uv_stat_count" + COMMON_REDIS_PREFIX;
	public static final String RESULT_PAGE_CHANNEL = "result_page_channel" + COMMON_REDIS_PREFIX;

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

	public static String getAdvertisementShowCountKey(String key) {
		return new StringBuffer().append(ADVERTISEMENT_SHOW_COUNT).append(key).toString();
	}

	public static String getApplyShowCountKey() {
		return new StringBuffer().append(APPLY_SHOW_COUNT).toString();
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
		return getAdvertisementClickCountKey(DateUtils.format(new Date()), function, id);
	}

	public static String getAdvertisementClickCountKey(String date, String function, String id) {
		return getCachePrefix(ADVERTISEMENT_CLICK_COUNT, date + "_" + function + "_" + id);
	}

	public static String getAdvertisementChannelClickCountKey(String function, String id, String channel) {
		return getAdvertisementChannelClickCountKey(DateUtils.format(new Date()), function, id, channel);
	}

	public static String getAdvertisementChannelClickCountKey(String date, String function, String id, String channel) {
		return getAdvertisementClickCountKey(date, function, id) + "_" + channel;
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
	

}
