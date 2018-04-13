package com.yuanshanbao.dsp.statistics.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementStatisticsDao {

	public int insertAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public int updateAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public int deleteAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public List<AdvertisementStatistics> selectAdvertisementStatistics(AdvertisementStatistics advertisementStatistics, PageBounds pageBounds);
	
	public List<AdvertisementStatistics> selectAdvertisementStatisticsByAdvertisementIds(Map<String, Object> map);

	public List<AdvertisementStatistics> selectAdvertisementStatisticsByChannels(Map<String, Object> map);
	
}
