package com.yuanshanbao.dsp.order.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {
	@Override
	public List<Order> selectOrders(Order order, PageBounds pageBounds) {
		return getSqlSession().selectList("order.selectOrder", order, pageBounds);
	}

	@Override
	public int insertOrder(Order order) {
		return getSqlSession().insert("order.insertOrder", order);
	}

	@Override
	public int deleteOrder(Order order) {
		return getSqlSession().delete("order.deleteOrder", order);
	}

	@Override
	public int updateOrder(Order order) {
		return getSqlSession().update("order.updateOrder", order);
	}

	@Override
	public List<Order> selectOrderByIds(List<Long> ids) {
		return getSqlSession().selectList("order.selectOrderByIds", ids);
	}

}
