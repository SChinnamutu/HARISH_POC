package com.mufg.alert.service.impl;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.mufg.alert.config.PropertiesCache;
import com.mufg.alert.io.SevMessage;
import com.mufg.alert.service.SendEmailAlertService;

@Service
public class SendEmailAlertImpl implements SendEmailAlertService {

	private static final Logger LOGGER = Logger.getLogger(SendEmailAlertImpl.class.getName());
	
	@Override
	public void sendAlertEmail(SevMessage sm) throws MessagingException {
		LOGGER.info("SendEmailAlertImpl :: sendAlertEmail() :: Init " + sm);
		String subjectLine = null;
		String alertTivoli = null;
		String topicString = null;
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "nymr1.btmna.com");
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(PropertiesCache.getInstance().getProperty("fromEmailAddress")));
		topicString = sm.getSevTopic().substring(0, sm.getSevTopic().length() - 4);
		if (PropertiesCache.getInstance().containsKey(topicString)) {
			message.addRecipients(Message.RecipientType.TO, PropertiesCache.getInstance().getProperty(topicString));
		}else {
			message.addRecipients(Message.RecipientType.TO, "MQAdmin_EO1@us.mufg.jp,MWDevelopers_EO1@us.mufg.jp");
			message.setSubject(sm.getSevTopic() + " Not Defined In Properties File - Call MQAdmins");
			message.setText(topicString);
			Transport.send(message);
		}
		if (sm.getSevTopic().contains("SEV1")) {
			subjectLine = sm.getSevTopic() + " SEV1 AGENT ALERT";
			alertTivoli = PropertiesCache.getInstance().getProperty("alertTivoli").toUpperCase();
			if (alertTivoli.contentEquals("TRUE")) {
				message.addRecipients(Message.RecipientType.CC, "tivoliesm@njtdwprod.btmna.com");
			}
		}else if (sm.getSevTopic().contains("SEV2")) {
			subjectLine = sm.getSevTopic() + " SEV2 AGENT ALERT";
		}
		else if (sm.getSevTopic().contains("SEV3")) {
			subjectLine = sm.getSevTopic() + " SEV3 AGENT ALERT";
		}else {
			subjectLine = "Unknown Alert " + sm.getSevTopic();
		}
		message.setSubject(subjectLine);
		message.setText(sm.toString());
		Transport.send(message);
		LOGGER.info("SendEmailAlertImpl :: sendAlertEmail() :: End");
	}
	

}
