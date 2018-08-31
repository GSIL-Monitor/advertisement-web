package com.yuanshanbao.dsp.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.model.Instance;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.config.model.Config;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.model.KeyValuePair;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.page.model.Page;
import com.yuanshanbao.dsp.product.model.ProductCategory;

@Service
public class ConfigManager implements ConfigConstants {

	protected static Map<String, Channel> channelMap = new LinkedHashMap<String, Channel>();

	protected static Map<String, Activity> activityMap = new LinkedHashMap<String, Activity>();

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

	protected static Map<Long, ProductCategory> productCategoryMap = new LinkedHashMap<Long, ProductCategory>();

	public static void refreshConfig(List<Channel> channels, List<Activity> activitys, List<Merchant> merchants,
			List<Page> pages, List<Function> functions, List<Config> configs, List<Advertisement> advertisements,
			List<AdvertisementStrategy> advertisementStrategies, List<AdvertisementCategory> advertisementCategories,
			List<ProductCategory> productCategorys) {
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

		Map<Long, ProductCategory> tempProductCategoryMap = new LinkedHashMap<Long, ProductCategory>();
		if (productCategorys != null) {
			for (ProductCategory category : productCategorys) {
				tempProductCategoryMap.put(category.getProductCategoryId(), category);
			}
			productCategoryMap = tempProductCategoryMap;
		}

		if (configs != null) {
			configList = configs;
		}
	}

	public static void setConfigMap(Map<String, Object> resultMap, Long activityId, String channel) {
		Instance instance = new Instance();
		instance.setActivityId(activityId);
		instance.setChannel(channel);
		setConfigMap(resultMap, instance);
	}

	public static void setConfigMap(Map<String, Object> resultMap, Instance instance) {
		Map<String, String> configMap = getConfigMap(instance.getActivityId(), instance.getChannel(), null, null);
		// setAdvertisementConfig(configMap, instance);
		resultMap.putAll(configMap);
		List<KeyValuePair> allConfigs = new ArrayList<KeyValuePair>();
		for (Entry<String, String> entry : configMap.entrySet()) {
			allConfigs.add(new KeyValuePair(entry.getKey(), entry.getValue()));
		}
		resultMap.put(ALL_CONFIG_IN_PAGE, allConfigs);
	}

