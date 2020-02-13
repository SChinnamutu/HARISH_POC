package com.mufg.us.amh.vln_ced_401.mapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.mufg.us.amh.vln_ced_401.constants.AppConstants;
import com.mufg.us.amh.vln_ced_401.input.InputDocument;
import com.mufg.us.amh.vln_ced_401.input.InputDocument.AREA.REC.FLD;
import com.mufg.us.amh.vln_ced_401.output.Document;
import com.mufg.us.amh.vln_ced_401.output.Document.AREAGRP.CustomerHeader.ContactDetails;

public  abstract class BaseMapper {

	public abstract Document transform(InputDocument ovsDoc);

	/**
	 * @param prtResc
	 * @return
	 */
	protected Document.AREAGRP.Body.FILE.REC getPrintMapping(com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC  prtResc) {
		Document.AREAGRP.Body.FILE.REC prtCntt = new Document.AREAGRP.Body.FILE.REC();
		prtCntt.setNAME(prtResc.getNAME());
		for (com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD fld : prtResc.getFLD()) {
			prtCntt.getFLD().add(getField(fld.getNAME(), fld.getValue()));
		}
		return prtCntt;
	}

	/**
	 * @param flds
	 * @return
	 */
	protected Document.AREAGRP.Body.FILE.REC getReportFieldsMapping(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD> flds) {
		Document.AREAGRP.Body.FILE.REC prtCntt = new Document.AREAGRP.Body.FILE.REC();
		prtCntt.setNAME(AppConstants.PRTCNTT);
		flds.forEach(fld -> prtCntt.getFLD().add(getField(fld.getNAME(), fld.getValue())));
		return prtCntt;
	}

	/**
	 * @param contactDtlFlds
	 * @return
	 */
	protected ContactDetails getContactDetailMapping(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD> contactDtlFlds) {
		Document.AREAGRP.CustomerHeader.ContactDetails contactDtl = new Document.AREAGRP.CustomerHeader.ContactDetails();
		Map<String, String> fieldMap = new HashMap<>();
		contactDtlFlds.forEach(fld -> fieldMap.put(fld.getNAME(), null != fld.getValue() ? fld.getValue().trim() : ""));
		contactDtl.setContactID(fieldMap.get(AppConstants.CTCTID));
		contactDtl.setCustomerName(fieldMap.get(AppConstants.CSTMRNM));
		contactDtl.setAttention(fieldMap.get(AppConstants.ATTN));
		contactDtl.setCustomerID(fieldMap.get(AppConstants.CSTMRID));
		contactDtl.setEmail(fieldMap.get(AppConstants.EMAILADR).toLowerCase());
		contactDtl.setPrimaryFaxCountryCode(fieldMap.get(AppConstants.PMRYFAXNBCTRYCD));
		contactDtl.setPrimaryFax(fieldMap.get(AppConstants.PMRYFAXNB));
		contactDtl.setSecondaryFaxCountryCode(fieldMap.get(AppConstants.SCNDRYFAXNBCTRYCD));
		contactDtl.setSecondaryFax(fieldMap.get(AppConstants.SCNDRYFAXNB));
		contactDtl.setCopyFax1CountryCode(fieldMap.get(AppConstants.CPYFAXNB1CTRYCD));
		contactDtl.setCopyFax1(fieldMap.get(AppConstants.CPYFAXNB1));
		contactDtl.setCopyFax2CountryCode(fieldMap.get(AppConstants.CPYFAXNB2CTRYCD));
		contactDtl.setCopyFax2(fieldMap.get(AppConstants.CPYFAXNB2));
		contactDtl.setContactAddress1(fieldMap.get(AppConstants.PSTLADR1));
		contactDtl.setContactAddress2(fieldMap.get(AppConstants.PSTLADR2));
		contactDtl.setContactAddress3(fieldMap.get(AppConstants.PSTLADR3));
		contactDtl.setContactAddress4(fieldMap.get(AppConstants.PSTLADR4));
		contactDtl.setCity(fieldMap.get(AppConstants.CITYNM));
		contactDtl.setState(fieldMap.get(AppConstants.STATNM));
		contactDtl.setZip(fieldMap.get(AppConstants.PSTLCDDGTS));
		contactDtl.setCountry(fieldMap.get(AppConstants.CTRYNM));
		contactDtl.setBookingBranchNumber(fieldMap.get(AppConstants.BOOKGBRNCHNB));
		contactDtl.setOperationBranchNumber(fieldMap.get(AppConstants.OPRBRNCHNB));
		contactDtl.setBookingCostCenterCode(fieldMap.get(AppConstants.BOOKGCOSTCENTCD));
		contactDtl.setResponsibleCostCenterCode(fieldMap.get(AppConstants.RSPNSBICOSTCENTCD));
		return contactDtl;
	}

