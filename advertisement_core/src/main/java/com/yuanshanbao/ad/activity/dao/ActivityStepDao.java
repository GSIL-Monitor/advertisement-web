package com.yuanshanbao.ad.activity.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.activity.model.ActivityStep;

public interface ActivityStepDao {

	public List<ActivityStep> selectActivitySteps(ActivityStep activityStep,
			PageBounds pageBounds);

	public int insertActivityStep(ActivityStep activityStep);

	public int deleteActivityStep(Long activityStepId);

	public int updateActivityStep(ActivityStep activityStep);

	public List<ActivityStep> selectActivitySteps(List<Long> activityStepIdList);
}
