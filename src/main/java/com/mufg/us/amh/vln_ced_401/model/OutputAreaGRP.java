package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"customerHeader","searchKey","body"})
public class OutputAreaGRP {

	private Body body;
	private SearchKey searchKey;
	private CustomerHeader customerHeader;
	
}
