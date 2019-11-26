package com.devglan.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MBRequest {

	private String clientId;
	private Remittance Remittance;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Remittance{
		private String paymentId;
		private String amount;
		private String currency;
	}
	
}
