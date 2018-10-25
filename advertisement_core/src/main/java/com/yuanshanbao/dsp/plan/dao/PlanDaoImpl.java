package com.yuanshanbao.dsp.plan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class PlanDaoImpl extends BaseDaoImpl implements PlanDao {
	@Override
	public List<Plan> selectPlans(Plan plan, PageBounds pageBounds) {
		return getSqlSession().selectList("plan.selectPlan", plan, pageBounds);
	}

	@Override
	public int insertPlan(Plan plan) {
		return getSqlSession().insert("plan.insertPlan", plan);
	}

	@Override
	public int deletePlan(Plan plan) {
		return getSqlSession().delete("plan.deletePlan", plan);
	}

	@Override
	public int updatePlan(Plan plan) {
		return getSqlSession().update("plan.updatePlan", plan);
	}

	@Override
	public List<Plan> selectPlanByIds(List<Long> ids) {
		return getSqlSession().selectList("plan.selectPlanByIds", ids);
	}

	@Override
	public int cutPayment(Map<String, Object> parameters) {
		return getSqlSession().update("plan.cutPayment", parameters);
	}
}
