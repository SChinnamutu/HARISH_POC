package com.mufg.us.amh.vln_ced_401.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.constants.AppConstants;
import com.mufg.us.amh.vln_ced_401.input.InputDocument;
import com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA;
import com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC;
import com.mufg.us.amh.vln_ced_401.input.InputDocument.AREAGRP.AREA.REC.FLD;
import com.mufg.us.amh.vln_ced_401.output.Document;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CANLOANMapper_401 extends BaseMapper{

	@Override
	public Document transform(InputDocument ovsDoc)  {
		log.info("OutputTransformHandler :: marshallingDocument() :: Init");
		Document irisDocument = new Document();
		irisDocument.setVersion(new BigDecimal(2));
		
		// Information mapping
		Document.FileHeader irisFieldHeader = new Document.FileHeader();
		irisFieldHeader.setInformation(getFileHeaderInfoMapping(ovsDoc));
		irisDocument.getFileHeader().add(irisFieldHeader);

		// ContactDetails Mapping
		List<AREA> areas = ovsDoc.getAREAGRP().getAREA();
		List<REC> contactDtlRec = getAreaType(areas,AppConstants.CSTMRHDR).getREC();
		List<FLD> contactDtlFlds = getReCordType(contactDtlRec, AppConstants.CTCTDTLS).getFLD();

		Document.AREAGRP areaGrp = new Document.AREAGRP();
		Document.AREAGRP.CustomerHeader ch = new Document.AREAGRP.CustomerHeader();
		ch.getContactDetails().add(getContactDetailMapping(contactDtlFlds));
		areaGrp.getCustomerHeader().add(ch);

		// SearchKey mapping
		List<AREA> searchKeyAreaList = getAreaTypeList(areas,AppConstants.SCHKEY);
		if (!searchKeyAreaList.isEmpty()) {
			for (AREA searchKeyArea : searchKeyAreaList) {
				if (searchKeyArea != null) {
					List<REC> searchAreaRcds = searchKeyArea.getREC();
					REC searchFieldRecords = getReCordType(searchAreaRcds, AppConstants.SEARCHFLD);
					if (searchFieldRecords != null) {
						List<FLD> searchKeyFlds = searchFieldRecords.getFLD();
						Document.AREAGRP.SearchKey searchKey = new Document.AREAGRP.SearchKey();
						searchKey.setSearchFields(searchKeyMapping(searchKeyFlds));
						areaGrp.getSearchKey().add(searchKey);
					}
				}
			}
		}

		// PRTCNTT
		log.debug("Inside OvsToIrisMapping() PRTCNTT Mapping start.");
		List<AREA> prtAreas = getAreaTypeList(areas, AppConstants.RPT);
		if (!prtAreas.isEmpty()) {
			for (AREA prtArea : prtAreas) {
				// IRISCONTACT Mapping
				log.debug("Inside OvsToIrisMapping() IRISCONTACT Mapping start.");
				Document.AREAGRP.Body.FILE file = new Document.AREAGRP.Body.FILE();
				file.setNAME("Data Design");
				if (contactDtlFlds != null) {
					file.getREC().add(getIrisContactMapping(contactDtlFlds));
				}
				Document.AREAGRP.Body body = new Document.AREAGRP.Body();
				body.setFILE(file);
				areaGrp.getBody().add(body);
				if (prtArea != null) {
					List<REC> prtRec = prtArea.getREC();
					if (!prtRec.isEmpty()) {
						for (REC rec : prtRec) {
							file.getREC().add(getPrintMapping(rec));
						}
					}
				}
			}
		}
		irisDocument.getAREAGRP().add(areaGrp);
		log.info("OutputTransformHandler :: marshallingDocument() :: Ends");
		return irisDocument;
	}

	
}
