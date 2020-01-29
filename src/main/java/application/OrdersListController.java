/**
 * Sample Skeleton for 'OrdersList.fxml' Controller Class
 */

package src.main.java.application;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import src.main.java.application.ItemCatalogClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OrdersListController implements Initializable{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Back2CatalogBtn"
    private Button Back2CatalogBtn; // Value injected by FXMLLoader

    @FXML // fx:id="CanselOrderBtn"
    private Button CanselOrderBtn; // Value injected by FXMLLoader

    @FXML // fx:id="OrdersTable"
    private TableView<Order> OrdersTable; // Value injected by FXMLLoader

    @FXML // fx:id="OrderIdCol"
    private TableColumn<Order, Integer> OrderIdCol; // Value injected by FXMLLoader

    @FXML // fx:id="DateCol"
    private TableColumn<Order, String> DateCol; // Value injected by FXMLLoader

    @FXML // fx:id="StatusCol"
    private TableColumn<Order, String> StatusCol; // Value injected by FXMLLoader
    
    @FXML
    private TableColumn<Order, Double> totalprisecol;
    
    
    
    private String Email;
    private String Name;
    ObservableList<Order> Orders = FXCollections.observableArrayList();
    public void setEmail(String myEmail) {
		Email=myEmail;
		
	}
	public void setName(String name) {
		Name=name;
		
	}
	
	
	public void viewTable() {
    	String msg = "ViewOrder%" + shoperID; //get Items sorted by sale wich is the def'
		ConnectController.client.handleMessageFromClientUI(msg);
		String Msg="";
		Msg=ConnectController.client.servermsg;
			String[] Msg1 = Msg.split("/n",-1);
			Order order = new Order();
			for(String a : Msg1) {
				String[] orderstring = a.split("%",4);
				order.setID(orderstring[0]);
				order.setTotal(Double.valueOf(orderstring[1]));
				order.setStatus(orderstring[2]);
				order.setPurchesTime(orderstring[3]);
				
				Orders.add(order);
			}
		
		/* add column to the tableview and set its items */
		OrdersTable.getColumns().add(OrderIdCol);
		OrdersTable.getColumns().add(DateCol);
		OrdersTable.getColumns().add(StatusCol);
		OrdersTable.getColumns().add(totalprisecol);
        
	}
	
	
	
	 private String shoperID;
	    public String getShoperID() {
	    		String message = "ShowShoperID#"+Email;
	    		ConnectController.client.handleMessageFromClientUI(message);
	    		if (ConnectController.client.servermsg != null ) {
	    			shoperID=ConnectController.client.servermsg;
	    		
	    	}
	    	return shoperID;
	    }

    @FXML
    void Back2catalog(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		HomePageController home = loader.getController();
		home.setEmail(Email);
		home.setName(Name);
		Scene SignUp = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(SignUp);
		app_stage.show();

    }

    @FXML
    void CanselOrder(ActionEvent event) {
    	ObservableList<Order> allItems,singalItem;
    	allItems= OrdersTable.getItems();
    	singalItem= OrdersTable.getSelectionModel().getSelectedItems();
    	Date ptime=singalItem.get(0).getPurchesTime();
    	long time=ptime.getTime();
    	time=TimeUnit.MILLISECONDS.toHours(time);
    	if(time <= 3) {
    		int i=0;
    		while(!singalItem.isEmpty()) {
    		singalItem.remove(i);
    		i++;
    		}
    		if(time <= 2) {//get's 100% refund
    			//deletorder%itemid%refund
    			Double total=Double.valueOf(singalItem.get(0).getMoney());
    			//total=total/2;
    			String msg="DeletOrder#" +singalItem.get(0).getID() +"#"+Double.toString(total) + "#" + shoperID;
    			ConnectController.client.handleMessageFromClientUI(msg);
    			
    		}else {// cancel Order without refund
    			Double total=Double.valueOf(singalItem.get(0).getMoney());
    			total=total/2;
    			String msg="DeletOrder#" +singalItem.get(0).getID() +"#"+Double.toString(total) + "#" + shoperID;
    			ConnectController.client.handleMessageFromClientUI(msg);
    			
    		}
    	}else {// send message you can't cancel
			String msg="DeletOrder#" +singalItem.get(0).getID() +"#0" + "#" + shoperID;
			ConnectController.client.handleMessageFromClientUI(msg);
			JOptionPane.showMessageDialog(null, "you can't cancle the order");
    		
    	}

    }
    
    
    


    @FXML
    void EnableCansel(MouseEvent event) {
    	ObservableList<Order> allItems,singalItem;
    	allItems= OrdersTable.getItems();
    	singalItem= OrdersTable.getSelectionModel().getSelectedItems();
    	Date ptime=singalItem.get(0).getPurchesTime();
    	long time=ptime.getTime();
    	time=TimeUnit.MILLISECONDS.toHours(time);
    	if(time <= 3) {
    		CanselOrderBtn.setDisable(false);
    	}

    }
    OrderIdCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, String>("ID"));
	DateCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, String>("PurchesTime"));
	StatusCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, Double>("status"));
	totalprisecol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, ImageView>("total"));
   
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	  assert Back2CatalogBtn != null : "fx:id=\"Back2CatalogBtn\" was not injected: check your FXML file 'OrdersList.fxml'.";
          assert CanselOrderBtn != null : "fx:id=\"CanselOrderBtn\" was not injected: check your FXML file 'OrdersList.fxml'.";
          assert OrdersTable != null : "fx:id=\"OrdersTable\" was not injected: check your FXML file 'OrdersList.fxml'.";
          assert OrderIdCol != null : "fx:id=\"OrderIdCol\" was not injected: check your FXML file 'OrdersList.fxml'.";
          assert DateCol != null : "fx:id=\"DateCol\" was not injected: check your FXML file 'OrdersList.fxml'.";
          assert StatusCol != null : "fx:id=\"StatusCol\" was not injected: check your FXML file 'OrdersList.fxml'.";
          assert totalprisecol != null : "fx:id=\"totalprisecol\" was not injected: check your FXML file 'OrdersList.fxml'.";

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		
	}
}