package com.yuanshanbao.ad.statistics.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.channel.model.Channel;
import com.yuanshanbao.ad.common.constant.RedisConstant;
import com.yuanshanbao.ad.common.redis.base.RedisService;
import com.yuanshanbao.ad.config.ConfigManager;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.ad.statistics.dao.CommonStatisticsDao;
import com.yuanshanbao.ad.statistics.dao.StatisticsDao;
import com.yuanshanbao.ad.statistics.model.Statistics;
import com.yuanshanbao.ad.statistics.model.StatisticsStatus;
import com.yuanshanbao.ad.statistics.model.StatisticsType;
import com.yuanshanbao.ad.weixin.service.WeixinService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsDao statisticsDao;

	@Autowired
	private CommonStatisticsDao commonStatisticsDao;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private RedisService redisCacheService;

	@Override
	public void insertStatistics(Statistics statistics) {
		int result = -1;

		result = statisticsDao.insertStatistics(statistics);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateStatistics(Statistics statistics) {
		int result = -1;

		result = statisticsDao.updateStatistics(statistics);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteStatistics(Statistics statistics) {
		int result = -1;

		result = statisticsDao.deleteStatistics(statistics);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Statistics> selectStatistics(Statistics statistics,
			PageBounds pageBounds) {
		return setProperty(statisticsDao.selectStatistics(statistics,
				pageBounds));
	}

	private List<Statistics> setProperty(List<Statistics> list) {
		List<Long> merchantIds = new ArrayList<Long>();
		List<Long> productIds = new ArrayList<Long>();
		for (Statistics statistics : list) {
			productIds.add(statistics.getProductId());
			merchantIds.add(statistics.getMerchantId());
		}

		Map<Long, Merchant> merchantMap = merchantService
				.selectMerchantByIds(merchantIds);
		for (Statistics statistics : list) {
			statistics.setMerchant(merchantMap.get(statistics.getMerchantId()));
		}
		return list;

	}

	@Override
	public Statistics selectStatisticsById(Long statisticsId) {
		if (statisticsId == null) {
			return null;
		}
		Statistics statistics = new Statistics();
		statistics.setStatisticsId(statisticsId);
		List<Statistics> list = selectStatistics(statistics, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Statistics> selectStatisticsByIds(List<Long> statisticsIds) {
		return statisticsDao.selectStatisticsByIds(statisticsIds);
	}

	@Override
	public void runAndInsertStatistics(int dateDiff) {
		try {
			List<Statistics> list = intervalStatistics(dateDiff, false);
			String date = DateUtils.format(DateUtils.addDays(new Date(),
					-dateDiff));
			for (Statistics stat : list) {
				Statistics param = new Statistics();
				stat.setStatus(StatisticsStatus.ALREADY);
				stat.setType(StatisticsType.CHANNEL_ORIGINAL_DATA);
				stat.setDate(date);
				if (stat.getChannel() == null) {
					stat.setChannel("");
				}
				param.setProductId(stat.getProductId());
				param.setChannel(stat.getChannel());
				param.setDate(date);
				param.setType(StatisticsType.CHANNEL_ORIGINAL_DATA);
				List<Statistics> existList = selectStatistics(param,
						new PageBounds());
				if (existList.size() > 0) {
					stat.setStatisticsId(existList.get(0).getStatisticsId());
					updateStatistics(stat);
				} else {
					insertStatistics(stat);
				}
				Channel channel = ConfigManager.getChannel(stat.getChannel());
				if (channel != null && channel.getBonus() != null) {
					stat.setStatisticsId(null);
					stat.calculateBonus(channel.getBonus());
					stat.setStatus(StatisticsStatus.INIT);
					stat.setType(StatisticsType.CHANNEL_WORKING_DATA);
					param.setType(StatisticsType.CHANNEL_WORKING_DATA);
					existList = selectStatistics(param, new PageBounds());
					if (existList.size() > 0) {
						Statistics existStat = existList.get(0);
						if (existStat.getStatus() != StatisticsStatus.ALREADY) {
							stat.setStatisticsId(existStat.getStatisticsId());
							updateStatistics(stat);
						}
					} else {
						insertStatistics(stat);
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[runAndInsertStatistics error]", e);
		}
	}

	public List<Statistics> getRealtimeStatistics(List<String> channelList) {
		List<Statistics> resultList = new ArrayList<Statistics>();
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			String date = setDateDiff(0, parameterMap);

			parameterMap.put("channelList", channelList);
			Map<String, Statistics> resultMap = new HashMap<String, Statistics>();
			calculate(resultMap,
					commonStatisticsDao.selectDownloadCount(parameterMap));
			calculate(resultMap,
					commonStatisticsDao.selectRegisterCount(parameterMap));
			calculate(resultMap,
					commonStatisticsDao.selectFirstLoginCount(parameterMap));
			calculate(resultMap,
					commonStatisticsDao.selectApplyCount(parameterMap));
			calculate(resultMap,
					commonStatisticsDao.selectApplyUserCount(parameterMap));
			calculate(resultMap,
					commonStatisticsDao.selectApplySuccessCount(parameterMap));

			calculateUVAndPv(resultMap, date);

			resultList.addAll(resultMap.values());
			Collections.sort(resultList, new Comparator<Statistics>() {
				@Override
				public int compare(Statistics arg0, Statistics arg1) {
					return arg1.getRegisterCount() - arg0.getRegisterCount();
				}
			});
			for (Statistics statistics : resultList) {
				Channel channel = ConfigManager.getChannel(statistics
						.getChannel());
				statistics.setDate(date);
				if (channel != null && channel.getBonus() != null) {
					statistics.calculateBonus(channel.getBonus());
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[calculate_realtime_statistics_error]", e);
		}
		return resultList;
	}

	private void calculateUVAndPv(Map<String, Statistics> resultMap, String date) {
		Long startTime = System.currentTimeMillis();
		Set<String> channelList = redisCacheService.smembers(RedisConstant
				.getAdvertisementChannelKey(date));
		for (String channel : channelList) {
			String ret = (String) redisCacheService.get(RedisConstant
					.getAdvertisementChannelClickCountKey(date, "download", "",
							channel));
			String pv = (String) redisCacheService.get(RedisConstant
					.getAdvertisementChannelClickPVCountKey(date, "download",
							"", channel));
			int count = 0;
			int pvCount = 0;
			if (ValidateUtil.isNumber(ret)) {
				count = Integer.parseInt(ret);
			}
			if (ValidateUtil.isNumber(pv)) {
				pvCount = Integer.parseInt(pv);
			}
			Statistics stat = resultMap.get(channel);
			if (stat != null) {
				stat.setUvCount(count);
				stat.setPvCount(pvCount);
			}
		}
		Long endTime = System.currentTimeMillis();
		LoggerUtil.info("[statistics_redis_time] time={}ms",
				(endTime - startTime));
	}

	private String setDateDiff(int dateDiff, Map<String, Object> parameterMap) {
		String start = DateUtils.format(DateUtils
				.addDays(new Date(), -dateDiff));
		parameterMap.put("createTimeStart", start);
		parameterMap.put("createTimeEnd", DateUtils.dateInc(start, "D"));
		return start;
	}

	@Override
	public Map<String, Statistics> selectApplyStatistics(Map<String, Object> map) {
		Map<String, Statistics> resultMap = new HashMap<String, Statistics>();
		if (map == null || map.size() == 0) {
			return resultMap;
		}
		List<Statistics> applyStatistics = commonStatisticsDao
				.selectApplyCount(map);
		for (Statistics statistics : applyStatistics) {
			resultMap.put(statistics.getChannel(), statistics);
		}
		return resultMap;
	}

	@Override
	public List<Statistics> selectMonthStatistics(List<String> channelList,
			String date) {
		List<Statistics> resultList = new ArrayList<Statistics>();
		if (StringUtils.isBlank(date)) {
			date = DateUtils.format(new Date(), "yyyy-MM");
		}
		Statistics statistics = new Statistics();
		statistics.setStatus(CommonStatus.ONLINE);
		statistics.setChannelList(channelList);
		statistics.setType(StatisticsType.CHANNEL_WORKING_DATA);
		statistics.setQueryDate(date);
		List<Statistics> statisticsList = selectStatistics(statistics,
				new PageBounds());
		Iterator<Statistics> it = statisticsList.iterator();
		Map<String, Statistics> map = new HashMap<String, Statistics>();
		while (it.hasNext()) {
			Statistics exist = it.next();
			if (exist == null) {
				continue;
			}
			Statistics param = map.get(exist.getChannel());
			if (param == null) {
				param = new Statistics();
				map.put(exist.getChannel(), param);
			}
			param.setChannel(exist.getChannel());
			param.setUvCount(param.getUvCount() + exist.getUvCount());
			param.add(exist);

		}
		resultList.addAll(map.values());
		return resultList;
	}

	@Override
	public List<Statistics> intervalStatistics(List<String> channelList,
			int dateDiff, boolean fromDB, boolean hasAllStatistics) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		if (channelList != null) {
			parameterMap.put("channelList", channelList);
		}
		String date = setDateDiff(dateDiff, parameterMap);
		return getStatistics(channelList, parameterMap, dateDiff > 0 && fromDB,
				hasAllStatistics, date);
	}

	private List<Statistics> getStatistics(List<String> channelList,
			Map<String, Object> parameterMap, boolean fromDB,
			boolean hasAllStatistics, String date) {
		List<Statistics> resultList = new ArrayList<Statistics>();
		if (fromDB) {
			Statistics param = new Statistics();
			param.setDate(date);
			if (channelList != null) {
				param.setChannelList(channelList);
			}
			param.setType(StatisticsType.CHANNEL_ORIGINAL_DATA);
			resultList = selectStatistics(param, new PageBounds());
		}
		if (resultList.size() == 0) {
			Map<String, Statistics> resultMap = new HashMap<String, Statistics>();

			Long startTime = System.currentTimeMillis();
			calculate(resultMap,
					commonStatisticsDao.selectRegisterCount(parameterMap));
			LoggerUtil.info("[statistics_use_time] registerTime={}ms",
					System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			calculate(resultMap,
					commonStatisticsDao.selectApplyCount(parameterMap));
			LoggerUtil.info("[statistics_use_time] applyTime={}ms",
					System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			calculate(resultMap,
					commonStatisticsDao.selectDownloadCount(parameterMap));
			LoggerUtil.info("[statistics_use_time] downloadTime={}ms",
					System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			calculate(resultMap,
					commonStatisticsDao.selectApplyUserCount(parameterMap));
			LoggerUtil.info("[statistics_use_time] ApplyUserTime={}ms",
					System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			calculate(resultMap,
					commonStatisticsDao.selectFirstLoginCount(parameterMap));
			LoggerUtil.info("[statistics_use_time] firstTime={}ms",
					System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			calculate(resultMap,
					commonStatisticsDao.selectApplySuccessCount(parameterMap));
			LoggerUtil.info("[statistics_use_time] applySuccessTime={}ms",
					System.currentTimeMillis() - startTime);

			calculateUVAndPv(resultMap, date);
			resultList.addAll(resultMap.values());
		}
		Collections.sort(resultList, new Comparator<Statistics>() {
			@Override
			public int compare(Statistics arg0, Statistics arg1) {
				return arg1.getRegisterCount() - arg0.getRegisterCount();
			}
		});
		Statistics total = new Statistics();
		total.setChannel("总数");
		for (Statistics statisticsInsurant : resultList) {
			total.add(statisticsInsurant);
			if ("总数".equals(statisticsInsurant.getChannel())) {
				return resultList;
			}
		}
		resultList.add(0, total);
		return resultList;
	}

	private void calculate(Map<String, Statistics> result, List<Statistics> list) {
		for (Statistics statistics : list) {
			Statistics total = result.get(statistics.getChannel());
			if (total == null) {
				total = new Statistics();
				total.setChannel(statistics.getChannel());
				result.put(statistics.getChannel(), total);
			}
			total.add(statistics);
		}
	}

	@Override
	public List<Statistics> intervalStatistics(int dateDiff, boolean fromDB) {
		return intervalStatistics(null, dateDiff, fromDB, true);
	}

	@Override
	public Map<String, Statistics> selectDownloadStatistics(
			Map<String, Object> map) {
		Map<String, Statistics> resultMap = new HashMap<String, Statistics>();
		if (map == null || map.size() == 0) {
			return resultMap;
		}
		List<Statistics> applyStatistics = commonStatisticsDao
				.selectDownloadCount(map);
		for (Statistics statistics : applyStatistics) {
			resultMap.put(statistics.getChannel(), statistics);
		}
		return resultMap;
	}

	@Override
	public Map<String, Statistics> selectApplyUserStatistics(
			Map<String, Object> map) {
		Map<String, Statistics> resultMap = new HashMap<String, Statistics>();
		if (map == null || map.size() == 0) {
			return resultMap;
		}
		List<Statistics> applyStatistics = commonStatisticsDao
				.selectApplyUserCount(map);
		for (Statistics statistics : applyStatistics) {
			resultMap.put(statistics.getChannel(), statistics);
		}
		return resultMap;
	}

	@Override
	public Map<String, Statistics> selectFirstLoginStatistics(
			Map<String, Object> map) {
		Map<String, Statistics> resultMap = new HashMap<String, Statistics>();
		if (map == null || map.size() == 0) {
			return resultMap;
		}
		List<Statistics> applyStatistics = commonStatisticsDao
				.selectFirstLoginCount(map);
		for (Statistics statistics : applyStatistics) {
			resultMap.put(statistics.getChannel(), statistics);
		}
		return resultMap;
	}

	@Override
	public void runAndInsertProductStatistics(int diffDay) {
		List<Statistics> list = intervalProductStatistics(diffDay, false);
		String date = DateUtils.format(DateUtils.addDays(new Date(), -diffDay));
		for (Statistics stat : list) {
			Statistics param = new Statistics();
			param.setMerchantId(stat.getMerchantId());
			param.setProductId(stat.getProductId());
			param.setProductName(stat.getProductName());
			param.setDate(date);
			param.setType(StatisticsType.PRODUCT_DATA);
			stat.setStatus(StatisticsStatus.ALREADY);
			stat.setDate(date);
			stat.setType(StatisticsType.PRODUCT_DATA);
			List<Statistics> existList = selectStatistics(param,
					new PageBounds());
			if (existList.size() > 0) {
				stat.setStatisticsId(existList.get(0).getStatisticsId());
				updateStatistics(stat);
			} else {
				insertStatistics(stat);
			}
		}
	}

	@Override
	public List<Statistics> intervalProductStatistics(int diffDay,
			boolean fromDB) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		String date = setDateDiff(diffDay, parameterMap);
		List<Statistics> resultList = new ArrayList<Statistics>();
		if (diffDay > 0 && fromDB) {
			Statistics param = new Statistics();
			param.setDate(date);
			param.setType(StatisticsType.PRODUCT_DATA);
			resultList = selectStatistics(param, new PageBounds());
		}
		if (resultList.size() == 0) {
			Map<String, Statistics> resultMap = new HashMap<String, Statistics>();
			calculateProduct(resultMap,
					commonStatisticsDao.selectApplyCount(parameterMap));
			calculateProduct(resultMap,
					commonStatisticsDao.selectApplySuccessCount(parameterMap));
			calculateProdcutDetailUVAndPv(resultMap, date);
			resultList.addAll(resultMap.values());
		}
		Collections.sort(resultList, new Comparator<Statistics>() {
			@Override
			public int compare(Statistics arg0, Statistics arg1) {
				return arg1.getApplyCount() - arg0.getApplyCount();
			}
		});
		Statistics total = new Statistics();
		total.setProductName("总数");
		for (Statistics statisticsInsurant : resultList) {
			total.add(statisticsInsurant);
			if ("总数".equals(statisticsInsurant.getProductName())) {
				return resultList;
			}
		}
		resultList.add(0, total);
		return resultList;
	}

	private void calculateProduct(Map<String, Statistics> result,
			List<Statistics> list) {

		List<Long> productIds = new ArrayList<Long>();
		for (Statistics statistics : list) {
			if (statistics == null || statistics.getProductId() == null) {
				continue;
			}
			productIds.add(statistics.getProductId());
		}

		for (Statistics statistics : list) {
			String remark = "";
			// if (StringUtils.isNotBlank(statistics.getRemark())) {
			// remark = statistics.getRemark();
			// }
			Statistics total = result.get(statistics.getProductId() + remark);
			if (total == null) {
				total = new Statistics();
				total.setProductId(statistics.getProductId());
				// 从常量取product，会出现下午删除的产品，没有当日统计，改为先取出来

				if (remark != null) {
					total.setProductName(total.getProductName() + remark);
				}
				result.put(statistics.getProductId() + remark, total);
			}
			total.add(statistics);
		}
	}

	private void calculateProdcutDetailUVAndPv(
			Map<String, Statistics> resultMap, String date) {
		for (Statistics statistics : resultMap.values()) {
			if (statistics == null) {
				continue;
			}

			String uv = "";
			int uvCount = 0;
			if (ValidateUtil.isNumber(uv)) {
				uvCount = Integer.parseInt(uv);
			}
			statistics.setProductDetailUvCount(uvCount);
		}
	}
}
