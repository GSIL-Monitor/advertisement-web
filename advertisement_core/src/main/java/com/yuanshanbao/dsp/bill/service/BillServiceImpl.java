package com.yuanshanbao.dsp.bill.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertiser.dao.AdvertiserDao;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.dao.BillDao;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.bill.model.BillType;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.probability.dao.ProbabilityDao;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.quota.service.operation.AdvertisementOperation;
import com.yuanshanbao.dsp.quota.service.operation.QuotaOperationFactory;
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
	private AdvertiserDao advertiserDao;

	@Autowired
	private ProbabilityDao probabilityDao;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBill(Bill bill) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Bill> selectBill(Bill bill, PageBounds pageBounds) {
		return setProperty(billDao.selectBill(bill, pageBounds));
	}

	private List<Bill> setProperty(List<Bill> list) {
		// TODO Auto-generated method stub
		return list;
	}

	@Transactional
	@Override
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

	@Transactional
	@Override
	public void paymentForPlan(Probability probability) {
		// 总消耗
		double nowCount = getCount(RedisConstant.getPlanBalanceCountKey(null, probability.getProbabilityId()));
		// 上次操作扣费时消耗的数量
		double lastCount = getCount(RedisConstant.getPlanLastBalanceCountKey(null, probability.getProbabilityId()));
		double difference = nowCount - lastCount;
		if (difference > 0) {
			createBill(probability, difference, BillType.DEDUCTION);
			redisService.set(RedisConstant.getPlanLastBalanceCountKey(null, probability.getProbabilityId()),
					String.valueOf(nowCount));
		}
	}

	public void createBill(Advertiser advertiser, double difference, int type) {
		Bill bill = new Bill();
		bill.setAdvertiserId(advertiser.getAdvertiserId());
		bill.setAmount(BigDecimal.valueOf(difference));
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

	public void createBill(Probability probability, double difference, int type) {
		Bill bill = new Bill();
		bill.setProbabilityId(probability.getProbabilityId());
		bill.setAmount(BigDecimal.valueOf(difference));
		bill.setType(type);
		bill.setStatus(CommonStatus.ONLINE);
		insertBill(bill);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("probabilityId", probability.getProbabilityId());
		parameters.put("difference", bill.getAmount());
		int result = probabilityDao.cutPayment(parameters);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
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
		insertBill(bill);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("advertiserId", bill.getAdvertiserId());
		parameters.put("amount", bill.getAmount());
		int result = advertiserDao.lockBalance(parameters);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
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
			List<Probability> proList = probabilityService.selectGeneralProbabilitys(proParams, new PageBounds());
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
		Map<String, Map<Long, BigDecimal>> channelCostMap = getChannelAllBid();
		Order param = new Order();
		param.setStatus(CommonStatus.ONLINE);
		List<Order> orderList = orderService.selectOrder(param, new PageBounds());
		BigDecimal money = null;
		Integer count = 0;
		for (Order order : orderList) {
			Quota quoParam = new Quota();
			quoParam.setOrderId(order.getOrderId());
			quoParam.setStatus(CommonStatus.ONLINE);
			List<Quota> quoList = quotaService.selectQuota(quoParam, new PageBounds());
			for (Quota quota : quoList) {
				money = new BigDecimal(0);
				QuotaOperationFactory factory = QuotaType.getCountFactory(quota.getType());
				AdvertisementOperation operation = factory.getOperation();
				operation.setQuota(quota);
				operation.setRedisCacheService(redisCacheService);
				String countValue = operation.getResult();
				// 竞价信息随时改变，因此不能按照所有数量进行计算
				if (ValidateUtil.isNumber(countValue)) {
					Integer currentCount = Integer.parseInt(countValue);
					Integer lastCount = getClickOrShowCount(RedisConstant.getPlanLastBalanceCountKey(date,
							quota.getProbabilityId()));
					count = currentCount - lastCount;
				}
				// 获取竞价信息
				Map<Long, BigDecimal> proCostMap = channelCostMap.get(quota.getChannel());
				if (proCostMap != null) {
					if (proCostMap.get(quota.getProbabilityId()) != null) {
						money = proCostMap.get(quota.getProbabilityId()).multiply(BigDecimal.valueOf(count));
					}
				}
				redisService.set(RedisConstant.getPlanBalanceCountKey(date, quota.getProbabilityId()),
						String.valueOf(money));
			}
		}
	}

	@Override
	public void payment(Order order) {
		// TODO Auto-generated method stub

	}
}
