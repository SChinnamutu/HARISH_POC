package com.mufg.us.amh.vln_ced_401.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import lombok.Setter;


@Setter
public class File {

	private String name;
	
	private List<Rec> REC;

	/**
	 * @return the rec
	 */
	@XmlElement(name = "REC")
	public List<Rec> getRec() {
		return REC;
	}
	
	/**
	 * @return the name
	 */
	@XmlAttribute(name = "NAME")
	public String getName() {
		return name;
	}

}
