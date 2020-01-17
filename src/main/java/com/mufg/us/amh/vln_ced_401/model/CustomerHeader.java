package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlElement;

import lombok.Setter;


@Setter
public class CustomerHeader {


	private ContactDetail contactDetail;
	
	/**
	 * @return the contactDetails
	 */

	@XmlElement(name = "ContactDetails")
	public ContactDetail getContactDetail() {
		return contactDetail;
	}
}
