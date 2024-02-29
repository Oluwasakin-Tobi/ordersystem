package com.melita.orderapproval.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.melita.orderapproval.dto.OrderDTO;
import com.melita.orderapproval.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApprovalController {
	
	final OrderService orderService;
	final ModelMapper mapper;
	
	@GetMapping("/orders")
	public ResponseEntity<?> fetchOrders(@RequestParam(defaultValue = "0") int offset, 
			@RequestParam(defaultValue = "3") int pageSize) {
		return ResponseEntity.ok(orderService.fetchOrders(offset, pageSize)
				.map(order -> mapper.map(order, OrderDTO.class)));
	}
	
	@PatchMapping("/orders/{id}")
	public ResponseEntity<?> approveOrder(@PathVariable("id") long id) {
		return ResponseEntity.ok(mapper.map(orderService.approveOrder(id), OrderDTO.class) );
		
	}

}
