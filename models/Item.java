package src.main.java.models;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.awt.Image;

public class Item {
	
	int itemId;
	int  storeId;
	String color;
	int quantity;
	double price;
	String type;
	String sale;
	String image;
	

static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
static private final String DB = "7VP6RBaQoU";
static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
static private final String USER = "7VP6RBaQoU";
static private final String PASS = "ov97FOeUst";

public Item()
{
	
}

 public Item(int  storeId,String color,int quantity,double price,String type,String image)
{
	this.storeId=storeId;
	this.color=color;
	this.quantity=quantity;
	this.price=price;
	this.type=type;
	this.image=image;
}

 public Item(int  storeId,String color,int quantity,double price,String type)
{
	this.storeId=storeId;
	this.color=color;
	this.quantity=quantity;
	this.price=price;
	this.type=type;
}


public boolean deleteItem(int itemId) throws ClassNotFoundException {
	

	Connection conn = null;
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

	
   String sql = "DELETE FROM Item WHERE itemId=?";;
	PreparedStatement update= conn.prepareStatement(sql);

  
	update.setLong(1, itemId);
	update.executeUpdate();

    
    }
	catch (SQLException se) {
		
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
        System.out.println("SQLState: " + se.getSQLState());
        System.out.println("VendorError: " + se.getErrorCode());
        return false;
	}
return true;

}

 
public boolean editItem(int itemId) throws ClassNotFoundException {


	Connection conn = null;
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

	
   String sql = "UPDATE Item SET color=?,quantity=?,price=?,type=?  WHERE itemId =?";
	PreparedStatement update= conn.prepareStatement(sql);

  
	update.setString(1, color);
	update.setLong(2, quantity);
	update.setDouble(3, price);
	update.setString(4, color);
	update.setLong(5, itemId);

	update.executeUpdate();

    
    }
	catch (SQLException se) {
		
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
        System.out.println("SQLState: " + se.getSQLState());
        System.out.println("VendorError: " + se.getErrorCode());
        return false;
	}

return true;
}



public String viewItem(int itemId) throws ClassNotFoundException {
	Connection conn = null;
	Statement stmt = null;
	String MsgtoClient="";
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		String sql1="SELECT * FROM Item WHERE itemId="+ itemId ;
		ResultSet rs = stmt.executeQuery(sql1);
		
		while (rs.next()) {
			MsgtoClient=rs.getString(1)+"%"+rs.getString(2)+"%"+rs.getString(3)+"%"+rs.getString(4)+"%"+rs.getString(5)+"%"+rs.getString(6)+"%"+rs.getString(7);
							
		}
		
		
		String sql2="SELECT image FROM Item WHERE itemId="+ itemId ;
		rs = stmt.executeQuery(sql2);

	if (rs.next()) {
	        byte[] img = rs.getBytes("Image");
	        System.out.println(img);
	    	MsgtoClient=MsgtoClient+"%"+img;

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
	

public boolean addItem() throws ClassNotFoundException {

	Connection conn = null;
	PreparedStatement stmt=null;
	ResultSet rs=null;
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		// checking if the item  exist
		String sql = "SELECT itemId FROM Item WHERE type=?";
	      stmt = conn.prepareStatement(sql);
	  	stmt.setString(1, type);
	  	rs=stmt.executeQuery();
	  	if(rs.next())
	  	{
           return false;
	  	} 
	  	else {

	
   String sql2 = "INSERT INTO Item (storeId, color,quantity,price,type,image) VALUES (?, ?, ?,?,?,?)";
	PreparedStatement update= conn.prepareStatement(sql2);

    
	update.setLong(1, storeId);
	update.setString(2, color);
	update.setLong(3, quantity);
	update.setDouble(4, price);
	update.setString(5, type);
	//String  s="C:\\WORKSPACE\\EX\\src\\main\\java\\models\\flower.jpg";
	String  s="C:\\Users\\doaa ziadat\\Desktop\\TOOLBOX\\flower2.jpg";

		InputStream img;
		try {
			img = new FileInputStream(new File(s));
		    update.setBlob(6,img);
            update.executeUpdate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	
    }
	}
	catch (SQLException se) {
		
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
        System.out.println("SQLState: " + se.getSQLState());
        System.out.println("VendorError: " + se.getErrorCode());
	}
	return true;
}


public String showCatalog(int storeId,char c)
{
	Connection conn = null;
	Statement stmt = null;
	String sql1="";
	String msg="";
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		if(c=='n') 
		 sql1="SELECT * FROM Item WHERE storeId="+storeId+" ORDER BY type" ;
		else if(c=='p') 
			sql1="SELECT * FROM Item WHERE storeId="+storeId+" ORDER BY price" ;
		else 
			sql1="SELECT * FROM Item WHERE storeId="+storeId+" ORDER BY (price*sale)" ;
		
		ResultSet rs = stmt.executeQuery(sql1);
		while(rs.next())
		{
			String type=rs.getString("type");
			String color=rs.getString("color");
			int quantity=rs.getInt("quantity");
			double price=rs.getDouble("price");
			Blob image=rs.getBlob("image");
			double sale=rs.getDouble("sale");
			msg+=type+"%"+color+"%"+quantity+"%"+price+"%"+image+"%"+sale*price+"\n";
			
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
	System.out.println(msg);
	return msg;	


}

	
public boolean discount (int storeId,double d)

{
	

	Connection conn = null;
	try {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

	
   String sql = "UPDATE Item SET sale=?  WHERE storeId =?";
	PreparedStatement update= conn.prepareStatement(sql);

	update.setDouble(1, d);
	update.setLong(2, storeId);

	update.executeUpdate();

    
    }
	catch (SQLException se) {
		
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
        System.out.println("SQLState: " + se.getSQLState());
        System.out.println("VendorError: " + se.getErrorCode());
        return false;
	}

return true;
	
}
	

	
	

}







	


 