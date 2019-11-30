package com.devglan.io;

public class MBResponse {

	
	private String referenceId;
	private Object data;
	
	
	public static class StatusMessage {
		
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
		@Override
		public String toString() {
			return "StatusMessage [code=" + code + ", description=" + description + "]";
		}
		
		
		
	}


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
		return "BaseResponse [referenceId=" + referenceId + ", data=" + data + "]";
	}
	
	
	
}
