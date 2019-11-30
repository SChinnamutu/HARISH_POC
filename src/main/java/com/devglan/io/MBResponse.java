package com.devglan.io;

public class MBResponse {

	
	private String referenceId;
	private Object data;


	/**
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}


	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}


	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "MBResponse [referenceId=" + referenceId + ", data=" + data + "]";
	}


	
	
	
}
