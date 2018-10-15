package com.yuanshanbao.dsp.order.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface OrderService {

	public void insertOrder(Order order);

	public void updateOrder(Order order);

	public void deleteOrder(Order order);

	public List<Order> selectOrder(Order order, PageBounds pageBounds);

	public Order selectOrder(Long orderId);

	public List<Long> selectOrderIds(Order order);

	public Map<Long, Order> selectOrderByIds(List<Long> orderIds);

}
