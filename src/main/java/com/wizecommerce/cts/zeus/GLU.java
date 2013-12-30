package com.wizecommerce.cts.zeus;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class GLU {
	
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet resultSet = null;
	
	public void connectDb(HashMap<String, String> credentialDetails) throws SQLException {
		conn = DriverManager.getConnection(credentialDetails.get("host"), credentialDetails.get("user") , credentialDetails.get("password"));
	}
	
	public String getChanges(HashMap<String, String> sourceInfo_) {
		try{
			
			String query = "SELECT id,description,unix_timestamp(start_date) as start_date,end_date,fabric,status,username,details"
					+ " FROM db_deployment WHERE start_date >= '" +  sourceInfo_.get("adminDate") + "'";
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			
			HashMap<String, String> changeInfoCommon = new HashMap<String, String>();
			HashMap<String, String> changeInfoCustom = new HashMap<String, String>();
			
			String changeXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><changePacket source = \"" + sourceInfo_.get("sourceName") + "\">";
			
			while(resultSet.next()){
				
				changeInfoCommon.put("attempt", "0");
				changeInfoCommon.put("source_name", sourceInfo_.get("sourceName"));
				changeInfoCommon.put("source_id", sourceInfo_.get("sourceID"));
				changeInfoCommon.put("sub_source_name", sourceInfo_.get("subSourceName"));
				changeInfoCommon.put("sub_source_id", sourceInfo_.get("subSourceID"));
				changeInfoCommon.put("description", resultSet.getString("description"));
				changeInfoCommon.put("source_datetime", resultSet.getString("start_date"));
				changeInfoCommon.put("status", resultSet.getString("status"));
				
				changeInfoCustom.put("glu_local_id", resultSet.getString("id"));
				changeInfoCustom.put("start_date", resultSet.getString("start_date"));
				changeInfoCustom.put("end_date", resultSet.getString("end_date"));
				changeInfoCustom.put("fabric", resultSet.getString("fabric"));
				changeInfoCustom.put("details", aggregateDetails(resultSet.getString("details")));
				
				changeXML += new ChangeXML(changeInfoCommon, changeInfoCustom).getchangeXML();
			}

			changeXML += "</changePacket>";
			return changeXML;
		}
		catch(SQLException se){
		    //Handle errors for JDBC
			return se.getMessage();
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	private String aggregateDetails(String details) throws Exception{
		
		String retStr = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(details));
		Document xmlDoc = builder.parse(is);
		
		NodeList sequentialNodes = xmlDoc.getElementsByTagName("sequential");
		for(int i = 0; i < sequentialNodes.getLength(); i++){
			Element sequentialNode = (Element)sequentialNodes.item(i);
			retStr += sequentialNode.getAttribute("agent").trim() 
					+ "###" + sequentialNode.getAttribute("status").trim();
		}
		return retStr;
	}
	
	public void closeConnection() throws SQLException{
		if(!this.conn.isClosed())
			this.conn.close();
	}
}
