package com.wizecommerce.cts.zeus;

import java.sql.*;
import java.util.HashMap;

public class DbHandler {
	
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet resultSet = null;
	public String query = "";
	
	public DbHandler(String dbType, String host, String username, String paswd) {
		try {
				System.out.println("in constructor ---- " + dbType 
						+ "--" + host + "--" + username + "--" + paswd);
				conn = DriverManager.getConnection(host,username,paswd);
		}
		catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * finalize -- destroy jdbc connection when all tasks are completed
	 * @author panand 
	 */
	protected void finalize() {
		try {
			System.out.println("Shutting down jdbc connection !!!!!!!!");
			conn.close();
		}
		catch(SQLException se){
		    //Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, HashMap<String, String>> getSourceInfo() {
		
		HashMap<String, HashMap<String, String>> sourceHash = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> sourceInfoHash;
		
		// #TODO check if connection is created with database
		try {
			String query = "SELECT source_id,source_name,last_modified_datetime FROM source WHERE is_active=1";
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			
			while(resultSet.next()) {
				sourceInfoHash = new HashMap<String, String>();
				sourceInfoHash.put("source_id", resultSet.getString("source_id"));
				sourceInfoHash.put("source_name", resultSet.getString("source_name"));
				sourceInfoHash.put("last_modified_datetime", resultSet.getString("last_modified_datetime"));
				sourceHash.put(resultSet.getString("source_name"), sourceInfoHash);
			}
		}
		catch(SQLException se){
		    //Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return sourceHash;
	}
	
	public HashMap<String, HashMap<String, String>> getPerSourceInfo(String sourceID) {
		HashMap<String, HashMap<String, String>> perSourceHash = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> perSourceInfoHash;
		try{
			String query = "SELECT source_id,sub_source_id,sub_source_name,last_modified_datetime,credential_details,admin_date"
					+ " FROM sub_sources WHERE source_id = " + sourceID + " AND  is_active=1";
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			while(resultSet.next()) {
				perSourceInfoHash = new HashMap<String, String>();
				perSourceInfoHash.put("source_id", resultSet.getString("source_id"));
				perSourceInfoHash.put("sub_source_id", resultSet.getString("sub_source_id"));
				perSourceInfoHash.put("sub_source_name", resultSet.getString("sub_source_name"));
				perSourceInfoHash.put("last_modified_datetime", resultSet.getString("last_modified_datetime"));
				perSourceInfoHash.put("credential_details", resultSet.getString("credential_details"));
				perSourceInfoHash.put("admin_date", resultSet.getString("admin_date"));
				
				perSourceHash.put(resultSet.getString("sub_source_name"), perSourceInfoHash);
			}
		}
		catch(SQLException se){
		    //Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return perSourceHash;
	}
	
	/**
	 * This method will store the data only for change_record table.
	 * Primary consumer of this method will writer
	 */
	public HashMap<String, String> logChange(HashMap<String, String> changeLogHash) {
		
		HashMap<String, String> retHash = new HashMap<String, String>();
		int isSuccess = 0;
		String retStr = "NA";
		
		try{
			this.query = "INSERT INTO"
					+ " change_record"
					+ "(source_id,source_name,sub_source_id,sub_source_name,status,description,source_datetime)"
					+ " VALUES ('" + changeLogHash.get("source_id")
					+ "','" + changeLogHash.get("source_name")
					+ "','" + changeLogHash.get("sub_source_id")
					+ "','" + changeLogHash.get("sub_source_name")
					+ "','" + changeLogHash.get("status")
					+ "','" + changeLogHash.get("description") 
					+ "','" + changeLogHash.get("source_datetime") 
					+ "')";
			System.out.println("Query is - " + query);
			stmt = conn.createStatement();
			isSuccess = stmt.executeUpdate(query);
			retStr = "SUCCESS";
			
			//System.out.println("Query is - " + query + " and resultSet is - " + isSuccess);
		}
		catch(SQLException se){
		    //Handle errors for JDBC
			retStr = se.getMessage();
		}
		catch(Exception e) {
			retStr = e.getMessage();
		}
		retHash.put("isSuccess", new Integer(isSuccess).toString());
		retHash.put("msg", retStr);
		return retHash;
	}
	
	/**
	 * This method will touch the admin date for subSource in sub_source table
	 * @author panand
	 */
	public void touchAdminDate(String subSourceId) {
		
		try {
			stmt = conn.createStatement();
			this.query = "UPDATE sub_sources SET admin_date = now() WHERE sub_source_id = '" + subSourceId + "'"; 
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

