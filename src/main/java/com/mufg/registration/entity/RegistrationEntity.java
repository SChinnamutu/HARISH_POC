package com.mufg.registration.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="registration_master")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String passportNumber;
	private String referenceId;
	private String passportFront;
	private Date createAt;
	
}
