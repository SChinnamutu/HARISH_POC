package com.mufg.us.amh.vln_ced_401.processer;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.input.InputDocument;
import com.mufg.us.amh.vln_ced_401.mapper.CANLOANMapper_401;
import com.mufg.us.amh.vln_ced_401.output.Document;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CANLOANProcessor {
	
	@Autowired
	CANLOANMapper_401 can401Mapper;
	
	@Value("${api.message.ccisd}")
	private String ccisd;
	
	@Value("${api.message.encoding}")
	private String encoding;

	/**
	 * @param exchange
	 * @param response
	 * @throws Exception
	 */
	public void sendResponse(Exchange exchange, String response) throws Exception {
		try {
			log.debug("CANLOANProcessor:sendresponse set respone to camel context start");
			exchange.getIn().setHeader("CCSID", ccisd);
			exchange.getIn().setHeader("Encoding", encoding);
			exchange.getIn().setBody(response);
			log.debug("CANLOANProcessor:sendresponse set respone to camel context end");
		} catch (Exception e) {
			log.error("IRISMessageProccessor: Failed to create IRIS message {}", e.getMessage());
			exchange.setException(new Throwable(e.getMessage()));
		}
	}
	
	
	/**
	 * @param exchange
	 * @param response
	 * @throws Exception
	 */
	public InputDocument unmarshallingDocument(String payload) throws JAXBException {
		log.info("CANLOANProcessor :: unmarshallingDocument() :: Init ");
		JAXBContext jc = JAXBContext.newInstance(InputDocument.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource streamSource = new StreamSource(new StringReader(payload));
		JAXBElement<InputDocument> je = unmarshaller.unmarshal(streamSource, InputDocument.class);
		log.info("CANLOANProcessor :: unmarshallingDocument() :: Ends ");
		return je.getValue();
	}
	
	
	/**
	 * @param exchange
	 * @param response
	 * @throws Exception
	 */
	public static String objectToXml(Document outboundDocument) throws JAXBException {
		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxbMarshaller.marshal(outboundDocument, sw);
		String xmlContent = sw.toString();
		xmlContent = xmlContent.replaceAll("standalone=\"yes\"".trim(),"".trim());
        xmlContent = xmlContent.replaceAll("xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
        xmlContent = xmlContent.replaceAll("</ns2:", "</");
        xmlContent = xmlContent.replaceAll(":ns2=", "=");
        xmlContent = xmlContent.replaceAll("<ns2:", "<");
        xmlContent = xmlContent.replace(" ?", "?");
		return xmlContent;
	}

}