	/**
	 * @param ovsDocument
	 * @return
	 */
	public Document.FileHeader.Information getFileHeaderInfoMapping(InputDocument ovsDocument) {
		Document.FileHeader.Information irisInformation = new Document.FileHeader.Information();
		List<FLD> flds = ovsDocument.getAREA().getREC().getFLD();
		irisInformation.setSystemName(getInformationFields(flds, AppConstants.SYSNM));
		irisInformation.setProduct(getInformationFields(flds, AppConstants.PDCT));
		irisInformation.setDateCreated(getInformationFields(flds, AppConstants.CREDT));
		irisInformation.setDateTime(getInformationFields(flds, AppConstants.CRETM));
		irisInformation.setUserCreated(getInformationFields(flds, AppConstants.CREUSRID));
		irisInformation.setUserApproved(getInformationFields(flds, AppConstants.APPDUSRID));
		irisInformation.setReportID(getInformationFields(flds, AppConstants.RPTID));
		irisInformation.setUniqueID(getInformationFields(flds, AppConstants.UNQREF));
		irisInformation.setReportName(getInformationFields(flds, AppConstants.RPTNM));
		if (AppConstants.OVS.equals(irisInformation.getSystemName())) {
			irisInformation.setDocFormat(AppConstants.PDF);
		} else {
			irisInformation.setDocFormat(AppConstants.XML);
		}
		irisInformation.setPageCount("");
		return irisInformation;
	}

	/**
	 * @param irisContactAFlds
	 * @return
	 */
	protected Document.AREAGRP.Body.FILE.REC getIrisContactMapping(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD> irisContactAFlds) {
		Document.AREAGRP.Body.FILE.REC irisContact = new Document.AREAGRP.Body.FILE.REC();
		Map<String, String> fieldMap = new HashMap<>();
		irisContactAFlds.forEach(fld -> fieldMap.put(fld.getNAME(), null != fld.getValue() ? fld.getValue().trim() : ""));
		irisContact.setNAME(AppConstants.IRISCONTACT);
		irisContact.getFLD().add(getField(AppConstants.ATTENTION_NAME, fieldMap.get(AppConstants.ATTN)));
		irisContact.getFLD().add(getField(AppConstants.CUSTOMER_NAME, fieldMap.get(AppConstants.CSTMRNM)));
		irisContact.getFLD().add(getField(AppConstants.ADDRESS1, fieldMap.get(AppConstants.PSTLADR1)));
		irisContact.getFLD().add(getField(AppConstants.ADDRESS2, fieldMap.get(AppConstants.PSTLADR2)));
		irisContact.getFLD().add(getField(AppConstants.ADDRESS3, fieldMap.get(AppConstants.PSTLADR3)));
		irisContact.getFLD().add(getField(AppConstants.ADDRESS4, fieldMap.get(AppConstants.PSTLADR4)));
		irisContact.getFLD().add(getField(AppConstants.CITY, fieldMap.get(AppConstants.CITYNM)));
		irisContact.getFLD().add(getField(AppConstants.STATE, fieldMap.get(AppConstants.STATNM)));
		irisContact.getFLD().add(getField(AppConstants.ZIP, fieldMap.get(AppConstants.PSTLCDDGTS)));
		irisContact.getFLD().add(getField(AppConstants.COUNTRY, fieldMap.get(AppConstants.CTRYNM)));
		String email = getContactDetailField(irisContactAFlds, AppConstants.EMAILADR);
		irisContact.getFLD().add(getField("Emails", email == null ? email : email.trim().toLowerCase()));

		StringBuilder faxes = new StringBuilder();

		String cCode = getContactDetailField(irisContactAFlds, AppConstants.PMRYFAXNBCTRYCD);
		String pFax = getContactDetailField(irisContactAFlds, AppConstants.PMRYFAXNB);
		if (!StringUtils.isEmpty(cCode)) {
			faxes.append(cCode.trim());
		}
		if (!StringUtils.isEmpty(pFax)) {
			faxes.append(pFax.trim());
		}

		String cpFax = getContactDetailField(irisContactAFlds, AppConstants.CPYFAXNB1CTRYCD);
		String cpyFaxNB1 = getContactDetailField(irisContactAFlds, AppConstants.CPYFAXNB1);
		if (faxes.length() > 0
				&& (!StringUtils.isEmpty(cpFax) || !StringUtils.isEmpty(cpyFaxNB1))) {
			faxes.append(AppConstants.COMMA);
		}
		if (!StringUtils.isEmpty(cpFax)) {
			faxes.append(cpFax.trim());
		}
		if (!StringUtils.isEmpty(cpyFaxNB1)) {
			faxes.append(cpyFaxNB1.trim());
		}
		String cpyFaxNB2ctryCD = getContactDetailField(irisContactAFlds, AppConstants.CPYFAXNB2CTRYCD);
		String cpyFaxNB2 = getContactDetailField(irisContactAFlds, AppConstants.CPYFAXNB2);
		if (faxes.length() > 0 && (!StringUtils.isEmpty(cpyFaxNB2ctryCD)
				|| !StringUtils.isEmpty(cpyFaxNB2))) {
			faxes.append(AppConstants.COMMA);
		}
		if (!StringUtils.isEmpty(cpyFaxNB2ctryCD)) {
			faxes.append(cpyFaxNB2ctryCD.trim());
		}
		if (!StringUtils.isEmpty(cpyFaxNB2)) {
			faxes.append(cpyFaxNB2.trim());
		}

		irisContact.getFLD().add(getField("Faxes", faxes.toString()));
		return irisContact;
	}

