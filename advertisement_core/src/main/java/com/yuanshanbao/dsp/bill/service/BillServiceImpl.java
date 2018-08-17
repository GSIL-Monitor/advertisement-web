package com.yuanshanbao.dsp.bill.service;

import java.math.BigDecimal;
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
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
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
	private AdvertiserService advertiserService;

	@Autowired
	private AdvertiserDao advertiserDao;

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
		double nowCount = getCount(RedisConstant.getAdvertiserBalanceCountKey(advertiser.getAdvertiserId()));
		// 上次操作扣费时消耗的数量
		double lastCount = getCount(RedisConstant.getAdvertiserLastBalanceCountKey(advertiser.getAdvertiserId()));
		double difference = nowCount - lastCount;
		if (difference > 0) {
			createBill(advertiser, difference, BillType.DEDUCTION);
			redisService.set(RedisConstant.getAdvertiserLastBalanceCountKey(advertiser.getAdvertiserId()),
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

	public void calculate() {
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
							count = getClickOrShowCount(RedisConstant.getAdvertisementClickCountPVKey(null,
									quo.getAdvertisementId() + "", quo.getChannel()));
						} else {
							count = getClickOrShowCount(RedisConstant.getAdvertisementActivityClickCountPVKey(null,
									quo.getAdvertisementId() + "", quo.getChannel()));
						}
					} else if (quo.getType().equals(QuotaType.CPT)) {
						if (StringUtils.isNotBlank(quo.getChannel())) {
							count = getClickOrShowCount(RedisConstant.getAdvertisementShowCountPVKey(null,
									quo.getAdvertisementId() + "", quo.getChannel()));
						} else {
							count = getClickOrShowCount(RedisConstant.getAdvertisementActivityShowCountPVKey(null,
									quo.getAdvertisementId() + "", quo.getChannel()));
						}
					}
					count = count + getClickOrShowCount(RedisConstant.getQuotaCount(quo.getQuotaId()));
					redisService.increBy(RedisConstant.getQuotaCount(quo.getQuotaId()), count);
				}
				if (quo.getUnitPrice() != null) {
					money = money.add(quo.getUnitPrice().multiply(BigDecimal.valueOf(count)));
				}
			}
			System.err.println(money);
			redisService.set(RedisConstant.getAdvertiserBalanceCountKey(advertiser.getAdvertiserId()),
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
}
