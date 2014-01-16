package com.wizecommerce.cts.zeus;

import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CredentialDetails {
	
	public HashMap<String, String> getCredentialDetails(String credential_details){
	
		HashMap<String, String> credentialDetails = new HashMap<String, String>();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(credential_details));
			Document xmlDoc = builder.parse(is);
		    
		    Element rootElement = (Element) xmlDoc.getElementsByTagName("credential_details").item(0);
		    NodeList nList = rootElement.getChildNodes();
		    
		    for(int i = 0; i < nList.getLength(); i++){
		    	//System.out.println(nList.item(i).getNodeName() + "----" + nList.item(i).getTextContent());
		    	credentialDetails.put(nList.item(i).getNodeName(), nList.item(i).getTextContent());	
		    }
		}
		catch(Exception e){
			credentialDetails.put("Failed", e.getMessage());
		}
	    return credentialDetails;
	}
	
}
