package com.yuanshanbao.dsp.activity.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.activity.dao.ActivityCombineDao;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.model.ActivityCombine;
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

}
