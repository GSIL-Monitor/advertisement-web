package com.yuanshanbao.dsp.bill.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
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
	public List<Bill> selectBill(Bill bill, PageBounds pageBounds) {
		return getSqlSession().selectList("bill.selectBills", bill, pageBounds);
	}

	@Override
	public int deleteBill(Long billId) {
		return getSqlSession().delete("bill.deleteBill", billId);
	}
}
