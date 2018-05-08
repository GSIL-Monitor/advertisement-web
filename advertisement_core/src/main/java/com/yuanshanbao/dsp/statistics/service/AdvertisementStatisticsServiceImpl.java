package com.yuanshanbao.dsp.statistics.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ExcelUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NumberUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.dsp.position.service.PositionService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.statistics.dao.AdvertisementStatisticsDao;
import com.yuanshanbao.dsp.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.dsp.statistics.model.AdvertisementStatisticsType;
import com.yuanshanbao.dsp.statistics.model.SuccessPageClick;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertisementStatisticsServiceImpl implements AdvertisementStatisticsService {

	@Autowired
	private AdvertisementStatisticsDao advertisementStatisticsDao;

	@Autowired
	private RedisService redisCacheService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private QuotaService quotaService;

	@Override
	public void insertAdvertisementStatistics(AdvertisementStatistics advertisementStatistics) {
		int result = -1;

		result = advertisementStatisticsDao.insertAdvertisementStatistics(advertisementStatistics);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateAdvertisementStatistics(AdvertisementStatistics advertisementStatistics) {
		int result = -1;

		result = advertisementStatisticsDao.updateAdvertisementStatistics(advertisementStatistics);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteAdvertisementStatistics(AdvertisementStatistics advertisementStatistics) {
		int result = -1;

		result = advertisementStatisticsDao.deleteAdvertisementStatistics(advertisementStatistics);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatistics(AdvertisementStatistics advertisementStatistics,
			PageBounds pageBounds) {
		return setProperty(advertisementStatisticsDao
				.selectAdvertisementStatistics(advertisementStatistics, pageBounds));
	}

	private List<AdvertisementStatistics> setProperty(List<AdvertisementStatistics> selectAdvertisementStatistics) {
		List<Long> advertisementIds = new ArrayList<Long>();
		for (AdvertisementStatistics adStatistics : selectAdvertisementStatistics) {
			advertisementIds.add(adStatistics.getAdvertisementId());
		}

		Map<Long, Advertisement> map = advertisementService.selectAdvertisementByIds(advertisementIds);
		for (AdvertisementStatistics adStatistics : selectAdvertisementStatistics) {
			adStatistics.setAdvertisement(map.get(adStatistics.getAdvertisementId()));
		}
		return selectAdvertisementStatistics;
	}

	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatisticsByLists(int datediff, Boolean pv,
			List<Long> advertisementIds, List<String> channels) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (advertisementIds == null || advertisementIds.size() == 0) {
			return new ArrayList<AdvertisementStatistics>();
		}
		map.put("type", pv ? AdvertisementStatisticsType.PV_DATA : AdvertisementStatisticsType.UV_DATA);
		map.put("advertisementIds", advertisementIds);
		if (channels == null) {
			map.put("channel", "");
		} else {
			map.put("channels", channels);
		}
		map.put("date", DateUtils.format(DateUtils.addDays(new Date(), -datediff)));
		List<AdvertisementStatistics> list = setProperty(advertisementStatisticsDao
				.selectAdvertisementStatisticsByAdvertisementIds(map));
		return list;
	}

	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatisticsByChannels(int diffDate, Boolean pv,
			List<String> channels) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (channels == null || channels.size() == 0) {
			return new ArrayList<AdvertisementStatistics>();
		}
		map.put("type", pv ? AdvertisementStatisticsType.PV_DATA : AdvertisementStatisticsType.UV_DATA);
		map.put("channels", channels);
		map.put("date", DateUtils.format(DateUtils.addDays(new Date(), -diffDate)));
		List<AdvertisementStatistics> list = setProperty(advertisementStatisticsDao
				.selectAdvertisementStatisticsByChannels(map));
		return list;
	}

	@Override
	public void runAndInsertAdvertisementStatistics(int diffDay) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDay));
		List<AdvertisementStatistics> list = intervalAdvertisementStatistics(diffDay, false, null);
		for (AdvertisementStatistics advertisementStatistics : list) {
			advertisementStatistics.setStatus(CommonStatus.ONLINE);
			AdvertisementStatistics param = new AdvertisementStatistics();
			param.setAdvertisementId(advertisementStatistics.getAdvertisementId());
			param.setChannel(advertisementStatistics.getChannel());
			param.setType(advertisementStatistics.getType());
			param.setDate(date);
			param.setStatus(advertisementStatistics.getStatus());
			List<AdvertisementStatistics> existList = selectAdvertisementStatistics(param, new PageBounds());
			if (advertisementStatistics.getTotal() > 0) {
				if (existList.size() > 0) {
					advertisementStatistics.setAdvertisementStatisticsId(existList.get(0)
							.getAdvertisementStatisticsId());
					updateAdvertisementStatistics(advertisementStatistics);
				} else {
					insertAdvertisementStatistics(advertisementStatistics);
				}
			}
		}
	}

	@Override
	public List<AdvertisementStatistics> selectAdvertisementStatistic(int diffDate, Boolean pv, Long advertisementId) {
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		Advertisement param = new Advertisement();
		param.setStatus(CommonStatus.ONLINE);
		List<Advertisement> list = advertisementService.selectAdvertisement(param, new PageBounds());
		Set<String> channelList = redisCacheService.smembers(RedisConstant.getAdvertisementChannelKey(date));
		LoggerUtil.info("[selectAdvertisementStatistic] channelList={}", channelList);
		if (diffDate > 0) {
			List<Long> advertisementIds = new ArrayList<Long>();
			List<String> channels = null;
			if (advertisementId != null) {
				channels = new ArrayList<String>();
				advertisementIds.add(advertisementId);
				for (String channel : channelList) {
					channels.add(channel);
				}
			} else {
				for (Advertisement advertisement : list) {
					advertisementIds.add(advertisement.getAdvertisementId());
				}
			}
			result = selectAdvertisementStatisticsByLists(diffDate, pv, advertisementIds, channels);
			for (AdvertisementStatistics adStatistics : result) {
				if (StringUtils.isBlank(adStatistics.getChannel())) {
					adStatistics.setChannel("总数");
				}
				calculateTotal(adStatistics);
				adStatistics.setTotal(calculateTotal(adStatistics));
			}
		}
		if (result.size() == 0) {
			for (Advertisement advertisement : list) {
				if (advertisementId != null) {
					if (advertisementId.equals(advertisement.getAdvertisementId())) {
						for (String channel : channelList) {
							AdvertisementStatistics click = createAdvertisementStatistic(diffDate, pv, advertisement,
									channel);
							if (click.getTotal() > 0) {
								result.add(click);
							}
						}
					}
				} else {
					result.add(createAdvertisementStatistic(diffDate, pv, advertisement, null));
				}
			}
		}
		if (advertisementId == null) {
			for (Advertisement advertisement : list) {
				boolean isExist = false;
				for (AdvertisementStatistics params : result) {
					if (params.getAdvertisement().getAdvertisementId().equals(advertisement.getAdvertisementId())) {
						isExist = true;
					}
				}
				if (!isExist) {
					AdvertisementStatistics ads = new AdvertisementStatistics();
					ads.setAdvertisementId(advertisement.getAdvertisementId());
					ads.setAdvertisement(advertisement);
					ads.setDate(date);
					result.add(ads);
				}
			}
		}
		Collections.sort(result, new Comparator<AdvertisementStatistics>() {
			@Override
			public int compare(AdvertisementStatistics arg0, AdvertisementStatistics arg1) {
				return (int) (arg1.getTotal() - arg0.getTotal());
			}
		});
		return result;
	}

	@Override
	public List<AdvertisementStatistics> selectChannelAdvertisementStatistic(int diffDate, Boolean pv, String channelKey) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		Set<String> channelList = redisCacheService.smembers(RedisConstant.getAdvertisementChannelKey(date));
		LoggerUtil.info("[selectChannelAdvertisementStatistic] channelList={}", channelList);
		Advertisement param = new Advertisement();
		param.setStatus(CommonStatus.ONLINE);
		List<Advertisement> list = advertisementService.selectAdvertisement(param, new PageBounds());
		if (diffDate > 0) {
			List<String> channels = new ArrayList<String>();
			if (channelKey != null) {
				channels.add(channelKey);
			} else {
				for (String channel : channelList) {
					channels.add(channel);
				}
			}
			result = selectAdvertisementStatisticsByChannels(diffDate, pv, channels);
			for (AdvertisementStatistics adStatistics : result) {
				if (StringUtils.isBlank(adStatistics.getChannel())) {
					adStatistics.setChannel("总数");
				}
				calculateTotal(adStatistics);
				adStatistics.setTotal(calculateTotal(adStatistics));
			}
		}
		if (result.size() == 0) {
			for (String channel : channelList) {
				if (StringUtils.isNotBlank(channelKey)) {
					if (channelKey.equals(channel)) {
						for (Advertisement advertisement : list) {
							AdvertisementStatistics click = createAdvertisementStatistic(diffDate, pv, advertisement,
									channel);
							result.add(click);
						}
					}
				} else {
					AdvertisementStatistics click = createChannelAdvertisementStatistic(diffDate, pv, channel);
					if (click.getTotal() > 0) {
						result.add(click);
					}
				}
			}
		}
		if (channelKey != null) {
			for (Advertisement advertisement : list) {
				boolean isExist = false;
				for (AdvertisementStatistics params : result) {
					if (params.getAdvertisement().getAdvertisementId().equals(advertisement.getAdvertisementId())) {
						isExist = true;
					}
				}
				if (!isExist) {
					AdvertisementStatistics ads = new AdvertisementStatistics();
					ads.setAdvertisementId(advertisement.getAdvertisementId());
					ads.setAdvertisement(advertisement);
					ads.setDate(date);
					result.add(ads);
				}
			}
		}
		Collections.sort(result, new Comparator<AdvertisementStatistics>() {
			@Override
			public int compare(AdvertisementStatistics arg0, AdvertisementStatistics arg1) {
				return (int) (arg1.getTotal() - arg0.getTotal());
			}
		});
		return result;
	}

	private AdvertisementStatistics createAdvertisementStatistic(int diffDate, Boolean pv, Advertisement advertisement,
			String channel) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		AdvertisementStatistics result = new AdvertisementStatistics();
		result.setAdvertisement(advertisement);
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (pv != null && pv) {
			dataTypeFunction(AdvertisementStatisticsType.PV_DATA, date, channel, resultList,
					advertisement.getAdvertisementId());
		} else {
			dataTypeFunction(AdvertisementStatisticsType.UV_DATA, date, channel, resultList,
					advertisement.getAdvertisementId());
		}
		for (AdvertisementStatistics adStatistics : resultList) {
			if (adStatistics.getAdvertisementId().equals(advertisement.getAdvertisementId())) {
				if (StringUtils.isNotBlank(adStatistics.getChannel())) {
					result.setChannel(adStatistics.getChannel());
				}
				// result.addWelfareCount(adStatistics.getWelfareCount());
				// result.addBannerCount(adStatistics.getBannerCount());
				// result.addTagsCount(adStatistics.getTagsCount());
				result.addDownloadCount(adStatistics.getDownloadCount());
			}
		}
		result.setTotal(calculateTotal(result));
		return result;
	}

	private AdvertisementStatistics createChannelAdvertisementStatistic(int diffDate, Boolean pv, String channelKey) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		AdvertisementStatistics result = new AdvertisementStatistics();
		result.setChannel(channelKey);
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (pv != null && pv) {
			dataTypeFunction(AdvertisementStatisticsType.PV_DATA, date, channelKey, resultList, null);
		} else {
			dataTypeFunction(AdvertisementStatisticsType.UV_DATA, date, channelKey, resultList, null);
		}
		for (AdvertisementStatistics adStatistics : resultList) {
			if (adStatistics.getChannel() != null && adStatistics.getChannel().equals(channelKey)) {
				Long advertisementId = adStatistics.getAdvertisementId();
				if (advertisementId != null) {
					result.setAdvertisementId(advertisementId);
					result.setAdvertisement(advertisementService.selectAdvertisement(advertisementId));
				}
				// result.addWelfareCount(adStatistics.getWelfareCount());
				// result.addBannerCount(adStatistics.getBannerCount());
				// result.addTagsCount(adStatistics.getTagsCount());
				result.addDownloadCount(adStatistics.getDownloadCount());
			}
		}
		result.setTotal(calculateTotal(result));
		return result;
	}

	private List<AdvertisementStatistics> intervalAdvertisementStatistics(int dateDiff, boolean fromDB, Integer dataType) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -dateDiff));
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		Set<String> channelList = redisCacheService.smembers(RedisConstant.getAdvertisementChannelKey(date));
		Advertisement param = new Advertisement();
		param.setStatus(CommonStatus.ONLINE);
		List<Advertisement> list = advertisementService.selectAdvertisement(param, new PageBounds());
		for (Advertisement advertisement : list) {
			for (String c : channelList) {
				dataTypeFunction(dataType, date, c, resultList, advertisement.getAdvertisementId());
			}
		}
		return resultList;
	}

	private void dataTypeFunction(Integer dataType, String date, String channel,
			List<AdvertisementStatistics> resultList, Long advertisementId) {
		if (dataType == null) {
			setPv(date, channel, resultList, advertisementId);
			setUv(date, channel, resultList, advertisementId);
		} else if (dataType.equals(AdvertisementStatisticsType.PV_DATA)) {
			setPv(date, channel, resultList, advertisementId);
		} else if (dataType.equals(AdvertisementStatisticsType.UV_DATA)) {
			setUv(date, channel, resultList, advertisementId);
		}
	}

	private void setUv(String date, String channel, List<AdvertisementStatistics> resultList, Long advertisementId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setAdvertisementId(advertisementId);
		adStatistics.setChannel(channel);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.UV_DATA);
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private void setPv(String date, String channel, List<AdvertisementStatistics> resultList, Long advertisementId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setAdvertisementId(advertisementId);
		adStatistics.setChannel(channel);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.PV_DATA);
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private Integer calculateTotal(AdvertisementStatistics adStatistics) {
		// return adStatistics.getWelfareCount() + adStatistics.getBannerCount()
		// + adStatistics.getTagsCount()
		// + adStatistics.getDownloadCount();
		return 1;
	}

	private Integer getUv(String date, String channel, String position, Long advertisementId) {
		if (advertisementId == null && StringUtils.isNotBlank(channel)) {
			Advertisement param = new Advertisement();
			param.setStatus(CommonStatus.ONLINE);
			List<Advertisement> list = advertisementService.selectAdvertisement(param, new PageBounds());
			String[] keys = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				keys[i] = RedisConstant.getAdvertisementChannelClickCountKey(date, position, list.get(i)
						.getAdvertisementId() + "", channel);
			}
			List<String> strs = redisCacheService.mgetWithEnv(keys);
			int count = 0;
			for (String str : strs) {
				if (ValidateUtil.isNumber(str)) {
					count += Integer.parseInt(str);
				}
			}
			return count;
		}

		// String key = RedisConstant.getAdvertisementClickCountKey(date,
		// position, advertisementId + "");
		if (StringUtils.isNotBlank(channel)) {
			// key = RedisConstant.getAdvertisementChannelClickCountKey(date,
			// position, advertisementId + "", channel);
		}
		// String str = (String) redisCacheService.get(key);
		// if (ValidateUtil.isNumber(str)) {
		// return Integer.parseInt(str);
		// }
		return 0;
	}

	private Integer getPv(String date, String channel, String position, Long advertisementId) {
		String key = RedisConstant.getAdvertisementClickPVCountKey(date, position, advertisementId + "");
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getAdvertisementChannelClickPVCountKey(date, position, advertisementId + "", channel);
		}
		String str = (String) redisCacheService.get(key);
		if (ValidateUtil.isNumber(str)) {
			return Integer.parseInt(str);
		}
		return 0;
	}

	@Override
	public List<SuccessPageClick> selectSuccessPageClicks(int dateDiff, boolean fromDB) {
		List<SuccessPageClick> resultList = new ArrayList<SuccessPageClick>();
		String date = DateUtils.format(DateUtils.addDays(new Date(), -dateDiff));
		Set<String> channelList = redisCacheService.smembers(RedisConstant.getResultPageChannelKey(date));
		for (String channel : channelList) {
			SuccessPageClick click = new SuccessPageClick();
			click.setChannel(channel);
			click.setDate(date);
			int count = 0;
			click.setFailUvCount(count);
			click.setTotal(click.getFailUvCount() + click.getSuccessUvCount());
			resultList.add(click);
		}
		return resultList;
	}

	@Override
	public List<AdvertisementStatistics> combineAdvertiserAndPosition(List<AdvertisementStatistics> list) {
		int showCount;
		int clickCount;
		BigDecimal totalMoney;
		List<String> existDateList = new ArrayList<String>();
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		AdvertisementStatistics advertisementStatistics = new AdvertisementStatistics();
		for (AdvertisementStatistics statistic : list) {
			if (!existDateList.contains(statistic.getDate())) {
				existDateList.add(statistic.getDate());
			}
		}
		for (String date : existDateList) {
			advertisementStatistics = new AdvertisementStatistics();
			showCount = 0;
			clickCount = 0;
			totalMoney = new BigDecimal(0);
			for (AdvertisementStatistics sta : list) {
				if (date.equals(sta.getDate())) {
					showCount += sta.getShowCount();
					clickCount += sta.getClickCount();
					totalMoney = totalMoney.add(sta.getTotalAmount());
				}
			}
			advertisementStatistics.setDate(date);
			advertisementStatistics.setClickCount(clickCount);
			advertisementStatistics.setShowCount(showCount);
			advertisementStatistics.setTotalAmount(totalMoney);
			advertisementStatistics.setClickRate(NumberUtil.getPercent(clickCount, showCount));
			advertisementStatistics.setAvgPrice(totalMoney.divide(new BigDecimal(clickCount), 2));
			resultList.add(advertisementStatistics);
		}
		return resultList;
	}

	@Override
	public List<AdvertisementStatistics> combineDateAndPosition(List<AdvertisementStatistics> list) {
		int showCount;
		int clickCount;
		BigDecimal totalMoney;
		List<Long> existAdvertiserList = new ArrayList<Long>();
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		Map<Long, String> companyNames = new HashMap<Long, String>();
		AdvertisementStatistics advertisementStatistics = new AdvertisementStatistics();

		companyNames = this.getAdvertiserName();
		for (AdvertisementStatistics statistic : list) {
			if (!existAdvertiserList.contains(statistic.getAdvertiserId())) {
				existAdvertiserList.add(statistic.getAdvertiserId());
			}
		}
		for (Long adverId : existAdvertiserList) {
			advertisementStatistics = new AdvertisementStatistics();
			showCount = 0;
			clickCount = 0;
			totalMoney = new BigDecimal(0);
			for (AdvertisementStatistics sta : list) {
				if (adverId.equals(sta.getAdvertiserId())) {
					showCount += sta.getShowCount();
					clickCount += sta.getClickCount();
					totalMoney = totalMoney.add(sta.getTotalAmount());
				}
			}
			advertisementStatistics.setAdvertiserId(adverId);
			advertisementStatistics.setCompanyName(companyNames.get(adverId));
			advertisementStatistics.setClickCount(clickCount);
			advertisementStatistics.setShowCount(showCount);
			advertisementStatistics.setTotalAmount(totalMoney);
			advertisementStatistics.setClickRate(NumberUtil.getPercent(clickCount, showCount));
			if (clickCount != 0) {
				advertisementStatistics.setAvgPrice(totalMoney.divide(new BigDecimal(clickCount), 2));
			} else {
				advertisementStatistics.setAvgPrice(totalMoney);
			}
			resultList.add(advertisementStatistics);

		}
		return resultList;
	}

	@Override
	public List<AdvertisementStatistics> combineAdvertiserAndDate(List<AdvertisementStatistics> list) {
		int showCount;
		int clickCount;
		BigDecimal totalMoney;
		Map<Long, String> positionNames = new HashMap<Long, String>();
		List<Long> existPositionList = new ArrayList<Long>();
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		AdvertisementStatistics advertisementStatistics = new AdvertisementStatistics();

		positionNames = this.getPositionName();
		for (AdvertisementStatistics statistic : list) {
			if (!existPositionList.contains(statistic.getPositionId())) {
				existPositionList.add(statistic.getPositionId());
			}
		}
		for (Long posId : existPositionList) {
			advertisementStatistics = new AdvertisementStatistics();
			showCount = 0;
			clickCount = 0;
			totalMoney = new BigDecimal(0);
			for (AdvertisementStatistics sta : list) {
				if (posId.equals(sta.getPositionId())) {
					showCount += sta.getShowCount();
					clickCount += sta.getClickCount();
					totalMoney = totalMoney.add(sta.getTotalAmount());
				}
			}
			advertisementStatistics.setPositionName(positionNames.get(posId));
			advertisementStatistics.setClickCount(clickCount);
			advertisementStatistics.setShowCount(showCount);
			advertisementStatistics.setTotalAmount(totalMoney);
			advertisementStatistics.setClickRate(NumberUtil.getPercent(clickCount, showCount));
			advertisementStatistics.setAvgPrice(totalMoney.divide(new BigDecimal(clickCount), 2));
			resultList.add(advertisementStatistics);
		}

		return resultList;
	}

	@Override
	public List<AdvertisementStatistics> calculateStatistics(List<Probability> list, String date) {
		String showKey = null;
		String clickKey = null;

		Integer showCount = null;
		Integer clickCount = null;
		String clickRate = null;
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal unitPrice = new BigDecimal(0);

		AdvertisementStatistics advertisementStatistics = new AdvertisementStatistics();

		List<Long> advertisementIdList = new ArrayList<Long>();
		List<Long> positions = new ArrayList<Long>();
		List<Quota> quotaList = new ArrayList<Quota>();
		List<Advertisement> adList = new ArrayList<Advertisement>();
		List<AdvertisementStatistics> resutlList = new ArrayList<AdvertisementStatistics>();
		Map<Long, String> titleMap = new HashMap<Long, String>();
		Map<Long, String> companyMap = new HashMap<Long, String>();
		Map<Long, String> positionMap = new HashMap<Long, String>();
		Map<Long, Advertisement> adMap = new HashMap<Long, Advertisement>();

		titleMap = this.getAdvertisementName();
		companyMap = this.getAdvertiserName();
		positionMap = this.getPositionName();

		for (Probability pro : list) {
			if (!positions.contains(pro.getPositionId())) {
				positions.add(pro.getPositionId());
			}
			if (!advertisementIdList.contains(pro.getAdvertisementId())) {
				advertisementIdList.add(pro.getAdvertisementId());
			}
		}

		adMap = advertisementService.selectAdvertisementByIds(advertisementIdList);

		for (Probability pro : list) {
			advertisementIdList = new ArrayList<Long>();
			advertisementStatistics = new AdvertisementStatistics();
			showCount = 0;
			clickCount = 0;
			totalAmount = new BigDecimal(0);
			unitPrice = new BigDecimal(0);
			advertisementIdList.add(pro.getAdvertisementId());

			quotaList = quotaService.selectQuotaFromCache((long) 1, pro.getPositionId(), advertisementIdList);

			showKey = RedisConstant.getAdvertisementShowCountKey(date, pro.getAdvertisementId(), pro.getPositionId());
			clickKey = RedisConstant.getAdvertisementClickCountKey(date, pro.getAdvertisementId(), pro.getPositionId());
			if (redisService.get(showKey) != null) {
				showCount = Integer.parseInt(redisService.get(showKey));
			}
			if (redisService.get(clickKey) != null) {
				clickCount = Integer.parseInt(redisService.get(clickKey));
			}
			if (quotaList.size() != 0) {
				unitPrice = quotaList.get(0).getUnitPrice();
			}
			clickRate = NumberUtil.getPercent(clickCount, showCount);
			totalAmount = unitPrice.multiply(new BigDecimal(clickCount));
			advertisementStatistics.setAdvertisementId(pro.getAdvertisementId());
			advertisementStatistics.setAdvertiserId(adMap.get(pro.getAdvertisementId()).getAdvertiser()
					.getAdvertiserId());
			advertisementStatistics
					.setCompanyName(adMap.get(pro.getAdvertisementId()).getAdvertiser().getCompanyName());
			advertisementStatistics.setTitle(titleMap.get(pro.getAdvertisementId()));
			advertisementStatistics.setPositionId(pro.getPositionId());
			advertisementStatistics.setPositionName(positionMap.get(pro.getPositionId()));
			advertisementStatistics
					.setCompanyName(adMap.get(pro.getAdvertisementId()).getAdvertiser().getCompanyName());
			advertisementStatistics.setClickCount(clickCount);
			advertisementStatistics.setShowCount(showCount);
			advertisementStatistics.setClickRate(clickRate);
			advertisementStatistics.setTotalAmount(totalAmount);
			advertisementStatistics.setAvgPrice(unitPrice);
			advertisementStatistics.setDate(date);
			advertisementStatistics.setStatus(1);
			resutlList.add(advertisementStatistics);
		}

		return resutlList;
	}

	public String downStatistics(List<AdvertisementStatistics> list) {
		String path = null;

		if (list.size() != 0 && list != null) {
			Map<String, List<List<String>>> sheetMap = new HashMap<String, List<List<String>>>();
			List<List<String>> rowList = new ArrayList<List<String>>();
			List<String> columnList = new ArrayList<String>();

			columnList.add("广告名称");
			columnList.add("广告主名称");
			columnList.add("位置");
			columnList.add("时间");
			columnList.add("曝光量(次)");
			columnList.add("点击量(次)");
			columnList.add("点击率");
			columnList.add("点击均价");
			columnList.add("总消耗(元)");
			rowList.add(columnList);

			for (AdvertisementStatistics temp : list) {
				columnList = new ArrayList<String>();

				columnList.add(temp.getTitle());
				columnList.add(temp.getCompanyName());
				columnList.add(temp.getPositionName());
				columnList.add(temp.getDate());
				columnList.add(temp.getShowCount().toString());
				columnList.add(temp.getClickCount().toString());
				columnList.add(temp.getClickRate());
				if (temp.getAvgPrice() != null) {
					columnList.add(temp.getAvgPrice().toString());
				}
				columnList.add(temp.getTotalAmount().toString());
				rowList.add(columnList);
			}
			sheetMap.put("sheet1", rowList);
			try {
				path = ExcelUtil.createSuffixXlsExcelByMoreSheets(sheetMap, "数据统计表");
			} catch (Exception e) {
				LoggerUtil.error("[excel creat error]", e);
			}
		}
		return path;
	}

	private Map<Long, String> getPositionName() {
		Map<Long, String> result = new HashMap<Long, String>();
		List<Position> list = new ArrayList<Position>();
		Position position = new Position();
		list = positionService.selectPosition(position, new PageBounds());
		for (Position pos : list) {
			result.put(pos.getPositionId(), pos.getName());
		}
		return result;
	}

	private Map<Long, String> getAdvertisementName() {
		Map<Long, String> result = new HashMap<Long, String>();
		Advertisement advertisement = new Advertisement();
		List<Advertisement> list = new ArrayList<Advertisement>();
		list = advertisementService.selectAdvertisement(advertisement, new PageBounds());
		for (Advertisement adv : list) {
			result.put(adv.getAdvertisementId(), adv.getTitle());
		}
		return result;
	}

	private Map<Long, String> getAdvertiserName() {
		Map<Long, String> result = new HashMap<Long, String>();
		Advertiser advertiser = new Advertiser();
		List<Advertiser> list = new ArrayList<Advertiser>();
		list = advertiserService.selectAdvertiser(advertiser, new PageBounds());
		for (Advertiser adv : list) {
			result.put(adv.getAdvertiserId(), adv.getCompanyName());
		}
		return result;
	}
}
