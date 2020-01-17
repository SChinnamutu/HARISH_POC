package com.mufg.us.amh.vln_ced_401.handler;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import com.mufg.us.amh.vln_ced_401.model.RequestData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InputTransformHandler {

	public RequestData unmarshallingDocument(String payload, String processId) throws JAXBException {
		log.info("InputTransformHandler :: unmarshallingDocument() :: Init " + processId);
		JAXBContext jc = JAXBContext.newInstance(RequestData.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource streamSource = new StreamSource(new StringReader(payload));
		JAXBElement<RequestData> je = unmarshaller.unmarshal(streamSource, RequestData.class);
		log.info("InputTransformHandler :: unmarshallingDocument() :: Ends " + processId);
		return je.getValue();
	}
	
	
	
}
