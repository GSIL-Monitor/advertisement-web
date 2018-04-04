package com.yuanshanbao.ad.activity.dao;

import java.util.List;

import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ActivityDao {

	public List<Activity> selectActivitys(Activity activity, PageBounds pageBounds);

	public int insertActivity(Activity activity);

	public int deleteActivity(Long activityId);

	public int updateActivity(Activity activity);

	public List<Activity> selectActivitys(List<Long> activityIdList);
}
