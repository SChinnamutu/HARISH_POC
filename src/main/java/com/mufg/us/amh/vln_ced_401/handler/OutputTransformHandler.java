package com.mufg.us.amh.vln_ced_401.handler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mufg.us.amh.vln_ced_401.constants.AppConstants;
import com.mufg.us.amh.vln_ced_401.model.Body;
import com.mufg.us.amh.vln_ced_401.model.ContactDetail;
import com.mufg.us.amh.vln_ced_401.model.CustomerHeader;
import com.mufg.us.amh.vln_ced_401.model.Field;
import com.mufg.us.amh.vln_ced_401.model.File;
import com.mufg.us.amh.vln_ced_401.model.FileHeader;
import com.mufg.us.amh.vln_ced_401.model.Information;
import com.mufg.us.amh.vln_ced_401.model.OutputAreaGRP;
import com.mufg.us.amh.vln_ced_401.model.Rec;
import com.mufg.us.amh.vln_ced_401.model.RequestData;
import com.mufg.us.amh.vln_ced_401.model.SearchFields;
import com.mufg.us.amh.vln_ced_401.model.SearchKey;
import com.mufg.us.amh.vln_ced_401.model.TransmissionData;
import com.mufg.us.amh.vln_ced_401.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OutputTransformHandler {
	
	public String marshallingDocument(RequestData unMarshalDocument) throws JAXBException {
		log.info("OutputTransformHandler :: marshallingDocument() :: Init");
		TransmissionData outboundDocument = new TransmissionData();
		//OutputDocument starts
		prepareFileHeader(unMarshalDocument,outboundDocument);
		OutputAreaGRP areaGRP = new OutputAreaGRP();
		prepareCustomerHeader(unMarshalDocument,areaGRP);
		prepareSearchKey(unMarshalDocument,areaGRP);
		processPrepareBody(unMarshalDocument,areaGRP);
		outboundDocument.setAreaGRP(areaGRP);
		outboundDocument.setKey(AppConstants.DOCUMENT_VERSION);
		//OutputDocument ends
		String response = CommonUtil.objectToXml(outboundDocument);
        log.info("OutputTransformHandler :: marshallingDocument() :: Ends");
		return response;
	}

	private void prepareFileHeader(RequestData unMarshalDocument,TransmissionData outboundDocument) {
		List<Field> FLDS = unMarshalDocument.getAREA().getREC().get(0).getFLD();
		Information information = new Information();
		information.setSystemName(!StringUtils.isEmpty(FLDS.get(0).getValue()) ? FLDS.get(0).getValue() : AppConstants.EMPTY_VALUE);
		information.setProduct(!StringUtils.isEmpty(FLDS.get(1).getValue()) ? FLDS.get(1).getValue() : null );
		information.setDateCreated(!StringUtils.isEmpty(FLDS.get(2).getValue()) ? FLDS.get(2).getValue() : null );
		information.setDateTime(!StringUtils.isEmpty(FLDS.get(3).getValue()) ? FLDS.get(3).getValue() : null );
		information.setUserCreated(!StringUtils.isEmpty(FLDS.get(4).getValue()) ? FLDS.get(4).getValue() : null );
		information.setUserApproved(!StringUtils.isEmpty(FLDS.get(5).getValue()) ? FLDS.get(5).getValue() : null );
		information.setReportID(!StringUtils.isEmpty(FLDS.get(6).getValue()) ? FLDS.get(6).getValue() : null );
		information.setUniqueID(!StringUtils.isEmpty(FLDS.get(7).getValue()) ? FLDS.get(7).getValue() : null );
		information.setReportName(!StringUtils.isEmpty(FLDS.get(8).getValue()) ? FLDS.get(8).getValue() : null );
		information.setAreaGroupCount(!StringUtils.isEmpty(FLDS.get(9).getValue()) ? String.valueOf(Integer.parseInt(FLDS.get(9).getValue())) : null );
		information.setDocFormat(AppConstants.DEFAULT_DOC_FORMAT);
		information.setPageCount(AppConstants.EMPTY_VALUE);
		FileHeader fileHeader = new FileHeader();
		fileHeader.setInformation(information);
		outboundDocument.setFileHeader(fileHeader);
	}
	
	private void prepareCustomerHeader(RequestData unMarshalDocument, OutputAreaGRP areaGRP) {
		List<Field> CUSTOMER_FLDS = unMarshalDocument.getAREAGRP().getAREA().get(0).getREC().get(0).getFLD();
		ContactDetail contactDetails = new ContactDetail();
		contactDetails.setContactID(!StringUtils.isEmpty(CUSTOMER_FLDS.get(0).getValue()) ? CUSTOMER_FLDS.get(0).getValue() : null );
		contactDetails.setCustomerName(!StringUtils.isEmpty(CUSTOMER_FLDS.get(1).getValue()) ? CUSTOMER_FLDS.get(1).getValue() : null );
		contactDetails.setAttention(!StringUtils.isEmpty(CUSTOMER_FLDS.get(2).getValue()) ? CUSTOMER_FLDS.get(2).getValue() : AppConstants.EMPTY_VALUE);
		contactDetails.setCustomerID(!StringUtils.isEmpty(CUSTOMER_FLDS.get(3).getValue()) ? CUSTOMER_FLDS.get(3).getValue() : null );
		contactDetails.setEmail(!StringUtils.isEmpty(CUSTOMER_FLDS.get(4).getValue()) ? CUSTOMER_FLDS.get(4).getValue().replaceAll("\\s+","") : null );
		contactDetails.setPrimaryFaxCountryCode(!StringUtils.isEmpty(CUSTOMER_FLDS.get(5).getValue()) ? CUSTOMER_FLDS.get(5).getValue() : AppConstants.EMPTY_VALUE);
		contactDetails.setPrimaryFax(!StringUtils.isEmpty(CUSTOMER_FLDS.get(6).getValue()) ? CUSTOMER_FLDS.get(6).getValue() :AppConstants.EMPTY_VALUE);
		contactDetails.setSecondaryFaxCountryCode(!StringUtils.isEmpty(CUSTOMER_FLDS.get(7).getValue()) ? CUSTOMER_FLDS.get(7).getValue() : AppConstants.EMPTY_VALUE);
		contactDetails.setSecondaryFax(!StringUtils.isEmpty(CUSTOMER_FLDS.get(8).getValue()) ? CUSTOMER_FLDS.get(8).getValue() :AppConstants.EMPTY_VALUE );
		contactDetails.setCopyFax1CountryCode(!StringUtils.isEmpty(CUSTOMER_FLDS.get(9).getValue()) ? CUSTOMER_FLDS.get(9).getValue() : AppConstants.EMPTY_VALUE);
		contactDetails.setCopyFax1(!StringUtils.isEmpty(CUSTOMER_FLDS.get(10).getValue()) ? CUSTOMER_FLDS.get(10).getValue() :AppConstants.EMPTY_VALUE);
		contactDetails.setCopyFax2CountryCode(!StringUtils.isEmpty(CUSTOMER_FLDS.get(11).getValue()) ? CUSTOMER_FLDS.get(11).getValue() : AppConstants.EMPTY_VALUE );
		contactDetails.setCopyFax2(!StringUtils.isEmpty(CUSTOMER_FLDS.get(12).getValue()) ? CUSTOMER_FLDS.get(12).getValue() : AppConstants.EMPTY_VALUE);
		contactDetails.setContactAddress1(!StringUtils.isEmpty(CUSTOMER_FLDS.get(13).getValue()) ? CUSTOMER_FLDS.get(13).getValue() : null );
		contactDetails.setContactAddress2(!StringUtils.isEmpty(CUSTOMER_FLDS.get(14).getValue()) ? CUSTOMER_FLDS.get(14).getValue(): null );
		contactDetails.setContactAddress3(!StringUtils.isEmpty(CUSTOMER_FLDS.get(15).getValue()) ? CUSTOMER_FLDS.get(15).getValue() : AppConstants.EMPTY_VALUE );
		contactDetails.setContactAddress4(!StringUtils.isEmpty(CUSTOMER_FLDS.get(16).getValue()) ? CUSTOMER_FLDS.get(16).getValue() : AppConstants.EMPTY_VALUE );
		contactDetails.setCity(!StringUtils.isEmpty(CUSTOMER_FLDS.get(17).getValue()) ? CUSTOMER_FLDS.get(17).getValue() : null );
		contactDetails.setState(!StringUtils.isEmpty(CUSTOMER_FLDS.get(18).getValue()) ? CUSTOMER_FLDS.get(18).getValue() : null );
		contactDetails.setZip(!StringUtils.isEmpty(CUSTOMER_FLDS.get(19).getValue()) ? CUSTOMER_FLDS.get(19).getValue() : null );
		contactDetails.setCountry(!StringUtils.isEmpty(CUSTOMER_FLDS.get(20).getValue()) ? CUSTOMER_FLDS.get(20).getValue() : null );
		contactDetails.setBookingBranchNumber(!StringUtils.isEmpty(CUSTOMER_FLDS.get(21).getValue()) ? CUSTOMER_FLDS.get(21).getValue() : null );
		contactDetails.setOperationBranchNumber(!StringUtils.isEmpty(CUSTOMER_FLDS.get(22).getValue()) ? CUSTOMER_FLDS.get(22).getValue() : null );
		contactDetails.setBookingCostCenterCode(!StringUtils.isEmpty(CUSTOMER_FLDS.get(23).getValue()) ? CUSTOMER_FLDS.get(23).getValue() : null );
		contactDetails.setResponsibleCostCenterCode(!StringUtils.isEmpty(CUSTOMER_FLDS.get(24).getValue()) ? CUSTOMER_FLDS.get(24).getValue() : null );
		CustomerHeader customerHeader = new CustomerHeader();
		customerHeader.setContactDetail(contactDetails);
		areaGRP.setCustomerHeader(customerHeader); 
	}
	
	private void prepareSearchKey(RequestData unMarshalDocument, OutputAreaGRP areaGRP) {
		List<Field> SEARCH_FLDS = unMarshalDocument.getAREAGRP().getAREA().get(1).getREC().get(0).getFLD();
		SearchKey searchKey = new SearchKey();
		SearchFields searchFields = new SearchFields();
		searchFields.setCustomerName(!StringUtils.isEmpty(SEARCH_FLDS.get(0).getValue()) ? SEARCH_FLDS.get(0).getValue() : null );
		searchFields.setCustomerNumber(!StringUtils.isEmpty(SEARCH_FLDS.get(1).getValue()) ? SEARCH_FLDS.get(1).getValue() : null );
		searchFields.setBranchCode(!StringUtils.isEmpty(SEARCH_FLDS.get(2).getValue()) ? SEARCH_FLDS.get(2).getValue() : null );
		searchFields.setTransactionNumber(!StringUtils.isEmpty(SEARCH_FLDS.get(3).getValue()) ? SEARCH_FLDS.get(3).getValue() : AppConstants.EMPTY_VALUE  );
		searchFields.setCurrentOustandingAmount(!StringUtils.isEmpty(SEARCH_FLDS.get(4).getValue()) ? SEARCH_FLDS.get(4).getValue() : AppConstants.EMPTY_VALUE );
		searchFields.setTransactionType(!StringUtils.isEmpty(SEARCH_FLDS.get(5).getValue()) ? SEARCH_FLDS.get(5).getValue() : null );
		searchFields.setTransactionEffectiveDate(!StringUtils.isEmpty(SEARCH_FLDS.get(6).getValue()) ? SEARCH_FLDS.get(6).getValue() : null );
		searchFields.setFacilityNumber(!StringUtils.isEmpty(SEARCH_FLDS.get(7).getValue()) ? SEARCH_FLDS.get(7).getValue() : null );
		searchKey.setSearchFields(searchFields);
		areaGRP.setSearchKey(searchKey);
	}
	
	private void processPrepareBody(RequestData unMarshalDocument, OutputAreaGRP areaGRP){
		Rec IRISCONTACT = new Rec();
		IRISCONTACT.setNAME("IRISCONTACT");
		List<Field> IRISCONTACT_FIELDS = new ArrayList<>();
		Field FLD = null;
		for(Field fld : unMarshalDocument.getAREAGRP().getAREA().get(0).getREC().get(0).getFLD()) {
			FLD = new Field();
			if(fld.getName().equalsIgnoreCase("ATTN")) {
				FLD.setName("AttentionName");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("CSTMRNM")) {
				FLD.setName("CustomerName");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("PSTLADR1")) {
				FLD.setName("Address1");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("PSTLADR2")) {
				FLD.setName("Address2");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("PSTLADR3")) {
				FLD.setName("Address3");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("PSTLADR4")) {
				FLD.setName("Address4");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("CITYNM")) {
				FLD.setName("City");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("STATNM")) {
				FLD.setName("State");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("PSTLCDDGTS")) {
				FLD.setName("Zip");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("CTRYNM")) {
				FLD.setName("Country");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("EMAILADR")) {
				FLD.setName("Emails");
				FLD.setValue(fld.getValue().replaceAll("\\s+",""));
				IRISCONTACT_FIELDS.add(FLD);
			}else if(fld.getName().equalsIgnoreCase("SCNDRYFAXNB")) {
				FLD.setName("Faxes");
				FLD.setValue(fld.getValue());
				IRISCONTACT_FIELDS.add(FLD);
			}
		}
		IRISCONTACT.setFLD(IRISCONTACT_FIELDS);
		
		Rec PRTCNTT = new Rec();
		PRTCNTT.setNAME("NOTICE");
		List<Field> PRTCNTT_FIELDS = new ArrayList<>();
		for(Field fld : unMarshalDocument.getAREAGRP().getAREA().get(0).getREC().get(0).getFLD()) {
			FLD = new Field();
			FLD.setName(fld.getName());
			FLD.setValue(fld.getValue());
			PRTCNTT_FIELDS.add(FLD);
		}
		PRTCNTT.setFLD(PRTCNTT_FIELDS);
		
		Rec NOTICE = new Rec();
		NOTICE.setNAME("PRTCNTT");
		List<Field> NOTICE_FIELDS = new ArrayList<>();
		for(Field fld : unMarshalDocument.getAREAGRP().getAREA().get(2).getREC().get(0).getFLD()) {
			FLD = new Field();
			FLD.setName(fld.getName());
			FLD.setValue(fld.getValue());
			NOTICE_FIELDS.add(FLD);
		}
		NOTICE.setFLD(NOTICE_FIELDS);
		List<Rec> list_recs = new ArrayList<>();
		list_recs.add(0, IRISCONTACT);
		list_recs.add(1, NOTICE);
		list_recs.add(2, PRTCNTT);
		
		File file = new File();
		file.setREC(list_recs);
		file.setName("Data Design");
		
		Body body = new Body();
		body.setFile(file);
		areaGRP.setBody(body);
	}
}
