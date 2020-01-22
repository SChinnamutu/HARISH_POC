package com.mufg.us.amh.vln_ced_401.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Setter;


@Setter
@XmlRootElement(name="REC")
public class Rec {

	private String NAME;

	private List<Field> FLD;

	/**
	 * @return the fLD
	 */
	@XmlElement(name = "FLD")
	public List<Field> getFLD() {
		 if( FLD == null ){
			 FLD = new ArrayList<>();
	     }
	     return FLD;
	}

	/**
	 * @return the name
	 */
	@XmlAttribute(name = "NAME")
	public String getName() {
		return NAME;
	}

	
}
