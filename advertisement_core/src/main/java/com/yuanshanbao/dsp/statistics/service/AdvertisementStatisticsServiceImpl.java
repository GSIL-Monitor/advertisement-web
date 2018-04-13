package com.yuanshanbao.dsp.statistics.service;

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
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.position.model.Position;
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
				result.addWelfareCount(adStatistics.getWelfareCount());
				result.addBannerCount(adStatistics.getBannerCount());
				result.addTagsCount(adStatistics.getTagsCount());
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
				result.addWelfareCount(adStatistics.getWelfareCount());
				result.addBannerCount(adStatistics.getBannerCount());
				result.addTagsCount(adStatistics.getTagsCount());
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
		adStatistics.setWelfareCount(getUv(date, channel, Position.WELFARE, advertisementId));
		adStatistics.setBannerCount(getUv(date, channel, Position.BANNER, advertisementId));
		adStatistics.setTagsCount(getUv(date, channel, Position.TAGS, advertisementId));
		adStatistics.setDownloadCount(getUv(date, channel, Position.DOWNLOAD, advertisementId));
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private void setPv(String date, String channel, List<AdvertisementStatistics> resultList, Long advertisementId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setAdvertisementId(advertisementId);
		adStatistics.setChannel(channel);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.PV_DATA);
		adStatistics.setWelfareCount(getPv(date, channel, Position.WELFARE, advertisementId));
		adStatistics.setBannerCount(getPv(date, channel, Position.BANNER, advertisementId));
		adStatistics.setTagsCount(getPv(date, channel, Position.TAGS, advertisementId));
		adStatistics.setDownloadCount(getPv(date, channel, Position.DOWNLOAD, advertisementId));
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private Integer calculateTotal(AdvertisementStatistics adStatistics) {
		return adStatistics.getWelfareCount() + adStatistics.getBannerCount() + adStatistics.getTagsCount()
				+ adStatistics.getDownloadCount();
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

		String key = RedisConstant.getAdvertisementClickCountKey(date, position, advertisementId + "");
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getAdvertisementChannelClickCountKey(date, position, advertisementId + "", channel);
		}
		String str = (String) redisCacheService.get(key);
		if (ValidateUtil.isNumber(str)) {
			return Integer.parseInt(str);
		}
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
			String successUvStr = (String) redisCacheService.get(RedisConstant.getResultPageUVCountKey(
					Position.WELFARE, date, channel));
			int count = 0;
			if (ValidateUtil.isNumber(successUvStr)) {
				count = Integer.parseInt(successUvStr);
			}
			click.setSuccessUvCount(count);
			String failUvStr = (String) redisCacheService.get(RedisConstant.getResultPageUVCountKey(
					Position.BANNER, date, channel));
			count = 0;
			if (ValidateUtil.isNumber(failUvStr)) {
				count = Integer.parseInt(failUvStr);
			}
			click.setFailUvCount(count);
			click.setTotal(click.getFailUvCount() + click.getSuccessUvCount());
			resultList.add(click);
		}
		return resultList;
	}

}
