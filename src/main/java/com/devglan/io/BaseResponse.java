package com.devglan.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse {

	//private String status;
	//private StatusMessage statusMessage;
	private String referenceId;
	private Object data;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StatusMessage {
		
		private String code;
		private String description;
		
	}
	
}
