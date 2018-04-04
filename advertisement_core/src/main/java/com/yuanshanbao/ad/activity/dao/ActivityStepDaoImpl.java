package com.yuanshanbao.ad.activity.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.activity.model.ActivityStep;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;

@Repository
public class ActivityStepDaoImpl extends BaseDaoImpl implements ActivityStepDao {
	@Override
	public List<ActivityStep> selectActivitySteps(ActivityStep activityStep,
			PageBounds pageBounds) {
		return getSqlSession().selectList("ActivityStep.selectActivityStep",
				activityStep, pageBounds);
	}

	@Override
	public int insertActivityStep(ActivityStep activityStep) {
		return getSqlSession().insert("ActivityStep.insertActivityStep",
				activityStep);
	}

	@Override
	public int deleteActivityStep(Long activityStepId) {
		return getSqlSession().delete("ActivityStep.deleteActivityStep",
				activityStepId);
	}

	@Override
	public int updateActivityStep(ActivityStep activityStep) {
		return getSqlSession().update("ActivityStep.updateActivityStep",
				activityStep);
	}

	@Override
	public List<ActivityStep> selectActivitySteps(List<Long> activityStepIdList) {
		if (activityStepIdList == null || activityStepIdList.size() == 0) {
			return new ArrayList<ActivityStep>();
		}
		return getSqlSession().selectList(
				"ActivityStep.selectActivityStepByIds", activityStepIdList);
	}

}
