package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlElement;

import lombok.Setter;

@Setter
public class FileHeader {

	
	private Information information;

	@XmlElement(name = "Information")
	public Information getInformation() {
		return information;
	}	
	
	
	
}
