package com.mufg.us.amh.vln_ced_401.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import lombok.Setter;


@Setter
public class Area {

	private List<Rec> REC;
	 
	/**
	 * @return the REC
	 */
	@XmlElement(name = "REC")
	public List<Rec> getREC() {
		return REC;
	}
	

}
