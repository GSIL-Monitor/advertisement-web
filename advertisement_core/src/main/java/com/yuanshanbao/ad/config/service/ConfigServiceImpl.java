package com.yuanshanbao.ad.config.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.ad.activity.service.ActivityService;
import com.yuanshanbao.ad.channel.service.ChannelService;
import com.yuanshanbao.ad.config.ConfigManager;
import com.yuanshanbao.ad.config.dao.ConfigDao;
import com.yuanshanbao.ad.config.model.Config;
import com.yuanshanbao.ad.config.model.ConfigCategory;
import com.yuanshanbao.ad.config.model.ConfigCategoryType;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigDao configDao;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ChannelService channelService;

	@Override
	public void insertConfig(Config config) {
		int result = -1;

		config.calculateSort();

		result = configDao.insertConfig(config);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateConfig(Config config) {
		int result = -1;

		result = configDao.updateConfig(config);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteConfig(Long configId) {
		int result = -1;

		if (configId == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}
		Config config = new Config();
		config.setConfigId(configId);
		result = configDao.deleteConfig(config);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Config> selectConfig(Config config, PageBounds pageBounds) {
		return setProperty(configDao.selectConfigs(config, pageBounds));
	}

	private List<Config> setProperty(List<Config> list) {
		List<Long> idsList = new ArrayList<Long>();
		List<Long> productIds = new ArrayList<Long>();
		List<Long> merchantIds = new ArrayList<Long>();
		List<Long> activityIds = new ArrayList<Long>();

		for (Config config : list) {
			idsList.add(config.getFunctionId());
			productIds.add(config.getProductId());
			merchantIds.add(config.getMerchantId());
			activityIds.add(config.getActivityId());
		}

		Map<Long, Function> map = functionService.selectFunctionByIds(idsList);
		Map<Long, Merchant> merMap = merchantService.selectMerchantByIds(merchantIds);
		Map<Long, Activity> acMap = activityService.selectActivitys(activityIds);

		for (Config config : list) {
			config.setFunction(map.get(config.getFunctionId()));
			config.setMerchant(merMap.get(config.getMerchantId()));

			config.setActivity(acMap.get(config.getActivityId()));
		}
		return list;
	}

	@Override
	public Config selectConfigById(Long configId) {
		if (configId == null) {
			return null;
		}
		Config config = new Config();
		config.setConfigId(configId);
		List<Config> list = selectConfig(config, new PageBounds());

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Config> selectConfigByIds(List<Long> idsList) {
		return configDao.selectConfigByIds(idsList);
	}

	@Override
	public void insertOrUpdateConfig(Config config) {
		if (config.getConfigId() == null) {
			insertConfig(config);
		} else {
			updateConfig(config);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateActivityConfig(HttpServletRequest request, Long activityId, String channel, String productKey) {
		// 获取function表 key， defaultAction参数。
		Map<String, String> map = ConfigManager.getConfigMap(activityId, channel, productKey, null, null);

		List<String> keys = new ArrayList<String>();
		Iterator i = map.entrySet().iterator();
		// 获取function表所以的key
		while (i.hasNext()) {
			Entry entry = (Entry) i.next();
			keys.add((String) entry.getKey());
		}
		// 获取key的function map
		Map<String, Function> functionMap = functionService.selectFunctionByKeys(keys);
		for (String key : keys) {
			// 取出function中key对应的value；
			String params = request.getParameter(key); // key对应的value
			// value比较不相等，意味著更改
			if (StringUtils.isBlank(params) && map.get(key) == null) {
				continue;
			}
			if (!params.equals(map.get(key))) {
				Config config = new Config();
				config.setActivityId(activityId);
				config.setFunctionId(functionMap.get(key).getFunctionId());
				config.setStatus(CommonStatus.ONLINE);
				config.setChannel(channel);
				List<Config> configList = selectConfig(config, new PageBounds());
				for (Config exist : configList) {
					if (exist.getChannel() == null) {
						if (config.getChannel() == null) {
							config = exist;
						}
					} else if (exist.getChannel().equals(config.getChannel())) {
						config = exist;
					}
				}
				config.setAction(params);
				insertOrUpdateConfig(config);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, ConfigCategory> getConfigCategoryList(Long activityId, String channel, String productKey) {
		// 获取function表 key， defaultAction参数。
		Map<String, String> map = ConfigManager.getConfigMap(activityId, channel, productKey, null, null);

		List<String> keys = new ArrayList<String>();
		Iterator i = map.entrySet().iterator();
		// 获取function表所以的key
		while (i.hasNext()) {// 只遍历一次,速度快
			Entry entry = (Entry) i.next();
			keys.add((String) entry.getKey());
		}
		// for (Entry<String, String> entry : map.entrySet()) {
		//
		// }

		Map<String, Function> functionMap = functionService.selectFunctionByKeys(keys);
		for (String key : keys) {
			Function function = functionMap.get(key);
			String action = map.get(key);
			if (action == null) {
				action = "";
			}
			function.setDefaultAction(action);
		}
		// 获取key的function map
		List<Function> functionList = new ArrayList<Function>();
		// map中value转换为list
		functionList.addAll(functionMap.values());
		// 重新排序。按照category从低到高顺序，若category相同则判断functionId
		Collections.sort(functionList, new Comparator<Function>() {

			@Override
			public int compare(Function o1, Function o2) {
				if (o1.getCategory() > o2.getCategory()) {
					return 1;
				} else if (o1.getCategory() < o2.getCategory()) {
					return -1;
				} else {
					return (int) (o1.getFunctionId() - o2.getFunctionId());
				}
			}
		});

		// 新增category类，类包含 按类型排序后的数据，即 category ，该类型的function list
		//
		Map<Integer, ConfigCategory> categoryMap = new LinkedHashMap<Integer, ConfigCategory>();
		for (Function function : functionList) {
			ConfigCategory categoryList = categoryMap.get(function.getCategory());
			if (categoryList == null) {
				categoryList = new ConfigCategory();
				categoryMap.put(function.getCategory(), categoryList);
			}
			categoryList.setConfigCategory(function.getCategory());
			categoryList.setCategoryName(ConfigCategoryType.getDescription(function.getCategory()));
			categoryList.getFunctionList().add(function);
		}
		return categoryMap;
	}

	@Override
	public Map<Long, String> selectConfigByActivityAndFunctionIds(Long activityId, List<Long> functionIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (activityId == null || functionIds == null || functionIds.size() == 0) {
			return new HashMap<Long, String>();
		}
		map.put("activityId", activityId);
		map.put("list", functionIds);
		List<Config> list = configDao.selectConfigByActivityIdAndFunctionIds(map);
		Map<Long, String> getConfigValueMap = new HashMap<Long, String>();
		for (Config config : list) {
			getConfigValueMap.put(config.getFunctionId(), config.getAction());
		}
		return getConfigValueMap;
	}

	@Override
	public Map<Long, String> selectConfigsDivideByChannel(Long activityId, List<Long> functionIds, String channel) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (activityId == null || functionIds == null || functionIds.size() == 0) {
			return new HashMap<Long, String>();
		}
		map.put("activityId", activityId);
		map.put("list", functionIds);
		List<Config> list = configDao.selectConfigsDivideByChannel(map);
		Map<Long, String> getConfigValueMap = new HashMap<Long, String>();
		for (Config config : list) {
			getConfigValueMap.put(config.getFunctionId(), 0 + ":" + config.getAction());
		}

		if (channel != null) {
			if (channel.equals("")) {
				channel = null;
			}
			map.put("channel", channel);
			list = configDao.selectConfigsDivideByChannel(map);
			for (Config config : list) {
				getConfigValueMap.put(config.getFunctionId(), config.getConfigId() + ":" + config.getAction());
			}
		}
		return getConfigValueMap;
	}

}
