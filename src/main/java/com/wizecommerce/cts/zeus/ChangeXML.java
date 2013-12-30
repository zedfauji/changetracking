package com.wizecommerce.cts.zeus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ChangeXML {

	public HashMap<String, String> changeCommon;
	public HashMap<String, String> changeCustom;
	
	//public String changeXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><changePacket>";
	public String changeXML = "";
	public String changeCommonXML = "";
	public String changeCustomXML = "";
	
	public ChangeXML(HashMap<String, String> changeCommon, HashMap<String, String> changeCustom) {
		this.changeCommon = changeCommon;
		this.changeCustom = changeCustom;
	}

	public String getchangeXML(){
		
		Set<Entry<String, String>> changeCommonInfo = changeCommon.entrySet();
		Iterator<Entry<String, String>> changeCommonIterator = changeCommonInfo.iterator(); 
		
		changeCommonXML = "<common ";
		while(changeCommonIterator.hasNext()) {
			 Entry<String, String> entry = changeCommonIterator.next();
			 changeCommonXML = changeCommonXML + entry.getKey() + "=\"" + entry.getValue() + "\" ";
		}
		
		changeCommonXML += "></common>";
		
		Set<Entry<String, String>> changeCustomInfo = changeCustom.entrySet();
		Iterator<Entry<String, String>> changeCustomIterator = changeCustomInfo.iterator(); 
		
		changeCustomXML = "<custom ";
		while(changeCustomIterator.hasNext()) {
			 Entry<String, String> entry = changeCustomIterator.next();
			 changeCustomXML += entry.getKey() + "=\"" + entry.getValue() + "\" ";
		}
		changeCustomXML += "></custom>";
		changeXML += changeCommonXML + changeCustomXML;
	
		return changeXML;
	}
	
}
