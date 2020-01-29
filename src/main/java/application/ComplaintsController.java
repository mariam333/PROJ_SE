package src.main.java.application;
/**
 * Sample Skeleton for 'Complaints.fxml' Controller Class
 */

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import src.main.java.application.ConnectController;


public class ComplaintsController  implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="shopperId"
    private TextField shopperId; // Value injected by FXMLLoader

    @FXML // fx:id="shopId"
    private TextField shopId; // Value injected by FXMLLoader

    @FXML // fx:id="Date"
    private TextField Date; // Value injected by FXMLLoader

    @FXML // fx:id="content"
    private TextField content; // Value injected by FXMLLoader

    @FXML // fx:id="submit"
    private Button submit; // Value injected by FXMLLoader

    @FXML // fx:id="time"
    private TextField time; // Value injected by FXMLLoader

    @FXML // fx:id="topic"
    private TextField topic; // Value injected by FXMLLoader

    @FXML // fx:id="Reply"
    private Label Reply; // Value injected by FXMLLoader
    
    String MyEmail = null;

	public void SeTEmail(String theEmail) 
	{
		// TODO Auto-generated method stub
		MyEmail = theEmail;
	}
	public void SetReply(String messageReply) 
	{
		Reply.setVisible(true);
		Reply.setText(messageReply);
		
	}
	

    @FXML
    void back(ActionEvent event) throws IOException 
    {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerProfile.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		CustomerProfileController employee = loader.getController();
		//Image im = new Image("images/background.jpg");
		//employee.setimage(im);
		employee.SeTEmail(MyEmail);
		Scene regist = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(regist);
		app_stage.show();
	}
    @FXML
    void MakeComplaint(ActionEvent event) 
    {
    	String message = "addComplaint#" + shopperId.getText() + "#" + shopId.getText() + "#" + Date.getText() + "#"
				+ time.getText() + "#"+ topic.getText() + "#" + content.getText();
		ConnectController.client.handleMessageFromClientUI(message);
		if ("AddComplaint".equals(ConnectController.client.servermsg)) {
			JOptionPane.showMessageDialog(null, "Your complaint has been registered");

		} else if ("NotAddComplaint".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "Something wrong!! ");

		}

    }

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
 
}
