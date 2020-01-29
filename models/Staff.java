package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Staff {
	int personId;
	String email;
	String password;
	
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the website:
	// https://remotemysql.com/
	// in future move these hard coded strings into separated config file or even better env variables
	static private final String DB = "7VP6RBaQoU";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "7VP6RBaQoU";
	static private final String PASS = "ov97FOeUst";

	public Staff(int personId, String email, String password) {
		super();
		this.personId = personId;
		this.email = email;
		this.password = password;
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
			System.out.println("ff");
	//*whire sql here*//
			PreparedStatement prep_stmt = conn.prepareStatement("SELECT * FROM Staff WHERE email=?");
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
						.prepareStatement("UPDATE Staff SET access = ? WHERE email=?");
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
			PreparedStatement prep_stmt = conn.prepareStatement("SELECT * FROM Staff WHERE email=?");
			prep_stmt.setString(1, email);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				useremail = rs.getString("email");
				userpass = rs.getString("password");
				access = rs.getInt("access");
			}
			System.out.println(useremail+"bbbbbb"+access);
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
								.prepareStatement("UPDATE Staff SET access = ? WHERE email=?");
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





}