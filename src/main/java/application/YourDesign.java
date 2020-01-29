package src.main.java.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


import src.main.java.application.ConnectController;


public class YourDesign implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pro"
    private TextField pro; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TextField price; // Value injected by FXMLLoader

    @FXML // fx:id="color"
    private TextField color; // Value injected by FXMLLoader

    @FXML // fx:id="details"
    private TextField details; // Value injected by FXMLLoader

    @FXML // fx:id="submit"
    private Button submit; // Value injected by FXMLLoader

    String MyEmail = null;

	public void SeTEmail(String theEmail) {
		// TODO Auto-generated method stub
		MyEmail = theEmail;
	}
	
	@FXML
    void back(ActionEvent event) throws IOException 
    {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MyOrder.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		OrderController employee = loader.getController();
		//Image im = new Image("images/background.jpg");
		//employee.setimage(im);
		employee.setEmail(MyEmail);
		Scene regist = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(regist);
		app_stage.show();
	}
    @FXML
	void Create(ActionEvent event) throws IOException {
		ItemPersonalClient C=  new ItemPersonalClient(color.getText(),Double.parseDouble(price.getText()) , pro.getText());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		OrderController employee = loader.getController();
		employee.setEmail(MyEmail);
		Scene regist = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(regist);
		app_stage.show();
    }
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}