package com.yuanshanbao.dsp.advertisement.dao;

import java.util.List;

import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementStrategyDao {

	public int insertAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public int updateAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public int deleteAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);

	public List<AdvertisementStrategy> selectAdvertisementStrategy(AdvertisementStrategy advertisementStrategy,
			PageBounds pageBounds);

	public List<AdvertisementStrategy> selectAdvertisementStrategyByIds(List<Long> advertisementStrategyIds);

	public List<AdvertisementStrategy> selectAdvertisementStrategyByFunctionIds(List<Long> functionIds);

	public List<AdvertisementStrategy> selectPlanStrategy(AdvertisementStrategy advertisementStrategy,
			PageBounds pageBounds);

}
