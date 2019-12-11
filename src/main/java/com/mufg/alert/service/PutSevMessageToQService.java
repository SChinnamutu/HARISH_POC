package com.mufg.alert.service;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;

public interface PutSevMessageToQService {

	public void putMessage(String qMgrName, String qName, MQMessage pMsg) throws MQException;
}
