package com.melita.orderapproval.test.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.melita.orderapproval.controller.OrderApprovalController;
import com.melita.orderapproval.entity.Customer;
import com.melita.orderapproval.entity.Orders;
import com.melita.orderapproval.entity.Product;
import com.melita.orderapproval.service.OrderService;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderApprovalControllerTest {
	
	@Mock
	private OrderService orderService;
	
	@Mock
	private ModelMapper mapper;
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@BeforeEach
	public void setup() {
		
		OrderApprovalController orderController = new OrderApprovalController(orderService, mapper);
		
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
		
		objectMapper = JsonMapper.builder()
			    .addModule(new JavaTimeModule())
			    .build();
	}
	
	@Test
	public void testApproveOrder() throws Exception {
		
		
		
		Orders order = getCustomerOrder();
		order.setApproved(true);
		
		when(orderService.approveOrder(order.getId()))
		.thenReturn(order);
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/orders/3")
				.content(objectMapper.writeValueAsString(order))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
	}
	
	@Test
	public void testFetchOrders() throws Exception {

		Pageable pageable = PageRequest.of(0, 3, Sort.by("createdDateTime").descending());
		
		Page<Orders> page = new PageImpl<>(Collections.singletonList(getCustomerOrder()), pageable, 1);
		
		when(orderService.fetchOrders(0, 3))
		.thenReturn(page);
		
		MvcResult response = mockMvc.perform(MockMvcRequestBuilders
				.get("/orders?offset=0&pageSize=3")
				.content(objectMapper.writeValueAsString(page))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print())
				.andReturn();
		
		String json = response.getResponse().getContentAsString();
		
		assertNotNull(json);
		
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
