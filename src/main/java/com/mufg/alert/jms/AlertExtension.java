package com.mufg.alert.jms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.MQTMC2;
import com.mufg.alert.config.PropertiesCache;
import com.mufg.alert.io.SevMessage;
import com.mufg.alert.service.ParseLertMessageService;
import com.mufg.alert.service.PutSevMessageToQService;
import com.mufg.alert.service.SendEmailAlertService;


@Component
public class AlertExtension {
    
	private Logger LOGGER = Logger.getLogger(AlertExtension.class.getName());
	
	
	@Autowired
	private SendEmailAlertService s2e;
	
	@Autowired
	private PutSevMessageToQService m2q;
	
	@Autowired
	private ParseLertMessageService pam;
	
	@JmsListener(destination = "in.xml.queue", containerFactory = "myFactory")
	public void processAlert(final Message jsonMessage) throws JMSException{
		LOGGER.info("AlertExtension :: processAlert() :: Init");
		MQTMC2 mqtrg = null;
		MQQueueManager qMgr = null;
		MQQueue queue = null;
		MQMessage msg = null;
		SevMessage sm = null;
		MQGetMessageOptions gmo = null;
		String x = null;
		String boQueue = null;
		String failQueue = null;
		String qpasaq = null;
		String saveQueue = null;
		String mqHost = null;
		String mqChannel = null;
		int mqPort = -1;
		String alertTivoli = null;

		FileHandler fileHandler = null;

		int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_FAIL_IF_QUIESCING
				| MQConstants.MQOO_INQUIRE | MQConstants.MQOO_SET;

		System.setProperty("javax.net.ssl.trustStore", "D:\\IR360\\SSL\\IR360_USNY01DEV215_ad_btmna_com.jks");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		//
		// ADDED THE FOLLOWING
		//
		TextMessage textMessage =null;
		if(jsonMessage instanceof TextMessage) {
			textMessage = (TextMessage) jsonMessage;
		}//New Code by harish
		
		//DataInputStream dis = new DataInputStream(new ByteArrayInputStream(args[0].getBytes()));
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(textMessage.getText().getBytes()));

