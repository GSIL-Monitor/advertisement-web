package com.yuanshanbao.dsp.bill.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface BillService {
	public void insertBill(Bill bill);

	public void updateBill(Bill bill);

	public void deleteBill(Long billId);

	public List<Bill> selectBill(Bill bill, PageBounds pageBounds);

	public void payment(Advertiser advertiser);

	public void payment(Order order);

	public void calculate(String date);

	public void calculateByPlan(String date);

	public void recharge(Bill bill);

	public void paymentForPlan(Plan plan);

	public void combineProbabilityBill(Plan plan);

	public Map<String, BigDecimal> selectAdvertiserConsume(Long advertiserId, HttpServletRequest request);

	public void createBill(Plan plan, Probability probability, double nowCount, double lastCount, int type);

	public void checkBillAndCount(Plan plan, Probability probability, double lastCount);

}
