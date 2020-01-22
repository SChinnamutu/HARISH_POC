package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;


@XmlRootElement(name = "SearchFields")
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFields {

	@XmlElement(name = "CustomerName" , required=true, nillable = true)
	private String customerName;
	
	@XmlElement(name = "CustomerNumber" , required=true, nillable = true)
	private String customerNumber;
	
	@XmlElement(name = "BranchCode" , required=true, nillable = true)
	private String branchCode;
	
	@XmlElement(name = "TransactionNumber" , required=true, nillable = true)
	private String transactionNumber;
	
	@XmlElement(name = "CurrentOustandingAmount" , required=true, nillable = true)
	private String currentOustandingAmount;
	
	@XmlElement(name = "TransactionType" , required=true, nillable = true)
	private String transactionType;
	
	@XmlElement(name = "TransactionEffectiveDate" , required=true, nillable = true)
	private String transactionEffectiveDate;
	
	@XmlElement(name = "FacilityNumber" , required=true, nillable = true)
	private String facilityNumber;
	
		
}
