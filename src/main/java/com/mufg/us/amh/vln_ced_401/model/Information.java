package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;


@XmlRootElement(name = "Information")
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"systemName","product","dateCreated","dateTime",
		"userCreated","userApproved","reportID","uniqueID","reportName",
		"areaGroupCount","docFormat","pageCount"})
public class Information {

	
	@XmlElement(name = "SystemName" , required=true, nillable = true)
	private String systemName;
	
	@XmlElement(name = "Product" , required=true, nillable = true)
	private String product;
	
	@XmlElement(name = "DateCreated" , required=true, nillable = true)
	private String dateCreated;
	
	@XmlElement(name = "DateTime" , required=true, nillable = true)
	private String dateTime;
	
	@XmlElement(name = "UserCreated" , required=true, nillable = true)
	private String userCreated;
	
	@XmlElement(name = "UserApproved" , required=true, nillable = true)
	private String userApproved;
	
	@XmlElement(name = "ReportID" , required=true, nillable = true)
	private String reportID;
	
	@XmlElement(name = "UniqueID" , required=true, nillable = true)
	private String uniqueID;
	
	@XmlElement(name = "ReportName" , required=true, nillable = true)
	private String reportName;
	
	@XmlElement(name = "AreaGroupCount" , required=true, nillable = true)
	private String areaGroupCount;
	
	@XmlElement(name = "DocFormat" , required=true, nillable = true)
	private String docFormat;
	
	@XmlElement(name = "PageCount" , required=true, nillable = true)
	private String pageCount  ;
	
	
}
