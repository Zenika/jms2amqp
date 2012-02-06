package com.zenika.bridge.common;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Converter {

	public byte[] convertToByte(Message message) throws JMSException {
		if (message instanceof TextMessage) {
			return ((TextMessage) message).getText().getBytes();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public Message convertToMessage(byte[] bytes, Session session) throws JMSException {
		return session.createTextMessage(new String(bytes));
	}
}
