package com.yuanshanbao.dsp.activity.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.activity.model.Activity;

public interface ActivityService {

	public List<Activity> selectActivitys(Activity activity, PageBounds pageBounds);

	public Activity selectActivity(Long activityId);

	public void insertActivity(Activity activity);

	public void deleteActivity(Long activityId);

	public void updateActivity(Activity activity);

	public void insertOrUpdateActivity(Activity activity);

	public Map<Long, Activity> selectActivitys(List<Long> activityIdList);

	public Activity selectActivity(String key);

}
