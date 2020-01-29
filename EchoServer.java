import java.util.ArrayList;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ocsf.server.*;
import common.*;
import models.SendEmail;
import models.Person;
import models.Shopper;
import models.Sold;
import models.Item;
import models.Order;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the website:
	// https://remotemysql.com/
	// in future move these hard coded strings into separated config file or even better env variables
	static private final String DB = "7VP6RBaQoU";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "7VP6RBaQoU";
	static private final String PASS = "ov97FOeUst";
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT =5555;
  
  /**
   * The interface type variable. It allows the implementation of 
   * the display method in the client.
   */
  ChatIF serverUI;
  
  int lastChange =0;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

   /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * @param serverUI The interface type variable.
   */
  public EchoServer(int port, ChatIF serverUI) throws IOException
  {
    super(port);
    this.serverUI = serverUI;
	Connection conn = null;
	Statement stmt = null;
	try {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	} catch (SQLException se) {
				se.printStackTrace();
				System.out.println("SQLException: " + se.getMessage());
				System.out.println("SQLState: " + se.getSQLState());
				System.out.println("VendorError: " + se.getErrorCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
  }

  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {

    if (msg.toString().startsWith("#login "))
    {
      if (client.getInfo("loginID") != null)
      {
        try
        {
          client.sendToClient("You are already logged in.");
        }
        catch (IOException e)
        {
        }
        return;
      }
      client.setInfo("loginID", msg.toString().substring(7));
    }
    else 
    {
      if (client.getInfo("loginID") == null)
      {
        try
        {
          client.sendToClient("You need to login before you can chat.");
          client.close();
        }
        catch (IOException e) {}
        return;
      }
      
		String email = null;
		String pass = null;
		String firstname = null;
		String lastname = null;
		String tel = null;
		String visa = null;
		String cvv = null;
		String[] detail = ((String) msg).split(" ");
		
		System.out.println(msg);
		String command = detail[0];
		System.out.println(command);
		switch (command) {
		//SignUp mar 123 mar@gmail.com 0529761893 
		case "SignUp":
			
			
			Shopper shopper=new Shopper(detail[1],detail[2],detail[3],Integer.parseInt(detail[4]),Integer.parseInt(detail[5]),Integer.parseInt(detail[6]),Integer.parseInt(detail[7]));
			try {
				if (shopper.addShopper() == true) {
					this.handleMessageFromServerUI("SignUp");
					break;

				} else {
					this.handleMessageFromServerUI("SignUpFailed");
					break;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "ShowShopper":
			String msgToClient= Shopper.ShowShopper(Integer.parseInt(detail[1]));
			if (msgToClient!=null) {
				msgToClient="ShowShopper%"+msgToClient;
				System.out.println(msgToClient);
				this.handleMessageFromServerUI(msgToClient);
				break;
			}else {
				this.handleMessageFromServerUI("CantShowShopper");
				break;
			}
			
		case "AddItem":
			//Item item=new Item(Integer.parseInt(detail[1]),detail[2],Integer.parseInt(detail[3]),Double.parseDouble(detail[4]),detail[5],detail[6]);
			Item item=new Item(Integer.parseInt(detail[1]),detail[2],Integer.parseInt(detail[3]),Double.parseDouble(detail[4]),detail[5]);
			try {
				boolean flag=item.addItem();
				if(flag)
				this.handleMessageFromServerUI("Item has been Added");
				else 
					this.handleMessageFromServerUI("Item already exist");
				break;

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				this.handleMessageFromServerUI("erroe adding item!");
				e.printStackTrace();
				break;
			}
			
		case "DeleteItem":
			
			Item item1=new Item();
			try {
				item1.deleteItem(Integer.parseInt(detail[1]));
				this.handleMessageFromServerUI("Item has been deleted");
				break;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				this.handleMessageFromServerUI("error deleting item");
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				this.handleMessageFromServerUI("error deleting item");
				e.printStackTrace();
				break;
			}
			
		case "EditItem":
			int ItemId=Integer.parseInt(detail[1]);
			Item item2=new Item(0,detail[2],Integer.parseInt(detail[3]),Double.parseDouble(detail[4]),detail[5]);
			try {
				item2.editItem(ItemId);
				this.handleMessageFromServerUI("Item has been updated");
				break;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.handleMessageFromServerUI("error updating item");
				break;
			}
			
		case "ViewItem":
			Item item3=new Item();
			try {
				String itemToclient=item3.viewItem(Integer.parseInt(detail[1]));
				System.out.println(itemToclient);
				this.handleMessageFromServerUI(itemToclient);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.handleMessageFromServerUI("error viewing item");
				break;
			} catch (ClassNotFoundException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.handleMessageFromServerUI("error viewing item");
				break;
			}

			
	case "ShowCatalog":
		Item item4=new Item();
		try {
			String catalogToclient=item4.showCatalog(Integer.parseInt(detail[1]),detail[2].charAt(0));
			System.out.println(catalogToclient);
			this.handleMessageFromServerUI(catalogToclient);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.handleMessageFromServerUI("error viewing item");
			break;
		}
		
		
	case "Discount":
		Item item5=new Item();
		try {
			String s;
			boolean f=item5.discount(Integer.parseInt(detail[1]),Double.parseDouble(detail[2]));
			if(f) {  s="item discounted";
			System.out.println(s);
			}
			else 
				s="error";
				
			this.handleMessageFromServerUI(s);
            break;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.handleMessageFromServerUI("error viewing item");
			break;
			
		}
		
	
	
	case "CreateOrder":
		int storeId=Integer.parseInt(detail[1]);
		int shoppedId=Integer.parseInt(detail[2]);
	    String date=detail[3];
		String orderingTime=detail[4];
		int delivery=Integer.parseInt(detail[5]);
		String deliveryAddress=detail[6];
		String reciptionName=detail[7];
		double supTime=Double.parseDouble(detail[8]);
		double totalPrice=Double.parseDouble(detail[9]);

		Order O=new Order(storeId,shoppedId,totalPrice,delivery,supTime, orderingTime, deliveryAddress,reciptionName,0, date);
		
			try {
				ArrayList<Integer> ItemsId = new ArrayList<Integer>();
				int i=10;
			
				while((detail[i]).indexOf('@')!=0 && i!=detail.length) {
					
					ItemsId.add(Integer.parseInt(detail[i]));   //adding id and next index quantity
				ItemsId.add(Integer.parseInt(detail[i+1]));
					i+=2;

				}
				System.out.println(i);
				i++;
				ArrayList<Sold> soldItem=new ArrayList<Sold>();

				while(i!=detail.length)
				{
				
					String type=detail[i];
					String dominantColor=detail[i+1];
					String priceRange=detail[i+2];
					
					
					Sold I=new Sold(type,dominantColor,priceRange);
					soldItem.add(I);
					i+=3;
					
				}
				
				O.createOrder(ItemsId,soldItem);
			
				break;
			}
			
			catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				break;
			}
	
		
	case "DeleteOrder":
		Order o2=new Order();
			try {  
				
				o2.deleteOrder(Integer.parseInt(detail[1]), Double.parseDouble(detail[2]),Integer.parseInt(detail[3]));
			break;
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
	
	
		
		case "ViewOrder":
		Order Or=new Order();
		String Mes="";
		try {
		 try {
			Mes=Or.viewOrders(Integer.parseInt(detail[1]));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println(Mes);
			this.handleMessageFromServerUI(Mes);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.handleMessageFromServerUI("error viewing item");
			break;
		}
		
		
		case "ViewAllOrders":
			String mseg="";
			Order o=new Order();
			try {
				msg=o.viewAllOrders();
				this.handleMessageFromServerUI(mseg);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				this.handleMessageFromServerUI("error viewing Orders ");
				e.printStackTrace();
				break;
			}
			
			
		case "Arrived":
			try {
				Order O5=new Order();
				O5.Arrived(Integer.parseInt(detail[1]), detail[2]); // OrderId ,ShopperEmail
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		}
    }
    
    


//			if(msg.toString().charAt(0)=='S')
//			{
//				
//	    
//			String sql = "SELECT * FROM catalog";
//			ResultSet rs = stmt.executeQuery(sql);
//			 msg=" ";
//			while (rs.next()) {
//		
//				int num = rs.getInt("num");
//				String type = rs.getString("type");
//				String color = rs.getString("color");
//				int price = rs.getInt("price");
//				//System.out.format("Number %5s Type %15s color %18s Price %d\n", num, type, color,price);
//				String curr= "Number: " + num+ " type: " + type + " Color: " + color + " price: " + price + "\n";
//				msg=msg+curr;
//			}
//		     client.setInfo("", msg);
//
//		}
//			else if(msg.toString().charAt(0)=='U')
//			{
//				String k=" updated price by num";
//				client.setInfo("", k);
//			String [] str=msg.toString().split(" ");
//
//			String sql="SELECT * FROM  catalog";
//			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//			ResultSet rs = st.executeQuery(sql);
//			PreparedStatement update= conn.prepareStatement("UPDATE catalog SET price=? WHERE num =?");
//			
//
//				int newPrice=Integer.parseInt(str[2]);
//				int num=Integer.parseInt(str[1]);
//				lastChange=num;
//				update.setInt(1,newPrice );
//				update.setInt(2,num );
//				update.executeUpdate();
//			
//			}
//			
//			else if(msg.toString().charAt(0)=='P')
//			{
//				
//				 msg=" ";
//				System.out.println("laaast change num =" + lastChange);
//			
//				if (lastChange==0) {
//				msg=" no update yet";
//				 client.setInfo("", msg);
//				}
//				else {
//					String [] str=msg.toString().split(" ");
//					String sql="SELECT * FROM  catalog WHERE num ="+ lastChange ;
//					System.out.println(sql);
//
//					ResultSet rs = stmt.executeQuery(sql);
//					while (rs.next()) {
//						int num = rs.getInt("num");
//						String type = rs.getString("type");
//						String color = rs.getString("color");
//						int price = rs.getInt("price");
//						String curr= "Number: " + num+ " type: " + type + " Color: " + color + " price: " + price + "\n";
//						msg=msg+curr;
//					}
//					 client.setInfo("", msg);
//				}
//				
//			}
//
//		}
//		catch (SQLException se) {
//		
//			se.printStackTrace();
//			System.out.println("SQLException: " + se.getMessage());
//            System.out.println("SQLState: " + se.getSQLState());
//            System.out.println("VendorError: " + se.getErrorCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (stmt != null)
//					stmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			}
//		}
//    
//      this.sendToAllClients(client.getInfo("loginID") + "> " + msg);
//    }
  }

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI
   */
  public void handleMessageFromServerUI(String message)
  {
    if (message.charAt(0) == '#')
    {
      runCommand(message);
    }
    else
    {
      // send message to clients
      serverUI.display(message);
      this.sendToAllClients("SERVER MSG> " + message);
    }
  }

  /**
   * This method executes server commands.
   *
   * @param message String from the server console.
   */
  private void runCommand(String message)
  {
    // run commands
    // a series of if statements

    if (message.equalsIgnoreCase("#quit"))
    {
      quit();
    }
    else if (message.equalsIgnoreCase("#stop"))
    {
      stopListening();
    }
    else if (message.equalsIgnoreCase("#close"))
    {
      try
      {
        close();
      }
      catch(IOException e) {}
    }
    else if (message.toLowerCase().startsWith("#setport"))
    {
      if (getNumberOfClients() == 0 && !isListening())
      {
        // If there are no connected clients and we are not 
        // listening for new ones, assume server closed.
        // A more exact way to determine this was not obvious and
        // time was limited.
        int newPort = Integer.parseInt(message.substring(9));
        setPort(newPort);
        //error checking should be added
        serverUI.display
          ("Server port changed to " + getPort());
      }
      else
      {
        serverUI.display
          ("The server is not closed. Port cannot be changed.");
      }
    }
    else if (message.equalsIgnoreCase("#start"))
    {
      if (!isListening())
      {
        try
        {
          listen();
        }
        catch(Exception ex)
        {
          serverUI.display("Error - Could not listen for clients!");
        }
      }
      else
      {
        serverUI.display
          ("The server is already listening for clients.");
      }
    }
    else if (message.equalsIgnoreCase("#getport"))
    {
      serverUI.display("Currently port: " + Integer.toString(getPort()));
    }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  /**
   * Run when new clients are connected. Implemented by Benjamin Bergman,
   * Oct 22, 2009.
   *
   * @param client the connection connected to the client
   */
  protected void clientConnected(ConnectionToClient client) 
  {
    // display on server and clients that the client has connected.
    String msg = "A Client has connected";
    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * Run when clients disconnect. Implemented by Benjamin Bergman,
   * Oct 22, 2009
   *
   * @param client the connection with the client
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client)
  {
    // display on server and clients when a user disconnects
    String msg = client.getInfo("loginID").toString() + " has disconnected";

    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * Run when a client suddenly disconnects. Implemented by Benjamin
   * Bergman, Oct 22, 2009
   *
   * @param client the client that raised the exception
   * @param Throwable the exception thrown
   */
  synchronized protected void clientException(
    ConnectionToClient client, Throwable exception) 
  {
    String msg = client.getInfo("loginID").toString() + " has disconnected";

    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * This method terminates the server.
   */
  public void quit()
  {
    try
    {
      close();
    }
    catch(IOException e)
    {
    }
    System.exit(0);
  }


  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class