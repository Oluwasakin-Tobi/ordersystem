package com.melita.orderapproval.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.melita.orderapproval.dto.OrderDTO;
import com.melita.orderapproval.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderListener {
	
	private final OrderService orderService;
	
	@RabbitListener(queues = "${rabbitmq.orderTakingQueue}")
	public void orderListener(OrderDTO order) {
		orderService.processOrders(order);
		
	}

}
