package com.mufg.alert.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.stereotype.Service;

import com.mufg.alert.io.SevMessage;
import com.mufg.alert.service.ParseLertMessageService;

@Service
public class ParseAlertMessageImpl implements ParseLertMessageService {

	private static final Logger LOGGER = Logger.getLogger(SendEmailAlertImpl.class.getName());
	
	@Override
	public SevMessage parseAlert(String alertEvent) throws XMLStreamException {
		LOGGER.info("ParseAlertMessageImpl :: parseAlert() :: Init " + alertEvent);
		String xmlAttribName = null;
		SevMessage sm = null;
		Map<String, String> xmlMap = new HashMap<String, String>();
		if (!alertEvent.startsWith("<TopicList>")) {
			return null;
		}
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		InputStream instream = new ByteArrayInputStream(alertEvent.getBytes());
		XMLStreamReader xmlr = xmlif.createXMLStreamReader(instream);
		while (xmlr.hasNext()) {
			int event = xmlr.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT: {
				if ("Attribute".equals(xmlr.getLocalName())) {
					xmlAttribName = xmlr.getAttributeValue(0);
				}
			}
				break;
			case XMLStreamConstants.SPACE:
			case XMLStreamConstants.CDATA:
			case XMLStreamConstants.CHARACTERS: {
				xmlMap.put(xmlAttribName,
						new String(xmlr.getTextCharacters(), xmlr.getTextStart(), xmlr.getTextLength()));
			}
				break;
			case XMLStreamConstants.END_DOCUMENT: {
				LOGGER.info(xmlMap.entrySet().toString());

				if (!(xmlMap.containsKey("ERR_MSG1") && xmlMap.containsKey("ERR_MSG2") && xmlMap.containsKey("ERR_MSG3")
						&& xmlMap.containsKey("ERR_MSG4") && xmlMap.containsKey("ERR_MSG5"))) {
					return null;
				}
				sm = new SevMessage();
				StringBuilder sb = new StringBuilder();
				sm.setSevHost(xmlMap.get("HOST"));
				sm.setSevDate(xmlMap.get("DATE"));
				sm.setSevTime(xmlMap.get("TIMESTAMP"));
				sm.setSevTopic(xmlMap.get("TOPICID"));
				sb.append(xmlMap.get("ERR_MSG1"));
				sb.append(xmlMap.get("ERR_MSG2"));
				sb.append(xmlMap.get("ERR_MSG3"));
				sb.append(xmlMap.get("ERR_MSG4"));
				sb.append(xmlMap.get("ERR_MSG5"));
				sm.setSevErrMsg(sb.toString());
			}
		  }
		}
		xmlr.close();
		LOGGER.info("ParseAlertMessageImpl :: parseAlert() :: End");
		return sm;
	}

}
