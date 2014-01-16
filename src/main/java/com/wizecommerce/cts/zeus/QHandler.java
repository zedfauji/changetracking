package com.wizecommerce.cts.zeus;

//import java.util.HashMap;
import java.util.logging.Logger;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;

public class QHandler {

	/*
	 *  By default queue name would be cts_scrubber.
	 *  Unless specified in cts.properties
	 */
	public String QUEUE_NAME = "cts_scrubber";
	public Channel channel;
	public Connection connection;
	
	public QHandler() {
		
		//Properties properties = new Properties();
		//HashMap<String, String> propertiesHash = new HashMap<String, String>();
		//propertiesHash = properties.getProperties();
		
		//this.QUEUE_NAME = propertiesHash.get("cts.rabbitmq.job.queue");
		Logger logger = Logger.getLogger("rabbitMQ");
		this.QUEUE_NAME = "cts_job";
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("localhost");
		    
		    connection = factory.newConnection();
		    channel = connection.createChannel();
		    
		    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		}
		catch(ShutdownSignalException rExc){
			logger.info(rExc.getReason().toString());
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