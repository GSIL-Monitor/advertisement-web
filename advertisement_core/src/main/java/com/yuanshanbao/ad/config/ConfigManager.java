package com.yuanshanbao.ad.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.ad.advertisement.model.Advertisement;
import com.yuanshanbao.ad.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.ad.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.ad.channel.model.Channel;
import com.yuanshanbao.ad.config.model.Config;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.ad.config.model.KeyValuePair;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.page.model.Page;

@Service
public class ConfigManager implements ConfigConstants {

	protected static Map<String, Channel> channelMap = new LinkedHashMap<String, Channel>();

	protected static Map<String, Activity> activityMap = new LinkedHashMap<String, Activity>();

	protected static Map<Long, Merchant> merchantMap = new LinkedHashMap<Long, Merchant>();

	protected static Map<String, Page> pageMap = new LinkedHashMap<String, Page>();

	protected static Map<Long, Page> pageIdMap = new LinkedHashMap<Long, Page>();

	protected static Map<Long, Activity> activityIdMap = new LinkedHashMap<Long, Activity>();

	protected static List<Function> functionList = new ArrayList<Function>();

	protected static Map<String, Function> functionMap = new LinkedHashMap<String, Function>();

	protected static List<Config> configList = new ArrayList<Config>();

	protected static Map<String, List<AdvertisementStrategy>> advertisementStrategyMap = new LinkedHashMap<String, List<AdvertisementStrategy>>();

	protected static Map<String, Advertisement> advertisementMap = new LinkedHashMap<String, Advertisement>();

	protected static List<Advertisement> advertisementList = new ArrayList<Advertisement>();

	protected static Map<Long, AdvertisementCategory> advertisementCategoryMap = new LinkedHashMap<Long, AdvertisementCategory>();

	public static void refreshConfig(List<Channel> channels, List<Activity> activitys, List<Merchant> merchants,
			List<Page> pages, List<Function> functions, List<Config> configs, List<Advertisement> advertisements,
			List<AdvertisementStrategy> advertisementStrategies, List<AdvertisementCategory> advertisementCategories) {
		Map<String, Channel> tempChannelMap = new LinkedHashMap<String, Channel>();
		if (channels != null) {
			for (Channel channel : channels) {
				tempChannelMap.put(channel.getKey(), channel);
			}
			channelMap = tempChannelMap;
		}

		Map<String, Activity> tempActivityMap = new LinkedHashMap<String, Activity>();
		Map<Long, Activity> tempActivityIdMap = new LinkedHashMap<Long, Activity>();
		if (activitys != null) {
			for (Activity activity : activitys) {
				tempActivityMap.put(activity.getKey(), activity);
				tempActivityIdMap.put(activity.getActivityId(), activity);
			}
			activityMap = tempActivityMap;
			activityIdMap = tempActivityIdMap;
		}

		Map<String, Page> tempPageMap = new LinkedHashMap<String, Page>();
		Map<Long, Page> tempPageIdMap = new LinkedHashMap<Long, Page>();
		if (activitys != null) {
			for (Page page : pages) {
				tempPageMap.put(page.getKey(), page);
				tempPageIdMap.put(page.getPageId(), page);
			}
			pageMap = tempPageMap;
			pageIdMap = tempPageIdMap;
		}

		Map<Long, Merchant> tempMerchantMap = new LinkedHashMap<Long, Merchant>();
		if (activitys != null) {
			for (Merchant merchant : merchants) {
				tempMerchantMap.put(merchant.getMerchantId(), merchant);
			}
			merchantMap = tempMerchantMap;
		}

		Map<String, Function> tempFunctionMap = new LinkedHashMap<String, Function>();
		if (functions != null) {
			for (Function function : functions) {
				tempFunctionMap.put(function.getKey(), function);
			}

			functionList = functions;
			functionMap = tempFunctionMap;
		}
		Map<String, Advertisement> tempAdvertisementMap = new LinkedHashMap<String, Advertisement>();
		if (advertisements != null) {
			for (Advertisement advertisement : advertisements) {
				tempAdvertisementMap.put(advertisement.getAdvertisementId() + "", advertisement);
			}
			advertisementList = advertisements;
			advertisementMap = tempAdvertisementMap;
		}

		Map<String, List<AdvertisementStrategy>> tempAdvertisementStrategyMap = new LinkedHashMap<String, List<AdvertisementStrategy>>();
		if (advertisementStrategies != null) {
			for (AdvertisementStrategy strategy : advertisementStrategies) {
				List<AdvertisementStrategy> list = tempAdvertisementStrategyMap.get(strategy.getAdvertisementId() + "");
				if (list == null) {
					list = new ArrayList<AdvertisementStrategy>();
					tempAdvertisementStrategyMap.put(strategy.getAdvertisementId() + "", list);
				}
				list.add(strategy);
			}

			advertisementStrategyMap = tempAdvertisementStrategyMap;
		}

		Map<Long, AdvertisementCategory> tempAdvertisementCategoryMap = new LinkedHashMap<Long, AdvertisementCategory>();
		if (advertisementCategories != null) {
			for (AdvertisementCategory category : advertisementCategories) {
				tempAdvertisementCategoryMap.put(category.getCategoryId(), category);
			}
			advertisementCategoryMap = tempAdvertisementCategoryMap;
		}

		if (configs != null) {
			configList = configs;
		}
	}

