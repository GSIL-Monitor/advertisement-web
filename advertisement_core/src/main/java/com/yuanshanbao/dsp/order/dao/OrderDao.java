package com.yuanshanbao.dsp.order.dao;

import java.util.List;

import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface OrderDao {

	public List<Order> selectOrders(Order order, PageBounds pageBounds);

	public int insertOrder(Order order);

	public int deleteOrder(Order order);

	public int updateOrder(Order order);

	public List<Order> selectOrderByIds(List<Long> ids);

}
