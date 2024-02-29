package com.melita.orderapproval.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductDTO {
	
	private long id;
	private String type;
	private String packages;

}
