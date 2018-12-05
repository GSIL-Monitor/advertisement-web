package com.yuanshanbao.dsp.plan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertiser.dao.AdvertiserDao;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.model.BillType;
import com.yuanshanbao.dsp.bill.service.BillService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.plan.dao.PlanDao;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.project.model.Project;
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

	@Autowired
	private BillService billService;

	@Autowired
	private AdvertiserDao advertiserDao;

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
			plan.setDayConsumed(redisService.get(RedisConstant.getPlanDayBalanceCountKey(null, plan.getPlanId())));
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
		List<Plan> list = setProperty(planDao.selectPlanByIds(planIds));

		for (Plan param : list) {
			map.put(param.getPlanId(), param);
		}
		return map;
	}

	@Override
	public void calculateCTR(List<Probability> list) {
		String protectCountValue = IniBean.getIniValue("plan_protect_count");
		Integer protectCount = 100;
		if (protectCountValue != null && ValidateUtil.isNumber(protectCountValue)) {
			protectCount = Integer.valueOf(protectCountValue);
		}
		Double ctr = new Double(0);
		Integer clickCount = 0;
		Integer showCount = 0;
		for (Probability probability : list) {
			clickCount = getClickOrShowCount(RedisConstant.getProbabilityClickCountKey(null,
					probability.getProbabilityId()));
			showCount = getClickOrShowCount(RedisConstant.getProbabilityShowCountKey(null,
					probability.getProbabilityId()));
			if (showCount > protectCount) {
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

	// 按计划扣费
	@Override
	public void paymentForPlan(Plan plan) {
		Probability params = new Probability();
		params.setPlanId(plan.getPlanId());
		List<Probability> list = probabilityService.selectProbabilitys(params, new PageBounds());
		for (Probability probability : list) {
			// 总消耗
			double nowCount = getCount(RedisConstant
					.getProbabilityBalanceCountKey(null, probability.getProbabilityId()));
			// 上次操作扣费时消耗的金额
			double lastCount = getCount(RedisConstant.getProbabilityLastBalanceCountKey(null,
					probability.getProbabilityId()));
			double difference = nowCount - lastCount;
			billService.checkBillAndCount(plan, probability, lastCount);
			if (difference > 0) {
				billService.createBill(plan, probability, nowCount, lastCount, BillType.DEDUCTION);
				redisService.set(RedisConstant.getProbabilityLastBalanceCountKey(null, probability.getProbabilityId()),
						String.valueOf(nowCount));
				// 该计划当日消耗金额，用于日限额限制
				redisService.increByDouble(RedisConstant.getPlanDayBalanceCountKey(null, plan.getPlanId()), difference);
				// 计划总金额消耗
				redisService.increByDouble(RedisConstant.getPlanBalanceCountKey(plan.getPlanId()), difference);
				// 订单总金额消耗
				redisService.increByDouble(RedisConstant.getOrderBalanceCountKey(plan.getOrderId()), difference);
			}
		}
		calculateIncrement(plan.getPlanId());
	}

	private void calculateIncrement(Long planId) {
		Plan plan = ConfigManager.getPlanById(planId);
		Project project = ConstantsManager.getProjectById(plan.getProjectId());
		if ("jy".equals(project.getName())) {
			return;
		}
		try {
			Integer totalCount = getClickOrShowCount(RedisConstant.getPlanChargeTypeCountKey(planId + ""));
			Integer lastCount = getClickOrShowCount(RedisConstant.getPlanLastChargeTypeCountKey(planId + ""));
			Integer difference = totalCount - lastCount;
			String scale = IniBean.getIniValue("increment_scale");
			difference = (int) (difference * Double.valueOf(scale));
			if (difference == 0) {
				return;
			}
			// 比例
			Probability param = new Probability();
			param.setPlanId(planId);
			param.setStatus(ProbabilityStatus.ONLINE);
			List<Probability> list = probabilityService.selectProbabilitys(param, new PageBounds());
			for (Probability probability : list) {
				String channel = probability.getChannel();
				redisService.sadd(RedisConstant.getChannelIncreIdKey(channel),
						planId + ":" + probability.getProbabilityId());
			}
			redisService.increBy(RedisConstant.getPlanChargeTypeCountKey(planId + ""), difference);
		} catch (Exception e) {
			LoggerUtil.error("calculateIncrement error", e);
		}

	}

	private double getCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isPositiveFloat(str)) {
			return Double.parseDouble(str);
		}
		return 0;
	}

}
