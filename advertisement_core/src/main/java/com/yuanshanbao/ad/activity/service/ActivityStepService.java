package com.yuanshanbao.ad.activity.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.activity.model.ActivityStep;

public interface ActivityStepService {

	public List<ActivityStep> selectActivitySteps(ActivityStep activityStep,
			PageBounds pageBounds);

	public ActivityStep selectActivityStep(Long activityStepId);

	public void insertActivityStep(ActivityStep activityStep);

	public void deleteActivityStep(Long activityStepId);

	public void updateActivityStep(ActivityStep activityStep);

	public void insertOrUpdateActivityStep(ActivityStep activityStep);

	public Map<Long, ActivityStep> selectActivitySteps(
			List<Long> activityStepIdList);

}
