package com.yuanshanbao.dsp.activity.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.model.ActivityCombine;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ActivityCombineService {

	public List<ActivityCombine> selectActivityCombine(ActivityCombine activityCombine, PageBounds pageBounds);

	public ActivityCombine selectActivityCombine(Long activityId);

	public void insertActivityCombine(ActivityCombine activityCombine);

	public void deleteActivity(Long activityId);

	public void updateActivityCombine(ActivityCombine activityCombine);

	public void insertOrUpdateActivity(Activity activity);

	public Map<Long, Activity> selectActivitys(List<Long> activityIdList);

	public Activity selectActivity(String key);

	public Map<Long, List<Probability>> allocatePrize(String parentKey, List<Probability> probabilityList,
			String channel);

}
