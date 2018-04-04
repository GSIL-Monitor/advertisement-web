package com.yuanshanbao.ad.statistics.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.statistics.model.Statistics;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface StatisticsService {

	public void insertStatistics(Statistics statistics);

	public void updateStatistics(Statistics statistics);

	public void deleteStatistics(Statistics statistics);

	public List<Statistics> selectStatistics(Statistics statistics, PageBounds pageBounds);

	public Statistics selectStatisticsById(Long statisticsId);

	public List<Statistics> selectStatisticsByIds(List<Long> statisticsIds);

	public void runAndInsertStatistics(int diffDay);

	public void runAndInsertProductStatistics(int diffDay);

	public List<Statistics> intervalProductStatistics(int diffDay, boolean fromDB);

	public List<Statistics> getRealtimeStatistics(List<String> channelList);

	public Map<String, Statistics> selectApplyStatistics(Map<String, Object> map);

	public Map<String, Statistics> selectDownloadStatistics(Map<String, Object> map);

	public Map<String, Statistics> selectApplyUserStatistics(Map<String, Object> map);

	public Map<String, Statistics> selectFirstLoginStatistics(Map<String, Object> map);

	public List<Statistics> selectMonthStatistics(List<String> channelList, String date);

	public List<Statistics> intervalStatistics(int diffDay, boolean fromDB);

	public List<Statistics> intervalStatistics(List<String> channelList, int diffDay, boolean fromDB,
			boolean hasAllStatistics);

}
