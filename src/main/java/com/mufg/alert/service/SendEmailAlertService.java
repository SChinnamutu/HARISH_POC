package com.mufg.alert.service;

import javax.mail.MessagingException;

import com.mufg.alert.io.SevMessage;

public interface SendEmailAlertService {

	public void sendAlertEmail(SevMessage sm) throws MessagingException;
}
