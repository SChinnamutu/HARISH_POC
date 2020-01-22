package com.mufg.us.amh.vln_ced_401.util;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.mufg.us.amh.vln_ced_401.model.TransmissionData;



public class CommonUtil {

	public static String generateUniqueId() {
		return UUID.randomUUID().toString();
	}

	public static String objectToXml(TransmissionData  outboundDocument) throws JAXBException {
		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(TransmissionData.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxbMarshaller.marshal(outboundDocument, sw);
		String xmlContent = sw.toString();
		xmlContent = xmlContent.replaceAll("standalone=\"yes\"","".trim());
        xmlContent = xmlContent.replaceAll("xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
        xmlContent = xmlContent.replaceAll("</ns2:", "</");
        xmlContent = xmlContent.replaceAll(":ns2=", "=");
        xmlContent = xmlContent.replaceAll("<ns2:", "<");
		return xmlContent;
	}
}
