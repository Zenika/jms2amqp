package com.zenika.bridge.amqp2jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;
import com.zenika.bridge.common.Converter;

public class AMQPConsumer {

	private QueueingConsumer consumer;
	private Channel channel;
	private Converter converter;
	private Session session;
	private JMSProducer jmsProducer;

	public void consume() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, JMSException {

		while (true) {

			Delivery delivery = consumer.nextDelivery();
			byte[] bytes = delivery.getBody();
			long deliveryTag = delivery.getEnvelope().getDeliveryTag();

			Message message = converter.convertToMessage(bytes, session);
			try {
				jmsProducer.produce(message);
				session.commit();
				channel.basicAck(deliveryTag, false);
			} catch (JMSException e) {
				// TODO
				session.rollback();
				e.printStackTrace();
			}

		}
	}

	public void setConsumer(QueueingConsumer consumer) {
		this.consumer = consumer;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setJmsProducer(JMSProducer jmsProducer) {
		this.jmsProducer = jmsProducer;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
