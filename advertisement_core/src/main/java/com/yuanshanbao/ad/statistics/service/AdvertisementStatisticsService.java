package com.yuanshanbao.ad.statistics.service;

import java.util.List;

import com.yuanshanbao.ad.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.ad.statistics.model.SuccessPageClick;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementStatisticsService {

	public void insertAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public void updateAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public void deleteAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public List<AdvertisementStatistics> selectAdvertisementStatistics(AdvertisementStatistics advertisementStatistics, PageBounds pageBounds);

	public void runAndInsertAdvertisementStatistics(int diffDay);

	public List<AdvertisementStatistics> selectAdvertisementStatisticsByLists(int diffDate, Boolean pv, List<Long> advertisementIds, List<String> channels);

	public List<AdvertisementStatistics> selectAdvertisementStatisticsByChannels(int diffDate, Boolean pv, List<String> channels);

	public List<AdvertisementStatistics> selectAdvertisementStatistic(int diffDate, Boolean pv, Long advertisementId);

	public List<AdvertisementStatistics> selectChannelAdvertisementStatistic(int diffDate, Boolean pv, String channelKey);

	public List<SuccessPageClick> selectSuccessPageClicks(int dateDiff, boolean fromDB);
}
