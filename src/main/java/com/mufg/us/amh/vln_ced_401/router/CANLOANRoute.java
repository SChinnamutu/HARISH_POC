package com.mufg.us.amh.vln_ced_401.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.constants.AppConstants;
import com.mufg.us.amh.vln_ced_401.processer.CANLOANProcessor_401;


@Component
public class CANLOANRoute extends RouteBuilder {

	@Value("${spring.vlnced401inputQname}")
	private String inputQname;

	@Value("${spring.vlnced401outputQname}")
	private String outputQname;

	@Autowired
	private CANLOANProcessor_401 processor;
	
	@Override
	public void configure() throws Exception {
		from(AppConstants.JMS_PREFIX + inputQname)
				.doTry()
				  .process(processor)
					.to(AppConstants.JMS_PREFIX + outputQname)
					  .doCatch(Exception.class)
			.end();
	}

}
