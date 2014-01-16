package com.wizecommerce.cts.zeus;

import java.util.Iterator;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.Source;

/**
 * Scrubber class will create n number of threads that will go to specific data sources
 * it will scrub data and put it in rabbitMQ
 * 
 * Integrating {@link Hibernate}
 * 
 * @author panand
 * @version 0.0.1
 */
public class Scrubber implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {

		Logger logger = Logger.getLogger("Scrubber");
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		logger.info("==============Scrubber Class================");
		
		Hibernate hib = new Hibernate();
		Iterator<?> sourceInfoIterator = hib.executeSelectQuery("FROM Source WHERE is_active = 1 AND sourceName = '" + dataMap.getString("source") + "'", false);
		
		while(sourceInfoIterator.hasNext()) {
			Source entry = (Source) sourceInfoIterator.next();
			logger.info("==============STARTING THREAD FOR ====" + entry.getSourceName() + "============");
			new ExtractData(entry);
		}
		
		hib.terminateSession();
	}
}