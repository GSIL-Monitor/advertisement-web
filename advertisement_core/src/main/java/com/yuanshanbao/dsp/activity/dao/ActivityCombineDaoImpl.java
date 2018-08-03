package com.yuanshanbao.dsp.activity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.activity.model.ActivityCombine;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ActivityCombineDaoImpl extends BaseDaoImpl implements ActivityCombineDao {
	@Override
	public List<ActivityCombine> selectActivityCombine(ActivityCombine activityCombine, PageBounds pageBounds) {
		return getSqlSession().selectList("ActivityCombine.selectActivityCombine", activityCombine, pageBounds);
	}

	@Override
	public int insertActivityCombine(ActivityCombine activityCombine) {
		return getSqlSession().insert("ActivityCombine.insertActivityCombine", activityCombine);
	}

	@Override
	public int deleteActivity(Long activityId) {
		return getSqlSession().delete("Activity.deleteActivity", activityId);
	}

	@Override
	public int updateActivityCombine(ActivityCombine activityCombine) {
		return getSqlSession().update("ActivityCombine.updateActivityCombine", activityCombine);
	}

	@Override
	public List<ActivityCombine> selectActivitys(List<Long> activityIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public List<Activity> selectActivitys(List<Long> activityIdList) {
	// if (activityIdList == null || activityIdList.size() == 0) {
	// return new ArrayList<Activity>();
	// }
	// return getSqlSession().selectList("Activity.selectActivityByIds",
	// activityIdList);
	// }

}
