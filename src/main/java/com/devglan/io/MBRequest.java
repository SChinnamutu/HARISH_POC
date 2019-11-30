package com.devglan.io;

public class MBRequest {

	private String clientId;
	private Remittance Remittance;
	
	
	public static class Remittance{
		
		private String paymentId;
		private String amount;
		private String currency;
		/**
		 * @return the paymentId
		 */
		public String getPaymentId() {
			return paymentId;
		}
		/**
		 * @param paymentId the paymentId to set
		 */
		public void setPaymentId(String paymentId) {
			this.paymentId = paymentId;
		}
		/**
		 * @return the amount
		 */
		public String getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(String amount) {
			this.amount = amount;
		}
		/**
		 * @return the currency
		 */
		public String getCurrency() {
			return currency;
		}
		/**
		 * @param currency the currency to set
		 */
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		@Override
		public String toString() {
			return "Remittance [paymentId=" + paymentId + ", amount=" + amount + ", currency=" + currency + "]";
		}
		
		
	}


	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}


	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	/**
	 * @return the remittance
	 */
	public Remittance getRemittance() {
		return Remittance;
	}


	/**
	 * @param remittance the remittance to set
	 */
	public void setRemittance(Remittance remittance) {
		Remittance = remittance;
	}


	@Override
	public String toString() {
		return "MBRequest [clientId=" + clientId + ", Remittance=" + Remittance + "]";
	}
	
	
	
}