	/**
	 * @param searchKeyFlds
	 * @return
	 */
	protected Document.AREAGRP.SearchKey.SearchFields searchKeyMapping(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD> searchKeyFlds) {
		Document.AREAGRP.SearchKey.SearchFields searchFields = new Document.AREAGRP.SearchKey.SearchFields();
		searchFields.setReportID(getContactDetailField(searchKeyFlds, AppConstants.RPTID));
		searchFields.setReportName(getContactDetailField(searchKeyFlds, AppConstants.RPTNM));
		searchFields.setBranchCode(getContactDetailField(searchKeyFlds, AppConstants.BRNCHCD));
		searchFields.setCurrencyCode(getContactDetailField(searchKeyFlds, AppConstants.CCYCD));
		searchFields.setCostCenterCode(getContactDetailField(searchKeyFlds, AppConstants.COSTCENTCD));
		searchFields.setCustomerNumber(getContactDetailField(searchKeyFlds, AppConstants.CSTMRID));
		searchFields.setMailingAddressCountry(getContactDetailField(searchKeyFlds, AppConstants.CTRY));
		searchFields.setAccountNumber(getContactDetailField(searchKeyFlds, AppConstants.ACCTNB));
		searchFields.setAccountType(getContactDetailField(searchKeyFlds, AppConstants.ACCTTP));
		searchFields.setStatementDate(getContactDetailField(searchKeyFlds, AppConstants.STMTDT));
		return searchFields;
	}

	/**
	 * @param searchKeyFlds
	 * @param fieldName
	 * @return
	 */
	private String getContactDetailField(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD> searchKeyFlds,String fieldName) {
		Optional<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD> fld = searchKeyFlds.stream().filter(a -> fieldName.equals(a.getNAME())).findFirst();
		if (fld.isPresent()) {
			return fld.get().getValue() == null ? "" : fld.get().getValue().trim();
		}
		return "";
	}

	/**
	 * @param flds
	 * @param fieldName
	 * @return
	 */
	private String getInformationFields(List<FLD> flds, String fieldName) {
		Optional<FLD> fld = flds.stream().filter(a -> fieldName.equals(a.getNAME())).findFirst();
		if (fld.isPresent()) {
			return fld.get().getValue();
		}
		return null;
	}

	/**
	 * @param areas
	 * @param fieldName
	 * @return
	 */
	protected com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA getAreaType(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA> areas, String fieldName) {
		Optional<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA> area = areas.stream().filter(a -> fieldName.equals(a.getNAME())).findFirst();
		if (area.isPresent()) {
			return area.get();
		}
		return null;
	}
	/**
	 * @param areas
	 * @param fieldName
	 * @return
	 */
	protected List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA> getAreaTypeList(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA> areas, String fieldName) {
		List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA> areaList = areas.stream().filter(a -> fieldName.equals(a.getNAME())).collect(Collectors.toList());
		return areaList;
	}
	/**
	 * @param contactDtlRec
	 * @param fieldName
	 * @return
	 */
	protected com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC getReCordType(List<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC> contactDtlRec, String fieldName) {
		Optional<com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC> rcd = contactDtlRec.stream().filter(a -> fieldName.equals(a.getNAME())).findFirst();
		if (rcd.isPresent()) {
			return rcd.get();
		}
		return null;
	}

	/**
	 * @param fldName
	 * @param value
	 * @return
	 */
	private Document.AREAGRP.Body.FILE.REC.FLD getField(String fldName, String value) {
		Document.AREAGRP.Body.FILE.REC.FLD address2Fld = new Document.AREAGRP.Body.FILE.REC.FLD();
		address2Fld.setNAME(fldName == null ? null : fldName.trim());
		address2Fld.setValue(value == null ? null : value.trim());
		return address2Fld;
	}
}
