package src.main.java.application;
import src.main.java.application.ConnectController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MyComplaintController implements Initializable {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="shopperId"
    private TextField shopperId; // Value injected by FXMLLoader

    @FXML // fx:id="NameOfShoop"
    private Label NameOfShoop; // Value injected by FXMLLoader

    @FXML // fx:id="Date"
    private TextField Date; // Value injected by FXMLLoader

    @FXML // fx:id="replyy"
    private Label replyy; // Value injected by FXMLLoader

    @FXML // fx:id="show"
    private Button show; // Value injected by FXMLLoader

    @FXML // fx:id="TimeComp"
    private Label TimeComp; // Value injected by FXMLLoader

    @FXML // fx:id="topic"
    private Label topic; // Value injected by FXMLLoader
    String MyEmail = null;
    public void SeTEmail(String theEmail) 
	{
		// TODO Auto-generated method stub
		MyEmail = theEmail;
	}

    @FXML
    void back(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerProfile.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		CustomerProfileController employee = loader.getController();
		employee.SeTEmail(MyEmail);
		Scene regist = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(regist);
		app_stage.show();
    }

    @FXML
    void showComplaint(ActionEvent event) 
    {
    	String message = "ShowComplaintBycomplaintIDAndDate#"+shopperId.getText() + "#" + Date.getText();
 		ConnectController.client.handleMessageFromClientUI(message);
 		if ("ShowComplaintBycomplaintIDAndDate".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "Item Added Successfully!! ");
        String[] details = ConnectController.client.servermsg.split("#");
 		NameOfShoop.setText(details[3]);
 		TimeComp.setText(details[5]);
     	topic.setText(details[6]);
     	replyy.setText(details[8]);
		}
 		else if ("ShowComplaintBycomplaintIDAndDateCant".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "complaint don't add!!");

		}
    }

    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {
		// TODO Auto-generated method stub

	}
 

  
}
