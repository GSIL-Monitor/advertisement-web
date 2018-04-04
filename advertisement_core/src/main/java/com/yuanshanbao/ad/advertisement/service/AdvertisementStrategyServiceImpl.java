package com.yuanshanbao.ad.advertisement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.advertisement.dao.AdvertisementStrategyDao;
import com.yuanshanbao.ad.advertisement.model.Advertisement;
import com.yuanshanbao.ad.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.ad.config.service.FunctionService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertisementStrategyServiceImpl implements AdvertisementStrategyService {

	@Autowired
	private AdvertisementStrategyDao strategyDao;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private FunctionService functionService;

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

	/*@Override
	public List<AdvertisementStrategy> selectAdvertisementStrategy(Long activityId, String channel) {
		List<AdvertisementStrategy> adList = new ArrayList<AdvertisementStrategy>();

		if (activityId == null && StringUtil.isBlank(channel)) {
			return adList;
		}

		AdvertisementStrategy param = new AdvertisementStrategy();
		List<AdvertisementStrategy> list = selectAdvertisementStrategy(param, new PageBounds());

		for (AdvertisementStrategy strategy : list) {
			if (strategy.judge(activityId, channel)) {
				adList.add(strategy);
			}
		}

		return adList;
	}*/

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
}
