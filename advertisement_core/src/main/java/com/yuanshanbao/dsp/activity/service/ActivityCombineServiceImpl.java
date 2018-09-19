package com.yuanshanbao.dsp.activity.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.activity.dao.ActivityCombineDao;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.model.ActivityCombine;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ActivityCombineServiceImpl implements ActivityCombineService {

	@Autowired
	private ActivityCombineDao activityCombineDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityService activityService;

	@Override
	public List<ActivityCombine> selectActivityCombine(ActivityCombine activityCombine, PageBounds pageBounds) {
		return setProperty(activityCombineDao.selectActivityCombine(activityCombine, pageBounds));
	}

	private List<ActivityCombine> setProperty(List<ActivityCombine> list) {
		List<ActivityCombine> resultList = new ArrayList<ActivityCombine>();
		List<Long> activityIds = new ArrayList<Long>();
		for (ActivityCombine activityCombine : list) {
			activityIds.add(activityCombine.getActivityId());
		}
		Map<Long, Activity> map = activityService.selectActivitys(activityIds);
		for (ActivityCombine activityCombine : list) {
			activityCombine.setActivity(map.get(activityCombine.getActivityId()));
		}
		resultList = list;
		return resultList;
	}

	// @Override
	// public Activity selectActivity(Long activityId) {
	// Activity activity = new Activity();
	// if (activityId == null) {
	// return null;
	// }
	// activity.setActivityId(activityId);
	// List<Activity> list = selectActivitys(activity, new PageBounds());
	// if (list.size() > 0) {
	// return list.get(0);
	// }
	// return null;
	// }

	@Override
	public void insertActivityCombine(ActivityCombine activityCombine) {
		int result = -1;

		result = activityCombineDao.insertActivityCombine(activityCombine);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteActivity(Long activityId) {
		int result = -1;

		result = activityCombineDao.deleteActivity(activityId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateActivityCombine(ActivityCombine activityCombine) {
		int result = -1;

		result = activityCombineDao.updateActivityCombine(activityCombine);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param activity
	 * @return
	 * @throws IOException
	 */
	// @Override
	// public void insertOrUpdateActivity(Activity activity) {
	// if (activity.getActivityId() == null) {
	// insertActivity(activity);
	// } else {
	// updateActivity(activity);
	// }
	// }
	//
	//
	//
	// @Override
	// public Activity selectActivity(String key) {
	// if (StringUtils.isBlank(key)) {
	// return null;
	// }
	// Activity param = new Activity();
	// param.setKey(key);
	//
	// List<Activity> list = selectActivitys(param, new PageBounds());
	// for (Activity activity : list) {
	// if (key.equals(activity.getKey())) {
	// return activity;
	// }
	// }
	// return null;
	// }

	@Override
	public ActivityCombine selectActivityCombine(Long activityId) {
		if (activityId == null) {
			return null;
		}
		ActivityCombine activityCombine = new ActivityCombine();
		activityCombine.setActivityId(activityId);
		List<ActivityCombine> list = selectActivityCombine(activityCombine, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertOrUpdateActivity(Activity activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Long, Activity> selectActivitys(List<Long> activityIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity selectActivity(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, List<Probability>> allocatePrize(String parentKey, List<Probability> probabilityList,
			String channel) {
		Activity parentActivity = ConfigManager.getActivityByKey(parentKey);
		ActivityCombine activityCombine = new ActivityCombine();
		activityCombine.setParentId(parentActivity.getActivityId());
		List<ActivityCombine> list = selectActivityCombine(activityCombine, new PageBounds());
		// 若活动奖品数量过少，则不进行分配
		Iterator<ActivityCombine> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (list.size() == 1) {
				break;
			}
			ActivityCombine ac = iterator.next();
			if (!checkActivityMinSize(ac, probabilityList, channel)) {
				iterator.remove();
			} else {
				break;
			}
		}
		return setChanceBySubActivityKey(list, probabilityList, channel);
	}

	private boolean checkActivityMinSize(ActivityCombine activityCombine, List<Probability> probabilityList,
			String channel) {
		double chance = getChance(activityCombine, probabilityList, channel);
		double minChance = Double.valueOf(ConfigManager.getConfigValue(activityCombine.getActivityId(), null,
				ConfigConstants.PICK_PRIZE_MIN_CHANCE_CONFIG));
		if (chance < minChance) {
			return false;
		}
		return true;
	}

	private int getChance(ActivityCombine activityCombine, List<Probability> probabilityList, String channel) {

		String allocateConfig = ConfigManager.getConfigValue(activityCombine.getParentId(), channel,
				ConfigConstants.ACTIVITY_COMBINE_PRIZE_ALLOCATE_CONFIG);
		List<String> countPro = new ArrayList<String>();
		if (allocateConfig != null) {
			String[] config = allocateConfig.split(",");
			countPro = Arrays.asList(config);
		} else {
			ActivityCombine params = new ActivityCombine();
			params.setParentId(activityCombine.getParentId());
			List<ActivityCombine> combineList = selectActivityCombine(params, new PageBounds());
			int size = combineList.size();
			for (int i = size; i > 0; i--) {
				countPro.add(String.valueOf(i));
			}
		}
		int total = 0;
		for (String s : countPro) {
			total += Integer.valueOf(s);
		}
		double chance = probabilityList.size() * Double.valueOf(countPro.get(activityCombine.getSort() - 1)) / total;
		if (chance < 1) {
			return 0;
		}
		return (int) Math.round(chance);
	}

	private Map<Long, List<Probability>> setChanceBySubActivityKey(List<ActivityCombine> list,
			List<Probability> probabilityList, String channel) {
		Map<Long, List<Probability>> map = new HashMap<Long, List<Probability>>();
		List<Probability> getList = new ArrayList<Probability>(probabilityList);
		for (ActivityCombine ac : list) {
			List<Probability> acList = new ArrayList<Probability>();
			// 若只有一个活动，将次数全部给他
			if (list.size() == 1) {
				acList.addAll(probabilityList);
				map.put(ac.getActivityId(), acList);
				break;
			}
			int chance = getChance(ac, probabilityList, channel);
			for (int i = 0; i < chance; i++) {
				if (getList.size() > 0) {
					acList.add(getList.get(0));
					getList.remove(0);
				} else {
					break;
				}
			}
			map.put(ac.getActivityId(), acList);
		}

		return map;
	}
}