	// public static void setAdvertisementConfig(Map<String, String> configMap,
	// Insurant insurant) {
	// try {
	// String channel = "";
	// if (insurant != null && StringUtils.isNotBlank(insurant.getChannel())) {
	// channel = insurant.getChannel();
	// }
	// List<Advertisement> advertisementPromotionList = getAdvertisementList(
	// configMap.get(ConfigConstants.ADVERTISEMENT_PROMOTION_CONFIG), insurant);
	// List<Advertisement> advertisementCalculatorList = getAdvertisementList(
	// configMap.get(ConfigConstants.ADVERTISEMENT_CALCULATOR_CONFIG),
	// insurant);
	// List<Advertisement> advertisementFailList = getAdvertisementList(
	// configMap.get(ConfigConstants.ADVERTISEMENT_FAIL_CONFIG), insurant);
	// List<Advertisement> advertisementInvalidList = getAdvertisementList(
	// configMap.get(ConfigConstants.ADVERTISEMENT_INVALID_CONFIG), insurant);
	// List<Advertisement> advertisementExclusiveList = getAdvertisementList(
	// configMap.get(ConfigConstants.ADVERTISEMENT_EXCLUSIVE_CONFIG), insurant);
	// String failTipTextConfig = "", failTipLinkConfig = "",
	// invalidTipTextConfig = "", invalidTipLinkConfig = "";
	// String surveyPromotionTextConfig = "", surveyPromotionLinkConfig = "",
	// allPromotionTipTextConfig = "", allPromotionTipLinkConfig = "";
	// if (advertisementCalculatorList.size() > 0) {
	// surveyPromotionTextConfig =
	// advertisementCalculatorList.get(0).getTitle();
	// surveyPromotionLinkConfig =
	// advertisementCalculatorList.get(0).getJumperLink(
	// AdvertisementPosition.PROMOTION, channel);
	// configMap.put(ConfigConstants.SURVEY_PROMOTION_TEXT,
	// surveyPromotionTextConfig);
	// configMap.put(ConfigConstants.SURVEY_PROMOTION_LINK,
	// surveyPromotionLinkConfig);
	// } else {
	// configMap.put(ConfigConstants.SURVEY_PROMOTION_TEXT, "");
	// configMap.put(ConfigConstants.SURVEY_PROMOTION_LINK, "");
	// }
	// for (Advertisement advertisement : advertisementPromotionList) {
	// allPromotionTipTextConfig += advertisement.getSubTitle() + ",";
	// allPromotionTipLinkConfig +=
	// advertisement.getJumperLink(AdvertisementPosition.PROMOTION, channel)
	// + ",";
	// }
	// for (Advertisement advertisement : advertisementFailList) {
	// failTipTextConfig += advertisement.getSubTitle() + ",";
	// failTipLinkConfig +=
	// advertisement.getJumperLink(AdvertisementPosition.FAIL, channel) + ",";
	// }
	// for (Advertisement advertisement : advertisementInvalidList) {
	// invalidTipTextConfig += advertisement.getTitle() + ",";
	// invalidTipLinkConfig +=
	// advertisement.getJumperLink(AdvertisementPosition.INVALID, channel) +
	// ",";
	// }
	// for (Advertisement advertisement : advertisementExclusiveList) {
	// failTipTextConfig += advertisement.getSubTitle() + ",";
	// failTipLinkConfig +=
	// advertisement.getJumperLink(AdvertisementPosition.FAIL, channel) + ",";
	// allPromotionTipTextConfig += advertisement.getSubTitle() + ",";
	// allPromotionTipLinkConfig +=
	// advertisement.getJumperLink(AdvertisementPosition.PROMOTION, channel)
	// + ",";
	// }
	// configMap.put(ConfigConstants.ALL_PROMOTION_TIP_TEXT,
	// substringLastChar(allPromotionTipTextConfig));
	// configMap.put(ConfigConstants.ALL_PROMOTION_TIP_LINK,
	// substringLastChar(allPromotionTipLinkConfig));
	// configMap.put(ConfigConstants.FAIL_TIP_TEXT,
	// substringLastChar(failTipTextConfig));
	// configMap.put(ConfigConstants.FAIL_TIP_LINK,
	// substringLastChar(failTipLinkConfig));
	// configMap.put(ConfigConstants.INVALID_TIP_TEXT,
	// substringLastChar(invalidTipTextConfig));
	// configMap.put(ConfigConstants.INVALID_TIP_LINK,
	// substringLastChar(invalidTipLinkConfig));
	// } catch (Exception e) {
	// LoggerUtil.error("[setAdvertisementConfig]", e);
	// }
	// }

	public static Channel getChannel(String key) {
		return channelMap.get(key);
	}

	public static String getConfigValue(Long activityId, String channel, String functionKey) {
		return getConfigValue(activityId, channel, null, null, functionKey);
	}

	public static String getConfigValue(Long activityId, String channel, Long merchantId, Long insuranceId,
			String functionKey) {
		Function function = functionMap.get(functionKey);
		if (function == null) {
			return "false";
		}
		String result = function.getDefaultAction();
		for (Config config : configList) {
			if (config.isMatch(activityId, channel, merchantId, insuranceId, function.getFunctionId())) {
				result = config.getAction();
			}
		}
		return result;
	}

	public static void setConfigMap(Map<String, Object> resultMap, Long activityId, String channel, Long merchantId,
			Long insuranceId) {
		Map<String, String> configMap = getConfigMap(activityId, channel, merchantId, insuranceId);
		// setAdvertisementConfig(configMap, null);
		resultMap.putAll(configMap);
		List<KeyValuePair> allConfigs = new ArrayList<KeyValuePair>();
		for (Entry<String, String> entry : configMap.entrySet()) {
			allConfigs.add(new KeyValuePair(entry.getKey(), entry.getValue()));
		}
		resultMap.put(ALL_CONFIG_IN_PAGE, allConfigs);
	}

	public static Map<String, String> getConfigMap(Long activityId, String channel, Long merchantId, Long insuranceId) {
		Map<String, String> configMap = new LinkedHashMap<String, String>();
		for (Config config : configList) {
			if (config.isMatchWithoutFunction(activityId, channel, merchantId, insuranceId)) {
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

	/**
	 * ids获取产品List
	 */
	public static List<ProductCategory> getProductCategoryList(String ids) {
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		if (StringUtils.isBlank(ids)) {
			return productCategoryList;
		}
		for (String id : ids.split(",")) {
			if (!ValidateUtil.isNumber(id)) {
				continue;
			}
			ProductCategory productCategory = productCategoryMap.get(Long.parseLong(id));
			if (productCategory == null) {
				continue;
			}
			productCategoryList.add(productCategory);
		}
		return productCategoryList;
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

	public static ProductCategory getProductCategory(Long id) {
		return productCategoryMap.get(id);
	}

}