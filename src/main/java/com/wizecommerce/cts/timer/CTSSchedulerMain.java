package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.utils.RabbitMQ;
import org.quartz.SchedulerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.logging.Logger;

//import com.wizecommerce.cts.zeus.Properties;
//import java.util.HashMap;

//TODO : Add writer logic

public class CTSSchedulerMain {

    /**
     * This class is the main class which gets initiated and calls main function
     * 
     * @throws SchedulerException
     */
    public static void main(String args[]) throws SchedulerException {
        Logger logger = Logger.getLogger("devSchedulerMain");

        /**
         * I think i got a solution for your Daemon's thing.
         *
         * Now starting Writer thread as daemon thread which might not be affected by main thread execution and will initiate
         * as an individual thread < May be a temp solution but it's worth> !!!!
         */
        CTSWriterThread writerThread1 = new CTSWriterThread();
        writerThread1.setDaemon(true);
        writerThread1.start();

        
        try {
        	
        	//Properties properties = new Properties();
        	//HashMap<String, String> propertiesHash = properties.getProperties();
            CTSschedulerUtil scheduler;

            RabbitMQ rabbitMq = new RabbitMQ();
            rabbitMq.connectRabbitMQ("cts_job");
            rabbitMq.setConsume();

            while(true) {
            	try{

            		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            		DocumentBuilder builder = factory.newDocumentBuilder();
            		InputSource is = new InputSource(new StringReader(rabbitMq.getPacketFromQueue()));
            		Document xmlDoc = builder.parse(is);

            		NodeList jobInfoNode = xmlDoc.getElementsByTagName("info");
            		NodeList jobParamsNode = xmlDoc.getElementsByTagName("job_params");
                
            		Element jobInfo = (Element) jobInfoNode.item(0);
            		Element jobParams = (Element) jobParamsNode.item(0);

                    //TODO: Add Scheduler builder code here

                    scheduler = new CTSschedulerUtil(jobInfo,jobParams);
	                logger.info("Starting job now ");
	                //scheduler.start();
                    /*
                    here we are going to control the execution thread of scheduler with a timeout value
                    we can add this timeout value as a property or any packet message lately
                     */

                    long timeOut = 10000;
                    if(timeOut < 0)   //Means no timout just start that bitch !!
                    {
                        final CTSschedulerUtil finalScheduler = scheduler;
                        Runtime.getRuntime().addShutdownHook(new Thread() {
                            public void run() {
                                    finalScheduler.shutdown(true);
                            }
                        });

                        scheduler.startAndWait();
                    }

                    else // we need to start and then wait for timeout and shutdown scheduler after wards
                    {
                       scheduler.startAndShutdown(timeOut);
                    }
            	}
            	catch(Exception e) {
            		e.printStackTrace();
            	}
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




}