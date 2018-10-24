package com.yuanshanbao.dsp.advertisement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.dsp.advertisement.dao.AdvertisementStrategyDao;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategyStatus;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategyType;
import com.yuanshanbao.dsp.advertisement.model.Instance;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.service.IpLocationService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertisementStrategyServiceImpl implements AdvertisementStrategyService {

	@Autowired
	private AdvertisementStrategyDao strategyDao;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private IpLocationService ipLocationService;

	@Override
	public void insertAdvertisementStrategy(AdvertisementStrategy advertisementStrategy) {

		int result = -1;

		result = strategyDao.insertAdvertisementStrategy(advertisementStrategy);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateAdvertisementStrategy(AdvertisementStrategy advertisementStrategy) {

		int result = -1;

		result = strategyDao.updateAdvertisementStrategy(advertisementStrategy);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteAdvertisementStrategy(AdvertisementStrategy advertisementStrategy) {

		int result = -1;

		result = strategyDao.deleteAdvertisementStrategy(advertisementStrategy);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void insertOrUpdateAdvertisementStrategy(AdvertisementStrategy advertisementStrategy) {
		if (advertisementStrategy.getAdvertisementStrategyId() == null) {
			insertAdvertisementStrategy(advertisementStrategy);
		} else {
			updateAdvertisementStrategy(advertisementStrategy);
		}
	}

	@Override
	public List<AdvertisementStrategy> selectAdvertisementStrategy(AdvertisementStrategy advertisementStrategy,
			PageBounds pageBounds) {
		return setProperty(strategyDao.selectAdvertisementStrategy(advertisementStrategy, pageBounds));
	}

	private List<AdvertisementStrategy> setProperty(List<AdvertisementStrategy> list) {
		List<Long> advertisementIds = new ArrayList<Long>();
		List<Long> functionIds = new ArrayList<Long>();
		for (AdvertisementStrategy strategy : list) {
			advertisementIds.add(strategy.getAdvertisementId());
			functionIds.add(strategy.getFunctionId());
		}

		Map<Long, Advertisement> map = advertisementService.selectAdvertisementByIds(advertisementIds);
		Map<Long, Function> functionMap = functionService.selectFunctionByIds(functionIds);
		for (AdvertisementStrategy strategy : list) {
			strategy.setAdvertisement(map.get(strategy.getAdvertisementId()));
			strategy.setFunction(functionMap.get(strategy.getFunctionId()));
		}
		return list;
	}

	@Override
	public Map<Long, AdvertisementStrategy> selectAdvertisementStrategyByIds(List<Long> advertiementStrategyIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdvertisementStrategy selectAdvertisementStrategyById(Long advertisementStrategyId) {
		if (advertisementStrategyId == null) {
			return null;
		}
		AdvertisementStrategy advertisementStrategy = new AdvertisementStrategy();
		advertisementStrategy.setAdvertisementStrategyId(advertisementStrategyId);
		List<AdvertisementStrategy> list = selectAdvertisementStrategy(advertisementStrategy, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/*
	 * @Override public List<AdvertisementStrategy>
	 * selectAdvertisementStrategy(Long activityId, String channel) {
	 * List<AdvertisementStrategy> adList = new
	 * ArrayList<AdvertisementStrategy>();
	 * 
	 * if (activityId == null && StringUtil.isBlank(channel)) { return adList; }
	 * 
	 * AdvertisementStrategy param = new AdvertisementStrategy();
	 * List<AdvertisementStrategy> list = selectAdvertisementStrategy(param, new
	 * PageBounds());
	 * 
	 * for (AdvertisementStrategy strategy : list) { if
	 * (strategy.judge(activityId, channel)) { adList.add(strategy); } }
	 * 
	 * return adList; }
	 */

	@Override
	public Map<Long, List<AdvertisementStrategy>> selectStrategyByFunctionIds(List<Long> functionIds) {
		Map<Long, List<AdvertisementStrategy>> map = new HashMap<Long, List<AdvertisementStrategy>>();
		if (functionIds == null || functionIds.size() == 0) {
			return map;
		}
		List<AdvertisementStrategy> list = strategyDao.selectAdvertisementStrategyByFunctionIds(functionIds);

		for (AdvertisementStrategy str : list) {
			List<AdvertisementStrategy> strList = map.get(str.getFunctionId());
			if (strList == null) {
				strList = new ArrayList<AdvertisementStrategy>();
				map.put(str.getFunctionId(), strList);
			}
			strList.add(str);
		}
		return map;
	}

	@Override
	public List<AdvertisementStrategy> selectAdvertisementStrategyFromCache(Long projectId) {
		List<AdvertisementStrategy> resultList = new ArrayList<AdvertisementStrategy>();
		if (projectId == null) {
			return resultList;
		}
		List<AdvertisementStrategy> strategyList = ConstantsManager.getStrategyList(projectId);
		if (strategyList == null) {
			return resultList;
		}
		resultList = strategyList;
		return resultList;
	}

	@Override
	public List<Probability> getAvailableProbabilityList(HttpServletRequest request, List<Probability> list) {
		List<AdvertisementStrategy> ipRegionList = new ArrayList<AdvertisementStrategy>();
		List<AdvertisementStrategy> deviceTypeList = new ArrayList<AdvertisementStrategy>();
		List<Probability> resultList = new ArrayList<Probability>();
		// 针对计划进行处理
		for (Probability prob : list) {
			// 取出广告策略
			List<AdvertisementStrategy> advertisementStrategyList = ConfigManager.getAdvertisementStrategy(prob
					.getAdvertisementId() + "");
			for (AdvertisementStrategy as : advertisementStrategyList) {
				// 如果策略不属于该计划
				if (!prob.getProbabilityId().equals(as.getProbabilityId())) {
					break;
				}
				ipRegionList = new ArrayList<AdvertisementStrategy>();
				deviceTypeList = new ArrayList<AdvertisementStrategy>();
				if (as.getType() != null) {
					if (as.getType().equals(AdvertisementStrategyType.IP_REGION)) {
						ipRegionList.add(as);
					} else if (as.getType().equals(AdvertisementStrategyType.DEVICETYPE)) {
						deviceTypeList.add(as);
					}
				}
			}
			boolean strategyPass = judgeStrategy(request, ipRegionList, deviceTypeList);
			if (strategyPass) {
				resultList.add(prob);
			}
		}
		return resultList;
	}

	private boolean judgeStrategy(HttpServletRequest request, List<AdvertisementStrategy> ipRegionList,
			List<AdvertisementStrategy> deviceTypeList) {
		if (checkIpRegion(request, ipRegionList) && checkDeviceType(request, deviceTypeList)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkDeviceType(HttpServletRequest request, List<AdvertisementStrategy> list) {
		boolean strategyPass = true;
		if (list != null) {
			for (AdvertisementStrategy advertisementStrategy : list) {

			}
		}
		return strategyPass;
	}

	private boolean checkIpRegion(HttpServletRequest request, List<AdvertisementStrategy> list) {
		boolean strategyPass = true;
		if (list != null) {
			for (AdvertisementStrategy advertisementStrategy : list) {
				if (advertisementStrategy.getType().equals(AdvertisementStrategyType.REGION)) {
					Location location = ipLocationService.queryIpLocation(JSPHelper.getRemoteAddr(request));
					Location configLocation = ConstantsManager.getLocationByCode(advertisementStrategy.getValue());
					// 配置了该地域，该地域即可通过
					if (location != null && configLocation != null && !configLocation.contains(location.getCode())) {
						strategyPass = false;
						break;
					}
				}
			}
		}
		return strategyPass;
	}

	@Override
	public List<Long> getAvailableAdvertisementList(List<Long> advertisementIdList,
			List<AdvertisementStrategy> strategyList, Instance instrance) {
		List<Long> result = new ArrayList<Long>();
		List<Long> ageList = new ArrayList<Long>();
		List<Long> regionList = new ArrayList<Long>();
		List<Long> hasStrategyList = new ArrayList<Long>();
		List<AdvertisementStrategy> ageStrategyList = new ArrayList<AdvertisementStrategy>();
		List<AdvertisementStrategy> regionStrategyList = new ArrayList<AdvertisementStrategy>();
		List<AdvertisementStrategy> deviceTypeList = new ArrayList<AdvertisementStrategy>();
		List<AdvertisementStrategy> ipRegionList = new ArrayList<AdvertisementStrategy>();

		// 获取已经配置过策略的广告
		for (AdvertisementStrategy strategy : strategyList) {
			if (!hasStrategyList.contains(strategy.getAdvertisementId())) {
				hasStrategyList.add(strategy.getAdvertisementId());
			}
			if (strategy.getType() != null && strategy.getType().equals(AdvertisementStrategyType.AGE)) {
				ageStrategyList.add(strategy);
			} else if (strategy.getType() != null && strategy.getType().equals(AdvertisementStrategyType.REGION)) {
				regionStrategyList.add(strategy);
			} else if (strategy.getType() != null && strategy.getType().equals(AdvertisementStrategyType.DEVICETYPE)) {
				deviceTypeList.add(strategy);
			} else if (strategy.getType() != null && strategy.getType().equals(AdvertisementStrategyType.IP_REGION)) {
				deviceTypeList.add(strategy);
			}
		}

		// 若没有配置过广告策略，则不进行屏蔽
		for (Long advertisementId : advertisementIdList) {
			if (!hasStrategyList.contains(advertisementId)) {
				result.add(advertisementId);
			}
		}

		// 符合年龄的广告
		for (Long advertisementId : hasStrategyList) {
			for (AdvertisementStrategy strategy : ageStrategyList) {
				// 首先判断年龄
				if (strategy.getAdvertisementId().equals(advertisementId)) {
					if (instrance.getAge() != null
							&& judgeAge(strategy.getValue(), Integer.valueOf(instrance.getAge()))) {
						ageList.add(advertisementId);
					} else {
						continue;
					}
				}
			}
		}

		// 符合地域限制的广告
		for (Long advertisementId : hasStrategyList) {
			// 判断地域
			for (AdvertisementStrategy strategy : regionStrategyList) {
				if (strategy.getAdvertisementId().equals(advertisementId)) {
					if (strategy.getFlag() == 1) {
						if (judgeRegionForDisplay(strategy.getValue(), instrance.getProvince(), instrance.getCity())) {
							regionList.add(advertisementId);
						}
					} else if (strategy.getFlag() == 0) {
						if (judgeRegionForDisplay(strategy.getValue(), instrance.getProvince(), instrance.getCity())) {
							continue;
						} else {
							regionList.add(advertisementId);
						}
					}
				}
			}
		}
		ageList.addAll(regionList);
		List<Long> newList = new ArrayList<Long>(new HashSet<Long>(ageList));
		result.addAll(newList);
		return result;
	}

	// 配置了该城市就显示
	private boolean judgeRegionForDisplay(String value, String province, String city) {
		if (StringUtils.isNotBlank(province)) {
			if (!value.contains(province)) {
				// 如果市为空，省不相等就返回false，如果市不为空，但value不包含市，也返回错误
				if (StringUtils.isBlank(city) || !value.contains(city)) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean judgeAge(String value, int age) {
		String[] ages = value.split("-");
		if (ages.length < 2) {
			return false;
		}
		int min = Integer.valueOf(ages[0]);
		int max = Integer.valueOf(ages[1]);
		if (age < min || age > max) {
			return false;
		}
		return true;
	}

	@Override
	public void updateProbabilityStrategy(HttpServletRequest request, Long probabilityId, Long advertiserId) {
		Map<String, String> map = ConfigManager.getStrategyMap(probabilityId);
		Map<Integer, String> keysMap = AdvertisementStrategyType.getStrategyKeyMap();
		Collection<String> keys = keysMap.values();
		for (String key : keys) {
			String params = request.getParameter(key); // key对应的value
			// value比较不相等,被更改
			if (StringUtils.isBlank(params) && map.get(key) == null) {
				continue;
			}
			if (!params.equals(map.get(key))) {
				AdvertisementStrategy strategy = new AdvertisementStrategy();
				strategy.setProbabilityId(probabilityId);
				strategy.setKey(key);
				List<AdvertisementStrategy> strategyList = selectAdvertisementStrategy(strategy, new PageBounds());
				for (AdvertisementStrategy exist : strategyList) {
					strategy = exist;
				}
				strategy.setValue(params);
				strategy.setStatus(AdvertisementStrategyStatus.UNREVIEWED);
				if (advertiserId != null) {
					strategy.setAdvertiserId(advertiserId);
				}
				insertOrUpdateAdvertisementStrategy(strategy);
			}
		}
	}
}
