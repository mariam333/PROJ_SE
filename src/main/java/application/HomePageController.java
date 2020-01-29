/**
 * Sample Skeleton for 'HomePage.fxml' Controller Class
 */

package src.main.java.application;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.glass.ui.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;


public class HomePageController implements Initializable  { 

@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="SortBy"
    private MenuButton SortBy; // Value injected by FXMLLoader

    @FXML // fx:id="priceSort"
    private MenuItem priceSort; // Value injected by FXMLLoader

    @FXML // fx:id="NameSort"
    private MenuItem NameSort; // Value injected by FXMLLoader

    @FXML // fx:id="login"
    private Button login; // Value injected by FXMLLoader

    @FXML // fx:id="SignUp"
    private Button SignUp; // Value injected by FXMLLoader

    @FXML // fx:id="Search"
    private Button Search; // Value injected by FXMLLoader

    @FXML // fx:id="searchText"
    private TextField searchText; // Value injected by FXMLLoader

    @FXML // fx:id="go2Profile"
    private Button go2Profile; // Value injected by FXMLLoader

    @FXML // fx:id="CreatOrder"
    private Button CreatOrder; // Value injected by FXMLLoader

    @FXML // fx:id="SignOut"
    private Button SignOut; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTxt"
    private Label usernameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="catalog"
    private TableView<ItemCatalogClient> catalog; // Value injected by FXMLLoader
   
    @FXML // fx:id="NameCol"
    private TableColumn<ItemCatalogClient, String> NameCol; // Value injected by FXMLLoader

    @FXML // fx:id="colorCol"
    private TableColumn<ItemCatalogClient, String> colorCol; // Value injected by FXMLLoader
    
    @FXML
    private TableColumn<ItemCatalogClient, String> TypeCol;

    @FXML // fx:id="priceCol"
    private TableColumn<ItemCatalogClient, Double> priceCol; // Value injected by FXMLLoader


    @FXML // fx:id="pictureCol"
    private TableColumn<ItemCatalogClient, ImageView> pictureCol; // Value injected by FXMLLoader
    @FXML // fx:id="saleCol"
    private TableColumn<ItemCatalogClient, Double> saleCol; // Value injected by FXMLLoader
    @FXML
    private MenuItem mainStore;

    @FXML
    private MenuItem HaifaStore;
    
    @FXML
    private Button GoToCartBtn;

    @FXML // fx:id="choseStore"
    private MenuButton choseStore; // Value injected by FXMLLoader
   // private int counter;
  //These instance variables are used to create new Item objects
    @FXML private TextField Number;
    @FXML private TextField ItemName;
    @FXML private TextField color;
    @FXML private TextField price;
    @FXML private ImageView image;
    
    String Email ="";
    String Name="";
    String ShoperID="";
    public void setEmail(String email) {
    	Email=email;
    }
    public void setName(String name) {
    	Name=name;
    }
    public String getShoperID() {
    	if(isLogedIn()) {
    		String message = "ShowShoperID#"+Email;
    		ConnectController.client.handleMessageFromClientUI(message);
    		if (ConnectController.client.servermsg != null ) {
    			ShoperID=ConnectController.client.servermsg;
    		}
    		
    	}
    	return ShoperID;
    }
    
    String currentCatalog="main";
    
    ObservableList<ItemCatalogClient> Items = FXCollections.observableArrayList();
    
    @FXML
    void userClickedOnTable(MouseEvent event)throws IOException {//go to view Item and send an object Item
    	  FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("viewItem.fxml"));
          Parent tableViewParent = loader.load();
          
          Scene tableViewScene = new Scene(tableViewParent);
          
          //access the controller and call a method
          viewItemController controller = loader.getController();
          controller.initData(catalog.getSelectionModel().getSelectedItem());
          
          //This line gets the Stage information
          Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
          
