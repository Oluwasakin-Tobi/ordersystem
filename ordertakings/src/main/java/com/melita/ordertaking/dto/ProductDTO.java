package com.melita.ordertaking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductDTO {
	
	private String type;
	private String packages;

}
