package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestData {

	@XmlElement(name = "AREA")
	private Area AREA;

	@XmlElement(name = "AREAGRP")
	private InputAreaGRP AREAGRP;


}
