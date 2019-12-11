package com.mufg.alert.service;

import javax.xml.stream.XMLStreamException;

import com.mufg.alert.io.SevMessage;

public interface ParseLertMessageService {

	public SevMessage parseAlert(String alertEvent) throws XMLStreamException;
	
}
