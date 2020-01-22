package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlElement;

import lombok.Setter;

@Setter
public class Body {

	private File file;
	
	/**
	 * @return the file
	 */
	@XmlElement(name = "FILE")
	public File getFile() {
		return file;
	}

}
