package com.wizecommerce.cts.zeus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.Source;
import com.wizecommerce.cts.utils.SubSource;

public class ExtractData implements Runnable {
	
	public Source source;
	public SubSource subSource;
	
	/**
	 * This is the main program that will create the threads and then start extracting
	 * the data. It will also call the methods in DbHandler class to access DB.
	 * @author panand
	 */
	public ExtractData(Source source) {
		this.source = source;
		new Thread(this, source.getSourceName()).start();;
	}
	
	public void run() {
		try{

			Hibernate hib = new Hibernate();
			String query = "FROM SubSource WHERE source_id = '" + source.getSourceId() + "'" ;
			Iterator<?> subSourceIterator = hib.executeSelectQuery(query);
			
			while(subSourceIterator.hasNext()) {
				subSource = (SubSource) subSourceIterator.next();
				
				HashMap<String, String> sourceInfo_ = new HashMap<String, String>();
				sourceInfo_.put("sourceName", source.getSourceName());
				sourceInfo_.put("sourceID", source.getSourceId());
				sourceInfo_.put("subSourceName", subSource.getSubSourceName());
				sourceInfo_.put("subSourceID", subSource.getSubSourceId());
				sourceInfo_.put("adminDate", subSource.getAdminDate());
		
				String changeString = null;
				
				// Parse XML string to a hashMap
				HashMap<String, String> credentialDetails = new CredentialDetails().getCredentialDetails(subSource.getCredentialDetails());
				
				// Loading source class at runtime @author panand
				ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
				Class<?> sourceClass = myClassLoader.loadClass(this.getClass().getPackage().getName() + "." + source.getSourceName());
				Object subSourceInstance = sourceClass.newInstance();
	            
	            Method subSourceConstruct = sourceClass.getMethod("connectDb", new Class[] { java.util.HashMap.class });
	            subSourceConstruct.invoke(subSourceInstance, new Object[] { credentialDetails });
				
	            Method getChanges = sourceClass.getMethod("getChanges", new Class[] { java.util.HashMap.class });
	            changeString = (String) getChanges.invoke(subSourceInstance, new Object[] { sourceInfo_ });
	            
	            Method closeConnection = sourceClass.getMethod("closeConnection", new Class[] {});
	            closeConnection.invoke(subSourceInstance);
	            
	            /**
				 * 1. pushing the scrubbed packet into queue
				 * 2. touch admin date for the sub source
				 * @author panand
				*/
				if(changeString.length() > 0) {
					QHandler ctsQ = new QHandler();
					ctsQ.enQueue(changeString);
					ctsQ.finalize();
					//dbHandle.touchAdminDate(subSourceInfo.getSubSourceId());
				}
			}
			hib.terminateSession();
			Thread.sleep(1000);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
