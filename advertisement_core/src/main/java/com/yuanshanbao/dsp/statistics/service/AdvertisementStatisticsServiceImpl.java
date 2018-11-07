package com.yuanshanbao.dsp.statistics.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.plan.service.PlanService;
import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.dsp.position.service.PositionService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
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
	private ProbabilityService probabilityService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private QuotaService quotaService;
	@Autowired
	private PlanService planService;

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

	private Integer calculateTotal(AdvertisementStatistics adStatistics) {
		// return adStatistics.getWelfareCount() + adStatistics.getBannerCount()
		// + adStatistics.getTagsCount()
		// + adStatistics.getDownloadCount();
		return adStatistics.getClickCount() + adStatistics.getShowCount();
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
	public List<AdvertisementStatistics> calculateStatistics(Long projectId, List<Probability> list, String date) {
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
		List<AdvertisementStatistics> resutlList = new ArrayList<AdvertisementStatistics>();
		Map<Long, String> titleMap = new HashMap<Long, String>();
		Map<Long, String> positionMap = new HashMap<Long, String>();
		Map<Long, Advertisement> adMap = new HashMap<Long, Advertisement>();

		titleMap = this.getAdvertisementName();
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

			quotaList = quotaService.selectQuotaFromCache(projectId, pro.getPositionId(), advertisementIdList);

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
			if (unitPrice == null) {
				unitPrice = new BigDecimal(0);
			}
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
			advertisementStatistics.setStatus(CommonStatus.ONLINE);
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

	// --------------------------------------------------------------------------------------------
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

	private List<AdvertisementStatistics> intervalAdvertisementStatistics(int dateDiff, boolean fromDB, Integer dataType) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -dateDiff));
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		Set<String> channelAndIdList = redisCacheService.smembers(RedisConstant.getAdvertisementChannelAndIdKey(date));
		Advertisement param = new Advertisement();
		param.setStatus(CommonStatus.ONLINE);
		List<Advertisement> list = advertisementService.selectAdvertisement(param, new PageBounds());
		for (String channelAndId : channelAndIdList) {
			String[] segs = channelAndId.split(":");
			dataTypeFunction(dataType, date, segs[1], resultList, Long.parseLong(segs[0]));
		}
		return resultList;
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
				result.addClickCount(adStatistics.getClickCount());
				result.addShowCount(adStatistics.getShowCount());
			}
		}
		result.setTotal(calculateTotal(result));
		return result;
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

	private void setUv(String date, String channel, List<AdvertisementStatistics> resultList, Long advertisementId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setAdvertisementId(advertisementId);
		adStatistics.setChannel(channel);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.UV_DATA);
		adStatistics.setShowCount(getShowUv(date, channel, advertisementId));
		adStatistics.setClickCount(getClickUv(date, channel, advertisementId));
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private Integer getShowUv(String date, String channel, Long advertisementId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getAdvertisementShowCountUVKey(date, advertisementId + "", channel);
		}
		return getCount(key);
	}

	private Integer getClickUv(String date, String channel, Long advertisementId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getAdvertisementClickCountUVKey(date, advertisementId + "", channel);
		}
		return getCount(key);
	}

	private Integer getClickPv(String date, String channel, Long advertisementId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getAdvertisementClickCountPVKey(date, advertisementId + "", channel);
		}
		return getCount(key);
	}

	private Integer getShowPv(String date, String channel, Long advertisementId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getAdvertisementShowCountPVKey(date, advertisementId + "", channel);
		}
		return getCount(key);
	}

	private Integer getCount(String key) {
		String str = (String) redisCacheService.get(key);
		if (ValidateUtil.isNumber(str)) {
			return Integer.parseInt(str);
		}
		return 0;
	}

	private void addToResultMap(Map<String, AdvertisementStatistics> resultMap, String channel,
			AdvertisementStatistics click) {
		AdvertisementStatistics exists = resultMap.get(channel);
		if (exists != null) {
			exists.addClickCount(click.getClickCount());
			exists.addShowCount(click.getShowCount());
		} else {
			resultMap.put(channel, click);
		}
	}

	private void addAdvertisementToResultMap(Map<Long, AdvertisementStatistics> resultMap, Long advertisementId,
			AdvertisementStatistics click) {
		AdvertisementStatistics exists = resultMap.get(advertisementId);
		if (exists != null) {
			exists.addClickCount(click.getClickCount());
			exists.addShowCount(click.getShowCount());
		} else {
			resultMap.put(advertisementId, click);
		}
	}

	private void setPv(String date, String channel, List<AdvertisementStatistics> resultList, Long advertisementId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setAdvertisementId(advertisementId);
		adStatistics.setChannel(channel);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.PV_DATA);
		adStatistics.setShowCount(getShowPv(date, channel, advertisementId));
		adStatistics.setClickCount(getClickPv(date, channel, advertisementId));
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	public List<AdvertisementStatistics> selectChannelAdvertisementStatistic(
			AdvertisementStatistics advertisementStatistics, Boolean pv, String channelKey) {
		Probability probability = new Probability();
		probability.setStatus(CommonStatus.ONLINE);
		int diffDate = getDateDiff(advertisementStatistics);
		Map<String, AdvertisementStatistics> resultMap = new HashMap<String, AdvertisementStatistics>();
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		List<Probability> list = probabilityService.selectProbabilitys(probability, new PageBounds());
		// 查找当天数据
		if (diffDate == 0) {
			if (StringUtils.isNotBlank(channelKey)) {
				for (Probability pro : list) {
					if (channelKey.equals(pro.getChannel())) {
						Advertisement advertisement = advertisementService
								.selectAdvertisement(pro.getAdvertisementId());
						AdvertisementStatistics statistic = createAdStatistic(diffDate, pv, pro.getAdvertisementId(),
								channelKey);
						statistic.setAdvertisement(advertisement);
						result.add(statistic);
					}
				}
			} else {
				for (Probability pro : list) {
					AdvertisementStatistics statistic = createChannelAdvertisementStatistic(diffDate, pv,
							pro.getChannel(), pro.getAdvertisementId());
					addToResultMap(resultMap, pro.getChannel(), statistic);
				}
				result.addAll(resultMap.values());
			}
		}
		if (diffDate > 0) {
			List<AdvertisementStatistics> resultInDB = selectStatistic(advertisementStatistics, pv);
			if (StringUtils.isNotBlank(channelKey)) {
				result = resultInDB;
			} else {
				for (int i = 0, len = getDays(advertisementStatistics); i < len; i++) {
					Map<String, AdvertisementStatistics> totalMap = new HashMap<String, AdvertisementStatistics>();
					for (AdvertisementStatistics adStatistics : resultInDB) {
						if (advertisementStatistics.getQueryStartTime() != null
								&& incDate(advertisementStatistics.getQueryStartTime(), i).equals(
										adStatistics.getDate())) {
							AdvertisementStatistics total = totalMap.get(adStatistics.getChannel());
							if (total == null) {
								total = new AdvertisementStatistics();
								total.setDate(incDate(advertisementStatistics.getQueryStartTime(), i));
								total.setChannel(adStatistics.getChannel());
								total.addClickCount(adStatistics.getClickCount());
								total.addShowCount(adStatistics.getShowCount());
								totalMap.put(adStatistics.getChannel(), total);
								continue;
							}
							total.addClickCount(adStatistics.getClickCount());
							total.addShowCount(adStatistics.getShowCount());
						}
					}
					result.addAll(totalMap.values());
				}
			}
		}
		return result;
	}

	private List<AdvertisementStatistics> selectStatistic(AdvertisementStatistics advertisementStatistics, boolean pv) {
		advertisementStatistics.setType(pv ? AdvertisementStatisticsType.PV_DATA : AdvertisementStatisticsType.UV_DATA);
		List<AdvertisementStatistics> list = setProperty(advertisementStatisticsDao.selectAdvertisementStatistics(
				advertisementStatistics, new PageBounds()));
		return list;
	}

	private AdvertisementStatistics createChannelAdvertisementStatistic(int diffDate, Boolean pv, String channelKey,
			long advertisementId) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		AdvertisementStatistics result = new AdvertisementStatistics();
		result.setChannel(channelKey);
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (pv != null && pv) {
			dataTypeFunction(AdvertisementStatisticsType.PV_DATA, date, channelKey, resultList, advertisementId);
		} else {
			dataTypeFunction(AdvertisementStatisticsType.UV_DATA, date, channelKey, resultList, advertisementId);
		}
		for (AdvertisementStatistics adStatistics : resultList) {
			if (adStatistics.getChannel() != null && adStatistics.getChannel() != ""
					&& adStatistics.getChannel().equals(channelKey)) {
				// if (advertisementId != adStatistics.getAdvertisementId()) {
				// result.setAdvertisementId(adStatistics.getAdvertisementId());
				// result.setAdvertisement(advertisementService.selectAdvertisement(adStatistics.getAdvertisementId()));
				// }
				result.addShowCount(adStatistics.getShowCount());
				result.addClickCount(adStatistics.getClickCount());
			}
		}
		return result;
	}

	public List<AdvertisementStatistics> selectAdvertisementStatistic(AdvertisementStatistics advertisementStatistics,
			Boolean pv, Long advertisementId) {
		Probability probability = new Probability();
		probability.setStatus(CommonStatus.ONLINE);
		int diffDate = getDateDiff(advertisementStatistics);
		Map<Long, AdvertisementStatistics> resultMap = new HashMap<Long, AdvertisementStatistics>();
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		List<Probability> probabilityList = probabilityService.selectProbabilitys(probability, new PageBounds());
		Advertisement param = new Advertisement();
		param.setStatus(CommonStatus.ONLINE);
		if (diffDate == 0) {
			if (advertisementId != null) {
				for (Probability pro : probabilityList) {
					if (advertisementId.equals(pro.getAdvertisementId())) {
						AdvertisementStatistics statistic = createChannelAdvertisementStatistic(diffDate, pv,
								pro.getChannel(), advertisementId);
						result.add(statistic);
					}
				}
			} else {
				for (Probability pro : probabilityList) {
					Advertisement advertisement = advertisementService.selectAdvertisement(pro.getAdvertisementId());
					AdvertisementStatistics statistic = createAdStatistic(diffDate, pv, pro.getAdvertisementId(),
							pro.getChannel());
					statistic.setAdvertisement(advertisement);
					addAdvertisementToResultMap(resultMap, pro.getAdvertisementId(), statistic);
				}
				result.addAll(resultMap.values());
			}
		}
		if (diffDate > 0) {
			List<AdvertisementStatistics> resultInDB = selectStatistic(advertisementStatistics, pv);
			if (advertisementId != null) {
				for (AdvertisementStatistics adStatistics : resultInDB) {
					if (advertisementId.equals(adStatistics.getAdvertisementId())) {
						result = resultInDB;
					}
				}
			} else {
				for (int i = 0, len = getDays(advertisementStatistics); i < len; i++) {
					Map<Long, AdvertisementStatistics> totalMap = new HashMap<Long, AdvertisementStatistics>();
					for (AdvertisementStatistics adStatistics : resultInDB) {
						if (advertisementStatistics.getQueryStartTime() != null
								&& incDate(advertisementStatistics.getQueryStartTime(), i).equals(
										adStatistics.getDate())) {
							AdvertisementStatistics total = totalMap.get(adStatistics.getChannel());
							if (total == null) {
								total = new AdvertisementStatistics();
								total.setAdvertisement(adStatistics.getAdvertisement());
								total.setDate(incDate(advertisementStatistics.getQueryStartTime(), i));
								total.setChannel(adStatistics.getChannel());
								total.addClickCount(adStatistics.getClickCount());
								total.addShowCount(adStatistics.getShowCount());
								totalMap.put(adStatistics.getAdvertisementId(), total);
								continue;
							}
							total.addClickCount(adStatistics.getClickCount());
							total.addShowCount(adStatistics.getShowCount());
						}
					}
					result.addAll(totalMap.values());
				}
			}
		}
		return result;
	}

	private AdvertisementStatistics createAdStatistic(int diffDate, Boolean pv, Long advertisementId, String channel) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		AdvertisementStatistics result = new AdvertisementStatistics();
		result.setAdvertisementId(advertisementId);
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (pv != null && pv) {
			dataTypeFunction(AdvertisementStatisticsType.PV_DATA, date, channel, resultList, advertisementId);
		} else {
			dataTypeFunction(AdvertisementStatisticsType.UV_DATA, date, channel, resultList, advertisementId);
		}
		for (AdvertisementStatistics adStatistics : resultList) {
			if (adStatistics.getAdvertisementId().equals(advertisementId)) {
				result.addClickCount(adStatistics.getClickCount());
				result.addShowCount(adStatistics.getShowCount());
			}
		}
		result.setTotal(calculateTotal(result));
		return result;
	}

	private String incDate(String date, int addDays) {
		Date s = DateUtils.formatToDate(date, DateUtils.DEFAULT_DATE_FORMAT);
		s = DateUtils.addDays(s, addDays);
		return DateUtils.format(s);
	}

	private int getDays(AdvertisementStatistics advertisementStatistics) {
		Date s = DateUtils.formatToDate(advertisementStatistics.getQueryStartTime(), DateUtils.DEFAULT_DATE_FORMAT);
		Date e = DateUtils.formatToDate(advertisementStatistics.getQueryEndTime(), DateUtils.DEFAULT_DATE_FORMAT);
		System.err.println(DateUtils.getDays(s, e));
		return DateUtils.getDays(s, e) + 1;
	}

	private int getDateDiff(AdvertisementStatistics advertisementStatistics) {
		String startTime = advertisementStatistics.getQueryStartTime();
		String endTime = advertisementStatistics.getQueryEndTime();

		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			return 0;
		}
		if (DateUtils.getDifferentDays(DateUtils.formatToDate(startTime, "yyyy-MM-dd"), new Date()) <= 0) {
			return 0;
		}
		return DateUtils.getDiffDays(DateUtils.formatToDate(startTime, "yyyy-MM-dd"),
				DateUtils.formatToDate(endTime, "yyyy-MM-dd"));
	}

	@Override
	public void runAndInsertPlanStatistics(int diffDay) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDay));
		List<AdvertisementStatistics> list = intervalPlanStatistics(diffDay, false, null);
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

	private List<AdvertisementStatistics> intervalPlanStatistics(int dateDiff, boolean fromDB, Integer dataType) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -dateDiff));
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		Set<String> channelAndIdList = redisCacheService.smembers(RedisConstant.getAdvertisementChannelAndIdKey(date));
		for (String channelAndId : channelAndIdList) {
			String[] segs = channelAndId.split(":");
			dataPlanTypeFunction(dataType, date, segs[1], resultList, Long.parseLong(segs[0]));
		}
		return resultList;
	}

	private void dataPlanTypeFunction(Integer dataType, String date, String channel,
			List<AdvertisementStatistics> resultList, Long planId) {
		if (dataType == null) {
			setPlanPv(date, channel, resultList, planId);
			setPlanUv(date, channel, resultList, planId);
		} else if (dataType.equals(AdvertisementStatisticsType.PV_DATA)) {
			setPlanPv(date, channel, resultList, planId);
		} else if (dataType.equals(AdvertisementStatisticsType.UV_DATA)) {
			setPlanUv(date, channel, resultList, planId);
		}
	}

	private void setPlanPv(String date, String channel, List<AdvertisementStatistics> resultList, Long planId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setPlanId(planId);
		adStatistics.setChannel(channel);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.PV_DATA);
		adStatistics.setShowCount(getPlanShowPv(date, channel, planId));
		adStatistics.setClickCount(getPlanClickPv(date, channel, planId));
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private Integer getPlanClickPv(String date, String channel, Long planId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getPlanClickCountPVKey(date, planId + "", channel);
		}
		return getCount(key);
	}

	private Integer getPlanShowPv(String date, String channel, Long planId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getPlanShowCountPVKey(date, planId + "", channel);
		}
		return getCount(key);
	}

	private void setPlanUv(String date, String channel, List<AdvertisementStatistics> resultList, Long planId) {
		AdvertisementStatistics adStatistics = new AdvertisementStatistics();
		adStatistics.setChannel(channel);
		adStatistics.setPlanId(planId);
		adStatistics.setDate(date);
		adStatistics.setType(AdvertisementStatisticsType.UV_DATA);
		adStatistics.setShowCount(getPlanShowUv(date, channel, planId));
		adStatistics.setClickCount(getPlanClickUv(date, channel, planId));
		adStatistics.setTotal(calculateTotal(adStatistics));
		resultList.add(adStatistics);
	}

	private Integer getPlanShowUv(String date, String channel, Long planId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getPlanShowCountUVKey(date, planId + "", channel);
		}
		return getCount(key);
	}

	private Integer getPlanClickUv(String date, String channel, Long planId) {
		String key = null;
		if (StringUtils.isNotBlank(channel)) {
			key = RedisConstant.getPlanClickCountUVKey(date, planId + "", channel);
		}
		return getCount(key);
	}

	@Override
	public List<AdvertisementStatistics> selectPlanStatistic(AdvertisementStatistics advertisementStatistics,
			Boolean pv, Long planId, Long projectId) {
		Probability probability = new Probability();
		probability.setStatus(CommonStatus.ONLINE);
		probability.setProjectId(projectId);
		int diffDate = getDateDiff(advertisementStatistics);
		Map<Long, AdvertisementStatistics> resultMap = new HashMap<Long, AdvertisementStatistics>();
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		List<Probability> probabilityList = probabilityService.selectProbabilitys(probability, new PageBounds());
		Advertisement param = new Advertisement();
		param.setStatus(CommonStatus.ONLINE);
		if (diffDate == 0) {
			if (planId != null) {
				for (Probability pro : probabilityList) {
					if (planId.equals(pro.getPlanId())) {
						AdvertisementStatistics statistic = createChannelAdvertisementStatistic(diffDate, pv,
								pro.getChannel(), planId);
						result.add(statistic);
					}
				}
			} else {
				for (Probability pro : probabilityList) {
					Plan plan = planService.selectPlan(pro.getPlanId());
					AdvertisementStatistics statistic = createPlanStatistic(diffDate, pv, pro.getPlanId(),
							pro.getChannel());
					statistic.setPlan(plan);
					addPlanToResultMap(resultMap, pro.getPlanId(), statistic);
				}
				result.addAll(resultMap.values());
			}
		}
		if (diffDate > 0) {
			List<AdvertisementStatistics> resultInDB = selectStatistic(advertisementStatistics, pv);
			if (planId != null) {
				for (AdvertisementStatistics adStatistics : resultInDB) {
					if (planId.equals(adStatistics.getPlanId())) {
						result = resultInDB;
					}
				}
			} else {
				for (int i = 0, len = getDays(advertisementStatistics); i < len; i++) {
					Map<Long, AdvertisementStatistics> totalMap = new HashMap<Long, AdvertisementStatistics>();
					for (AdvertisementStatistics adStatistics : resultInDB) {
						if (advertisementStatistics.getQueryStartTime() != null
								&& incDate(advertisementStatistics.getQueryStartTime(), i).equals(
										adStatistics.getDate())) {
							AdvertisementStatistics total = totalMap.get(adStatistics.getChannel());
							if (total == null) {
								total = new AdvertisementStatistics();
								total.setPlan(adStatistics.getPlan());
								total.setDate(incDate(advertisementStatistics.getQueryStartTime(), i));
								total.setChannel(adStatistics.getChannel());
								total.addClickCount(adStatistics.getClickCount());
								total.addShowCount(adStatistics.getShowCount());
								totalMap.put(adStatistics.getAdvertisementId(), total);
								continue;
							}
							total.addClickCount(adStatistics.getClickCount());
							total.addShowCount(adStatistics.getShowCount());
						}
					}
					result.addAll(totalMap.values());
				}
			}
		}
		return result;
	}

	private AdvertisementStatistics createPlanStatistic(int diffDate, Boolean pv, Long planId, String channel) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		AdvertisementStatistics result = new AdvertisementStatistics();
		result.setPlanId(planId);
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (pv != null && pv) {
			dataPlanTypeFunction(AdvertisementStatisticsType.PV_DATA, date, channel, resultList, planId);
		} else {
			dataPlanTypeFunction(AdvertisementStatisticsType.UV_DATA, date, channel, resultList, planId);
		}
		for (AdvertisementStatistics adStatistics : resultList) {
			if (adStatistics.getPlanId().equals(planId)) {
				result.addClickCount(adStatistics.getClickCount());
				result.addShowCount(adStatistics.getShowCount());
			}
		}
		result.setTotal(calculateTotal(result));
		return result;
	}

	private void addPlanToResultMap(Map<Long, AdvertisementStatistics> resultMap, Long planId,
			AdvertisementStatistics click) {
		AdvertisementStatistics exists = resultMap.get(planId);
		if (exists != null) {
			exists.addClickCount(click.getClickCount());
			exists.addShowCount(click.getShowCount());
		} else {
			resultMap.put(planId, click);
		}
	}

	@Override
	public List<AdvertisementStatistics> selectMediaAdvertisementStatistic(
			AdvertisementStatistics advertisementStatistics, Boolean pv, String channelKey, Long projectId) {
		Probability probability = new Probability();
		probability.setProjectId(projectId);
		probability.setStatus(CommonStatus.ONLINE);
		int diffDate = getDateDiff(advertisementStatistics);
		Map<String, AdvertisementStatistics> resultMap = new HashMap<String, AdvertisementStatistics>();
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		List<Probability> list = probabilityService.selectProbabilitys(probability, new PageBounds());
		// 查找当天数据
		if (diffDate == 0) {
			if (StringUtils.isNotBlank(channelKey)) {
				for (Probability pro : list) {
					if (channelKey.equals(pro.getChannel())) {
						Plan plan = planService.selectPlan(pro.getPlanId());
						AdvertisementStatistics statistic = createPlanStatistic(diffDate, pv, pro.getPlanId(),
								channelKey);
						statistic.setPlan(plan);
						result.add(statistic);
					}
				}
			} else {
				for (Probability pro : list) {
					AdvertisementStatistics statistic = createMediaAdvertisementStatistic(diffDate, pv,
							pro.getChannel(), pro.getPlanId());
					addMediaToResultMap(resultMap, pro.getChannel(), statistic);
				}
				result.addAll(resultMap.values());
			}
		}
		if (diffDate > 0) {
			List<AdvertisementStatistics> resultInDB = selectStatistic(advertisementStatistics, pv);
			if (StringUtils.isNotBlank(channelKey)) {
				result = resultInDB;
			} else {
				for (int i = 0, len = getDays(advertisementStatistics); i < len; i++) {
					Map<String, AdvertisementStatistics> totalMap = new HashMap<String, AdvertisementStatistics>();
					for (AdvertisementStatistics adStatistics : resultInDB) {
						if (advertisementStatistics.getQueryStartTime() != null
								&& incDate(advertisementStatistics.getQueryStartTime(), i).equals(
										adStatistics.getDate())) {
							AdvertisementStatistics total = totalMap.get(adStatistics.getChannel());
							if (total == null) {
								total = new AdvertisementStatistics();
								total.setDate(incDate(advertisementStatistics.getQueryStartTime(), i));
								total.setChannel(adStatistics.getChannel());
								total.addClickCount(adStatistics.getClickCount());
								total.addShowCount(adStatistics.getShowCount());
								totalMap.put(adStatistics.getChannel(), total);
								continue;
							}
							total.addClickCount(adStatistics.getClickCount());
							total.addShowCount(adStatistics.getShowCount());
						}
					}
					result.addAll(totalMap.values());
				}
			}
		}
		return result;
	}

	private void addMediaToResultMap(Map<String, AdvertisementStatistics> resultMap, String channel,
			AdvertisementStatistics click) {
		AdvertisementStatistics exists = resultMap.get(channel);
		if (exists != null) {
			exists.addClickCount(click.getClickCount());
			exists.addShowCount(click.getShowCount());
		} else {
			resultMap.put(channel, click);
		}
	}

	private AdvertisementStatistics createMediaAdvertisementStatistic(int diffDate, Boolean pv, String channelKey,
			long planId) {
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDate));
		AdvertisementStatistics result = new AdvertisementStatistics();
		result.setChannel(channelKey);
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (pv != null && pv) {
			dataPlanTypeFunction(AdvertisementStatisticsType.PV_DATA, date, channelKey, resultList, planId);
		} else {
			dataPlanTypeFunction(AdvertisementStatisticsType.UV_DATA, date, channelKey, resultList, planId);
		}
		for (AdvertisementStatistics adStatistics : resultList) {
			if (adStatistics.getChannel() != null && adStatistics.getChannel() != ""
					&& adStatistics.getChannel().equals(channelKey)) {
				// if (advertisementId != adStatistics.getAdvertisementId()) {
				// result.setAdvertisementId(adStatistics.getAdvertisementId());
				// result.setAdvertisement(advertisementService.selectAdvertisement(adStatistics.getAdvertisementId()));
				// }
				result.addShowCount(adStatistics.getShowCount());
				result.addClickCount(adStatistics.getClickCount());
			}
		}
		return result;
	}
}
