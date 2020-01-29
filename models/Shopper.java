package models;


import java.sql.SQLException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class Shopper {
	
int shopperID;
String  fullName;
String password;
String email;
int number;
int visaNumber;
int cvv;
int expiration;
int refund;

static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

// update USER, PASS and DB URL according to credentials provided by the website:
// https://remotemysql.com/
// in future move these hard coded strings into separated config file or even better env variables
static private final String DB = "7VP6RBaQoU";
static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
static private final String USER = "7VP6RBaQoU";
static private final String PASS = "ov97FOeUst";

public Shopper(String  fullName,String password,String email,int number,int visaNumber,int cvv ,int expiration)
{
	this.fullName=fullName;
	this.password=password;
	this.email=email;
	this.number=number;
	this.visaNumber=visaNumber;
	this.cvv=cvv;
	this.expiration=expiration;
	refund=0;
}
public static String ShowShopper(int shopperID ) {
	Connection conn = null;
	Statement stmt = null;
	String MsgtoClient="";
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql1="SELECT * FROM Shopper WHERE shopperID="+ shopperID ;
		ResultSet rs = stmt.executeQuery(sql1);
		while (rs.next()) {
			MsgtoClient=rs.getString(1)+"%"+rs.getString(2)+"%"+rs.getString(3)+"%"+rs.getString(4)+"%"+rs.getString(5)+"%"+rs.getString(6)+"%"+rs.getString(7)+"%"+rs.getString(8)+"%"+rs.getString(9);
							
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
	return MsgtoClient;	
	
}


public static int LogIn(String email, String password) {
	Connection conn = null;
	Statement stmt = null;
	String useremail = null;
	String userpass = null;
	int access = 0;
	/// **sql**///
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
//*whire sql here*//
		PreparedStatement prep_stmt = conn.prepareStatement("SELECT * FROM Shopper WHERE email=?");
		prep_stmt.setString(1, email);
		ResultSet rs = prep_stmt.executeQuery();
		while (rs.next()) {
			useremail = rs.getString("email");
			userpass = rs.getString("password");
			access = rs.getInt("access");

		}
		rs.close();
		prep_stmt.close();
		if (useremail == null) {
			return 1;

		} else if (useremail != null) {
			if (!(userpass.equals(password))) {
				return 2;
			} else {
				if (access == 0) {
					PreparedStatement prep_stmt1 = conn
							.prepareStatement("UPDATE Shopper SET access = ? WHERE email=?");
					prep_stmt1.setInt(1, 1);
					prep_stmt1.setString(2, useremail);
					prep_stmt1.executeUpdate();
					prep_stmt1.close();
					conn.close();

					return 0;
				} else {
					return 3;
				}
			}
		}
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
		System.out.println("SQLState: " + se.getSQLState());
		System.out.println("VendorError: " + se.getErrorCode());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	return 3;
}
public static boolean DeleteShopper(int shopperID) {
	Connection conn = null;
	Statement stmt = null;
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql = "DELETE FROM Shopper WHERE shopperId=?";
		
		PreparedStatement update= conn.prepareStatement(sql);
		update.setString(1,shopperID+"");
		update.executeUpdate();
		return true;
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
	return false;
}

public static  boolean addShopper(String  fullName,String password,String email,int number,int visaNumber,int cvv ,int expiration) throws ClassNotFoundException {
	

	Connection conn = null;
	Statement stmt = null;
	List<String> Email = new ArrayList<String>();
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql = "SELECT * FROM Shopper";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Email.add(rs.getString("Email"));
		}
		 if (Email.contains(email)) {
			return false;
		 }
		 else {	
   String sql1 = "INSERT INTO Shopper VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	PreparedStatement update= conn.prepareStatement(sql1);
	update.setLong(1, 0);
	update.setString(2,fullName);
	update.setString(3, password);
	update.setString(4, email);
	update.setLong(5, number);
	update.setLong(6, visaNumber);
	update.setLong(7, cvv);
	update.setLong(8, expiration);
	update.setLong(9, 0);
	update.executeUpdate();
	return true ;
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
	return false;
}

public static boolean SignOut(String email) {
	Connection conn = null;
	Statement stmt = null;
	String useremail = null;
	int access = 0;
	/// **sql**///
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
//*whire sql here*//
		PreparedStatement prep_stmt = conn.prepareStatement("SELECT * FROM Shopper WHERE email=?");
		prep_stmt.setString(1, email);
		ResultSet rs = prep_stmt.executeQuery();
		while (rs.next()) {
			useremail = rs.getString("email");
			access = rs.getInt("access");
		}
		rs.close();
		prep_stmt.close();
		if (access == 1) {
			PreparedStatement prep_stmt1 = conn
					.prepareStatement("UPDATE Shopper SET access = ? WHERE email=?");
			prep_stmt1.setInt(1, 0);
			prep_stmt1.setString(2, useremail);
			prep_stmt1.executeUpdate();
			prep_stmt1.close();
			conn.close();
			return true;
		}
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
		System.out.println("SQLState: " + se.getSQLState());
		System.out.println("VendorError: " + se.getErrorCode());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	return false;

}
public static boolean Edit(String WhatToEdit, String useremail, String WhatToSet) {
	Connection conn = null;
	Statement stmt = null;


	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS); // *whire sql here*//
		PreparedStatement prep_stmt = conn.prepareStatement("SELECT * FROM Shopper WHERE email=?");
		prep_stmt.setString(1, useremail);
		ResultSet rs = prep_stmt.executeQuery();
		while (rs.next()) {
			PreparedStatement prep_stmt2 = conn.prepareStatement("SELECT email FROM Shopper ");
			ResultSet rs1 = prep_stmt2.executeQuery();
			while (rs1.next()) {
				if (WhatToSet.equals(rs1.getString("email"))) {//if i want to change the email but already used
					return false;
				}
			}
			PreparedStatement prep_stmt1 = conn
					.prepareStatement("UPDATE Shopper SET " + WhatToEdit + "=?" + " WHERE email=?");
			prep_stmt1.setString(1, WhatToSet);
			prep_stmt1.setString(2, useremail);
			prep_stmt1.executeUpdate();
			prep_stmt1.close();
		}
		prep_stmt.close();
		rs.close();
		conn.close();

	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
		System.out.println("SQLState: " + se.getSQLState());
		System.out.println("VendorError: " + se.getErrorCode());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	return true;
}
public static String ShowShopperID(String email) {
	Connection conn = null;
	Statement stmt = null;
	String MsgtoClient="";
	/// **sql**///
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
//*whire sql here*//
		PreparedStatement prep_stmt = conn.prepareStatement("SELECT shopperID FROM Shopper WHERE email=?");
		prep_stmt.setString(1, email);
		ResultSet rs = prep_stmt.executeQuery();
		while (rs.next()) {
			MsgtoClient=rs.getString(1);
		}
		
}catch (SQLException se) {
	se.printStackTrace();
	System.out.println("SQLException: " + se.getMessage());
	System.out.println("SQLState: " + se.getSQLState());
	System.out.println("VendorError: " + se.getErrorCode());
} catch (Exception e) {
	e.printStackTrace();
} finally {
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
public static boolean EditRefund(int shopperID, int refund) {
	int oldRefund=0;
	String email="";
	boolean flag;
	Connection conn = null;
	Statement stmt = null;
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql = "SELECT * FROM Shopper WHERE shopperID="+shopperID;
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			oldRefund=rs.getInt("refund");
			email=rs.getString("email");
		}
		refund=refund+oldRefund;
		 flag= Edit("refund",email,refund+"");
		if (flag == false) 
			return false;

}catch (SQLException se) {
	se.printStackTrace();
	System.out.println("SQLException: " + se.getMessage());
	System.out.println("SQLState: " + se.getSQLState());
	System.out.println("VendorError: " + se.getErrorCode());
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	} catch (SQLException se) {
		se.printStackTrace();
	}
}
	return true;
}

}