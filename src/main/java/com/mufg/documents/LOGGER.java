package com.mufg.documents;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LOGGER {

	 @XmlElement(name = "FIELD")
	 private List<FIELD> FIELD;

	/**
	 * @return the fIELD
	 */
	public List<FIELD> getFIELD() {
		return FIELD;
	}

	/**
	 * @param fIELD the fIELD to set
	 */
	public void setFIELD(List<FIELD> fIELD) {
		FIELD = fIELD;
	}	
	
	 
}

