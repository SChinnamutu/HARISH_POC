package com.devglan.validation;

import org.springframework.util.StringUtils;

import com.devglan.io.MBRequest;

public class ConversionValidator {

	public static void validate(MBRequest mbRequest) {
		if(StringUtils.isEmpty(mbRequest.getClientId())) {
			throw new IllegalArgumentException("Invalid ClientId");
		}
	}
	
}
