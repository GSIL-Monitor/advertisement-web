package com.yuanshanbao.dsp.statistics.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.statistics.model.Statistics;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class StatisticsDaoImpl extends BaseDaoImpl implements StatisticsDao {

	@Override
	public int insertStatistics(Statistics statistics) {
		return getSqlSession().insert("Statistics.insertStatistics", statistics);
	}

	@Override
	public int updateStatistics(Statistics statistics) {
		return getSqlSession().update("Statistics.updateStatistics", statistics);
	}

	@Override
	public int deleteStatistics(Statistics statistics) {
		return getSqlSession().delete("Statistics.deleteStatistics", statistics);
	}

	@Override
	public List<Statistics> selectStatistics(Statistics statistics, PageBounds pageBounds) {
		return getSqlSession().selectList("Statistics.selectStatistics", statistics, pageBounds);
	}

	@Override
	public List<Statistics> selectStatisticsByIds(List<Long> statisticsIds) {
		return getSqlSession().selectList("Statistics.selectStatisticsByIds", statisticsIds);
	}

}
