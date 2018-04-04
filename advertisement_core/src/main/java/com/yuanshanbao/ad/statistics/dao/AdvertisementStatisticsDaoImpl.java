package com.yuanshanbao.ad.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class AdvertisementStatisticsDaoImpl extends BaseDaoImpl implements AdvertisementStatisticsDao {

	@Override
	public int insertAdvertisementStatistics(AdvertisementStatistics advertisementStatistics) {
		return getSqlSession().insert("advertisementStatistics.insertAdvertisementStatistics", advertisementStatistics);
	}

	@Override
	public int updateAdvertisementStatistics(AdvertisementStatistics advertisementStatistics) {
		return getSqlSession().update("advertisementStatistics.updateAdvertisementStatistics", advertisementStatistics);
	}

	@Override
	public int deleteAdvertisementStatistics(AdvertisementStatistics advertisementStatistics) {
		return getSqlSession().delete("advertisementStatistics.deleteAdvertisementStatistics", advertisementStatistics);
	}

	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatistics(AdvertisementStatistics advertisementStatistics,
			PageBounds pageBounds) {
		return getSqlSession().selectList("advertisementStatistics.selectAdvertisementStatistics", advertisementStatistics, pageBounds);
	}

	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatisticsByAdvertisementIds(Map<String, Object> map) {
		return getSqlSession().selectList("advertisementStatistics.selectAdvertisementStatisticsByAdvertisementIds", map);
	}
	
	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatisticsByChannels(Map<String, Object> map) {
		return getSqlSession().selectList("advertisementStatistics.selectAdvertisementStatisticsByChannels", map);
	}

}
