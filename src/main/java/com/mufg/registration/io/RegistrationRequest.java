package com.mufg.registration.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

	private String passportNumber;
	private String referenceId;
	private String passport;
	private String bucketName;
	private String prefix;
	private String fileName;
	private String folderName;
}
