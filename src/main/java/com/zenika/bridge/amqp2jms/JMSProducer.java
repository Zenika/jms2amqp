package com.zenika.bridge.amqp2jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

public class JMSProducer {

	private MessageProducer jmsMessageProducer;

	public void produce(Message message) throws JMSException {
		jmsMessageProducer.send(message);
	}

	public void setJmsMessageProducer(MessageProducer jmsMessageProducer) {
		this.jmsMessageProducer = jmsMessageProducer;
	}

}
