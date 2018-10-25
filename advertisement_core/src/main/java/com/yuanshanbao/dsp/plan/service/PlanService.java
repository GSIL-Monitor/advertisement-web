package com.yuanshanbao.dsp.plan.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface PlanService {

	public void insertPlan(Plan plan);

	public void updatePlan(Plan plan);

	public void deletePlan(Plan plan);

	public List<Plan> selectPlan(Plan plan, PageBounds pageBounds);

	public Plan selectPlan(Long planId);

	public List<Long> selectPlanIds(Plan plan);

	public Map<Long, Plan> selectPlanByIds(List<Long> planIds);

	public void calculateCTR(List<Probability> list);

}
