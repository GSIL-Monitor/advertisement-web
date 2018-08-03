package com.yuanshanbao.dsp.bill.dao;

import java.util.List;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.paginator.domain.PageBounds;

public class BillDaoImpl extends BaseDaoImpl implements BillDao {

	@Override
	public int insertBill(Bill bill) {
		return getSqlSession().insert("bill.insertBill", bill);
	}

	@Override
	public int updateBill(Bill bill) {
		return getSqlSession().update("bill.updateBill", bill);
	}

	@Override
	public List<Information> selectBill(Bill bill, PageBounds pageBounds) {
		return getSqlSession().selectList("bill.selectBill", bill, pageBounds);
	}
}
