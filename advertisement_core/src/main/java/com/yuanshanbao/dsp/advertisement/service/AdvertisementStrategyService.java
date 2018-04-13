package com.yuanshanbao.dsp.advertisement.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementStrategyService {

	public void insertAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public void updateAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public void deleteAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public List<AdvertisementStrategy> selectAdvertisementStrategy(AdvertisementStrategy advertisementStrategy,
			PageBounds pageBounds);

	public Map<Long, AdvertisementStrategy> selectAdvertisementStrategyByIds(List<Long> advertiementStrategyIds);

	public AdvertisementStrategy selectAdvertisementStrategyById(Long advertisementStrategyId);

	public Map<Long, List<AdvertisementStrategy>> selectStrategyByFunctionIds(List<Long> functionIds);

	/*public List<AdvertisementStrategy> selectAdvertisementStrategy(Long activityId, String channel);*/
}
