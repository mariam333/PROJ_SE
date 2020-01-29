package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Order {

	int orderId;
	int storeId;
	int shopperId;
	double totalPrice;
	int delivery;
	double supTime;
	String orderingTime;
	String deliveryAddress;
	String reciptionName;
    int doneOrder;
    String date;
    
    static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static private final String DB = "7VP6RBaQoU";
    static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
    static private final String USER = "7VP6RBaQoU";
    static private final String PASS = "ov97FOeUst";
 
    public Order()
    {
    	
    }
 public Order(int storeId,	int shopperId,double totalPrice,int delivery,double supTime,String orderingTime	,String deliveryAddress,String reciptionName, int doneOrder, String date) 
 {
	 
	 this.storeId=storeId;
     this.delivery=delivery;
     this.supTime=supTime;
	 this.totalPrice=totalPrice;
	 this.orderingTime=orderingTime;
	 this.deliveryAddress=deliveryAddress;
	 this.reciptionName=reciptionName;
	 this.doneOrder=doneOrder;
	 this.date=date;
     this.shopperId=shopperId;
     
	 
 }
 
 public void createOrder(ArrayList <Integer>a1 ,ArrayList<Sold> a2) throws ClassNotFoundException {
 System.out.println("OK");
 	Connection conn = null;
	Statement stmt = null;
	String MsgtoClient="";
	 
	

	 try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			// Insert the new Order 
			
			String s= "INSERT INTO `Order` (`storeId`, `shopperId`, `totalPrice`, `delivery`, `supTime`, `orderingTime`, `deliveryAddress`, `reciptionName`, `doneOrder`, `date`) VALUES(?,?,?,?,?,?,?,?,?,?)";
				//PreparedStatement update= conn.prepareStatement("INSERT INTO Order (storeId,shopperId,totalPrice,delivery,supTime,orderingTime,deliveryAddress,reciptionName,doneOrder,date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?) ");
				PreparedStatement update= conn.prepareStatement(s);

				update.setLong(1, this.storeId);
				update.setLong(2, this.shopperId);
				update.setDouble(3, this.totalPrice);
				update.setLong(4, this.delivery);
				update.setDouble(5, this.supTime);
				update.setString(6, this.orderingTime);
				update.setString(7, this.deliveryAddress);
				update.setString(8, this.reciptionName);
				update.setLong(9, this.doneOrder);
				update.setString(10, this.date);
				
				update.executeUpdate();
				
				// getting the orderId that we just insert
String q="SELECT MAX(orderId) FROM `Order`"; 

ResultSet rs=stmt.executeQuery(q);
int ID=7793;
if(rs.next()) {
ID=rs.getInt("MAX(orderId)");
	System.out.println(ID);
}
			
				 int i=0;
			while(i!=a1.size()) {
				int x=a1.get(i);
				int y=a1.get(i+1);
				int Q=0;
				int S=0;
				// adding to the soldItem which are item in the catalog
	   String sql2 = "INSERT INTO SoldItem (itemId,storeId, shopperId,orderId,quantity) VALUES (?, ?, ?,?,?)";
	
	 update= conn.prepareStatement(sql2);
			update.setLong(2, this.storeId);
			update.setLong(3, this.shopperId);
			update.setLong(4, ID);
			update.setLong(1, x);  // id
			update.setLong(5, y); // quantity 
			update.executeUpdate();

			i+=2;	
		
			String sql3="SELECT quantity,soldQuantity FROM Item WHERE itemId="+x+" ";
			ResultSet RS2 = stmt.executeQuery(sql3);
			if(RS2.next()) {
				Q=RS2.getInt("quantity");
				S=RS2.getInt("soldQuantity");
				
			}

			
			String sql4="UPDATE Item SET quantity=?,soldQuantity=? WHERE itemId =?";
		
			PreparedStatement update2= conn.prepareStatement(sql4);
			update2.setInt(1, Q-y);
			update2.setInt(2, S+y);
			update2.setInt(3,x);
			update2.executeUpdate();

	 }
			i=0;
		
			while(i!=a2.size())
			{
				   String sql3 = "INSERT INTO Sold (storeId,orderId,shopperId,type,dominantColor,priceRange) VALUES (?, ?, ?,?,?,?)";
					Sold S=a2.get(i);
					
					        update= conn.prepareStatement(sql3);
							update.setLong(1, this.storeId);
							update.setLong(2, ID);
							update.setLong(3, this.shopperId);
							update.setString(4, S.type);
							update.setString(5, S.dominantColor);
							update.setString(6, S.priceRange);

							update.executeUpdate();
				i++;
			
			}
	 
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
				System.out.println("44444");
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

}
 
 
 
 public  void deleteOrder (int id,double add,int idS) throws ClassNotFoundException
{
	Connection conn = null;
	Statement stmt = null;
	double r=0;

	try {
		
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		
String s="UPDATE `Order` SET doneOrder="+2+" WHERE `Order`.`orderId` = ?";
PreparedStatement update= conn.prepareStatement(s);
update.setLong(1, id);
update.executeUpdate();

String sql3="SELECT refund FROM Shopper WHERE shopperID="+idS+"";
ResultSet Rs = stmt.executeQuery(sql3);
if(Rs.next()) {
	r=Rs.getDouble("refund");
}


String sql4="UPDATE Shopper SET refund=? WHERE shopperID="+idS+"";

PreparedStatement update2= conn.prepareStatement(sql4);
update2.setDouble(1, r+add);

update2.executeUpdate();



	/*
   String sql = "DELETE FROM `Order` WHERE `Order`.`orderId` = ?";
 
   PreparedStatement u= conn.prepareStatement(sql);

  
	u.setLong(1, id);
	u.executeUpdate();
*/
    
    }
	catch (SQLException se) {
		
		se.printStackTrace();
		System.out.println("SQLException: " + se.getMessage());
        System.out.println("SQLState: " + se.getSQLState());
        System.out.println("VendorError: " + se.getErrorCode());
    
	}


}
 
 
 public String viewOrders(int ShopperId) throws ClassNotFoundException
 {
	 String MyOrder="";
	 
	 Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
	 String sql="SELECT * FROM `Order` WHERE shopperId ="+ShopperId+"";
		ResultSet Rs = stmt.executeQuery(sql);
		while(Rs.next())
		{
		
			MyOrder+=Rs.getInt("orderId")+"%"+Rs.getDouble("totalPrice")+"%"+Rs.getInt("doneOrder")+"%"+Rs.getString("date")+"\n";
		
		}
		}
		
		catch (SQLException se) {
			
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	    
		}
	 
	 return MyOrder;
	 
 }
 public String Arrived(int orderId,String shopperEmail) throws ClassNotFoundException
 {
	 String msg="";
	 Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
	String s="UPDATE `Order` SET doneOrder="+1+" WHERE `Order`.`orderId` = ?";
	PreparedStatement update= conn.prepareStatement(s);
	update.setLong(1, orderId);
	update.executeUpdate();
	
	SendEmail E=new SendEmail(shopperEmail,"Update--->order number:"+orderId+"");
	E.sendMail(shopperEmail,"Updtae About your order :"+orderId+"");
	 
	    }
		catch (SQLException se) {
			
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	    
		}
	
	
	return msg;
	
 
 }
 
 
 public String viewAllOrders() throws ClassNotFoundException    /* dont work!! */
 {
	 
	 System.out.println("KK");
	 String Orders="";
	 Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
	        String sql="SELECT `orderId`, `storeId`, `shopperId`, `totalPrice`, `delivery`, `supTime`, `orderingTime`, `deliveryAddress`, `reciptionName`, `doneOrder`, `date` FROM `Order` ORDER BY `Order`.`doneOrder`";
	 		
		ResultSet Rs = stmt.executeQuery(sql);
		while(Rs.next())
		{
		
			Orders+=Rs.getInt("orderId")+"%"+Rs.getInt("storeId")+"%"+Rs.getInt("shopperId")+"%"+Rs.getDouble("totalPrice")+"%"+Rs.getInt("delivery")+"%"+Rs.getDouble("supTime")+Rs.getString("orderingTime")+"%"+Rs.getString("deliveryAddress")+"%"+Rs.getString("reciptionName")+"%"+Rs.getInt("doneOrder")+"%"+Rs.getString("date")+"\n";
		System.out.println(Orders);
		}
		 
		return Orders;
	
		}
		
		catch (SQLException se) {
			
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	    
		}
	 
	return Orders;
	 
	 
	
	
	
	 
 }
 
 
 
 }
			
		
 
 
 
 
 
 

