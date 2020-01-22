package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Setter;

//@Setter
@Setter
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"customerHeader", "searchKey","body"})
@XmlRootElement(name = "AREAGRP")
public class OutputAreaGRP {

	
	private CustomerHeader customerHeader;
	private SearchKey searchKey;
	private Body body;
	
	/**
	 * @return the body
	 */
	@XmlElement(name = "Body")
	public Body getBody() {
		return body;
	}
	/**
	 * @return the searchKey
	 */
	@XmlElement(name = "SearchKey")
	public SearchKey getSearchKey() {
		return searchKey;
	}
	/**
	 * @return the customerHeader
	 */
	@XmlElement(name = "CustomerHeader")
	public CustomerHeader getCustomerHeader() {
		return customerHeader;
	}
	
	
	
}
