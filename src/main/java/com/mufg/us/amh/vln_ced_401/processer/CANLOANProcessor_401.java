package com.mufg.us.amh.vln_ced_401.processer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.mapper.CANLOANMapper_401;
import com.mufg.us.amh.vln_ced_401.output.Document;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CANLOANProcessor_401 extends CANLOANProcessor implements Processor{
	
	@Autowired
	private CANLOANMapper_401 can401Mapper; 

	@Override
	public void process(Exchange exchange) throws Exception {
		try {
			log.info("CANLOANProcessor_401 :: process() :: Init");
			String payload = exchange.getIn().getBody(String.class);
			Document reponseDoc = can401Mapper.transform(unmarshallingDocument(payload));
			log.debug("CANLOANProcessor_401: Trasformation of Request XML end");
			sendResponse(exchange, objectToXml(reponseDoc));
		} catch (Exception e) {
			log.error("Error occured in process()  in class CANLOAN401Processor and the exception is " + e);
			throw e;
		}
	}

}
