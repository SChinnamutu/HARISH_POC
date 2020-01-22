package com.mufg.us.amh.vln_ced_401.processer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.handler.InputTransformHandler;
import com.mufg.us.amh.vln_ced_401.handler.OutputTransformHandler;
import com.mufg.us.amh.vln_ced_401.model.RequestData;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CANLOANProcessor implements Processor{
	
	@Autowired
	private InputTransformHandler inputHandler;
	
	@Autowired
	private OutputTransformHandler outputHandler;
	
	
	public void process(Exchange exchange) throws Exception {
		log.info("CANLOANProcessor :: process() :: Init");
		String payload = exchange.getIn().getBody(String.class);
		RequestData unMarshalDocument = inputHandler.unmarshallingDocument(payload);
		String marshalDocument = outputHandler.marshallingDocument(unMarshalDocument);
		exchange.getIn().setBody(marshalDocument);
		log.info("CANLOANProcessor :: process() :: Ends");
	}

}
