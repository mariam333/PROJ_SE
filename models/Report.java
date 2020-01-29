package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Report {
	String typeOfReport;
	String Date;
	int storId;
	
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the website:
	// https://remotemysql.com/
	// in future move these hard coded strings into separated config file or even better env variables
	static private final String DB = "7VP6RBaQoU";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "7VP6RBaQoU";
	static private final String PASS = "ov97FOeUst";

	public Report(String typeOfReport, String date, int storId) {
		super();
		this.typeOfReport = typeOfReport;
		Date = date;
		this.storId = storId;
	}
	public static void addReport(String type, String date1, int storeID) {
		Connection conn = null;
		Statement stmt = null;
		int count=0;
		String date="'%"+date1.substring(2)+"'";
		try {
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql1="SELECT COUNT(reportID) FROM Report WHERE storeID ="+storeID+ " AND date LIKE "+date;
			ResultSet rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				count=rs.getInt(1);
				System.out.println(count);
			}
			if(count==0) {
		
		sql1 = "INSERT INTO Report VALUES (?, ?, ?, ?)";
		PreparedStatement update= conn.prepareStatement(sql1);
		update.setInt(1, 0);
		update.setString(2, type);
		update.setString(3, date1);
		update.setInt(4, storeID);		
		update.executeUpdate();
			}
		
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
	}
	public static String ShowPaymentReport(int storeID, String date1) {
		Connection conn = null;
		Statement stmt = null;
		String MsgtoClient="";
		String dateOrder;
		int day;
		int price;
		String type="PaymentReport";
		int c1=0,c2=0,c3=0,c4=0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String date="'%"+date1.substring(2)+"'";
			String sql1="SELECT * FROM `Order` WHERE `storeId`="+storeID+ " AND date LIKE "+date;
			ResultSet rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				dateOrder=rs.getString("date");
				price=rs.getInt("totalPrice");
				String [] st = dateOrder.split("/");
				day=Integer.parseInt(st[0]);
				if(day>0 && day<8)
					c1=c1+price;
				else if(day>7 && day<15) 
					c2=c2+price;
				else if(day>14 && day<22) 
					c3=c3+price;
				else 
					c4=c4+price;
				
				System.out.println(MsgtoClient);
			}
			MsgtoClient=c1+"%"+c2+"%"+c3+"%"+c4;
			System.out.println(MsgtoClient);
			rs.close();
			stmt.close();	
			Report.addReport( type, date1,storeID);
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
	public static String ShowOrdersReport(int storeID, String date1) {
		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		int Sum=0;int x=0;
		String MsgtoClient="";
		int itemID;
		int orderID;
		String type;

		String type1="OrdersReport";

		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			ResultSet rs2;
			ResultSet rs1;
			ResultSet rs;
			
			
			String sql="SELECT * FROM Item";
			 rs1 = stmt.executeQuery(sql);
			while (rs1.next()) {
				
				itemID=rs1.getInt("itemId");
				System.out.println(itemID);
				type=rs1.getString("type");
				String date="'%"+date1.substring(2)+"'";
				String sql1="SELECT * FROM `Order` WHERE `storeId`="+storeID+ " AND date LIKE "+date; 
				stmt1 = conn.createStatement();
				 rs = stmt1.executeQuery(sql1);
				
				while (rs.next()) {
					
				orderID=rs.getInt("orderId");
				String sql2="SELECT SUM(quantity) FROM `SoldItem` WHERE `itemId`= "+itemID+" AND `orderId`="+ orderID; 
				stmt2 = conn.createStatement();
				 rs2 = stmt2.executeQuery(sql2);
				
				while (rs2.next()) {
					x=rs2.getInt(1);
				}
//				rs2.close();
//				stmt2.close();
				Sum=Sum+x;
			}
//				rs.close();
//				stmt1.close();
				MsgtoClient=MsgtoClient+"%"+type+"%"+Sum;
				Sum=0;
			}
			System.out.println(MsgtoClient);
//			rs.close();
			stmt1.close();
//			rs2.close();
			stmt2.close();
			rs1.close();
			stmt.close();	
			Report.addReport( type1, date1,storeID);
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
		return MsgtoClient;	

	}
}