package com.yuanshanbao.dsp.activity.dao;

import java.util.List;

import com.yuanshanbao.dsp.activity.model.ActivityCombine;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ActivityCombineDao {

	public List<ActivityCombine> selectActivityCombine(ActivityCombine activityCombine, PageBounds pageBounds);

	public int insertActivityCombine(ActivityCombine activityCombine);

	public int deleteActivity(Long activityId);

	public int updateActivityCombine(ActivityCombine activityCombine);

	public List<ActivityCombine> selectActivitys(List<Long> activityIdList);
}
