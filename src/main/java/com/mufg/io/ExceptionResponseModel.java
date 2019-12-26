package com.mufg.io;

import com.mufg.io.StatusMessage;

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
