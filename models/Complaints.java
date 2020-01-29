package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Complaints {
	int complaintId;
	int shopperID;
	static int storeID;
	String topic;
	String content;
	String time;
	String complaintDate;
	static int quarter;
	
static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

// update USER, PASS and DB URL according to credentials provided by the website:
// https://remotemysql.com/
// in future move these hard coded strings into separated config file or even better env variables
static private final String DB = "7VP6RBaQoU";
static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
static private final String USER = "7VP6RBaQoU";
static private final String PASS = "ov97FOeUst";

public Complaints(int shopperID, int storeID, String topic, String content, String time, String complaintDate) {
	super();
	this.shopperID = shopperID;
	this.storeID = storeID;
	this.topic = topic;
	this.content = content;
	this.time = time;
	this.complaintDate = complaintDate;
}
public static boolean addComplaint(int shopperID, String storeName, String topic, String content, String time, String complaintDate) {
	Connection conn = null;
	Statement stmt = null;
	int storeID=0;
	int quarter=0;
	
	try {
		
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		storeName="'"+storeName+"'";
		String sql1="SELECT storeID FROM Store WHERE name="+storeName;
		ResultSet rs = stmt.executeQuery(sql1);
		while (rs.next()) {
			storeID=rs.getInt(1);
			System.out.println(storeID);
			
		}
		String[] date=(complaintDate).split("/");
		if(Integer.parseInt(date[0])>0 && Integer.parseInt(date[0])<8)
		{
			quarter=1;
		}
		if(Integer.parseInt(date[0])>7 && Integer.parseInt(date[0])<15)
		{
			quarter=2;
		}
		if(Integer.parseInt(date[0])>14 && Integer.parseInt(date[0])<22)
		{
			quarter=3;
		}
		if(Integer.parseInt(date[0])>21 && Integer.parseInt(date[0])<29)
		{
			quarter=4;
		}
		System.out.println(quarter);
		 sql1 = "INSERT INTO Complaint VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement update= conn.prepareStatement(sql1);
		update.setInt(1, 0);
		update.setInt(2, shopperID);
		update.setInt(3, storeID);
		update.setString(4, complaintDate);
		update.setInt(5, quarter);
		update.setString(6, time);
		update.setString(7, topic);
		update.setString(8, content);
		
		update.executeUpdate();
		return true ;
    }
	catch (SQLException se) {
		
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
	
public static String ShowComplaintReport(int storeID,String date1) {
Connection conn = null;
Statement stmt = null;
String MsgtoClient="";
String type="ComplaintReport";
int c1=0,c2=0,c3=0,c4=0;

try {
	System.out.println("aaaaaa");
	System.out.println(date1.substring(2));

	Class.forName(JDBC_DRIVER);
	conn = DriverManager.getConnection(DB_URL, USER, PASS);
	stmt = conn.createStatement();
	String date="'%"+date1.substring(2)+"'";
	String sql1="SELECT COUNT(complaintID) FROM Complaint WHERE storeID ="+storeID+ " AND complaintDate LIKE "+date+" AND quarter=1";
	ResultSet rs = stmt.executeQuery(sql1);
	while (rs.next()) {
		c1=rs.getInt(1);
	}
	String sql2="SELECT COUNT(complaintID) FROM Complaint WHERE storeID ="+storeID+ " AND complaintDate LIKE "+date+" AND quarter=2";
	ResultSet rs2 = stmt.executeQuery(sql2);
	while (rs2.next()) {
		c2=rs2.getInt(1);
	}
	String sql3="SELECT COUNT(complaintID) FROM Complaint WHERE storeID ="+storeID+ " AND complaintDate LIKE "+date+" AND quarter=3";
	ResultSet rs3 = stmt.executeQuery(sql3);
	while (rs3.next()) {
		c3=rs3.getInt(1);
	}
	String sql4="SELECT COUNT(complaintID) FROM Complaint WHERE storeID ="+storeID+ " AND complaintDate LIKE "+date+" AND quarter=4";
	ResultSet rs4 = stmt.executeQuery(sql4);
	while (rs4.next()) {
		c4=rs4.getInt(1);
	}
	MsgtoClient=c1+"%"+c2+"%"+c3+"%"+c4;
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
public static String ShowAllComplaints() {
	Connection conn = null;
	Statement stmt = null;
	String MsgtoClient="";
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql1="SELECT * FROM Complaint" ;
		ResultSet rs = stmt.executeQuery(sql1);
		while (rs.next()) {
			MsgtoClient=MsgtoClient+rs.getInt("complaintID")+"%"+rs.getInt("shopperID")+"%"+rs.getInt("storeID")+"%"+rs.getString("complaintDate")+"%"+rs.getInt(5)+"%"+rs.getString("time")+"%"+rs.getString("topic")+"%"+rs.getString("content")+"%"+rs.getString("reply");
							
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
public static String ShowComplaintsToHandel(String currentTime,String currentComplaintDate) {
	Connection conn = null;
	Statement stmt = null;
	String MsgtoClient="";
	String [] st=currentComplaintDate.split("/");
	int cday=Integer.parseInt(st[0]);
	st =currentTime.split(":");
	int chour=Integer.parseInt(st[0]);
	int cminute=Integer.parseInt(st[1]);
	System.out.println(cday);
	int day;
	int hour,minute;
	
	String time;
	String complaintDate;
	try {
		Class.forName(JDBC_DRIVER);
		
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql1="SELECT * FROM `Complaint` WHERE `reply`= ''" ;
		ResultSet rs = stmt.executeQuery(sql1);
		while (rs.next()) {
			
			time=rs.getString("time");
			System.out.println(time);
			complaintDate=rs.getString("complaintDate");
			st=time.split(":");
			 hour=Integer.parseInt(st[0]);
			 minute=Integer.parseInt(st[1]);
			 st=complaintDate.split("/");
			 day=Integer.parseInt(st[0]);
			
			if (complaintDate.substring(2).contains((currentComplaintDate).substring(2)) && ((cday==day+1 && (chour<hour || (chour==hour && (cminute < minute))))||(cday==day &&(chour>hour||cminute>minute) )  ))
			{
				
				MsgtoClient=MsgtoClient+rs.getInt(1)+"%"+rs.getInt(2)+"%"+rs.getInt(3)+"%"+rs.getString(4)+"%"+rs.getInt(5)+"%"+rs.getString(6)+"%"+rs.getString(7)+"%"+rs.getString(8)+"%"+rs.getString(9);
			}
			else {
				
				boolean flag =ReplyComplaints(rs.getInt(1),"-");
				if (flag==true)
					System.out.println("addreply");
			}			
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
public static boolean ReplyComplaints(int complaintID, String reply) {
	Connection conn = null;
	Statement stmt = null;


	try {
		Class.forName(JDBC_DRIVER);
		
		conn = DriverManager.getConnection(DB_URL, USER, PASS); // *whire sql here*//
			PreparedStatement prep_stmt1 = conn
					.prepareStatement("UPDATE Complaint SET reply = ? WHERE complaintID=?");
			prep_stmt1.setString(1, reply);
			prep_stmt1.setInt(2, complaintID);
			prep_stmt1.executeUpdate();
			prep_stmt1.close();
		
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
public static String ShowComplaintBycomplaintIDAndDate(int complaintID, String date) {
	Connection conn = null;
	Statement stmt = null;
	date="'"+date+"'";
	String MsgtoClient="";
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql1="SELECT * FROM Complaint WHERE complaintID =" + complaintID + " AND complaintDate ="+date;
		ResultSet rs = stmt.executeQuery(sql1);
		while (rs.next()) {
			MsgtoClient=rs.getInt("complaintID")+"%"+rs.getInt("shopperID")+"%"+rs.getInt("storeID")+"%"+rs.getString("complaintDate")+"%"+rs.getInt(5)+"%"+rs.getString("time")+"%"+rs.getString("topic")+"%"+rs.getString("content")+"%"+rs.getString("reply");
							
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