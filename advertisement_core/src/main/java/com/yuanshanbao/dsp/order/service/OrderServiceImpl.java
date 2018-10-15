package com.yuanshanbao.dsp.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.order.dao.OrderDao;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public void insertOrder(Order order) {
		int result = -1;

		result = orderDao.insertOrder(order);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateOrder(Order order) {
		int result = -1;

		result = orderDao.updateOrder(order);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteOrder(Order order) {
		int result = -1;

		result = orderDao.deleteOrder(order);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Order> selectOrder(Order order, PageBounds pageBounds) {
		return setProperty(orderDao.selectOrders(order, pageBounds));

	}

	private List<Order> setProperty(List<Order> list) {
		return list;
	}

	@Override
	public Order selectOrder(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> selectOrderIds(Order order) {
		List<Long> resultList = new ArrayList<Long>();
		List<Order> list = selectOrder(order, new PageBounds());
		for (Order parma : list) {
			resultList.add(parma.getOrderId());
		}
		return resultList;
	}

	@Override
	public Map<Long, Order> selectOrderByIds(List<Long> orderIds) {

		Map<Long, Order> map = new HashMap<Long, Order>();
		if (orderIds == null || orderIds.size() == 0) {
			return map;
		}
		List<Order> list = orderDao.selectOrderByIds(orderIds);

		for (Order param : list) {
			map.put(param.getOrderId(), param);
		}
		return map;
	}
}
