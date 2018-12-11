package com.yuanshanbao.dsp.bill.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertiser.dao.AdvertiserDao;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.dao.BillDao;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.bill.model.BillStatus;
import com.yuanshanbao.dsp.bill.model.BillType;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.DspConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.plan.dao.PlanDao;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.plan.model.PlanStatus;
import com.yuanshanbao.dsp.plan.service.PlanService;
import com.yuanshanbao.dsp.probability.dao.ProbabilityDao;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.quota.service.operation.AdvertisementOperation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private BillDao billDao;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private RedisService redisCacheService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private PlanService planService;

	@Autowired
	private AdvertiserDao advertiserDao;

	@Autowired
	private ProbabilityDao probabilityDao;

	@Autowired
	private PlanDao planDao;

	@Override
	public void insertBill(Bill bill) {
		int result = -1;

		result = billDao.insertBill(bill);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateBill(Bill bill) {
		int result = -1;

		result = billDao.updateBill(bill);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteBill(Long billId) {
		int result = -1;

		result = billDao.deleteBill(billId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Bill> selectBill(Bill bill, PageBounds pageBounds) {
		return setProperty(billDao.selectBill(bill, pageBounds));
	}

	private List<Bill> setProperty(List<Bill> list) {
		List<Long> advertiserIds = new ArrayList<Long>();
		for (Bill bill : list) {
			advertiserIds.add(bill.getAdvertiserId());
		}
		Map<Long, Advertiser> advertiserMap = advertiserService.selectAdvertiserByIds(advertiserIds);
		for (Bill bill : list) {
			bill.setAdvertiser(advertiserMap.get(bill.getAdvertiserId()));
		}
		return list;
	}

	@Transactional
	@Override
	// 广告主扣费
	public void payment(Advertiser advertiser) {
		// 总消耗
		double nowCount = getCount(RedisConstant.getAdvertiserBalanceCountKey(null, advertiser.getAdvertiserId()));
		// 上次操作扣费时消耗的数量
		double lastCount = getCount(RedisConstant.getAdvertiserLastBalanceCountKey(null, advertiser.getAdvertiserId()));
		double difference = nowCount - lastCount;
		if (difference > 0) {
			createBill(advertiser, difference, BillType.DEDUCTION);
			redisService.set(RedisConstant.getAdvertiserLastBalanceCountKey(null, advertiser.getAdvertiserId()),
					String.valueOf(nowCount));
		}
	}

	@Override
	@Transactional
	public void paymentForPlan(Plan plan) {
		// 按计划扣费
		Probability params = new Probability();
		params.setPlanId(plan.getPlanId());
		List<Probability> list = probabilityService.selectProbabilitys(params, new PageBounds());
		Double money = new Double(0);
		for (Probability probability : list) {
			// 总消耗
			double nowCount = getCount(RedisConstant
					.getProbabilityBalanceCountKey(null, probability.getProbabilityId()));
			// 上次操作扣费时消耗的数量
			double lastCount = getCount(RedisConstant.getProbabilityLastBalanceCountKey(null,
					probability.getProbabilityId()));
			double difference = nowCount - lastCount;
			// 防止扣费错误的情况
			checkBillAndCount(plan, probability, lastCount);
			if (difference > 0) {
				createBill(plan, probability, nowCount, lastCount, BillType.DEDUCTION);
				redisService.set(RedisConstant.getProbabilityLastBalanceCountKey(null, probability.getProbabilityId()),
						String.valueOf(nowCount));
				money = money + difference;
			}
		}
		// dealWithOverPlanAndOrder(plan, money);
	}

	@Transactional
	@Override
	public void checkBillAndCount(Plan plan, Probability probability, double lastCount) {
		Bill param = new Bill();
		param.setPlanId(probability.getPlanId());
		param.setOrderId(plan.getOrderId());
		param.setDate(DateUtils.format(new Date()));
		param.setChannel(probability.getChannel());
		param.setProbabilityId(probability.getProbabilityId());
		param.setType(BillType.DEDUCTION);
		List<Bill> list = selectBill(param, new PageBounds());
		if (list != null && list.size() > 0) {
			Bill bill = list.get(0);
			if (bill.getNowCount().compareTo(BigDecimal.valueOf(lastCount).setScale(5, BigDecimal.ROUND_HALF_DOWN)) == 0) {
				return;
			} else {
				LoggerUtil.error("checkBill error,bill={},last={}", JacksonUtil.obj2json(bill),
						String.valueOf(lastCount));
			}
		}
	}

	private void dealWithOverPlanAndOrder(Plan plan, Double money) {
		checkPlanBalance(plan, money);
		checkOrderBalance(plan, money);
	}

	private void checkOrderBalance(Plan plan, Double money) {
		try {
			if (money <= 0) {
				return;
			}
			Double totalAmount = redisService.increByDouble(RedisConstant.getOrderBalanceCountKey(plan.getOrderId()),
					money);
			Double initAmount = Double.valueOf(redisService.get(RedisConstant.getOrderInitCountKey(plan.getOrderId())));
			if (initAmount < totalAmount) {
				// 把在投放的删除
				Probability proParam = new Probability();
				proParam.setOrderId(plan.getOrderId());
				proParam.setStatus(CommonStatus.ONLINE);
				List<Probability> probabilityList = probabilityService.selectProbabilitys(proParam, new PageBounds());
				for (Probability pro : probabilityList) {
					pro.setStatus(CommonStatus.OFFLINE);
					probabilityService.updateProbability(pro);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("checkOrderBalance", e);
		}
	}

	private void checkPlanBalance(Plan plan, Double money) {
		try {
			if (money <= 0) {
				return;
			}
			Double totalAmount = redisService.increByDouble(RedisConstant.getPlanBalanceCountKey(plan.getPlanId()),
					money);
			if (plan.getSpend().compareTo(new BigDecimal(totalAmount)) < 0) {
				Plan resultPlan = new Plan();
				resultPlan.setPlanId(plan.getPlanId());
				resultPlan.setStatus(PlanStatus.NOTFUNDS);
				// 把计划设置为余额不足
				planService.updatePlan(resultPlan);
				// 把在投放的删除
				Probability proParam = new Probability();
				proParam.setPlanId(plan.getPlanId());
				proParam.setStatus(CommonStatus.ONLINE);
				List<Probability> probabilityList = probabilityService.selectProbabilitys(proParam, new PageBounds());
				for (Probability pro : probabilityList) {
					pro.setStatus(CommonStatus.OFFLINE);
					probabilityService.updateProbability(pro);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("checkOrderBalance", e);
		}
	}

	public void createBill(Advertiser advertiser, double difference, int type) {
		Bill bill = new Bill();
		bill.setAdvertiserId(advertiser.getAdvertiserId());
		bill.setAmount(BigDecimal.valueOf(difference));
		bill.setDate(DateUtils.format(new Date()));
		bill.setType(type);
		bill.setStatus(CommonStatus.ONLINE);
		insertBill(bill);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("advertiserId", advertiser.getAdvertiserId());
		parameters.put("difference", bill.getAmount());
		int result = advertiserDao.cutPayment(parameters);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Transactional
	public void createBill(Plan plan, Probability probability, double nowCount, double lastCount, int type) {
		Bill bill = new Bill();
		bill.setPlanId(probability.getPlanId());
		bill.setAdvertiserId(plan.getAdvertiserId());
		bill.setOrderId(plan.getOrderId());
		bill.setProbabilityId(probability.getProbabilityId());
		// bill.setAmount(BigDecimal.valueOf(nowCount - lastCount).setScale(3,
		// RoundingMode.FLOOR));
		bill.setAmount(BigDecimal.valueOf(nowCount).subtract(BigDecimal.valueOf(lastCount)));
		bill.setDate(DateUtils.format(new Date()));
		bill.setChannel(probability.getChannel());
		bill.setNowCount(BigDecimal.valueOf(nowCount));
		bill.setType(type);
		bill.setStatus(CommonStatus.ONLINE);
		insertBill(bill);
		LoggerUtil.info("createBill success={}", JacksonUtil.obj2json(bill));
		Advertiser advertiser = advertiserService.selectAdvertiserForUpdate(plan.getAdvertiserId());
		if (advertiser != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("advertiserId", plan.getAdvertiserId());
			parameters.put("amount", bill.getAmount());
			int result = advertiserDao.cutPayment(parameters);
			if (result < 0) {
				throw new BusinessException(ComRetCode.FAIL);
			}
			LoggerUtil.info("cutPayment success={}  amount={}", JacksonUtil.obj2json(advertiser), bill.getAmount());
		}
	}

	private double getCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isPositiveFloat(str)) {
			return Double.parseDouble(str);
		}
		return 0;
	}

	private Integer getClickOrShowCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isNumber(str)) {
			return Integer.parseInt(str);
		}
		return 0;
	}

	public void calculate(String date) {
		Advertiser params = new Advertiser();
		params.setStatus(CommonStatus.ONLINE);
		List<Advertiser> list = advertiserService.selectAdvertiser(params, new PageBounds());
		BigDecimal money = null;
		Integer count = 0;
		for (Advertiser advertiser : list) {
			money = new BigDecimal(0);
			Quota paramsQuota = new Quota();
			paramsQuota.setAdvertiserId(advertiser.getAdvertiserId());
			paramsQuota.setStatus(CommonStatus.ONLINE);
			List<Quota> quotaList = quotaService.selectQuota(paramsQuota, new PageBounds());
			for (Quota quo : quotaList) {
				count = 0;
				if (quo.getType() != null) {
					if (quo.getType().equals(QuotaType.CPC)) {
						if (StringUtils.isNotBlank(quo.getChannel())) {
							count = getClickOrShowCount(RedisConstant.getAdvertisementClickCountPVKey(date,
									quo.getAdvertisementId() + "", quo.getChannel()));
						} else {
							count = getClickOrShowCount(RedisConstant.getAdvertisementActivityClickCountPVKey(date,
									quo.getAdvertisementId() + "", quo.getChannel()));
						}
					} else if (quo.getType().equals(QuotaType.CPT)) {
						if (StringUtils.isNotBlank(quo.getChannel())) {
							count = getClickOrShowCount(RedisConstant.getAdvertisementShowCountPVKey(date,
									quo.getAdvertisementId() + "", quo.getChannel()));
						} else {
							count = getClickOrShowCount(RedisConstant.getAdvertisementActivityShowCountPVKey(date,
									quo.getAdvertisementId() + "", quo.getChannel()));
						}
					}
					// count = count -
					// getClickOrShowCount(RedisConstant.getQuotaCount(quo.getQuotaId()));
					// redisService.increBy(RedisConstant.getQuotaCount(quo.getQuotaId()),
					// count);
				}
				if (quo.getUnitPrice() != null) {
					money = money.add(quo.getUnitPrice().multiply(BigDecimal.valueOf(count)));
				}
			}
			redisService.set(RedisConstant.getAdvertiserBalanceCountKey(date, advertiser.getAdvertiserId()),
					String.valueOf(money));
		}
	}

	@Transactional
	@Override
	public void recharge(Bill bill) {
		bill.setType(BillType.RECHARGE);
		bill.setStatus(CommonStatus.ONLINE);
		bill.setDate(DateUtils.format(new Date()));
		insertBill(bill);
		Advertiser advertiser = advertiserService.selectAdvertiserForUpdate(bill.getAdvertiserId());
		if (advertiser != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("advertiserId", bill.getAdvertiserId());
			parameters.put("amount", bill.getAmount());
			int result = advertiserDao.lockBalance(parameters);
			if (result < 0) {
				throw new BusinessException(ComRetCode.FAIL);
			}
			LoggerUtil.info("充值成功={}", JacksonUtil.obj2json(bill));
		}
	}

	public Map<String, Map<Long, BigDecimal>> getChannelAllBid() {
		Channel channelParams = new Channel();
		channelParams.setStatus(CommonStatus.ONLINE);
		List<Channel> channelList = channelService.selectChannels(channelParams, new PageBounds());
		Map<String, Map<Long, BigDecimal>> channelCostMap = new HashMap<String, Map<Long, BigDecimal>>();
		for (Channel channel : channelList) {
			Probability proParams = new Probability();
			proParams.setChannel(channel.getKey());
			proParams.setStatus(CommonStatus.ONLINE);
			List<Probability> proList = probabilityService.selectProbabilitys(proParams, new PageBounds());
			List<Long> proIds = new ArrayList<Long>();
			for (Probability probability : proList) {
				proIds.add(probability.getProbabilityId());
			}
			Map<Long, Quota> map = quotaService.selectQuotaByProbabilityId(proIds);
			List<Quota> quotaList = (List<Quota>) map.values();
			Collections.sort(quotaList, new Comparator<Quota>() {
				@Override
				public int compare(Quota o1, Quota o2) {
					return o2.getBestBid().compareTo(o1.getBestBid());
				}
			});
			// 为每一条计划设置价格
			Map<Long, BigDecimal> bidMap = setPlanBid(channel, quotaList);
			channelCostMap.put(channel.getKey(), bidMap);
		}
		return channelCostMap;
	}

	// 根据竞价原则对每条计划设置出价
	private Map<Long, BigDecimal> setPlanBid(Channel channel, List<Quota> quotaList) {
		Map<Long, BigDecimal> bidMap = new HashMap<Long, BigDecimal>();
		int size = quotaList.size();
		for (int i = 0; i < size; i++) {
			int j = i + 1;
			if (j > size) {
				bidMap.put(quotaList.get(i).getProbabilityId(), channel.getUnitPrice());
				break;
			}
			bidMap.put(quotaList.get(i).getProbabilityId(), quotaList.get(j).getBestBid());
		}
		return bidMap;
	}

	public void calculateByPlan(String date) {
		Plan param = new Plan();
		param.setStatus(PlanStatus.ONLINE);
		BigDecimal money = null;
		Integer count = 0;
		List<Plan> planList = planService.selectPlan(param, new PageBounds());
		for (Plan plan : planList) {
			Probability paramsPro = new Probability();
			paramsPro.setPlanId(plan.getPlanId());
			paramsPro.setStatus(CommonStatus.ONLINE);
			List<Probability> probabilityList = probabilityService.selectProbabilitys(paramsPro, new PageBounds());
			money = new BigDecimal(0);
			for (Probability probability : probabilityList) {
				count = 0;
				AdvertisementOperation operation = QuotaType.getCountFactory(plan.getChargeType());
				operation.setProbability(probability);
				operation.setRedisCacheService(redisCacheService);
				String countValue = operation.getProbabilityResult();
				// 竞价信息随时改变，因此不能按照所有数量进行计算
				if (ValidateUtil.isNumber(countValue)) {
					Integer currentCount = Integer.parseInt(countValue);
					Integer lastCount = getClickOrShowCount(RedisConstant.getPlanLastBalanceCountKey(date,
							probability.getProbabilityId()));
					count = currentCount - lastCount;
					if (count == 0) {
						continue;
					}
					// 获取竞价信息
					Map<Long, BigDecimal> proCostMap = DspConstantsManager.getBidByChannel(probability.getChannel());
					if (proCostMap != null) {
						if (proCostMap.get(probability.getProbabilityId()) != null) {
							BigDecimal unitPrice = getUnitPrice(plan, probability, proCostMap);
							money = unitPrice.multiply(BigDecimal.valueOf(count));
							String proMoneyValue = redisService.get(RedisConstant.getProbabilityBalanceCountKey(date,
									probability.getProbabilityId()));
							if (ValidateUtil.isDouble(proMoneyValue)) {
								BigDecimal proMoney = new BigDecimal(proMoneyValue);
								proMoney = proMoney.add(money);
								redisService.set(
										RedisConstant.getProbabilityBalanceCountKey(date,
												probability.getProbabilityId()), String.valueOf(proMoney));
							} else {
								redisService.set(
										RedisConstant.getProbabilityBalanceCountKey(date,
												probability.getProbabilityId()), String.valueOf(money));
							}
							redisService.set(
									RedisConstant.getPlanLastBalanceCountKey(date, probability.getProbabilityId()),
									currentCount.toString());
						}
					}
				}
			}
		}
	}

	private BigDecimal getUnitPrice(Plan plan, Probability probability, Map<Long, BigDecimal> proCostMap) {
		BigDecimal bestBid = proCostMap.get(probability.getProbabilityId());
		BigDecimal unitPrice = new BigDecimal(0);
		if (QuotaType.CPM.equals(plan.getChargeType())) {
			unitPrice = bestBid.divide(new BigDecimal(1000));
		} else {
			unitPrice = bestBid;
		}
		return unitPrice;
	}

	@Override
	public void payment(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void combineProbabilityBill(Plan plan) {
		Bill billParams = new Bill();
		billParams.setPlanId(plan.getPlanId());
		List<Bill> list = selectBill(billParams, new PageBounds());
		BigDecimal money = new BigDecimal(0);
		for (Bill bill : list) {
			money = new BigDecimal(0);
			money = money.add(bill.getAmount());
		}
		if (money.compareTo(new BigDecimal(0)) > 0) {
			Bill planBill = new Bill();
			planBill.setAdvertiserId(plan.getAdvertiserId());
			planBill.setAmount(money);
			planBill.setPlanId(plan.getPlanId());
			insertBill(planBill);
		}
		for (Bill bill : list) {
			deleteBill(bill.getBillId());
		}
	}

	@Override
	public Map<String, BigDecimal> selectAdvertiserConsume(Long advertiserId, HttpServletRequest request) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		Bill param = new Bill();
		param.setType(BillType.DEDUCTION);
		param.setAdvertiserId(advertiserId);
		param.setQueryEndTime(DateUtils.format(new Date()));
		// 当日消耗
		param.setQueryStartTime(DateUtils.format(new Date()));
		Bill todayBill = billDao.selectAdvertiserConsume(param);
		request.setAttribute("today", todayBill != null ? todayBill.getAmount() : 0);
		// 昨日消耗
		param.setQueryStartTime(DateUtils.format(DateUtils.addDays(new Date(), -1)));
		param.setQueryEndTime(DateUtils.format(DateUtils.addDays(new Date(), -1)));
		Bill yesterdayBill = billDao.selectAdvertiserConsume(param);
		request.setAttribute("yesterday", yesterdayBill != null ? yesterdayBill.getAmount() : 0);
		// 7日消耗
		param.setQueryEndTime(DateUtils.format(new Date()));
		param.setQueryStartTime(DateUtils.format(DateUtils.addDays(new Date(), -7)));
		Bill sevenBill = billDao.selectAdvertiserConsume(param);
		request.setAttribute("seven", sevenBill != null ? sevenBill.getAmount() : 0);
		// 30天消耗
		param.setQueryStartTime(DateUtils.format(DateUtils.addDays(new Date(), -30)));
		Bill thirtyBill = billDao.selectAdvertiserConsume(param);
		request.setAttribute("thirtyDay", thirtyBill != null ? thirtyBill.getAmount() : 0);
		// 当月消耗
		param.setQueryStartTime(DateUtils.format(DateUtils.getFirstDayOfMonth(new Date())));
		Bill monthBill = billDao.selectAdvertiserConsume(param);
		request.setAttribute("month", monthBill != null ? monthBill.getAmount() : 0);
		return map;
	}

	@Override
	public void combineAdvertiserBill(Advertiser advertiser, String date) {
		Bill param = new Bill();
		param.setAdvertiserId(advertiser.getAdvertiserId());
		param.setStatus(BillStatus.ORIGINAL_BILL);
		param.setType(BillType.DEDUCTION);
		param.setDate(date);
		Bill mergeBill = billDao.selectAdvertiserConsume(param);
		if (mergeBill != null && mergeBill.getAmount().compareTo(new BigDecimal(0)) > 0) {
			mergeBill.setStatus(BillStatus.MERGE_BILL);
			mergeBill.setType(BillType.DEDUCTION);
			mergeBill.setDate(date);
			insertBill(mergeBill);
		}
		List<Bill> list = selectBill(param, new PageBounds());
		if (list != null && list.size() > 0) {
			for (Bill bill : list) {
				bill.setStatus(CommonStatus.OFFLINE);
				updateBill(bill);
			}
		}
	}
}
