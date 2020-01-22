package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import lombok.Data;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="FLD")
public class Field {

	@XmlAttribute(name = "NAME")
	private String name;

	@XmlValue
    public String value;
	
}
