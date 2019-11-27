package com.devglan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "txn_id")
	private Long txnId;
	
	@Column(name = "txn_reference_id")
	private String txnReferenceId;
	
	@Column(name = "txn_payload_type")
	private String txnPayloadType;

	@Column(name = "txn_payload",columnDefinition="TEXT")
	private String txnPayload;

	@Column(name = "txn_owner")
	private String txnOwner;
	
	@Column(name = "trn_created_date")
	private Date txnCreatedDate;

	/**
	 * @return the txnId
	 */
	public Long getTxnId() {
		return txnId;
	}

	/**
	 * @param txnId the txnId to set
	 */
	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	/**
	 * @return the txnReferenceId
	 */
	public String getTxnReferenceId() {
		return txnReferenceId;
	}

	/**
	 * @param txnReferenceId the txnReferenceId to set
	 */
	public void setTxnReferenceId(String txnReferenceId) {
		this.txnReferenceId = txnReferenceId;
	}

	/**
	 * @return the txnPayloadType
	 */
	public String getTxnPayloadType() {
		return txnPayloadType;
	}

	/**
	 * @param txnPayloadType the txnPayloadType to set
	 */
	public void setTxnPayloadType(String txnPayloadType) {
		this.txnPayloadType = txnPayloadType;
	}

	/**
	 * @return the txnPayload
	 */
	public String getTxnPayload() {
		return txnPayload;
	}

	/**
	 * @param txnPayload the txnPayload to set
	 */
	public void setTxnPayload(String txnPayload) {
		this.txnPayload = txnPayload;
	}

	/**
	 * @return the txnOwner
	 */
	public String getTxnOwner() {
		return txnOwner;
	}

	/**
	 * @param txnOwner the txnOwner to set
	 */
	public void setTxnOwner(String txnOwner) {
		this.txnOwner = txnOwner;
	}

	/**
	 * @return the txnCreatedDate
	 */
	public Date getTxnCreatedDate() {
		return txnCreatedDate;
	}

	/**
	 * @param txnCreatedDate the txnCreatedDate to set
	 */
	public void setTxnCreatedDate(Date txnCreatedDate) {
		this.txnCreatedDate = txnCreatedDate;
	}

	@Override
	public String toString() {
		return "AuditLog [txnId=" + txnId + ", txnReferenceId=" + txnReferenceId + ", txnPayloadType=" + txnPayloadType
				+ ", txnPayload=" + txnPayload + ", txnOwner=" + txnOwner + ", txnCreatedDate=" + txnCreatedDate + "]";
	}
	
	
}
