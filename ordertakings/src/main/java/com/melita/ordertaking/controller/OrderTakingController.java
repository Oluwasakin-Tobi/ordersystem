package com.melita.ordertaking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.melita.ordertaking.dto.OrderDTO;
import com.melita.ordertaking.service.OrderTakingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderTakingController {
	
	private final OrderTakingService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<?> orderTaking(@RequestBody OrderDTO order){
		
		orderService.takeOrders(order);
		return ResponseEntity.ok().body("Order accepted");
	}
	

}
