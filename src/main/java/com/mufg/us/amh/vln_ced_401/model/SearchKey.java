package com.mufg.us.amh.vln_ced_401.model;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;


@Getter
public class SearchKey {

	private SearchFields searchFields;

	/**
	 * @param searchFields the searchFields to set
	 */
	@XmlElement(name = "SearchFields")
	public void setSearchFields(SearchFields searchFields) {
		this.searchFields = searchFields;
	}
	
	
	
}
