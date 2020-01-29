package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stores {
	int storeID;
	int storeManagerID;
	String name;
	String City;
	
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the website:
	// https://remotemysql.com/
	// in future move these hard coded strings into separated config file or even better env variables
	static private final String DB = "7VP6RBaQoU";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "7VP6RBaQoU";
	static private final String PASS = "ov97FOeUst";

	public Stores(int storeID, int storeManagerID, String name, String city) {
		super();
		this.storeID = storeID;
		this.storeManagerID = storeManagerID;
		this.name = name;
		City = city;
	}
	public static String ShowStores() {
		Connection conn = null;
		Statement stmt = null;
		String MsgtoClient="";
		try {
			System.out.println("aaaaaa");

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql1="SELECT * FROM Store" ;
			ResultSet rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				MsgtoClient=MsgtoClient+rs.getInt(1)+"%"+rs.getString(2)+"%"+rs.getString(3)+"%"+rs.getInt(4)+"%";
								
			}
			rs.close();
			stmt.close();	
		}catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("VendorError: " + se.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println(MsgtoClient);
		return MsgtoClient;	
		
	}
	
}