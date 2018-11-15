package com.yuanshanbao.dsp.probability.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.AESUtils;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementDisplayType;
import com.yuanshanbao.dsp.advertisement.model.MediaInformation;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementDetails;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementStrategyService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.DspConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.location.service.IpLocationService;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.plan.model.PlanStatus;
import com.yuanshanbao.dsp.plan.service.PlanService;
import com.yuanshanbao.dsp.probability.dao.ProbabilityDao;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.quota.service.operation.AdvertisementOperation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ProbabilityServiceImpl implements ProbabilityService {
	public static final String PLAN_ENCRYPT_KEY = "aadecfe68c1c06a7";

	@Autowired
	private ProbabilityDao probabilityDao;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private AdvertisementStrategyService advertisementStrategyService;

	@Autowired
	private IpLocationService ipLocationService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private RedisService redisCacheService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private PlanService planService;

	@Override
	public List<Probability> selectProbabilitys(Probability probability, PageBounds pageBounds) {
		return setProperty(probabilityDao.selectProbabilitys(probability, pageBounds));
	}

	private List<Probability> setProperty(List<Probability> probabilityList) {
		List<Long> activityIdList = new ArrayList<Long>();
		List<String> channelList = new ArrayList<String>();
		List<Long> advertiserList = new ArrayList<Long>();
		List<Long> planList = new ArrayList<Long>();
		for (Probability probability : probabilityList) {
			activityIdList.add(probability.getActivityId());
			channelList.add(probability.getChannel());
			advertiserList.add(probability.getAdvertiserId());
			planList.add(probability.getPlanId());
		}
		Map<Long, Activity> activityMap = activityService.selectActivitys(activityIdList);
		Map<String, Channel> channelMap = channelService.selectChannelByKeys(channelList);
		Map<Long, Advertiser> advertiserMap = advertiserService.selectAdvertiserByIds(advertiserList);
		Map<Long, Plan> planMap = planService.selectPlanByIds(planList);
		for (Probability probability : probabilityList) {
			probability.setActivity(activityMap.get(probability.getActivityId()));
			probability.setChannelObject(channelMap.get(probability.getChannel()));
			probability.setAdvertiser(advertiserMap.get(probability.getAdvertiserId()));
			probability.setPlan(planMap.get(probability.getPlanId()));
		}
		return probabilityList;
	}

	@Override
	public Probability selectProbability(Long probabilityId) {
		Probability probability = new Probability();
		if (probabilityId == null) {
			return null;
		}
		probability.setProbabilityId(probabilityId);
		List<Probability> list = selectProbabilitys(probability, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertProbability(Probability probability) {
		int result = -1;

		result = probabilityDao.insertProbability(probability);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteProbability(Long probabilityId) {
		int result = -1;

		result = probabilityDao.deleteProbability(probabilityId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateProbability(Probability probability) {
		int result = -1;

		result = probabilityDao.updateProbability(probability);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param probability
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateProbability(Probability probability) {
		if (probability.getProbabilityId() == null) {
			insertProbability(probability);
		} else {
			updateProbability(probability);
		}
	}

	@Override
	public Map<Long, Probability> selectProbabilitys(List<Long> probabilityIdList) {
		Map<Long, Probability> map = new HashMap<Long, Probability>();
		if (probabilityIdList == null || probabilityIdList.size() == 0) {
			return map;
		}
		List<Probability> list = probabilityDao.selectProbabilitys(probabilityIdList);
		for (Probability probability : list) {
			map.put(probability.getProbabilityId(), probability);
		}
		return map;
	}

	@Override
	public Probability pickPrize(HttpServletRequest request, String activityKey, String channel,
			List<Long> pickedAdvertisementIdList) {
		List<Probability> list = selectProbabilitys(request, null, activityKey, channel);
		List<Probability> unpickedList = new ArrayList<Probability>();
		for (Probability probability : list) {
			boolean isPicked = false;
			for (Long advertisementId : pickedAdvertisementIdList) {
				if (advertisementId.equals(probability.getAdvertisementId())) {
					isPicked = true;
					break;
				}
			}
			if (!isPicked) {
				unpickedList.add(probability);
			}
		}
		return pickGift(unpickedList);
	}

	@Override
	public Probability pickPrize(HttpServletRequest request, Long projectId, String activityKey, String channel,
			List<Long> pickedAdvertisementIdList) {
		// List<Probability> list = selectProbabilityByKeyFromCache(projectId,
		// activityKey, channel, null);
		List<Probability> list = selectProbabilitys(request, projectId, activityKey, channel);
		List<Probability> unpickedList = new ArrayList<Probability>();
		for (Probability probability : list) {
			boolean isPicked = false;
			for (Long advertisementId : pickedAdvertisementIdList) {
				if (advertisementId.equals(probability.getAdvertisementId())) {
					isPicked = true;
					break;
				}
			}
			if (!isPicked) {
				unpickedList.add(probability);
			}
		}
		return pickGift(unpickedList);
	}

	@Override
	public List<Probability> selectProbabilitys(HttpServletRequest request, Long projectId, String activityKey,
			String channel) {
		List<Probability> resultList = new ArrayList<Probability>();
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		if (activity == null) {
			LoggerUtil.info("未找到对应活动,activitykey={}", activityKey);
			throw new BusinessException();
		}
		List<Probability> probabilityList = selectProbabilityByKeyFromCache(projectId, activityKey, channel, null);
		Map<Long, Probability> advertisementIdMap = new LinkedHashMap<Long, Probability>();
		// 判断时间是否符合
		checkProbabilityTime(probabilityList, advertisementIdMap);

		if (advertisementIdMap.size() == 0) {
			return resultList;
		}

		List<Quota> quotaList = quotaService.selectQuotaByKeyFromCache(projectId, activityKey, channel);

		checkQuotaTimeAndCount(quotaList, advertisementIdMap);

		resultList.addAll(advertisementIdMap.values());
		return resultList;
	}

	private void checkProbabilityTime(List<Probability> probabilityList, Map<Long, Probability> advertisementIdMap) {
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
	}

	private void checkQuotaTimeAndCount(List<Quota> quotaList, Map<Long, Probability> advertisementIdMap) {

		for (Quota quota : quotaList) {
			if (quota.getCount() != null && quota.getCount() > 0 && quota.getType() != null) {
				String countValue = "";
				// QuotaOperationFactory factory =
				// QuotaType.getCountFactory(quota.getType());
				AdvertisementOperation operation = QuotaType.getCountFactory(quota.getType());
				operation.setQuota(quota);
				operation.setRedisCacheService(redisCacheService);
				countValue = operation.getResult();
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
	}

	private Probability pickGift(List<Probability> probabilityList) {
		double total = 0;
		for (Probability pro : probabilityList) {
			if (pro.getProbability() == null) {
				continue;
			} else {
				total += pro.getProbability();
			}
		}
		double value = Math.random() * total;
		double current = 0;
		for (int i = 0; i < probabilityList.size(); i++) {
			if (probabilityList.get(i).getProbability() == null) {
				continue;
			}
			current += probabilityList.get(i).getProbability();
			if (value < current) {
				return probabilityList.get(i);
			}
		}
		return null;
	}

	public List<Probability> selectProbabilitysByChannel(String channel) {
		Probability probability = new Probability();
		if (StringUtils.isBlank(channel)) {
			return null;
		}
		probability.setChannel(channel);
		List<Probability> list = selectProbabilitys(probability, new PageBounds());
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<Probability> selectGeneralProbabilitys(Probability probability, PageBounds pageBounds) {
		return setProperty(probabilityDao.selectGeneralProbabilitys(probability, pageBounds));
	}

	@Override
	public List<Probability> selectProbabilityFromCache(Long projectId, Long positionId, List<Long> advertisementIdList) {
		List<Probability> resultList = new ArrayList<Probability>();
		if (projectId == null) {
			return resultList;
		}
		List<Probability> probabilityList = ConstantsManager.getProbabilityList(projectId);
		if (probabilityList == null) {
			return resultList;
		}
		for (Probability probability : probabilityList) {
			if (!CommonUtil.isNullOrEquals(probability.getPositionId(), positionId)) {
				continue;
			}
			if (advertisementIdList == null || advertisementIdList.contains(probability.getAdvertisementId())) {
				resultList.add(probability);
			}
		}
		return resultList;
	}

	@Override
	public List<Probability> selectProbabilityByKeyFromCache(Long projectId, String activityKey, String channelKey,
			List<Long> advertisementIdList) {
		List<Probability> resultList = new ArrayList<Probability>();
		List<Probability> activityProList = new ArrayList<Probability>();
		List<Probability> channelProList = new ArrayList<Probability>();
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		Channel channel = ConfigManager.getChannel(channelKey);
		if (activity == null) {
			LoggerUtil.info("未找到对应活动,activitykey={}", activityKey);
			throw new BusinessException();
		}
		if (projectId == null) {
			return resultList;
		}
		List<Probability> probabilityList = ConstantsManager.getProbabilityList(projectId);
		if (probabilityList == null) {
			return resultList;
		}
		// 如果是活动组合
		// if (activity.checkCombination()) {
		// resultList =
		// selectProbabilityByChannelAndActivityKey(activity.getActivityId(),
		// channelKey, probabilityList);
		// } else {
		if (channel != null) {
			if (channel.checkIndependent()) {
				resultList = selectProbabilityByChannelAndActivityKey(activity.getActivityId(), channelKey,
						probabilityList);
			} else {
				activityProList = selectProbabilityByActivityId(activity.getActivityId(), probabilityList);
				channelProList = selectProbabilityByChannelAndActivityKey(activity.getActivityId(), channelKey,
						probabilityList);
				resultList = dealProbabilityConfig(activityProList, channelProList);
			}
		} else {
			resultList = selectProbabilityByActivityId(activity.getActivityId(), probabilityList);
		}
		// }
		return resultList;
	}

	private List<Probability> selectProbabilityByActivityId(Long activityId, List<Probability> probabilityList) {
		List<Probability> resultList = new ArrayList<Probability>();
		for (Probability probability : probabilityList) {
			if (activityId.equals(probability.getActivityId()) && StringUtils.isEmpty(probability.getChannel())) {
				resultList.add(probability);
			}
		}
		return resultList;
	}

	private List<Probability> selectProbabilityByChannelAndActivityKey(Long activityId, String channelKey,
			List<Probability> probabilityList) {
		List<Probability> resultList = new ArrayList<Probability>();
		for (Probability probability : probabilityList) {
			if (activityId.equals(probability.getActivityId()) && channelKey.equals(probability.getChannel())) {
				resultList.add(probability);
			}
		}
		return resultList;
	}

	private List<Probability> dealProbabilityConfig(List<Probability> activityProList, List<Probability> channelProList) {
		List<Probability> resultList = new ArrayList<Probability>(activityProList);
		for (Probability probability : channelProList) {
			if (probability.getDisplayType().equals(AdvertisementDisplayType.ADD)) {
				resultList.add(probability);
			} else {
				ListIterator<Probability> it = resultList.listIterator();
				while (it.hasNext()) {
					Probability pro = it.next();
					if (probability.getAdvertisementId().equals(pro.getAdvertisementId())) {
						if (probability.getDisplayType().equals(AdvertisementDisplayType.COVER)) {
							it.remove();
							it.set(probability);
						}
						if (probability.getDisplayType().equals(AdvertisementDisplayType.DELETE)) {
							it.remove();
						}
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public Probability pickSubPrize(List<Probability> probabilityList, List<Long> pickedPrizeIdList) {
		List<Probability> unpickedList = new ArrayList<Probability>();
		for (Probability probability : probabilityList) {
			boolean isPicked = false;
			for (Long advertisementId : pickedPrizeIdList) {
				if (advertisementId.equals(probability.getAdvertisementId())) {
					isPicked = true;
					break;
				}
			}
			if (!isPicked) {
				unpickedList.add(probability);
			}
		}
		return pickGift(unpickedList);
	}

	@Override
	public List<Probability> selectProbabilityByOrderIds(List<Long> orderIds) {
		if (orderIds == null || orderIds.size() == 0) {
			return new ArrayList<Probability>();
		}
		return setProperty(probabilityDao.selectProbabilityByOrderIds(orderIds));
	}

	// 查找计划并展示
	@Override
	public List<AdvertisementDetails> pickProbabilityByPlan(HttpServletRequest request, Long projectId,
			Channel channelObject, MediaInformation mediaInformation) {
		List<AdvertisementDetails> resultList = new ArrayList<AdvertisementDetails>();
		Map<Long, Probability> probabilitymap = selectPlanFromCache(request, projectId, channelObject.getKey(),
				mediaInformation);
		if (probabilitymap.size() <= 0) {
			return resultList;
		}
		Map<Long, BigDecimal> bidMap = DspConstantsManager.getBidByChannel(channelObject.getKey());
		Long probabilityId = dealWithCTRAndBid(bidMap);
		if (probabilityId == null) {
			return resultList;
		}
		Probability probability = probabilitymap.get(probabilityId);
		resultList = getContent(probability, channelObject);
		return resultList;
	}

	private List<AdvertisementDetails> getContent(Probability probability, Channel channelObject) {
		List<AdvertisementDetails> resultList = new ArrayList<AdvertisementDetails>();
		try {
			Plan plan = ConfigManager.getPlanById(probability.getPlanId());
			Material resultMaterial = new Material();
			if (StringUtils.isEmpty(plan.getMaterial())) {
				return resultList;
			}
			String[] creativeList = plan.getMaterial().split(",");
			for (String creativeId : creativeList) {
				// 判断素材与媒体尺寸大小是否相等
				Material material = ConstantsManager.getMaterialById(Long.valueOf(creativeId));
				if (material != null
						&& (material.getWidth().equals(channelObject.getWidth()) && material.getHeight().equals(
								channelObject.getHeight()))) {
					resultMaterial = material;
					break;
				}
			}
			// 增量url
			String increUrl = getIncrePlanUrl(channelObject);
			// 生成返回信息
			String planKey = getEncryptKey(plan.getPlanId() + ":" + increUrl);
			String probabilityKey = getEncryptKey(probability.getProbabilityId() + "");
			AdvertisementDetails advertisementDetails = new AdvertisementDetails(planKey, probabilityKey,
					resultMaterial, channelObject.getKey());
			resultList.add(advertisementDetails);
		} catch (Exception e) {
			LoggerUtil.error("getContent", e);
		}
		return resultList;
	}

	private String getIncrePlanUrl(Channel channelObject) {
		String url = "";
		Set<String> idList = redisService.smembers(RedisConstant.getChannelIncreIdKey(channelObject.getKey()));
		if (idList == null || idList.size() == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>(idList);
		// 随机取一个
		String key = list.get((int) (Math.random() * list.size()));
		String[] values = key.split(":");
		Long result = redisService.increBy(RedisConstant.getPlanChargeTypeCountKey(values[0]), -1);
		if (result <= 0) {
			redisService.srem(RedisConstant.getChannelIncreIdKey(channelObject.getKey()), key);
		}
		Plan plan = ConfigManager.getPlanById(Long.valueOf(values[0]));
		if (QuotaType.CPM.equals(plan.getChargeType())) {
			// recordPlanCount(values[0], values[1], channelObject.getKey(),
			// false);
		} else if (QuotaType.CPC.equals(plan.getChargeType())) {
			recordPlanCount(values[0], values[1], channelObject.getKey(), true);
			String planKey = getEncryptKey(values[0]);
			url = CommonUtil.getDspJumpUrl(CommonConstant.advertisement_HOST, planKey, channelObject.getKey());
		}
		return url;
	}

	private String getEncryptKey(String value) {
		String key = "";
		try {
			key = AESUtils.encrypt(PLAN_ENCRYPT_KEY, value);
		} catch (Exception e) {
			LoggerUtil.error("encrypt error ", e);
		}
		return key;
	}

	public Map<Long, Probability> selectPlanFromCache(HttpServletRequest request, Long projectId, String channelKey,
			MediaInformation mediaInformation) {
		Map<Long, Probability> resultMap = new HashMap<Long, Probability>();
		if (projectId == null || channelKey == null) {
			return resultMap;
		}

		List<Probability> list = ConstantsManager.getProbabilityList(projectId);
		if (list == null || list.size() == 0) {
			return resultMap;
		}

		// 查找媒体下计划
		List<Probability> channelList = new ArrayList<Probability>();
		for (Probability probability : list) {
			if (channelKey.equals(probability.getChannel())) {
				channelList.add(probability);
			}
		}

		List<Probability> availableList = new ArrayList<Probability>();
		for (Probability probability : channelList) {
			Plan plan = ConfigManager.getPlanById(probability.getPlanId());
			if (plan.getStartTime() != null) {
				if (plan.getStartTime().after(new Date())) {
					continue;
				}
			}
			if (plan.getEndTime() != null) {
				if (plan.getEndTime().before(new Date())) {
					continue;
				}
			}
			if (!plan.getStatus().equals(PlanStatus.ONLINE)) {
				continue;
			}
			// 验证金额是否已经达到上限
			Double consumed = getDoubleCount(RedisConstant.getPlanBalanceCountKey(plan.getPlanId()));
			Double dayConsumed = getDoubleCount(RedisConstant.getPlanDayBalanceCountKey(null, plan.getPlanId()));
			if (BigDecimal.valueOf(consumed).compareTo(plan.getSpend()) < 0) {
				if (plan.getDayLimit() != null && BigDecimal.valueOf(dayConsumed).compareTo(plan.getDayLimit()) < 0) {
					availableList.add(probability);
				} else {
					availableList.add(probability);
				}
			}
		}

		List<Probability> result = advertisementStrategyService.getAvailableProbabilityList(request, availableList,
				mediaInformation);

		for (Probability probability : result) {
			resultMap.put(probability.getProbabilityId(), probability);
		}

		return resultMap;
	}

	private double getDoubleCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isPositiveFloat(str)) {
			return Double.parseDouble(str);
		}
		return 0;
	}

	private Long dealWithCTRAndBid(Map<Long, BigDecimal> bidMap) {
		Long resultProbabilityId = null;
		try {
			Double score = new Double(0);
			Map<Long, Double> scoreMap = new HashMap<Long, Double>();
			for (Long probabilityId : bidMap.keySet()) {
				String ctrValue = redisService.get(RedisConstant.getProbabilityChannelCTRKey(probabilityId));
				if (ctrValue == null || !ValidateUtil.isDouble(ctrValue)) {
					return probabilityId;
				} else {
					Double ctr = Double.valueOf(ctrValue);
					score = ctr * bidMap.get(probabilityId).doubleValue();
					scoreMap.put(probabilityId, score);
				}
			}
			if (scoreMap.size() <= 0) {
				return null;
			}

			Double tempScore = new Double(0);
			for (Map.Entry<Long, Double> entry : scoreMap.entrySet()) {
				if (entry.getValue() > tempScore) {
					resultProbabilityId = entry.getKey();
					tempScore = entry.getValue();
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("dealWithCTRAndBid", e);
		}
		return resultProbabilityId;
	}

	@Override
	public void recordPlanCount(String pId, String key, String channel, boolean isClick) {
		String planIdAndUrlValue = AESUtils.decrypt(PLAN_ENCRYPT_KEY, pId);
		String[] planIdAndUrl = planIdAndUrlValue.split(":");
		String planId = planIdAndUrl[0];
		String probabilityId = AESUtils.decrypt(PLAN_ENCRYPT_KEY, key);
		Plan plan = ConfigManager.getPlanById(Long.valueOf(planId));
		if (isClick) {
			redisService.incr(RedisConstant.getPlanClickCountPVKey(null, planId, channel));
			if (QuotaType.CPC.equals(plan.getChargeType())) {
				increConsume(planId, probabilityId, channel);
			}
		} else {
			redisService.incr(RedisConstant.getPlanShowCountPVKey(null, planId, channel));
			if (QuotaType.CPM.equals(plan.getChargeType())) {
				increConsume(planId, probabilityId, channel);
			}
		}
	}

	private void increConsume(String planId, String probabilityId, String channel) {
		try {
			Map<Long, BigDecimal> proCostMap = DspConstantsManager.getBidByChannel(channel);
			redisService.increByDouble(RedisConstant.getProbabilityBalanceCountKey(null, Long.valueOf(probabilityId)),
					proCostMap.get(Long.valueOf(probabilityId)).doubleValue());
			redisService.increByDouble(RedisConstant.getPlanBalanceCountKey(Long.valueOf(planId)),
					proCostMap.get(Long.valueOf(probabilityId)).doubleValue());
			redisService.incr(RedisConstant.getPlanChargeTypeCountKey(planId));
		} catch (Exception e) {
			LoggerUtil.error("increConsume", e);
		}
	}
}
