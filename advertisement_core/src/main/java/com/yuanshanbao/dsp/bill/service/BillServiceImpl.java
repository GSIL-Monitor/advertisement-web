package com.yuanshanbao.dsp.bill.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.bill.model.BillType;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private RedisService redisService;

	@Override
	public void insertBill(Bill bill) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void payment(Advertiser advertiser) {
		// 今日总消耗
		double todayCount = getCount(RedisConstant.getAdvertiserBalanceCountKey(null, advertiser.getAdvertiserId()));
		// 上次操作扣费时消耗的数量
		double lastCount = getCount(RedisConstant.getAdvertiserLastBalanceCountKey(null, advertiser.getAdvertiserId()));
		double difference = todayCount - lastCount;
		Bill bill = createBill(advertiser, difference, BillType.DEDUCTION);
		insertBill(bill);
		redisService.set(RedisConstant.getAdvertiserBalanceCountKey(null, advertiser.getAdvertiserId()),
				String.valueOf(todayCount));

	}

	private Bill createBill(Advertiser advertiser, double difference, int type) {
		Bill bill = new Bill();
		bill.setAdvertiserId(advertiser.getAdvertiserId());
		bill.setAmount(BigDecimal.valueOf(difference));
		bill.setType(type);
		bill.setStatus(CommonStatus.ONLINE);
		return bill;
	}

	private double getCount(String key) {
		String str = (String) redisService.get(key);
		if (ValidateUtil.isPositiveFloat(str)) {
			return Double.parseDouble(str);
		} else {
			LoggerUtil.info("error");
		}
		return 0;
	}
}
