package com.wizecommerce.cts.utils;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;


public class TestHibernate {
	public static void main(String[] args) {
		/*
		Hibernate hib = new Hibernate();
		
		String query = "FROM Source WHERE is_active = 1";
		Iterator iter_ = hib.executeSelectQuery(query);
		while(iter_.hasNext()){
			Source record = (Source) iter_.next();
			System.out.println("CR ID	      		»       : " + record.getSourceName());
			
			System.out.println("Sub Source Name		»		: " + record.getLastModifiedDatetime());
			System.out.println("Status	     		»       : " + record.getIsActive());
		}
		
		hib.terminateSession();
		*/
		
		try {
			
			long unixTime = System.currentTimeMillis() / 1000L;
			System.out.println(unixTime);
			
			String sDateTime = "2013-08-30 12:00:00.0";
			String sDateTime_[] = sDateTime.split("\\.");
			System.out.println("D ==========" + sDateTime_[0]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(sDateTime_[0]);
			System.out.println("T ===" + date.getTime());
			long unixT = date.getTime();
			String str_ = String.valueOf(unixT);
			System.out.println("Q==========" + str_.substring(0, 10));
			
			
			String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><changePacket><common attempt=\"0\" source_datetime=\"2013-12-22 21:40:36.0\" status=\"COMPLETED\" sub_source_name=\"GLU_SDX\" description=\"Bounce - mountPoint [/tag/p9001] - SEQUENTIAL\" source_id=\"1\" sub_source_id=\"2\" source_name=\"GLU\"></common><custom glu_local_id=\"923\" end_date=\"2013-12-22 21:47:43.0\" details=\"soon...\" fabric=\"Service\" start_date=\"2013-12-22 21:40:36.0\"></custom></changePacket>";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(str));
			
			Document xmlDoc = builder.parse(is);
			
			Element common_ = (Element) xmlDoc.getElementsByTagName("common").item(0);
			System.out.println(common_.getAttribute("status"));
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