          window.setScene(tableViewScene);
          window.show();
    	
    }
    public boolean isLogedIn() {//is the user logedin
    	return((Email != "") &&(((Email.contains("@hotmail.com")) || (Email.contains("@gmail.com")))));
    	
    }
    @FXML
    void goToCart(ActionEvent event) throws IOException{//goes to Order scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		OrderController order = loader.getController();
		order.setEmail(Email);
		order.setName(Name);
		order.setshoperID(getShoperID());
		if(currentCatalog == "main") {
			order.setstoreID("2");
		}else {
			order.setstoreID("1");
		}
		
		Scene profile = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(profile);
		app_stage.show();
    }
    
    @FXML
    void Go2Profile(ActionEvent event)throws IOException {

    		FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerProfile.fxml"));
    		AnchorPane root = (AnchorPane) loader.load();
    		CustomerProfileController profileC = loader.getController();
    		profileC.SeTEmail(Email);
    		Scene profile = new Scene(root);
    		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		app_stage.setScene(profile);
    		app_stage.show();
    	}

    @FXML
    void GoToLogIn(ActionEvent event) throws IOException {
    	if (isLogedIn()) {
    		JOptionPane.showMessageDialog(null, "You alredy have an acount");
    	}else {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		//CustomerProfileController home = loader.getController();
		Scene profile = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(profile);
		app_stage.show();
    	}
    }
    	

    @FXML
    void GoToSignUp(ActionEvent event) throws IOException{
    	if (isLogedIn()) {
    		JOptionPane.showMessageDialog(null, "You alredy have an acount");
    	}else {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Regist.fxml"));
    		AnchorPane root = (AnchorPane) loader.load();
    		//CustomerProfileController home = loader.getController();
    		Scene SignUp = new Scene(root);
    		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		app_stage.setScene(SignUp);
    		app_stage.show();
    	}

    }

    @FXML
    void SearchText(ActionEvent event) {

    }


    @FXML
    void go2creatOrder(ActionEvent event) throws IOException {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("YourDesign.fxml"));
    		AnchorPane root = (AnchorPane) loader.load();
    		YourDesign home = loader.getController();
    		home.SeTEmail(Email);
    		home.SeTEmail(Name);
    		Scene SignUp = new Scene(root);
    		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		app_stage.setScene(SignUp);
    		app_stage.show();
    	

    }
    
    @FXML
    void signOut(ActionEvent event) throws IOException{
		String message = "SignOut#" + Email;
		ConnectController.client.handleMessageFromClientUI(message);
		if (ConnectController.client.servermsg != null && "SignOut".equals(ConnectController.client.servermsg)) {
			JOptionPane.showMessageDialog(null, "You are loged out ");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			//HomePageController home = loader.getController();
			Scene regist = new Scene(root);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(regist);
			app_stage.show();
		}else {
			JOptionPane.showMessageDialog(null, "Can't SignOut ");
		}
    }
    
    
    @FXML
    void sortByName(ActionEvent event) {
    	viewTable(currentCatalog,"n");
    }
    @FXML
    void sortByPrice(ActionEvent event) {
    	viewTable(currentCatalog,"p");
    }

    @FXML
    void ViewMainCatalog(ActionEvent event) {
    	viewTable("main","b");
    	currentCatalog = "main";
    }
    @FXML
    void viewHaifaCatalog(ActionEvent event) {
    	viewTable("haifa","b");
    	currentCatalog = "haifa";
    }
    
 

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert SortBy != null : "fx:id=\"SortBy\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert priceSort != null : "fx:id=\"priceSort\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert NameSort != null : "fx:id=\"NameSort\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert SignUp != null : "fx:id=\"SignUp\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert Search != null : "fx:id=\"Search\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert searchText != null : "fx:id=\"searchText\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert go2Profile != null : "fx:id=\"go2Profile\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert CreatOrder != null : "fx:id=\"CreatOrder\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert SignOut != null : "fx:id=\"SignOut\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert GoToCartBtn != null : "fx:id=\"GoToCartBtn\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert catalog != null : "fx:id=\"catalog\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert NameCol != null : "fx:id=\"NameCol\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert colorCol != null : "fx:id=\"colorCol\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert priceCol != null : "fx:id=\"priceCol\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert TypeCol != null : "fx:id=\"TypeCol\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert pictureCol != null : "fx:id=\"pictureCol\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert choseStore != null : "fx:id=\"choseStore\" was not injected: check your FXML file 'HomePage.fxml'.";

    }
    
    public void viewTable(String store,String SortBy) {
    	String msg = "ShowCatalog%" + store +"%" + SortBy; //get Items sorted by sale wich is the def'
		ConnectController.client.handleMessageFromClientUI(msg);
		String Msg="";
		Msg=ConnectController.client.servermsg;
			String[] Msg1 = Msg.split("/n",-1);
			ItemCatalogClient item = new ItemCatalogClient();
			for(String a : Msg1) {
				String[] itemString = a.split("%",7);
				item.setItemId(Integer.parseInt(itemString[0]));
				item.setStoreId(Integer.parseInt(itemString[1]));
				item.setColor(itemString[2]);
				item.setQuantity(Integer.parseInt(itemString[3]));
				item.setPrice(Double.parseDouble(itemString[4]));
				item.setType(itemString[5]);
				item.setSale(Integer.parseInt((itemString[6] + "%")));
				ImageView i = new ImageView(itemString[7]);
				item.setImage(i);
				
				Items.add(item);
			}
		
		NameCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, String>("name"));
		colorCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, String>("color"));
		priceCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, Double>("price"));
		pictureCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, ImageView>("image"));
		saleCol.setCellValueFactory(new PropertyValueFactory<ItemCatalogClient, Double> ("sale"));
		
		/* add column to the tableview and set its items */
        catalog.getColumns().add(NameCol);
        catalog.getColumns().add(colorCol);
        catalog.getColumns().add(priceCol);
        catalog.getColumns().add(pictureCol);
        catalog.getColumns().add(saleCol);
        catalog.setItems(Items);
	}
    
    
	public void initialize(URL location, ResourceBundle resources) {
		if(isLogedIn()) {
			usernameTxt.setText(Name);
			usernameTxt.setVisible(true);
			SignOut.setVisible(true);
			go2Profile.setVisible(true);
			CreatOrder.setVisible(true);
		}
		viewTable("main","b");
		
	}
}