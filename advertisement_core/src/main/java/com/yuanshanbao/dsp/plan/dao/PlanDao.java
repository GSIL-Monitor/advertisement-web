package com.yuanshanbao.dsp.plan.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface PlanDao {

	public List<Plan> selectPlans(Plan plan, PageBounds pageBounds);

	public int insertPlan(Plan plan);

	public int deletePlan(Plan plan);

	public int updatePlan(Plan plan);

	public List<Plan> selectPlanByIds(List<Long> ids);

	public int cutPayment(Map<String, Object> parameters);

}
