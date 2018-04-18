package com.yuanshanbao.dsp.probability.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategyType;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementStrategyService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.service.IpLocationService;
import com.yuanshanbao.dsp.probability.dao.ProbabilityDao;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ProbabilityServiceImpl implements ProbabilityService {

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

	@Override
	public List<Probability> selectProbabilitys(Probability probability, PageBounds pageBounds) {
		return setProperty(probabilityDao.selectProbabilitys(probability, pageBounds));
	}

	private List<Probability> setProperty(List<Probability> probabilityList) {
		List<Long> activityIdList = new ArrayList<Long>();
		List<String> channelList = new ArrayList<String>();
		for (Probability probability : probabilityList) {
			activityIdList.add(probability.getActivityId());
			channelList.add(probability.getChannel());
		}
		Map<Long, Activity> activityMap = activityService.selectActivitys(activityIdList);
		Map<String, Channel> channelMap = channelService.selectChannelByKeys(channelList);
		for (Probability probability : probabilityList) {
			probability.setActivity(activityMap.get(probability.getActivityId()));
			probability.setChannelObject(channelMap.get(probability.getChannel()));
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
		List<Probability> list = selectProbabilitys(request, activityKey, channel);
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
	public List<Probability> selectProbabilitys(HttpServletRequest request, String activityKey, String channel) {
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		if (activity == null) {
			LoggerUtil.info("未找到对应活动,activitykey={}", activityKey);
			throw new BusinessException();
		}
		Probability param = new Probability();
		param.setActivityId(activity.getActivityId());
		param.setChannel(channel);
		List<Probability> list = selectProbabilitys(param, new PageBounds());
		if (list == null || list.size() == 0) {
			list = new ArrayList<Probability>();
			param.setChannel(null);
			list = selectProbabilitys(param, new PageBounds());
		}
		List<Probability> resultList = new ArrayList<Probability>();
		for (Probability prob : list) {
			if (prob.getAdvertisementId() != null) {
				List<AdvertisementStrategy> advertisementStrategyList = ConfigManager.getAdvertisementStrategy(prob
						.getAdvertisementId() + "");
				if (advertisementStrategyList != null) {
					boolean strategyPass = true;
					for (AdvertisementStrategy advertisementStrategy : advertisementStrategyList) {
						if (advertisementStrategy.getType().equals(AdvertisementStrategyType.REGION)) {
							Location location = ipLocationService.queryIpLocation(JSPHelper.getRemoteAddr(request));
							Location configLocation = ConstantsManager.getLocationByCode(advertisementStrategy
									.getValue());
							if (location != null && configLocation != null
									&& !configLocation.contains(location.getCode())) {
								strategyPass = false;
								break;
							}
						}
					}
					if (!strategyPass) {
						continue;
					}
				}
			}
			if (param.getChannel() == null) {
				if (StringUtils.isBlank(prob.getChannel())) {
					resultList.add(prob);
				}
			} else if (param.getChannel().equals(prob.getChannel())) {
				resultList.add(prob);
			}

		}
		return resultList;
	}

	private Probability pickGift(List<Probability> probabilityList) {
		double total = 0;
		for (Probability pro : probabilityList) {
			total += pro.getProbability();
		}
		double value = Math.random() * total;
		double current = 0;
		for (int i = 0; i < probabilityList.size(); i++) {
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
}