		try {
			mqtrg = new MQTMC2(dis);
		} catch (MQDataException mqe) {
			LOGGER.log(Level.SEVERE, "MQDataException : Invalid Trigger Message passed ", mqe);
			return;
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "IOException while reading Trigger Msg buffer", ex);
			return;
		}

		//
		// END OF ADD
		//

		try {
			fileHandler = new FileHandler("D:\\IR360_ALERT_EXTENSION\\logs\\AlertExtension_"
					+ mqtrg.getQMgrName().trim() + "_" + dateFormat.format(cal.getTime()) + ".log", true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		}catch (IOException e) {
			e.printStackTrace();
			return;
		}

		LOGGER.info("BEGIN PROCESSING");

		LOGGER.info("HOST PROPERTY= " + mqtrg.getQMgrName().trim() + "host");

		if (PropertiesCache.getInstance().containsKey(mqtrg.getQMgrName().trim() + "host")) {
			mqHost = PropertiesCache.getInstance().getProperty(mqtrg.getQMgrName().trim() + "host");
			if (mqHost.isEmpty()) {
				LOGGER.severe("Properties file mqhost parameter is invalid");
			}

		} else {
			LOGGER.severe("MQ Host parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey(mqtrg.getQMgrName().trim() + "channel")) {
			mqChannel = PropertiesCache.getInstance().getProperty(mqtrg.getQMgrName().trim() + "channel").toUpperCase();
			if (mqChannel.isEmpty()) {
				LOGGER.severe("Properties file channel paramter is invalid");
			}

		} else {
			LOGGER.severe("MQ Channel parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey(mqtrg.getQMgrName().trim() + "port")) {
			try {
				mqPort = Integer
						.parseInt(PropertiesCache.getInstance().getProperty(mqtrg.getQMgrName().trim() + "port"));
			} catch (NumberFormatException ex) {
				LOGGER.log(Level.SEVERE, "Properties file port number is not valid ", ex);
			}

		} else {
			LOGGER.severe("MQ port parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey("alertTivoli")) {
			alertTivoli = PropertiesCache.getInstance().getProperty("alertTivoli").toUpperCase();
			if (alertTivoli.isEmpty()) {
				LOGGER.severe("Properties file alertTivoli paramter value is invalid");
			} else {
				if (!(alertTivoli.contentEquals("TRUE") || alertTivoli.contentEquals("FALSE"))) {
					LOGGER.severe("Properties file alertTivoli paramter value is not TRUE or FALSE");
				}
			}

		} else {
			LOGGER.severe("alertTivoli parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey("failQueue")) {
			failQueue = PropertiesCache.getInstance().getProperty("failQueue").toUpperCase();
			if (failQueue.isEmpty()) {
				LOGGER.severe("Properties file failQueue paramter value is invalid");
			}
		} else {
			LOGGER.severe("failQueue parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey("boQueue")) {
			boQueue = PropertiesCache.getInstance().getProperty("boQueue").toUpperCase();
			if (boQueue.isEmpty()) {
				LOGGER.severe("Properties file Back Out Queue paramter value is invalid");
			}
		} else {
			LOGGER.severe("Back Out Queue parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey("saveQueue")) {
			saveQueue = PropertiesCache.getInstance().getProperty("saveQueue").toUpperCase();
			if (boQueue.isEmpty()) {
				LOGGER.severe("Properties file Save Queue paramter value is invalid");
			}
		} else {
			LOGGER.severe("Save Queue parameter not specified in properties file");
		}

		if (PropertiesCache.getInstance().containsKey("qpasaQueue")) {
			qpasaq = PropertiesCache.getInstance().getProperty("qpasaQueue").toUpperCase();
			if (boQueue.isEmpty()) {
				LOGGER.severe("Properties file QASA Queue paramter value is invalid");
			}
		} else {
			LOGGER.severe("QPASA Queue parameter not specified in properties file");
		}

		if (!(PropertiesCache.getInstance().containsKey("fromEmailAddress"))) {
			LOGGER.severe("From Email Address parameter not specified in properties file");
			return;
		}

		//
		// COMMENTED OUT
		//

		/*
		 * DataInputStream dis = new DataInputStream(new
		 * ByteArrayInputStream(args[0].getBytes()));
		 * 
		 * try { mqtrg = new MQTMC2(dis); } catch (MQDataException mqe){
		 * LOGGER.log(Level.SEVERE,"MQDataException : Invalid Trigger Message passed "
		 * ,mqe); return; } catch (IOException ex) {
		 * LOGGER.log(Level.SEVERE,"IOException while reading Trigger Msg buffer",ex);
		 * return; }
		 */

		if (mqHost.isEmpty() || mqChannel.isEmpty() || alertTivoli.isEmpty() || boQueue.isEmpty() || failQueue.isEmpty()
				|| saveQueue.isEmpty() || qpasaq.isEmpty() || mqPort == -1) {
			return;
		}

		MQEnvironment.sharingConversations = 300;
		MQEnvironment.hostname = mqHost;
		MQEnvironment.channel = mqChannel;
		MQEnvironment.port = mqPort;
		MQEnvironment.sslCipherSuite = "SSL_ECDHE_RSA_WITH_AES_256_CBC_SHA384";
		MQEnvironment.sslFipsRequired = true;
		MQEnvironment.userID = "viper";

		try {
			qMgr = new MQQueueManager(mqtrg.getQMgrName());
		} catch (MQException mqe) {
			LOGGER.log(Level.SEVERE, "MQException : Cannot connect to queue manager ", mqe);
			return;
		}

		try {

			queue = qMgr.accessQueue(mqtrg.getQName(), openOptions);
		} catch (MQException mqe) {
			LOGGER.log(Level.SEVERE, "MQException : Cannot access queue");
			return;
		}

		LOGGER.info("Backout queue name is " + boQueue);

		gmo = new MQGetMessageOptions();

		gmo.matchOptions = MQConstants.MQMO_NONE;
		gmo.options = MQConstants.MQGMO_CONVERT | MQConstants.MQGMO_FAIL_IF_QUIESCING | MQConstants.MQGMO_SYNCPOINT;

		//PutSevMessageToQ m2q = new PutSevMessageToQ(); --> Spring injection

		// IN THIS LOOP A GET/BACKOUT/COMMIT or m2q.putMessage ERROR ENDS PROCESSING
		// OTHERWISE
		// MSG IS PUT TO BO QUEUE AND THE NEXT MSG IS PROCESSED

		try {
			for (;;) {
				msg = new MQMessage();

				try {
					queue.get(msg, gmo);
					LOGGER.info(
							"Message id read is: " + javax.xml.bind.DatatypeConverter.printHexBinary(msg.messageId));
				} catch (MQException e) {
					if (e.getReason() == MQConstants.MQRC_FORMAT_ERROR) {
						LOGGER.log(Level.WARNING, "MQ Format Error - Retrying without CONVERT option", e);
						gmo.options &= ~MQConstants.MQGMO_CONVERT;
						qMgr.backout();
						continue;
					} else {
						throw e;
					}

				}

				// turn on CONVERT in GMO
				gmo.options |= MQConstants.MQGMO_CONVERT;

				// for(byte b:msg.messageId)
				// {
				// System.out.print(Integer.toHexString(java.lang.Math.abs(b)));
				// }

				if (msg.backoutCount > 3) {
					LOGGER.log(Level.WARNING,
							"Message Backout Count exceeded " + msg.readStringOfByteLength(msg.getMessageLength()));

					// PutSevMessageToQ m2q = new PutSevMessageToQ();

					try {
						LOGGER.info("Putting Message to BackOut Queue " + boQueue);
						m2q.putMessage(qMgr.getName(), boQueue, msg);
						LOGGER.info("Put Message to BackOut Queue " + boQueue);
						LOGGER.info("Prepare to commit message");
						qMgr.commit();
						LOGGER.info("Message committed");
						continue;
					} // END TRY
					catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to BackOut queue", mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					} // END CATCH
				} // END IF

				try {
					x = msg.readStringOfByteLength(msg.getMessageLength());
				} // END TRY
				catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error reading message content - move msg to Backout Q", e);

					// PutSevMessageToQ m2q = new PutSevMessageToQ();

					try {
						LOGGER.info("Putting Message to BackOut Queue " + boQueue);
						m2q.putMessage(qMgr.getName(), boQueue, msg);
						LOGGER.info("Put Message to BackOut Queue " + boQueue);
						LOGGER.info("Prepare to commit message");
						qMgr.commit();
						LOGGER.info("Message committed");
						continue;
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to BackOut Queue", mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					}
				} // EBD CATCH

				LOGGER.info("Message read: " + x);

				// int begin = x.indexOf("Topic id=") + "Topic id=".length() + 1;

				int begin = x.indexOf("Topic id=");
				int end = x.indexOf(">", begin);

				if (end < 0 || begin < 0) {
					LOGGER.info("Invalid SEV Alert Message Format send to QPASA");

					// PutSevMessageToQ m2q = new PutSevMessageToQ();

					try {
						m2q.putMessage(qMgr.getName(), qpasaq, msg);
						qMgr.commit();
						continue;
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to queue " + x, mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					}
				} // END IF

				begin += "Topic id=".length() + 1;

				if (!(x.substring(begin, end - 1).contains("SEV1") | x.substring(begin, end - 1).contains("SEV2")
						| x.substring(begin, end - 1).contains("SEV3"))) {
					LOGGER.info("Invalid SEV Alert Message Format send to QPASA");

					// PutSevMessageToQ m2q = new PutSevMessageToQ();

					try {
						m2q.putMessage(qMgr.getName(), qpasaq, msg);
						qMgr.commit();
						continue;
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to queue " + x, mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					}

				}

				//ParseAlertMessage pam = new ParseAlertMessage(); --> Sprin Injection

				// throws XMLStreamException

				try {
					sm = pam.parseAlert(x);
				} catch (XMLStreamException ex) {
					LOGGER.log(Level.SEVERE, "XML Stream Exception parsing message " + x);

					try {
						m2q.putMessage(qMgr.getName(), failQueue, msg);
						qMgr.commit();
						continue;
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to queue " + x, mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					}
				}

				LOGGER.info("SM returned: " + sm);

				if (sm == null) {
					LOGGER.warning("Invalid SEV Alert Message Format send to QPASA");

					// PutSevMessageToQ m2q = new PutSevMessageToQ();

					try {
						m2q.putMessage(qMgr.getName(), qpasaq, msg);
						qMgr.commit();
						continue;
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to queue " + x, mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;

					}
				}

				try {
					s2e.sendAlertEmail(sm);
				} catch (Exception ex) {
					LOGGER.log(Level.SEVERE, "XML Stream Exception parsing message " + x);

					try {
						m2q.putMessage(qMgr.getName(), failQueue, msg);
						qMgr.commit();
						continue;
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message to queue " + x, mqe);
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					}
				}

				if (sm.getSevTopic().contains("SEV1")) {
					String topicString = sm.getSevTopic().substring(0, sm.getSevTopic().length() - 4) + ".AL";

					// PutSevMessageToQ m2q = new PutSevMessageToQ();

					try {
						LOGGER.info("Putting Message to SEV1 TOPIC QUEUE");
						m2q.putMessage(qMgr.getName(), topicString, msg);
						LOGGER.info("Put Message to SEV1 TOPIC QUEUE");
					} catch (MQException mqe) {
						LOGGER.log(Level.SEVERE, "Error putting message SEV1 ALERT queue " + x, mqe);
						try {
							LOGGER.info("Putting message to " + failQueue);
							m2q.putMessage(qMgr.getName(), failQueue, msg);
							LOGGER.info("Put message to " + failQueue);
						} catch (MQException e) {
							LOGGER.log(Level.SEVERE, "Error putting message to failQueue " + e);
							queue.setTriggerControl(MQConstants.MQTC_OFF);
							qMgr.backout();
							break;
						}
					}
				}

				// PutSevMessageToQ m2q = new PutSevMessageToQ();

				try {
					m2q.putMessage(qMgr.getName(), saveQueue, msg);
					qMgr.commit();
					LOGGER.info("Message Processed");
				} catch (MQException mqe) {
					LOGGER.info("Error putting message to save queue");
					try {
						m2q.putMessage(qMgr.getName(), failQueue, msg);
					} catch (MQException e) {
						queue.setTriggerControl(MQConstants.MQTC_OFF);
						qMgr.backout();
						break;
					}

				}

			} // END FOR
		} // END TRY
		catch (MQException e) {
			if (e.getReason() == MQConstants.MQRC_NO_MSG_AVAILABLE) {
				LOGGER.info("No More Messages To Process");
			} else {
				LOGGER.severe("A WebSphere MQ Error occured : Completion Code " + e.completionCode + " Reason Code "
						+ e.reasonCode);
				e.printStackTrace();
				for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
					System.out.println("... Caused by ");
					t.printStackTrace();
				}

			}

		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Unexpected Exception", ex);
			LOGGER.severe("Message at Error:" + x);
			ex.printStackTrace();

		} finally {

			if (queue.isOpen()) {
				try {
					queue.close();
				} catch (MQException e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.SEVERE, "MQException Closing Queue", e);
				}
			}

			if (qMgr.isConnected()) {
				try {
					qMgr.disconnect();
				} catch (MQException e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.SEVERE, "MQException Qmgr Disconnect", e);
				}
			}

		}
		LOGGER.info("AlertExtension :: processAlert() :: Ends");
	}	

}
