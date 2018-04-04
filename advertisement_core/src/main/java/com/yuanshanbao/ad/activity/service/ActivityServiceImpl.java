package com.yuanshanbao.ad.activity.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.activity.dao.ActivityDao;
import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.ad.activity.model.ActivityStep;
import com.yuanshanbao.ad.user.service.UserService;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityStepService activityStepService;

	@Override
	public List<Activity> selectActivitys(Activity activity,
			PageBounds pageBounds) {
		return setProperty(activityDao.selectActivitys(activity, pageBounds));
	}

	private List<Activity> setProperty(List<Activity> list) {
		List<Long> stepIds = new ArrayList<Long>();
		for (Activity activity : list) {
			stepIds.addAll(activity.getStepIds());
		}

		Map<Long, ActivityStep> map = activityStepService
				.selectActivitySteps(stepIds);
		for (Activity activity : list) {
			List<ActivityStep> activitySteps = new ArrayList<ActivityStep>();
			if (activity.getStepIds() != null
					&& activity.getStepIds().size() > 0) {
				for (Long stepId : activity.getStepIds()) {
					activitySteps.add(map.get(stepId));
				}
			}
			activity.setActivitySteps(activitySteps);
		}
		return list;
	}

	@Override
	public Activity selectActivity(Long activityId) {
		Activity activity = new Activity();
		if (activityId == null) {
			return null;
		}
		activity.setActivityId(activityId);
		List<Activity> list = selectActivitys(activity, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertActivity(Activity activity) {
		int result = -1;

		result = activityDao.insertActivity(activity);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteActivity(Long activityId) {
		int result = -1;

		result = activityDao.deleteActivity(activityId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateActivity(Activity activity) {
		int result = -1;

		result = activityDao.updateActivity(activity);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param activity
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateActivity(Activity activity) {
		if (activity.getActivityId() == null) {
			insertActivity(activity);
		} else {
			updateActivity(activity);
		}
	}

	@Override
	public Map<Long, Activity> selectActivitys(List<Long> activityIdList) {
		Map<Long, Activity> map = new HashMap<Long, Activity>();
		if (activityIdList == null || activityIdList.size() == 0) {
			return map;
		}
		List<Activity> list = setProperty(activityDao
				.selectActivitys(activityIdList));
		for (Activity activity : list) {
			map.put(activity.getActivityId(), activity);
		}
		return map;
	}

	@Override
	public Activity selectActivity(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Activity param = new Activity();
		param.setKey(key);
		List<Activity> list = selectActivitys(param, new PageBounds());
		for (Activity activity : list) {
			if (key.equals(activity.getKey())) {
				return activity;
			}
		}
		return null;
	}

}
