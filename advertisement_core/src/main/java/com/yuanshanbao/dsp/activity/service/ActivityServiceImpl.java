package com.yuanshanbao.dsp.activity.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.activity.dao.ActivityDao;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.service.InformationService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private UserService userService;

	@Autowired
	private InformationService informationService;

	@Override
	public List<Activity> selectActivitys(Activity activity, PageBounds pageBounds) {
		return activityDao.selectActivitys(activity, pageBounds);
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
		List<Activity> list = activityDao.selectActivitys(activityIdList);
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

	@Override
	public Information applyActivityForInformation(User user, Information information, Activity activity) {
		// if (activity == null) {
		// throw new
		// BusinessException(ComRetCode.FREE_INSURANCE_NOT_EXIST_ERROR);
		// }
		// if (information == null) {
		// throw new BusinessException(ComRetCode.INSURANT_NOT_EXIST_ERROR);
		// }
		// Quota quota =
		// quotaService.pickGoodsForInformation(activity.getActivityId(),
		// information);
		// if (quota == null) {
		// informationService.insertInformation(information);
		// throw new BusinessException(ComRetCode.ORDER_DELIVER_ERROR);
		// }
		// applyForInformation(information, activity, quota);
		// return information;
		return null;
	}
}
