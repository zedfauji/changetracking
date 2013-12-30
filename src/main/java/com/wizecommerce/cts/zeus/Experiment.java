package com.wizecommerce.cts.zeus;

import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Experiment {
	
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet resultSet = null;
	
	public void connectDb(HashMap<String, String> credentialDetails) throws ClassNotFoundException,SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(credentialDetails.get("host"), credentialDetails.get("user") , credentialDetails.get("password"));
	}
	
	public String getChanges(HashMap<String, String> sourceInfo_){
		try {
			
			String query = "SELECT experiment_id,name,description,experiment_xml,last_modified FROM experiment where ROWNUM <= 5";
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			
			HashMap<String, String> changeInfoCommon = new HashMap<String, String>();
			HashMap<String, String> changeInfoCustom = new HashMap<String, String>();
			
			String changeXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><changePacket source = \"" + sourceInfo_.get("sourceName") + "\">";
			
			while(resultSet.next()){
				
				Date dateToday = new Date();
			    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
			    String sDateTime_[] = resultSet.getString("last_modified").split("\\.");
			    Date date = ft.parse(sDateTime_[0]);
			    long unixT = date.getTime();
			   			    
			    changeInfoCommon.put("attempt", "0");
			    changeInfoCommon.put("source_name", sourceInfo_.get("sourceName"));
			    changeInfoCommon.put("source_id", sourceInfo_.get("sourceID"));
			    changeInfoCommon.put("sub_source_name", sourceInfo_.get("subSourceName"));
			    changeInfoCommon.put("sub_source_id", sourceInfo_.get("subSourceID"));
			    changeInfoCommon.put("description", resultSet.getString("name"));
			    changeInfoCommon.put("source_datetime", String.valueOf(unixT).substring(0, 10));
			    
			    changeInfoCustom.put("experiment_local_id", resultSet.getString("experiment_id"));
			    changeInfoCustom.put("details", resultSet.getString("description"));
				changeInfoCustom.put("start_date", resultSet.getString("last_modified"));
				changeInfoCustom.put("end_date", ft.format(dateToday));
				changeInfoCommon.put("status", "SUCCESS");						//---hard coding status
				changeInfoCustom.put("experiment_xml", aggregateDetails(resultSet.getString("experiment_xml")));
				
				changeXML += new ChangeXML(changeInfoCommon, changeInfoCustom).getchangeXML();
			}

			changeXML += "</changePacket>";
			return changeXML;
			
		} catch (SQLException e) {
			return e.getMessage();
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
	
	private String aggregateDetails(String details) throws Exception{
		
		String retStr = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(details));
		Document xmlDoc = builder.parse(is);
		
		NodeList segmentNodes = xmlDoc.getElementsByTagName("segment");
		for(int i = 0; i < segmentNodes.getLength(); i++){
			Element segmentNode = (Element)segmentNodes.item(i);
			retStr += "###LOW##" + segmentNode.getAttribute("low").trim() 
					+ "###HIGH##" + segmentNode.getAttribute("high").trim()
					+ "###APPSTYLE###" + segmentNode.getAttribute("appStyleId").trim();
		}
		return retStr;
	}
	
	public void closeConnection() throws SQLException{
		if(!this.conn.isClosed())
			this.conn.close();
	}
}
