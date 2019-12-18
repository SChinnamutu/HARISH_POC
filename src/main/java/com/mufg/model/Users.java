package com.mufg.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Users {
	
	private Long userId;
	private String name;
	private String dept;
	private BigDecimal account;
	private String orderId;
	
}
