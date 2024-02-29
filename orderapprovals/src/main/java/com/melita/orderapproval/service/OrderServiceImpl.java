package com.melita.orderapproval.service;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.melita.orderapproval.configuration.RabbitMQConfigProperties;
import com.melita.orderapproval.dto.OrderDTO;
import com.melita.orderapproval.entity.Orders;
import com.melita.orderapproval.exception.ResourceNotFoundException;
import com.melita.orderapproval.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	final RabbitTemplate rabbitTemplate;
	final RabbitMQConfigProperties configProperties;
	final OrderRepository orderRepository;
	final ModelMapper mapper;

	@Override
	public void processOrders(OrderDTO order) {

		log.info("processing order " + order);

		Orders customerOrder = mapper.map(order, Orders.class);
		Orders response = orderRepository.save(customerOrder);

		// email service
		rabbitTemplate.convertAndSend(configProperties.getOrderNotificationExchange(),
				configProperties.getOrderNotificationRoutingKey(), mapper.map(response, OrderDTO.class));

	}

	@Override
	public Page<Orders> fetchOrders(int offset, int pageSize) {
		Pageable pageable = PageRequest.of(offset, pageSize, Sort.by("createdDateTime").descending());

		return orderRepository.findAll(pageable);
	}

	@Override
	public Orders approveOrder(long id) {
		Orders order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Order with Id [%s] not found", id)));
		log.info("Approve order " + order);
		order.setApproved(true);

		// Assumption: A call is made to the order fulfillment system
		return orderRepository.save(order);
	}

}
