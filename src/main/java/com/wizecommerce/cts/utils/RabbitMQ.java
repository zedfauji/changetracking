package com.wizecommerce.cts.utils;

import java.io.IOException;
import java.util.HashMap;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
//import com.wizecommerce.cts.zeus.Properties;

public class RabbitMQ {

	HashMap<String, String> propertiesHash = new HashMap<String, String>();
	Channel channel;
    String QUEUE_NAME;
    QueueingConsumer consumer;
    Connection connection;
	
	public RabbitMQ() {
		//Properties properties = new Properties();
		//propertiesHash = properties.getProperties();
	}
	
	public void connectRabbitMQ() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
	    //factory.setHost(propertiesHash.get("cts.rabbitmq.host"));
		factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    channel = connection.createChannel();
	    
	    //QUEUE_NAME = propertiesHash.get("cts.rabbitmq.writer.queue");
	    QUEUE_NAME = "cts_scrubber";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}
	
	public void connectRabbitMQ(String queueName) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
	    //factory.setHost(propertiesHash.get("cts.rabbitmq.host"));
		factory.setHost("localhost");
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    System.out.println("Queue Name ----- " + queueName);
	    this.QUEUE_NAME = queueName;
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	}
	
	public boolean publishPacket(String packet) {
		try{
			channel.basicPublish("", QUEUE_NAME, null, packet.getBytes());
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
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
	
	public void terminateSession(){
		try {
			if(channel.isOpen())
				channel.close();
	    	if(connection.isOpen())
	    		connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
