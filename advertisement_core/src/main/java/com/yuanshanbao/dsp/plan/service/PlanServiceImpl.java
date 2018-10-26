package com.yuanshanbao.dsp.plan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.plan.dao.PlanDao;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanDao planDao;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private RedisService redisService;

	@Override
	public void insertPlan(Plan plan) {
		int result = -1;

		result = planDao.insertPlan(plan);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updatePlan(Plan plan) {
		int result = -1;

		result = planDao.updatePlan(plan);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deletePlan(Plan plan) {
		int result = -1;

		result = planDao.deletePlan(plan);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Plan> selectPlan(Plan plan, PageBounds pageBounds) {
		return setProperty(planDao.selectPlans(plan, pageBounds));

	}

	private List<Plan> setProperty(List<Plan> list) {
		List<Long> advertiserIds = new ArrayList<Long>();
		List<Long> orderIds = new ArrayList<Long>();
		for (Plan plan : list) {
			advertiserIds.add(plan.getAdvertiserId());
			orderIds.add(plan.getOrderId());
		}

		Map<Long, Advertiser> map = advertiserService.selectAdvertiserByIds(advertiserIds);
		Map<Long, Order> orderMap = orderService.selectOrderByIds(orderIds);
		for (Plan plan : list) {
			plan.setAdvertiser(map.get(plan.getAdvertiserId()));
			plan.setOrder(orderMap.get(plan.getOrderId()));
			plan.setConsumed(redisService.get(RedisConstant.getPlanBalanceCountKey(plan.getPlanId())));
		}
		return list;
	}

	@Override
	public Plan selectPlan(Long planId) {
		List<Long> planIds = new ArrayList<Long>();
		planIds.add(planId);
		Map<Long, Plan> map = selectPlanByIds(planIds);
		return map.get(planId);
	}

	@Override
	public List<Long> selectPlanIds(Plan plan) {
		List<Long> resultList = new ArrayList<Long>();
		List<Plan> list = selectPlan(plan, new PageBounds());
		for (Plan parma : list) {
			resultList.add(parma.getPlanId());
		}
		return resultList;
	}

	@Override
	public Map<Long, Plan> selectPlanByIds(List<Long> planIds) {

		Map<Long, Plan> map = new HashMap<Long, Plan>();
		if (planIds == null || planIds.size() == 0) {
			return map;
		}
		List<Plan> list = planDao.selectPlanByIds(planIds);

		for (Plan param : list) {
			map.put(param.getPlanId(), param);
		}
		return map;
	}

	@Override
	public void calculateCTR(List<Probability> list) {
		Double ctr = new Double(0);
		Integer clickCount = 0;
		Integer showCount = 0;
		for (Probability probability : list) {
			clickCount = getClickOrShowCount(RedisConstant.getProbabilityClickCountKey(null,
					probability.getProbabilityId()));
			showCount = getClickOrShowCount(RedisConstant.getProbabilityShowCountKey(null,
					probability.getProbabilityId()));
			if (showCount > 100) {
				ctr = (double) (clickCount / showCount);
				redisService.set(RedisConstant.getProbabilityChannelCTRKey(probability.getProbabilityId()),
						String.valueOf(ctr));
			} else {
				break;
			}
		}

	}

	private Integer getClickOrShowCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isNumber(str)) {
			return Integer.parseInt(str);
		}
		return 0;
	}
}
