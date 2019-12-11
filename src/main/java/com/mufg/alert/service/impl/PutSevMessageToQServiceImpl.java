package com.mufg.alert.service.impl;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import com.mufg.alert.service.PutSevMessageToQService;


@Service
public class PutSevMessageToQServiceImpl implements PutSevMessageToQService {

	private static final Logger LOGGER = Logger.getLogger(PutSevMessageToQServiceImpl.class.getName());
	
	@Override
	public void putMessage(String qMgrName, String qName, MQMessage pMsg) throws MQException {
		LOGGER.info("PutSevMessageToQServiceImpl :: putMessage() :: Init");
		MQQueueManager qMgr = new MQQueueManager(qMgrName);
		MQPutMessageOptions pmo = new MQPutMessageOptions();
		pmo.options = MQConstants.MQPMO_SET_ALL_CONTEXT | MQConstants.MQPMO_FAIL_IF_QUIESCING;
		qMgr.put(MQConstants.MQOT_Q, qName, pMsg, pmo);
		qMgr.disconnect();
		LOGGER.info("PutSevMessageToQServiceImpl :: putMessage() :: Init");
	}


}
