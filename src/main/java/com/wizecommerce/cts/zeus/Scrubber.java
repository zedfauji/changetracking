package com.wizecommerce.cts.zeus;

import java.util.Iterator;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.Source;

/**
 * Scrubber class will create n number of threads that will go to specific data sources
 * it will scrub data and put it in rabbitMQ
 * 
 * Integrating Hibernate
 * 
 * @author panand
 * @version 0.0.1
 */
public class Scrubber implements Job {
		
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		Hibernate hib = new Hibernate();
		// fetching source information and if required then starting thread for it.
		Iterator<?> sourceInfoIterator = hib.executeSelectQuery("FROM Source WHERE is_active = 1");
		
		while(sourceInfoIterator.hasNext()) {
			Source entry = (Source) sourceInfoIterator.next();
			System.out.println("==============STARTING THREAD FOR ====" + entry.getSourceName() +"============");
			new ExtractData(entry);
		}
		
		hib.terminateSession();
	}
}