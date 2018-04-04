package com.yuanshanbao.ad.advertisement.dao;

import java.util.List;

import com.yuanshanbao.ad.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementStrategyDao {

	public int insertAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);
	
	public int updateAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);
	
	public int deleteAdvertisementStrategy(AdvertisementStrategy advertisementStrategy);
	
	public List<AdvertisementStrategy> selectAdvertisementStrategy(AdvertisementStrategy advertisementStrategy, PageBounds pageBounds);
	
	public List<AdvertisementStrategy> selectAdvertisementStrategyByIds(List<Long> advertisementStrategyIds);
	
	public List<AdvertisementStrategy> selectAdvertisementStrategyByFunctionIds(List<Long> functionIds);
	
	
}
