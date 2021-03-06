package com.yuanshanbao.dsp.advertisement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.dao.AdvertisementDao;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.model.MediaInformation;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementVo;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.model.BillType;
import com.yuanshanbao.dsp.bill.service.BillService;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.dsp.position.service.PositionService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.tags.service.TagsService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	private static final String ADVERTIEMENT_INCREASE_RANGE = "3,7";

	private static final String ADVERTIEMENT_INITIAL_RANGE = "3000,5000";

	@Autowired
	private AdvertisementDao advertisementDao;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private RedisService redisCacheService;

	@Autowired
	private TagsService tagsService;

	@Autowired
	private AdvertisementCategoryService categoryService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private AdvertisementStrategyService strategyService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private BillService billService;

	@Override
	public void insertAdvertisement(Advertisement advertisement) {

		int result = -1;

		result = advertisementDao.insertAdvertisement(advertisement);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateAdvertisement(Advertisement advertisement) {

		int result = -1;

		result = advertisementDao.updateAdvertisement(advertisement);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteAdvertisement(Advertisement advertisement) {

		int result = -1;

		result = advertisementDao.deleteAdvertisement(advertisement);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Advertisement> selectAdvertisement(Advertisement advertisement, PageBounds pageBounds) {
		return setProperty(advertisementDao.selectAdvertisement(advertisement, pageBounds));
	}

	private List<Advertisement> setProperty(List<Advertisement> list) {
		List<Long> advertiserIds = new ArrayList<Long>();
		for (Advertisement advertisement : list) {
			advertiserIds.add(advertisement.getAdvertiserId());
		}

		Map<Long, Advertiser> map = advertiserService.selectAdvertiserByIds(advertiserIds);
		for (Advertisement advertisement : list) {
			advertisement.setAdvertiser(map.get(advertisement.getAdvertiserId()));
		}
		return list;
	}

	@Override
	public Map<Long, Advertisement> selectAdvertisementByIds(List<Long> advertisementIds) {
		Map<Long, Advertisement> map = new HashMap<Long, Advertisement>();
		if (advertisementIds == null || advertisementIds.size() == 0) {
			return map;
		}
		List<Advertisement> list = advertisementDao.selectAdvertisementByIds(advertisementIds);
		List<Advertisement> result = setProperty(list);
		for (Advertisement ad : result) {
			map.put(ad.getAdvertisementId(), ad);
		}
		return map;
	}

	@Override
	public Advertisement selectAdvertisement(Long advertisementId) {
		if (advertisementId == null) {
			return null;
		}
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvertisementId(advertisementId);
		List<Advertisement> list = selectAdvertisement(advertisement, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public Advertisement selectAdvertisement(Advertisement advertisement) {
		if (advertisement == null) {
			return null;
		}
		List<Advertisement> list = selectAdvertisement(advertisement, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Advertisement> selectAdvertisementByAdvertiserIds(Long advertserIds) {
		if (advertserIds == null) {
			return null;
		}
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvertiserId(advertserIds);
		List<Advertisement> list = selectAdvertisement(advertisement, new PageBounds());
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public Long getAdvertisementCount(Long advertisementId) {
		String key = RedisConstant.getAdvertisementGetCountKey(advertisementId);
		String str = (String) redisCacheService.get(key);
		long count = 0;
		if (ValidateUtil.isNumber(str)) {
			count = Long.parseLong(str);
		}
		if (count == 0) {
			count = (long) getRandomFromRange(ADVERTIEMENT_INITIAL_RANGE);
			redisCacheService.set(key, count + "");
		}
		return count;
	}

	@Override
	public void increaseAdvertisementCount(Long projectId, Long advertisementId, Long positionId) {
		List<Long> advertisementIdList = new ArrayList<Long>();
		advertisementIdList.add(advertisementId);
		recordAdvertisementCount(projectId, positionId, advertisementIdList, true);
	}

	@Override
	public void increaseAdvertisementShowCount(Long projectId, Long advertisementId, Long positionId) {
		List<Long> advertisementIdList = new ArrayList<Long>();
		advertisementIdList.add(advertisementId);
		recordAdvertisementCount(projectId, positionId, advertisementIdList, false);
	}

	private Integer getRandomFromRange(String numRange) {
		int random = 0;
		String[] s = numRange.split(",");
		if (s.length == 2) {
			double min = Double.parseDouble(s[0]);
			double max = Double.parseDouble(s[1]);
			random = (int) (Math.random() * (max - min + 1) + min);
		}
		return random;
	}

	public List<Advertisement> getAdvertisement(Long projectId, Long positionId, MediaInformation instance) {
		List<Advertisement> advertismentList = new ArrayList<Advertisement>();
		Position position = positionService.selectPosition(positionId);
		if (position == null) {
			return advertismentList;
		}
		List<Probability> probabilityList = probabilityService.selectProbabilityFromCache(projectId, positionId, null);
		Map<Long, Probability> advertisementIdMap = new LinkedHashMap<Long, Probability>();
		for (Probability probability : probabilityList) {
			if (probability.getStartTime() != null) {
				if (probability.getStartTime().after(new Date())) {
					continue;
				}
			}
			if (probability.getEndTime() != null) {
				if (probability.getEndTime().before(new Date())) {
					continue;
				}
			}
			advertisementIdMap.put(probability.getAdvertisementId(), probability);
		}
		if (advertisementIdMap.size() == 0) {
			return advertismentList;
		}
		List<Quota> quotaList = quotaService.selectQuotaFromCache(projectId, positionId, new ArrayList<Long>(
				advertisementIdMap.keySet()));
		for (Quota quota : quotaList) {
			if (quota.getCount() != null && quota.getCount() > 0) {
				String countValue = redisCacheService.get(RedisConstant.getQuotaCount(quota.getQuotaId()));
				if (ValidateUtil.isNumber(countValue)) {
					Integer currentCount = Integer.parseInt(countValue);
					if (currentCount > quota.getCount()) {
						advertisementIdMap.remove(quota.getAdvertisementId());
					}
				}
			}
			if (quota.getStartTime() != null) {
				if (quota.getStartTime().after(new Date())) {
					advertisementIdMap.remove(quota.getAdvertisementId());
				}
			}
			if (quota.getEndTime() != null) {
				if (quota.getEndTime().before(new Date())) {
					advertisementIdMap.remove(quota.getAdvertisementId());
				}
			}
		}
		List<Long> resultAdvertisementIdList = new ArrayList<Long>();
		Double totalProbability = 0D;
		for (Probability probability : advertisementIdMap.values()) {
			if (resultAdvertisementIdList.size() < position.getCount()) {
				if (probability.getProbability() == null) {
					resultAdvertisementIdList.add(probability.getAdvertisementId());
				} else if (advertisementIdMap.size() <= position.getCount()) {
					resultAdvertisementIdList.add(probability.getAdvertisementId());
				} else {
					totalProbability += probability.getProbability();
				}
			} else {
				break;
			}
		}
		for (int i = 0; i < 5; i++) {

			if (resultAdvertisementIdList.size() >= position.getCount() || totalProbability <= 0) {
				break;
			}
			for (Probability probability : advertisementIdMap.values()) {
				if (resultAdvertisementIdList.size() < position.getCount()) {
					if (probability.getProbability() != null) {
						double random = Math.random() * totalProbability;
						if (random < probability.getProbability()) {
							resultAdvertisementIdList.add(probability.getAdvertisementId());
							totalProbability -= probability.getProbability();
						}
					}
				} else {
					break;
				}
			}
		}

		// 判断广告策略
		List<AdvertisementStrategy> strategyList = strategyService.selectAdvertisementStrategyFromCache(projectId);
		resultAdvertisementIdList = strategyService.getAvailableAdvertisementList(resultAdvertisementIdList,
				strategyList, instance);

		Map<Long, Advertisement> advertisementMap = selectAdvertisementByIds(resultAdvertisementIdList);
		for (Long advertisementId : resultAdvertisementIdList) {
			advertismentList.add(advertisementMap.get(advertisementId));
		}
		return advertismentList;
	}

	@Override
	public Map<String, Object> countAdvertisementSize(Advertisement advertisement) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Advertisement> advertisements = this.selectAdvertisement(advertisement, new PageBounds());
		int count = 0;
		for (Advertisement adv : advertisements) {
			if (adv.getStatusValue().equals("已失效")) {
				count++;
			}
		}
		result.put("total", advertisements.size());
		result.put("use", advertisements.size() - count);
		result.put("down", count);
		return result;
	}

	private void recordAdvertisementCount(Long projectId, Long positionId, List<Long> advertisementIdList,
			boolean isClick) {
		List<Quota> quotaList = quotaService.selectQuotaFromCache(projectId, positionId, advertisementIdList);
		for (Quota quota : quotaList) {
			if (isClick) {
				redisCacheService.incr(RedisConstant.getAdvertisementClickCountKey(quota.getAdvertisementId(),
						positionId));
				if (QuotaType.CPC.equals(quota.getType())) {
					redisCacheService.incr(RedisConstant.getQuotaCount(quota.getQuotaId()));
				}
			} else {
				redisCacheService.incr(RedisConstant.getAdvertisementShowCountKey(quota.getAdvertisementId(),
						positionId));
				if (QuotaType.CPM.equals(quota.getType()) || QuotaType.CPT.equals(quota.getType())) {
					redisCacheService.incr(RedisConstant.getQuotaCount(quota.getQuotaId()));
				}
			}
		}
	}

	// 查找奖品信息
	public List<AdvertisementVo> selectGift(String activityId, String channel) {
		Map<Long, Probability> proMap = new HashMap<Long, Probability>();
		Map<Long, Quota> quotaMap = new HashMap<Long, Quota>();
		Probability probability = new Probability();
		Quota quota = new Quota();
		List<AdvertisementVo> result = new ArrayList<AdvertisementVo>();
		List<Long> advertisementIds = new ArrayList<Long>();
		Map<Long, Advertisement> adMap = new HashMap<Long, Advertisement>();

		probability.setActivityId(Long.valueOf(activityId));
		quota.setActivityId(Long.valueOf(activityId));
		if (!StringUtils.isBlank(channel)) {
			probability.setChannel(channel);
			quota.setChannel(channel);
		} else {
			probability.setChannel("");
			quota.setChannel("");
		}
		List<Probability> probabilityList = probabilityService.selectProbabilitys(probability, new PageBounds());
		List<Quota> quotaList = quotaService.selectQuota(quota, new PageBounds());
		for (Probability pro : probabilityList) {
			proMap.put(pro.getAdvertisementId(), pro);
			advertisementIds.add(pro.getAdvertisementId());
		}
		for (Quota quo : quotaList) {
			quotaMap.put(quo.getAdvertisementId(), quo);
		}
		adMap = selectAdvertisementByIds(advertisementIds);
		for (Long advertisementId : advertisementIds) {
			AdvertisementVo adVo = new AdvertisementVo(adMap.get(advertisementId));
			adVo.setProbability(proMap.get(advertisementId));
			adVo.setQuota(quotaMap.get(advertisementId));
			result.add(adVo);
		}
		return result;
	}

	public List<Advertisement> getGift(Long projectId, String activityKey, String channelKey, MediaInformation instance) {
		List<Advertisement> advertismentList = new ArrayList<Advertisement>();
		List<Probability> probabilityList = probabilityService.selectProbabilityByKeyFromCache(projectId, activityKey,
				channelKey, null);
		Map<Long, Probability> advertisementIdMap = new LinkedHashMap<Long, Probability>();
		for (Probability probability : probabilityList) {
			if (probability.getStartTime() != null) {
				if (probability.getStartTime().after(new Date())) {
					continue;
				}
			}
			if (probability.getEndTime() != null) {
				if (probability.getEndTime().before(new Date())) {
					continue;
				}
			}
			advertisementIdMap.put(probability.getAdvertisementId(), probability);
		}
		if (advertisementIdMap.size() == 0) {
			return advertismentList;
		}

		List<Quota> quotaList = quotaService.selectQuotaByKeyFromCache(projectId, activityKey, channelKey);

		for (Quota quota : quotaList) {
			// TODO 走活动下默认的quota数量判断
			if (quota.getCount() != null && quota.getCount() > 0) {
				String countValue = "";
				if (QuotaType.CPC.equals(quota.getType())) {
					countValue = redisCacheService.get(RedisConstant.getAdvertisementClickCountPVKey(null,
							quota.getAdvertisementId() + "", quota.getChannel()));
				}
				if (QuotaType.CPM.equals(quota.getType())) {
					countValue = redisCacheService.get(RedisConstant.getAdvertisementShowCountPVKey(null,
							quota.getAdvertisementId() + "", quota.getChannel()));
				}
				if (QuotaType.CPT.equals(quota.getType())) {
					countValue = redisCacheService.get(RedisConstant.getAdvertisementShowCountPVKey(null,
							quota.getAdvertisementId() + "", quota.getChannel()));
				}
				if (ValidateUtil.isNumber(countValue)) {
					Integer currentCount = Integer.parseInt(countValue);
					if (currentCount > quota.getCount()) {
						advertisementIdMap.remove(quota.getAdvertisementId());
					}
				}
			}
			if (quota.getStartTime() != null) {
				if (quota.getStartTime().after(new Date())) {
					advertisementIdMap.remove(quota.getAdvertisementId());
				}
			}
			if (quota.getEndTime() != null) {
				if (quota.getEndTime().before(new Date())) {
					advertisementIdMap.remove(quota.getAdvertisementId());
				}
			}
		}

		List<Long> resultAdvertisementIdList = new ArrayList<Long>();
		Double totalProbability = 0D;
		for (Probability probability : advertisementIdMap.values()) {
			if (probability.getProbability() == null) {
				continue;
			} else {
				totalProbability += probability.getProbability();
			}
		}

		for (Probability probability : advertisementIdMap.values()) {
			if (probability.getProbability() != null) {
				double random = Math.random() * totalProbability;
				if (random < probability.getProbability()) {
					resultAdvertisementIdList.add(probability.getAdvertisementId());
					break;
				} else {
					totalProbability -= probability.getProbability();
				}
			} else {
				continue;
			}
		}
		Map<Long, Advertisement> advertisementMap = selectAdvertisementByIds(resultAdvertisementIdList);
		for (Long advertisementId : resultAdvertisementIdList) {
			advertismentList.add(advertisementMap.get(advertisementId));
		}

		return advertismentList;
	}

	private void recordAdvertisementCount(Long projectId, String activityKey, String channel, boolean isClick) {
		List<Quota> quotaList = quotaService.selectQuotaByKeyFromCache(projectId, activityKey, channel);
		for (Quota quota : quotaList) {
			if (isClick) {
				if (QuotaType.CPC.equals(quota.getType())) {
					redisCacheService.incr(RedisConstant.getQuotaCount(quota.getQuotaId()));
				}
			} else {
				if (QuotaType.CPM.equals(quota.getType()) || QuotaType.CPT.equals(quota.getType())) {
					redisCacheService.incr(RedisConstant.getQuotaCount(quota.getQuotaId()));
				}
			}
		}
	}

	@Override
	public void paymentForAdvertisement(Advertisement advertisement) {
		Probability params = new Probability();
		params.setAdvertisementId(advertisement.getAdvertisementId());
		List<Probability> list = probabilityService.selectProbabilitys(params, new PageBounds());
		for (Probability probability : list) {
			// 总消耗
			double nowCount = getCount(RedisConstant
					.getProbabilityBalanceCountKey(null, probability.getProbabilityId()));
			// 上次操作扣费时消耗的金额
			double lastCount = getCount(RedisConstant.getProbabilityLastBalanceCountKey(null,
					probability.getProbabilityId()));
			double difference = BigDecimal.valueOf(nowCount).subtract(BigDecimal.valueOf(lastCount)).doubleValue();
			if (difference > 0) {
				billService.checkBillAndCount(advertisement, probability, lastCount);
				billService.createBill(advertisement, probability, nowCount, lastCount, BillType.DEDUCTION);
				redisService.set(RedisConstant.getProbabilityLastBalanceCountKey(null, probability.getProbabilityId()),
						String.valueOf(nowCount));
				// 该计划当日消耗金额，用于日限额限制
				// redisService.increByDouble(RedisConstant.getPlanDayBalanceCountKey(null,
				// plan.getPlanId()), difference);
				// 计划总金额消耗
				// redisService.increByDouble(RedisConstant.getPlanBalanceCountKey(plan.getPlanId()),
				// difference);
				// 订单总金额消耗
				// redisService.increByDouble(RedisConstant.getOrderBalanceCountKey(plan.getOrderId()),
				// difference);
			}
		}
		// calculateIncrement(plan.getPlanId());
	}

	private double getCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isPositiveFloat(str)) {
			return Double.parseDouble(str);
		}
		return 0;
	}
}
