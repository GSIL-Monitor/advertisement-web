package com.yuanshanbao.ad.statistics.dao;

import java.util.List;

import com.yuanshanbao.ad.statistics.model.Statistics;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface StatisticsDao {

	public int insertStatistics(Statistics statistics);

	public int updateStatistics(Statistics statistics);

	public int deleteStatistics(Statistics statistics);

	public List<Statistics> selectStatistics(Statistics statistics, PageBounds pageBounds);

	public List<Statistics> selectStatisticsByIds(List<Long> statisticsIds);

}
