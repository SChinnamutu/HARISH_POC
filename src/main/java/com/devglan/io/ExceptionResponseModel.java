package com.devglan.io;

public class ExceptionResponseModel {

	private String status;
	private StatusMessage statusMessage;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @return the statusMessage
	 */
	public StatusMessage getStatusMessage() {
		return statusMessage;
	}



	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
	}



	@Override
	public String toString() {
		return "CustomException [status=" + status + ", statusMessage=" + statusMessage + "]";
	}
	

}
