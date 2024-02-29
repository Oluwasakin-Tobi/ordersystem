package com.melita.ordertaking.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderDTO {
	
	private long id;
	private CustomerDTO customerDetails;
	private String installationAddress;
	private LocalDateTime installationDateTime;
	private List<ProductDTO> products;

}
