package com.yuanshanbao.dsp.bill.dao;

import java.util.List;

import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface BillDao {
	public int insertBill(Bill bill);

	public int updateBill(Bill bill);

	public List<Information> selectBill(Bill bill, PageBounds pageBounds);
}
