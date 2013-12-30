package com.wizecommerce.cts.utils;

import java.io.IOException;
import java.util.HashMap;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.wizecommerce.cts.zeus.Properties;

public class RabbitMQ {

	HashMap<String, String> propertiesHash = new HashMap<String, String>();
	Channel channel;
    String QUEUE_NAME;
    QueueingConsumer consumer;
	
	public RabbitMQ() {
		Properties properties = new Properties();
		propertiesHash = properties.getProperties();
	}
	
	public void connectRabbitMQ() throws IOException {
		
		// Creating connection with rabbitmq 
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(propertiesHash.get("cts.rabbitmq.host"));
	    Connection connection = factory.newConnection();
	    channel = connection.createChannel();
	    
	    QUEUE_NAME = propertiesHash.get("cts.rabbitmq.queue");
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}
	
	public void setQueue() throws IOException {}
	
	public void setConsume() throws IOException {

	    consumer = new QueueingConsumer(channel);
	    channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	public String getPacketFromQueue() throws InterruptedException {
		
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        String message = new String(delivery.getBody());
        return message;
	}
	
}
