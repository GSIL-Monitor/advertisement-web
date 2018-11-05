package com.yuanshanbao.dsp.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.service.BillService;
import com.yuanshanbao.dsp.order.dao.OrderDao;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private BillService billService;

	@Override
	@Transactional
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
		List<Long> advertiserIds = new ArrayList<Long>();
		for (Order order : list) {
			advertiserIds.add(order.getAdvertiserId());
		}

		Map<Long, Advertiser> map = advertiserService.selectAdvertiserByIds(advertiserIds);
		for (Order order : list) {
			order.setAdvertiser(map.get(order.getAdvertiserId()));
		}
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
