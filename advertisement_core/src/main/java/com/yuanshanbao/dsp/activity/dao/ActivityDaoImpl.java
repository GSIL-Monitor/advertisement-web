package com.yuanshanbao.dsp.activity.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl implements ActivityDao {
	@Override
	public List<Activity> selectActivitys(Activity activity, PageBounds pageBounds) {
		return getSqlSession().selectList("Activity.selectActivity", activity, pageBounds);
	}

	@Override
	public int insertActivity(Activity activity) {
		return getSqlSession().insert("Activity.insertActivity", activity);
	}

	@Override
	public int deleteActivity(Long activityId) {
		return getSqlSession().delete("Activity.deleteActivity", activityId);
	}

	@Override
	public int updateActivity(Activity activity) {
		return getSqlSession().update("Activity.updateActivity", activity);
	}

	@Override
	public List<Activity> selectActivitys(List<Long> activityIdList) {
		if (activityIdList == null || activityIdList.size() == 0) {
			return new ArrayList<Activity>();
		}
		return getSqlSession().selectList("Activity.selectActivityByIds", activityIdList);
	}

}
