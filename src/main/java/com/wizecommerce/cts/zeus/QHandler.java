package com.wizecommerce.cts.zeus;

import java.util.HashMap;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class QHandler {

	/*
	 *  By default queue name would be cts_scrubber.
	 *  Un less specified in cts.properties
	 */
	private String QUEUE_NAME = "cts_scrubber";
	private Channel channel;
	private Connection connection;
	
	public QHandler() {
		
		Properties properties = new Properties();
		HashMap<String, String> propertiesHash = new HashMap<String, String>();
		propertiesHash = properties.getProperties();
		
		this.QUEUE_NAME = propertiesHash.get("cts.rabbitmq.queue");
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost(propertiesHash.get("cts.rabbitmq.host"));
		    
		    connection = factory.newConnection();
		    channel = connection.createChannel();
		    
		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void finalize() {
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
	
	public boolean enQueue(String packet) {
		try{
			channel.basicPublish("", QUEUE_NAME, null, packet.getBytes());
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
