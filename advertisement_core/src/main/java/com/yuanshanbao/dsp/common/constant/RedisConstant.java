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

	public static final String ADVERTISER_BALANCE_COUNT = "advertiser_balance_count" + COMMON_REDIS_PREFIX;
	private static final String ADVERTISER_LAST_BALANCE_COUNT = "advertiser_last_balance_count" + COMMON_REDIS_PREFIX;

	public static final String ORDER_BALANCE_COUNT = "order_balance_count" + COMMON_REDIS_PREFIX;
	private static final String ORDER_LAST_BALANCE_COUNT = "order_last_balance_count" + COMMON_REDIS_PREFIX;

	public static final String QUOTA_COUNT = "quota_count" + COMMON_REDIS_PREFIX;
	public static final String REVERSE_SHOW_COUNT = "reverse_show_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_SHOW_COUNT = "advertisement_show_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT = "advertisement_click_count" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_GET_COUNT = "advertisement_get_count" + COMMON_REDIS_PREFIX;

	public static final String ADVERTISEMENT_CHANNEL = "advertisement_channel" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CHANNEL_AND_ID = "advertisement_channel_and_id" + COMMON_REDIS_PREFIX;

	public static final String ADVERTISEMENT_SHOW_COUNT_PV = "advertisement_show_count_pv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_SHOW_COUNT_UV = "advertisement_show_count_uv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT_PV = "advertisement_click_count_pv" + COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_CLICK_COUNT_UV = "advertisement_click_count_uv" + COMMON_REDIS_PREFIX;
	private static final String PRODUCT_APPLY_COUNT = "product_apply_count" + COMMON_REDIS_PREFIX;

	public static final String ADVERTISEMENT_ACTIVITY_SHOW_COUNT_PV = "advertisement_activity_show_count_pv"
			+ COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_ACTIVITY_SHOW_COUNT_UV = "advertisement_activity_show_count_uv"
			+ COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_ACTIVITY_CLICK_COUNT_PV = "advertisement_activity_click_count_pv"
			+ COMMON_REDIS_PREFIX;
	public static final String ADVERTISEMENT_ACTIVITY_CLICK_COUNT_UV = "advertisement_activity_click_count_uv"
			+ COMMON_REDIS_PREFIX;
	private static final String REQUEST_COUNT = "request_stat_count" + COMMON_REDIS_PREFIX;
	private static final String AGENT_NOTIFY_HANDLER_KEY = "agent_notify_handler_key" + COMMON_REDIS_PREFIX;
	private static final String ACTIVITY_COUNT = "activity_count" + COMMON_REDIS_PREFIX;

	// dsp
	public static final String PROBABILITY_SHOW_COUNT = "probability_show_count" + COMMON_REDIS_PREFIX;
	public static final String PROBABILITY_CLICK_COUNT = "probability_click_count" + COMMON_REDIS_PREFIX;
	public static final String PROBABILITY_LAST_SHOW_COUNT = "probability_last_show_count" + COMMON_REDIS_PREFIX;
	public static final String PROBABILITY_LAST_CLICK_COUNT = "probability_last_click_count" + COMMON_REDIS_PREFIX;
	// 具体消耗
	public static final String PROBABILITY_BALANCE_COUNT = "probability_balance_count" + COMMON_REDIS_PREFIX;
	public static final String PROBABILITY_LAST_BALANCE_COUNT = "probability_last_balance_count" + COMMON_REDIS_PREFIX;
	// 计划余额
	public static final String PLAN_BALANCE_COUNT = "plan_balance_count" + COMMON_REDIS_PREFIX;
	public static final String PLAN_LAST_BALANCE_COUNT = "plan_last_balance_count" + COMMON_REDIS_PREFIX;

	// 各个渠道计划的ctr
	public static final String PROBABILITY_CHANNEL_CTR = "probability_channel_ctr" + COMMON_REDIS_PREFIX;

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
	public static String getAdvertisementShowCountPVKey(String date, String advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT_PV, date + "_" + advertisementId + "_" + channel);
	}

	public static String getAdvertisementClickCountPVKey(String date, String advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_CLICK_COUNT_PV, date + "_" + advertisementId + "_" + channel);
	}

	public static String getAdvertisementShowCountUVKey(String date, String advertisementId, String channel) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_SHOW_COUNT_UV, date + "_" + advertisementId + "_" + channel);
	}

	public static String getAdvertisementClickCountUVKey(String date, String advertisementId, String channel) {
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

	public static String getAdvertisementActivityClickCountUVKey(String date, String advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_CLICK_COUNT_UV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getAdvertisementActivityClickCountPVKey(String date, String advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_CLICK_COUNT_PV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getAdvertisementActivityShowCountUVKey(String date, String advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_SHOW_COUNT_UV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getAdvertisementActivityShowCountPVKey(String date, String advertisementId, String activityKey) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISEMENT_ACTIVITY_SHOW_COUNT_PV, date + "_" + advertisementId + "_" + activityKey);
	}

	public static String getRequestCountKey(String channel) {
		return getRequestCountKey(DateUtils.format(new Date()), channel);
	}

	private static String getRequestCountKey(String date, String channel) {
		return getCachePrefix(REQUEST_COUNT, date + "_" + channel);
	}

	// 广告主余额消耗
	public static String getAdvertiserBalanceCountKey(String date, Long advertiserId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISER_BALANCE_COUNT, date + "_" + advertiserId + "");
	}

	public static String getAdvertiserLastBalanceCountKey(String date, Long advertiserId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ADVERTISER_LAST_BALANCE_COUNT, date + "_" + advertiserId + "");
	}

	// 具体消耗
	public static String getProbabilityBalanceCountKey(String date, Long probabilityId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PROBABILITY_BALANCE_COUNT, date + "_" + probabilityId + "");
	}

	public static String getProbabilityLastBalanceCountKey(String date, Long probabilityId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PROBABILITY_LAST_BALANCE_COUNT, date + "_" + probabilityId + "");
	}

	// 订单余额消耗
	public static String getOrderBalanceCountKey(String date, Long orderId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ORDER_BALANCE_COUNT, date + "_" + orderId + "");
	}

	public static String getOrderLastBalanceCountKey(String date, Long orderId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(ORDER_LAST_BALANCE_COUNT, date + "_" + orderId + "");
	}

	// 计划余额消耗
	public static String getPlanBalanceCountKey(Long planId) {
		return getCachePrefix(PLAN_BALANCE_COUNT, planId + "");
	}

	public static String getPlanLastBalanceCountKey(String date, Long orderId) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PLAN_LAST_BALANCE_COUNT, date + "_" + orderId + "");
	}

	// -------------------------------------------------------------------------
	public static String getProbabilityClickCountKey(String date, Long probability) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PROBABILITY_CLICK_COUNT, date + "_" + probability + "");
	}

	public static String getProbabilityLastClickCountKey(String date, Long probability) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PROBABILITY_LAST_CLICK_COUNT, date + "_" + probability + "");
	}

	// -------------------------------------------------------------------------
	public static String getProbabilityShowCountKey(String date, Long probability) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PROBABILITY_SHOW_COUNT, date + "_" + probability + "");
	}

	public static String getProbabilityLastShowCountKey(String date, Long probability) {
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date());
		}
		return getCachePrefix(PROBABILITY_LAST_SHOW_COUNT, date + "_" + probability + "");
	}

	public static String getApplyShowCountKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getProductShowCountKey(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getProductApplyCountKey(Long productId) {
		return getCachePrefix(PRODUCT_APPLY_COUNT, productId + "");
	}

	public static String getAgentNotifyHandlerKey(String mobile) {
		return getCachePrefix(AGENT_NOTIFY_HANDLER_KEY, mobile);
	}

	// 福利领取数量
	public static String getAdvertisementGetCountKey(Long advertisementId) {
		return getCachePrefix(ADVERTISEMENT_GET_COUNT, advertisementId + "");
	}

	public static String getActivityCountKey(String key) {
		return getCachePrefix(ACTIVITY_COUNT, key);
	}

	public static String getReverseShowCountKey(String key, String activityKey) {
		return getCachePrefix(REVERSE_SHOW_COUNT, key + "_" + activityKey);
	}

	public static String getProbabilityChannelCTRKey(Long probabilityId) {
		return getCachePrefix(PROBABILITY_CHANNEL_CTR, probabilityId + "");
	}
}
