package com.melita.orderapproval.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.melita.orderapproval.entity.Orders;
import com.melita.orderapproval.entity.Product;
import com.melita.orderapproval.exception.ResourceNotFoundException;
import com.melita.orderapproval.entity.Customer;
import com.melita.orderapproval.repository.OrderRepository;
import com.melita.orderapproval.service.OrderServiceImpl;

@SpringBootTest
public class OrderServiceTest {
	
	@InjectMocks
	OrderServiceImpl service;
	@Mock
	OrderRepository repository;
	@Mock
	ModelMapper mapper;
	
	@BeforeEach
	void setup() {
		
	}
	
	@Test
	public void testFetchOrders() {
		
		Orders order = getCustomerOrder();
		
		Pageable pageable = PageRequest.of(0, 3, Sort.by("createdDateTime").descending());

		Page<Orders> orders = new PageImpl<>(Collections.singletonList(order), pageable, 1);

		when(repository.findAll(pageable)).thenReturn(orders);

		Page<Orders> response = service.fetchOrders(0, 3);
		assertNotNull(response);
		assertEquals(response.getTotalElements(), 1L);
	}
	
	@Test
	public void testApproveOrder() {
		Orders order = getCustomerOrder();
		
		Orders updatedOrder = getCustomerOrder();
		updatedOrder.setApproved(true);
		
		when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(order));
		
		when(repository.save(ArgumentMatchers.any())).thenReturn(updatedOrder);
		
		Orders response = service.approveOrder(order.getId());
		assertNotNull(response);
		assertEquals(response.isApproved(), true);
		
	}
	
	@Test
	public void testApproveOrder_idNotFound() {
		
		when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.approveOrder(ArgumentMatchers.anyLong()));
		verify(repository, never()).save(ArgumentMatchers.any(Orders.class));
		
	}
	
	private Orders getCustomerOrder() {
		Orders order = new Orders();
		order.setApproved(false);
		order.setCreatedDateTime(LocalDateTime.now().minusHours(2));
		order.setId(3);
		
		Customer customer = new Customer();
		customer.setEmail("melita@email.com");
		customer.setFirstName("Meli");
		customer.setLastName("Lita");
		customer.setPhoneNo("123456789");
		
		order.setCustomerDetails(customer);
		
		Product product = new Product();
		product.setType("INTERNET");
		product.setPackages("1Gbps");
		
		order.setProducts(Collections.singletonList(product));
		
		return order;
	}

}
