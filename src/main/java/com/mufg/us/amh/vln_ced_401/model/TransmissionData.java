package com.mufg.us.amh.vln_ced_401.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Setter;


@Setter
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"fileHeader", "areaGRP"})
@XmlRootElement(name = "Document")
public class TransmissionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FileHeader fileHeader;
	private OutputAreaGRP areaGRP;
	private String key;
    private String val;
	
	/**
	 * @return the fileHeader
	 */
	@XmlElement(name = "FileHeader")
	public FileHeader getFileHeader() {
		return fileHeader;
	}

	/**
	 * @return the areaGRP
	 */
	@XmlElement(name = "AREAGRP")
	public OutputAreaGRP getAreaGRP() {
		return areaGRP;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return val;
	}

	/**
	 * @return the key
	 */
	@XmlAttribute(name = "version")
	public String getKey() {
		return key;
	}

	
	

}
