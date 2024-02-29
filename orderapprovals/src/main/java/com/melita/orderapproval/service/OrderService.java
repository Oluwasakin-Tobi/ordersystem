package com.melita.orderapproval.service;

import org.springframework.data.domain.Page;
import com.melita.orderapproval.dto.OrderDTO;
import com.melita.orderapproval.entity.Orders;

public interface OrderService {

	void processOrders(OrderDTO order);

	Page<Orders> fetchOrders(int offset, int pageSize);

	Orders approveOrder(long id);

}
