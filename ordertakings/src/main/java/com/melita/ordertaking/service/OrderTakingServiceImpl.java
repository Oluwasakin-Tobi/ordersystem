package com.melita.ordertaking.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.melita.ordertaking.configuration.RabbitMQConfigProperties;
import com.melita.ordertaking.dto.OrderDTO;
import com.melita.ordertaking.validator.OrderValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderTakingServiceImpl implements OrderTakingService {
	
	final RabbitTemplate rabbitTemplate;
	final RabbitMQConfigProperties configProperties; 
	final OrderValidator validator;
	
	@Override
	public void takeOrders(OrderDTO order) {
		
		validator.validate(order);
		log.info("Taking orders");
		rabbitTemplate.convertAndSend(configProperties.getOrderTakingExchange(), 
				configProperties.getOrderTakingRoutingKey(), order);
	}

}
