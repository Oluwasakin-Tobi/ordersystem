package com.melita.orderapproval.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerDTO {
	
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;

}
