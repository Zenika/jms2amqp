package com.zenika.bridge.amqp2jms;

import com.zenika.bridge.common.Converter;

public class AMQP2JMS {

	public static void main(String[] args) throws Exception {

		String queueName = "zenika.queue";

		javax.jms.ConnectionFactory jmsConnectionFactory = new org.apache.activemq.ActiveMQConnectionFactory();
		javax.jms.Destination jmsDestination = new org.apache.activemq.command.ActiveMQQueue(queueName + ".retour");
		javax.jms.Connection jmsConnection = jmsConnectionFactory.createConnection();

		javax.jms.Session jmsSession = jmsConnection.createSession(true, javax.jms.Session.CLIENT_ACKNOWLEDGE);
		javax.jms.MessageProducer jmsMessageProducer = jmsSession.createProducer(jmsDestination);
		JMSProducer jmsProducer = new JMSProducer();
		jmsProducer.setJmsMessageProducer(jmsMessageProducer);
		jmsConnection.start();

		com.rabbitmq.client.ConnectionFactory amqpConnectionfactory = new com.rabbitmq.client.ConnectionFactory();
		com.rabbitmq.client.Connection amqpConnection = amqpConnectionfactory.newConnection();
		com.rabbitmq.client.Channel channel = amqpConnection.createChannel();
		com.rabbitmq.client.QueueingConsumer consumer = new com.rabbitmq.client.QueueingConsumer(channel);
		boolean autoAck = false;
		channel.basicConsume(queueName, autoAck, consumer);

		AMQPConsumer amqpConsumer = new AMQPConsumer();
		amqpConsumer.setConsumer(consumer);
		amqpConsumer.setConverter(new Converter());
		amqpConsumer.setJmsProducer(jmsProducer);
		amqpConsumer.setSession(jmsSession);
		amqpConsumer.setChannel(channel);

		amqpConsumer.consume();

	}

}
