package com.yuanshanbao.dsp.bill.dao;

import java.util.List;

import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface BillDao {
	public int insertBill(Bill bill);

	public int updateBill(Bill bill);

	public List<Bill> selectBill(Bill bill, PageBounds pageBounds);

	public int deleteBill(Long billId);

	public Bill selectAdvertiserConsume(Bill bill);
}
