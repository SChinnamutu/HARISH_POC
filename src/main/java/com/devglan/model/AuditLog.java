package com.devglan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "audit_log")
@NoArgsConstructor
@AllArgsConstructor
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
	
	
}
