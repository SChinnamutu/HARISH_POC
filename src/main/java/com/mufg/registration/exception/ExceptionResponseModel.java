package com.mufg.registration.exception;

import com.mufg.registration.io.StatusMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseModel {

	private String status;
	private StatusMessage statusMessage;
	
}
