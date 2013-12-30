package com.wizecommerce.cts.zeus;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;


public class TestOracleConnection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		//String url = "jdbc:oracle:thin:@partners.db.nextag.com:1521:NTC4";
		/*
		String url = "jdbc:oracle:thin:@buildmain2.db.nextagqa.com:1521:QA_BDB2";
		String user = "ubdb";
		String password = "ubdb";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		*/
		
		String url = "jdbc:mysql://sdb-exp-s1.pv.sv.nextag.com:3306/experimentdb";
		String user = "searcher";
		String password = "nextag";
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		
		//ResultSet rs=stmt.executeQuery("SELECT experiment_id,name,description FROM experiment WHERE ROWNUM <= 5");  
		ResultSet rs=stmt.executeQuery("SELECT 7 from dual");
		while(rs.next())
			System.out.println("Experiment ID is " + rs.getString(1));
			
			//System.out.println("Experiment ID is " + rs.getString("experiment_id")+ "  Experiment Name -- " + rs.getString("name") + " and description is - " + rs.getString("description"));
		
		conn.close();
	}

}
