package com.yuanshanbao.dsp.quota.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.limitation.service.LimitationService;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.service.MobileLocationService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.quota.dao.QuotaDao;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.dsp.statistics.service.AdvertisementStatisticsService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class QuotaServiceImpl implements QuotaService {

	@Autowired
	private QuotaDao quotaDao;

	@Autowired
	private MobileLocationService mobileLocationService;

	@Autowired
	private LimitationService limitationService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private AdvertisementStatisticsService advertisementStatisticsService;

	@Override
	public void insertQuota(Quota quota) {
		int result = -1;

		result = quotaDao.insertQuota(quota);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateQuota(Quota quota) {
		int result = -1;

		result = quotaDao.updateQuota(quota);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteQuota(Quota quota) {
		int result = -1;

		result = quotaDao.deleteQuota(quota);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Quota> selectQuota(Quota quota, PageBounds pageBounds) {
		return quotaDao.selectQuota(quota, pageBounds);
	}

	@Override
	public Quota selectQuota(Long quotaId) {
		if (quotaId == null) {
			return null;
		}
		Quota quota = new Quota();
		quota.setQuotaId(quotaId);
		List<Quota> list = selectQuota(quota, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, Quota> selectQuotaByIds(List<Long> quotaIds) {
		Map<Long, Quota> map = new HashMap<Long, Quota>();
		if (quotaIds == null || quotaIds.size() == 0) {
			return map;
		}
		List<Quota> list = quotaDao.selectQuotaByIds(quotaIds);
		for (Quota quota : list) {
			map.put(quota.getQuotaId(), quota);
		}
		return map;
	}

	@Override
	public List<Quota> selectQuotaFromCache(Long projectId, Long positionId, List<Long> advertisementIdList) {
		List<Quota> resultList = new ArrayList<Quota>();
		if (projectId == null) {
			return resultList;
		}
		List<Quota> quotaList = ConstantsManager.getQuotaList(projectId);
		if (quotaList == null) {
			return resultList;
		}
		for (Quota quota : quotaList) {
			if (!CommonUtil.isNullOrEquals(quota.getPositionId(), positionId)) {
				continue;
			}
			if (advertisementIdList == null || advertisementIdList.contains(quota.getAdvertisementId())) {
				resultList.add(quota);
			}
		}
		return resultList;
	}

	public List<Long> isHide(List<Quota> list) {
		List<Long> advertisementIdList = new ArrayList<Long>();
		return advertisementIdList;
	}

	private boolean isOverTime(Quota quota) {
		Date nowDate = new Date();
		Date startTime = quota.getStartTime();
		Date endTime = quota.getEndTime();
		if (DateUtils.compareDate(nowDate, endTime) < 0 && DateUtils.compareDate(nowDate, startTime) > 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isOverClickCount(Quota quota) {
		Long advertisementId = quota.getAdvertisementId();
		Long positionId = quota.getPositionId();
		Integer count = 0;
		String key = RedisConstant.getAdvertisementClickCountKey(null, advertisementId, positionId);
		if (key != null) {
			count = Integer.valueOf(redisService.get(key));
		}
		AdvertisementStatistics advertisementStatistics = new AdvertisementStatistics();
		advertisementStatistics.setAdvertisementId(advertisementId);
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectAdvertisementStatistics(
				advertisementStatistics, new PageBounds());
		return true;
	}

	@Override
	public List<Quota> selectQuotaByKeyFromCache(Long projectId, String activityKey, String channelKey) {
		List<Quota> resultList = new ArrayList<Quota>();
		if (projectId == null) {
			return resultList;
		}
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		List<Quota> quotaList = ConstantsManager.getQuotaList(projectId);
		if (quotaList == null) {
			return resultList;
		}
		List<Quota> activityQuotaList = getActivityQuota(activity.getActivityId(), quotaList);
		List<Quota> channelQuotaList = getChannelQuota(activity.getActivityId(), channelKey, quotaList);
		resultList = dealQuotaList(activityQuotaList, channelQuotaList);
		return resultList;
	}

	private List<Quota> dealQuotaList(List<Quota> activityQuotaList, List<Quota> channelQuotaList) {
		List<Quota> result = new ArrayList<Quota>();
		Map<Long, Quota> activityMap = new HashMap<Long, Quota>();
		Map<Long, Quota> channelMap = new HashMap<Long, Quota>();
		for (Quota quota : activityQuotaList) {
			activityMap.put(quota.getAdvertisementId(), quota);
		}
		for (Quota quota : channelQuotaList) {
			channelMap.put(quota.getAdvertisementId(), quota);
		}
		for (Long advertisementId : activityMap.keySet()) {
			if (!channelMap.containsKey(advertisementId)) {
				channelQuotaList.add(activityMap.get(advertisementId));
			} else {
				continue;
			}
		}
		result = channelQuotaList;
		return result;
	}

	private List<Quota> getChannelQuota(Long activityId, String channelKey, List<Quota> quotaList) {
		List<Quota> result = new ArrayList<Quota>();
		for (Quota quota : quotaList) {
			if (activityId.equals(quota.getActivityId()) && channelKey.equals(quota.getChannel())) {
				result.add(quota);
			}
		}
		return result;
	}

	private List<Quota> getActivityQuota(Long activityId, List<Quota> quotaList) {
		List<Quota> result = new ArrayList<Quota>();
		for (Quota quota : quotaList) {
			if (activityId.equals(quota.getActivityId())) {
				result.add(quota);
			}
		}
		return result;
	}

	@Override
	public Quota pickGoodsForInformation(Long activityId, Information information) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quota pickProductForApply(User user, Product product) {
		Quota params = new Quota();
		params.setProductId(product.getProductId());
		List<Quota> list = selectQuota(params, new PageBounds());
		if (list.size() <= 0) {
			return null;
		}
		Quota quota = list.get(0);
		dealQuotaStock(quota);
		Location location = mobileLocationService.queryMobileLocation(user.getMobile());
		quota.setLocation(location);
		try {
			boolean result = limitationService.lockStock(quota);
			if (result) {
				return quota;
			}
		} catch (BusinessException e) {
			// 锁库存失败
		}
		return null;
	}

	private void dealQuotaStock(Quota quota) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("quotaId", quota.getQuotaId());
		parameters.put("count", 1);
		int result = quotaDao.lockStock(parameters);
		if (result <= 0) {
			throw new BusinessException(ComRetCode.ORDER_LOCK_STOCK_FAIL_ERROR);
		}
	}
}
