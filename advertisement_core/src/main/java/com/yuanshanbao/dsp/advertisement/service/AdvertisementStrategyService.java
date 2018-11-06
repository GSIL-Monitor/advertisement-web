package com.yuanshanbao.dsp.advertisement.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.model.MediaInformation;
import com.yuanshanbao.dsp.probability.model.Probability;
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

	/*
	 * public List<AdvertisementStrategy> selectAdvertisementStrategy(Long
	 * activityId, String channel);
	 */

	public List<AdvertisementStrategy> selectAdvertisementStrategyFromCache(Long projectId);

	public List<Long> getAvailableAdvertisementList(List<Long> advertisementIdList,
			List<AdvertisementStrategy> stagtegyList, MediaInformation instrance);

	public List<Probability> getAvailableProbabilityList(HttpServletRequest request, List<Probability> list,
			MediaInformation mediaInformation);

	public void updatePlanStrategy(HttpServletRequest request, Long probabilityId, Long advertiserId);

	void insertOrUpdateAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public Object selectPlanStrategy(AdvertisementStrategy strategy, PageBounds pageBounds);
}
