package com.wizecommerce.cts.zeus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Reads the properties file into an {@link HashMap}
 * @author panand
 *
 */
public class Properties {
	/**
	 * Reads the properties into a {@link HashMap}
	 * @return properties hash {@link HashMap}
	 * @author panand
	 */
	public HashMap<String, String> getProperties() {
		HashMap<String, String> properties = new HashMap<String, String>();
		try {
			String pLine = "";
			BufferedReader in = new BufferedReader(new FileReader("cts.properties"));
			
			while((pLine = in.readLine()) != null) {
				String assoc[] = pLine.split("=");
				properties.put(assoc[0], assoc[1]);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
}
