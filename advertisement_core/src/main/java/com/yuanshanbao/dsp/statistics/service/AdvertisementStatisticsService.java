package com.yuanshanbao.dsp.statistics.service;

import java.util.List;

import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.dsp.statistics.model.SuccessPageClick;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementStatisticsService {

	public void insertAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public void updateAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public void deleteAdvertisementStatistics(AdvertisementStatistics advertisementStatistics);

	public List<AdvertisementStatistics> selectAdvertisementStatistics(AdvertisementStatistics advertisementStatistics,
			PageBounds pageBounds);

	public void runAndInsertAdvertisementStatistics(int diffDay);

	public void runAndInsertPlanStatistics(int diffDay, Long projectId);

	public List<AdvertisementStatistics> selectAdvertisementStatisticsByLists(int diffDate, Boolean pv,
			List<Long> advertisementIds, List<String> channels);

	public List<AdvertisementStatistics> selectAdvertisementStatisticsByChannels(int diffDate, Boolean pv,
			List<String> channels);

	public List<AdvertisementStatistics> selectAdvertisementStatistic(AdvertisementStatistics advertisementStatistics,
			Boolean pv, Long advertisementId);

	public List<AdvertisementStatistics> selectChannelAdvertisementStatistic(
			AdvertisementStatistics advertisementStatistics, Boolean pv, String channelKey);

	public List<SuccessPageClick> selectSuccessPageClicks(int dateDiff, boolean fromDB);

	public List<AdvertisementStatistics> combineAdvertiserAndPosition(List<AdvertisementStatistics> list);

	public List<AdvertisementStatistics> combineDateAndPosition(List<AdvertisementStatistics> list);

	public List<AdvertisementStatistics> combineAdvertiserAndDate(List<AdvertisementStatistics> list);

	public List<AdvertisementStatistics> calculateStatistics(Long projectId, List<Probability> list, String date);

	public String downStatistics(List<AdvertisementStatistics> list);

	public List<AdvertisementStatistics> selectPlanStatistic(AdvertisementStatistics advertisementStatistics,
			Boolean pv, Long planId, Long projectId);

	public List<AdvertisementStatistics> selectMediaAdvertisementStatistic(
			AdvertisementStatistics advertisementStatistics, Boolean isPv, String channel, Long long1);

	public List<AdvertisementStatistics> selectAdvertiserStatistic(AdvertisementStatistics advertisementStatistics);
}
