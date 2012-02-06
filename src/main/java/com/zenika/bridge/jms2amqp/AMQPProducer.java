package com.zenika.bridge.jms2amqp;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class AMQPProducer {

	private Channel channel;
	private String queueName;

	public void produce(byte[] bytes) throws IOException {
		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, bytes);
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
