package com.zenika.bridge.jms2amqp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import com.zenika.bridge.common.Converter;

public class JMSMessageListener implements MessageListener {

	private AMQPProducer amqpProducer;
	private Converter jmsToAMQPConverter;
	private Session session;

	public void onMessage(Message message) {
		try {

			byte[] bytes = jmsToAMQPConverter.convertToByte(message);
			amqpProducer.produce(bytes);
			session.commit();

		} catch (Exception e) {
			// TODO
			e.printStackTrace();
			try {
				session.rollback();
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void setAmqpProducer(AMQPProducer amqpProducer) {
		this.amqpProducer = amqpProducer;
	}

	public void setJmsToAMQPConverter(Converter jmsToAMQPConverter) {
		this.jmsToAMQPConverter = jmsToAMQPConverter;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
