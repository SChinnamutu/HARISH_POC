package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "ContactDetails")
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactDetail {

	@XmlElement(name = "ContactID" , required=true, nillable = true)
	private String contactID;
	
	@XmlElement(name = "CustomerName" , required=true, nillable = true)
	private String customerName;
	
	@XmlElement(name = "Attention" , required=true, nillable = true)
	private String attention;
	
	@XmlElement(name = "CustomerID" , required=true, nillable = true)
	private String customerID;
	
	@XmlElement(name = "Email" , required=true, nillable = true)
	private String email;
	
	@XmlElement(name = "PrimaryFaxCountryCode" , required=true, nillable = true)
	private String primaryFaxCountryCode;
	
	@XmlElement(name = "PrimaryFax" , required=true, nillable = true)
	private String primaryFax;
	
	@XmlElement(name = "SecondaryFaxCountryCode" , required=true, nillable = true)
	private String secondaryFaxCountryCode;
	
	@XmlElement(name = "SecondaryFax" , required=true, nillable = true)
	private String secondaryFax;
	
	@XmlElement(name = "CopyFax1CountryCode" , required=true, nillable = true)
	private String copyFax1CountryCode;
	
	@XmlElement(name = "CopyFax1" , required=true, nillable = true)
	private String copyFax1;
	
	@XmlElement(name = "CopyFax2CountryCode" , required=true, nillable = true)
	private String copyFax2CountryCode;
	
	@XmlElement(name = "CopyFax2" , required=true, nillable = true)
	private String copyFax2;
	
	@XmlElement(name = "ContactAddress1" , required=true, nillable = true)
	private String contactAddress1;
	
	@XmlElement(name = "ContactAddress2" , required=true, nillable = true)
	private String contactAddress2;
	
	@XmlElement(name = "ContactAddress3" , required=true, nillable = true)
	private String contactAddress3;
	
	@XmlElement(name = "ContactAddress4" , required=true, nillable = true)
	private String contactAddress4;
	
	@XmlElement(name = "City" , required=true, nillable = true)
	private String city;
	
	@XmlElement(name = "State" , required=true, nillable = true)
	private String state;
	
	@XmlElement(name = "Zip" , required=true, nillable = true)
	private String zip;
	
	@XmlElement(name = "Country" , required=true, nillable = true)
	private String country;
	
	@XmlElement(name = "BookingBranchNumber" , required=true, nillable = true)
	private String bookingBranchNumber;
	
	@XmlElement(name = "OperationBranchNumber" , required=true, nillable = true)
	private String operationBranchNumber;
	
	@XmlElement(name = "BookingCostCenterCode" , required=true, nillable = true)
	private String bookingCostCenterCode;
	
	@XmlElement(name = "ResponsibleCostCenterCode" , required=true, nillable = true)
	private String responsibleCostCenterCode;


			 

}


