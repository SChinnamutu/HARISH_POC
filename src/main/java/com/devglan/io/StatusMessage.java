package com.devglan.io;

public class StatusMessage {

	private String code;
	private String description;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public StatusMessage() {
		
	}
	
	public StatusMessage(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	@Override
	public String toString() {
		return "StatusMessage [code=" + code + ", description=" + description + "]";
	}
	
}