	public static Channel getChannel(String key) {
		return channelMap.get(key);
	}

	public static void setConfigMap(Map<String, Object> resultMap, Long activityId, String channel, String appKey) {
		setConfigMap(resultMap, activityId, channel, appKey, null, null);
	}

	public static void setConfigMap(Map<String, Object> resultMap, String channel, String appKey) {
		setConfigMap(resultMap, null, channel, appKey, null, null);
	}

	public static void setConfigMap(Map<String, Object> resultMap, Long activityId, String channel, String appKey,
			Long merchantId, Long insuranceId) {
		Map<String, String> configMap = getConfigMap(activityId, channel, appKey, merchantId, insuranceId);
		resultMap.putAll(configMap);
		List<KeyValuePair> allConfigs = new ArrayList<KeyValuePair>();
		for (Entry<String, String> entry : configMap.entrySet()) {
			allConfigs.add(new KeyValuePair(entry.getKey(), entry.getValue()));
		}
		resultMap.put(ALL_CONFIG_IN_PAGE, allConfigs);
	}

	public static Map<String, String> getConfigMap(Long activityId, String channel, String appKey, Long merchantId,
			Long insuranceId) {
		Map<String, String> configMap = new LinkedHashMap<String, String>();
		for (Config config : configList) {
			if (config.isMatchWithoutFunction(activityId, channel, appKey, merchantId, insuranceId)) {
				configMap.put(config.getFunctionKey(), config.getAction());
			}
		}
		for (Function function : functionList) {
			if (configMap.get(function.getKey()) == null) {
				configMap.put(function.getKey(), function.getDefaultAction());
			}
		}
		return configMap;
	}

	public static String getConfigValue(String channel, String appKey, String functionKey) {
		return getConfigValue(null, channel, appKey, null, null, functionKey);
	}

	public static String getConfigValue(Long activityId, String channel, String appKey, String functionKey) {
		return getConfigValue(activityId, channel, appKey, null, null, functionKey);
	}

	public static String getConfigValue(Long activityId, String channel, String appKey, Long merchantId,
			Long insuranceId, String functionKey) {
		Function function = functionMap.get(functionKey);
		if (function == null) {
			return "false";
		}
		String result = function.getDefaultAction();
		for (Config config : configList) {
			if (config.isMatch(activityId, channel, appKey, merchantId, insuranceId, function.getFunctionId())) {
				result = config.getAction();
			}
		}
		return result;
	}

	public static Activity getActivityByKey(String activityKey) {
		return activityMap.get(activityKey);
	}

	public static Page getPageByKey(String pageKey) {
		return pageMap.get(pageKey);
	}

	public static Page getPageById(Long pageId) {
		return pageIdMap.get(pageId);
	}

	public static Merchant getMerchantById(Long merchantId) {
		return merchantMap.get(merchantId);
	}

	public static Activity getActivityById(Long activityId) {
		return activityIdMap.get(activityId);
	}

	/**
	 * ids获取广告List
	 */
	public static List<Advertisement> getAdvertisementList(String ids, Long activity, String channel) {
		List<Advertisement> advertisementList = new ArrayList<Advertisement>();
		if (StringUtils.isBlank(ids)) {
			return advertisementList;
		}
		for (String id : ids.split(",")) {
			Advertisement advertisement = advertisementMap.get(id);
			if (advertisement == null) {
				continue;
			}
			List<AdvertisementStrategy> strategyList = advertisementStrategyMap.get(advertisement.getAdvertisementId()
					+ "");
			boolean judgeResult = true;
			if (strategyList != null) {
				for (AdvertisementStrategy strategy : strategyList) {
					advertisement = strategy.getAdvertisement();
					if (!strategy.judge(activity, channel)) {
						judgeResult = false;
						break;
					}
				}
			}
			if (judgeResult) {
				advertisementList.add(advertisement);
			}
		}
		return advertisementList;
	}

	public static Advertisement getAdvertisement(String id) {
		return advertisementMap.get(id);
	}

	public static AdvertisementCategory getCategory(Long id) {
		return advertisementCategoryMap.get(id);
	}

	public static List<AdvertisementCategory> getCategoryMap() {
		List<AdvertisementCategory> list = new ArrayList<AdvertisementCategory>();
		if (advertisementCategoryMap.size() <= 0) {
			return list;
		} else {
			for (AdvertisementCategory category : advertisementCategoryMap.values()) {
				list.add(category);
			}
		}
		return list;
	}

	public static List<Merchant> getMerchantList() {
		List<Merchant> list = new ArrayList<Merchant>();
		if (merchantMap.size() <= 0) {
			return list;
		} else {
			for (Merchant merchant : merchantMap.values()) {
				list.add(merchant);
			}
		}
		return list;
	}

	public static List<Channel> getChannelList() {
		List<Channel> list = new ArrayList<Channel>();
		if (channelMap.size() <= 0) {
			return list;
		} else {
			for (Channel channel : channelMap.values()) {
				list.add(channel);
			}
		}
		return list;
	}

	public static List<Advertisement> getAdvertisements() {
		return advertisementList;
	}

	public static List<AdvertisementStrategy> getAdvertisementStrategy(String advertisementId) {
		return advertisementStrategyMap.get(advertisementId);
	}

}