package com.mufg.us.amh.vln_ced_401.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import lombok.Setter;

@Setter
public class InputAreaGRP {


	private List<Area> AREA;

	/**
	 * @return the aREA
	 */
	@XmlElement(name = "AREA")
	public List<Area> getAREA() {
		return AREA;
	}
	
}
