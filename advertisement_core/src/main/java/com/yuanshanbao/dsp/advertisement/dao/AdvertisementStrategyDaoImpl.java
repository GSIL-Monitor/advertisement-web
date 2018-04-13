package com.yuanshanbao.dsp.advertisement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class AdvertisementStrategyDaoImpl extends BaseDaoImpl implements AdvertisementStrategyDao {

	@Override
	public int insertAdvertisementStrategy(
			AdvertisementStrategy advertisementStrategy) {
		return getSqlSession().insert("advertisementStrategy.insertAdvertisementStrategy", advertisementStrategy);
	}

	@Override
	public int updateAdvertisementStrategy(
			AdvertisementStrategy advertisementStrategy) {
		return getSqlSession().update("advertisementStrategy.updateAdvertisementStrategy", advertisementStrategy);				
	}

	@Override
	public int deleteAdvertisementStrategy(
			AdvertisementStrategy advertisementStrategy) {
		return getSqlSession().delete("advertisementStrategy.updateAdvertisementStrategy", advertisementStrategy);				
	}

	@Override
	public List<AdvertisementStrategy> selectAdvertisementStrategy(
			AdvertisementStrategy advertisementStrategy, PageBounds pageBounds) {
		return getSqlSession().selectList("advertisementStrategy.selectAdvertisementStrategy", advertisementStrategy, pageBounds);
	}

	@Override
	public List<AdvertisementStrategy> selectAdvertisementStrategyByIds(
			List<Long> advertisementStrategyIds) {
		if (advertisementStrategyIds == null 
				|| advertisementStrategyIds.size() == 0) {
			return new ArrayList<AdvertisementStrategy>();
		}
		return getSqlSession().selectList("advertisementStrategy.selectAdvertisementStrategyByIds", advertisementStrategyIds);
	}

	@Override
	public List<AdvertisementStrategy> selectAdvertisementStrategyByFunctionIds(
			List<Long> functionIds) {
		if (functionIds == null 
				|| functionIds.size() == 0) {
			return new ArrayList<AdvertisementStrategy>();
		}
		return getSqlSession().selectList("advertisementStrategy.selectAdvertisementStrategyByFunctionIds", functionIds);
	}

}
