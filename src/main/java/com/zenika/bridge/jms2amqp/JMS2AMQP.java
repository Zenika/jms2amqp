package com.zenika.bridge.jms2amqp;

import com.zenika.bridge.common.Converter;

public class JMS2AMQP {

	public static void main(String[] args) throws Exception {

		String queueName = "zenika.queue";

		com.rabbitmq.client.ConnectionFactory amqpConnectionfactory = new com.rabbitmq.client.ConnectionFactory();
		com.rabbitmq.client.Connection amqpConnection = amqpConnectionfactory.newConnection();
		com.rabbitmq.client.Channel channel = amqpConnection.createChannel();

		AMQPProducer amqpProducer = new AMQPProducer();
		amqpProducer.setChannel(channel);
		amqpProducer.setQueueName(queueName);

		javax.jms.ConnectionFactory jmsConnectionFactory = new org.apache.activemq.ActiveMQConnectionFactory();
		javax.jms.Destination jmsDestination = new org.apache.activemq.command.ActiveMQQueue(queueName);
		javax.jms.Connection jmsConnection = jmsConnectionFactory.createConnection();

		javax.jms.Session jmsSession = jmsConnection.createSession(true, javax.jms.Session.CLIENT_ACKNOWLEDGE);
		javax.jms.MessageConsumer jmsMessageConsumer = jmsSession.createConsumer(jmsDestination);

		JMSMessageListener jmsMessageListener = new JMSMessageListener();
		jmsMessageListener.setAmqpProducer(amqpProducer);
		jmsMessageListener.setJmsToAMQPConverter(new Converter());
		jmsMessageListener.setSession(jmsSession);

		jmsMessageConsumer.setMessageListener(jmsMessageListener);
		jmsConnection.start();

	}

}
