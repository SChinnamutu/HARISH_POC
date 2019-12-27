package com.mufg.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchRequest {

	private String passportNumber;
	private String folderName;
	private String fileName;
}
