package src.main.java.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import src.main.java.application.ConnectController;

public class chainWorkerController  implements Initializable {

	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private AnchorPane choosItemToEdit;

	    @FXML
	    private CheckBox addItemBox;

	    @FXML
	    private TextField addcolor;

	    @FXML
	    private TextField addprice;

	    @FXML
	    private TextField addname;

	    @FXML
	    private Button AddItemButten;

	    @FXML
	    private TextField DeletId;

	    @FXML
	    private Button DeletItemButten;

	    @FXML
	    private CheckBox AddItemBox;

	    @FXML
	    private CheckBox EditItem;

	    @FXML
	    private TextField editid;

	    @FXML
	    private TextField editsale;

	    @FXML
	    private TextField editname;

	    @FXML
	    private TextField editcolor;

	    @FXML
	    private Button EditItemButten;

	    @FXML
	    private Button addimage;

	    @FXML
	    private TextField email;

	    @FXML
	    private Button logout;

	    @FXML
	    private Label fill;

	    @FXML
	    private TextField editprice;

	    @FXML
	    private TextField onsale;

	    @FXML
	    private TextField store;

	    @FXML
	    private TextField quantiy;

	    @FXML
	    private TextField editstore;

	    @FXML
	    private TextField editquanity;


    
    public void SeTEmail(String theEmail) 
    {
		// TODO Auto-generated method stub
		email.setText(theEmail);
	}
    @FXML
    void DeletItem(ActionEvent event) throws IOException 
    {
    	String message = "DeletItem#" + DeletId.getText();
    	ConnectController.client.handleMessageFromClientUI(message);
		if ("Delet".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "Item Delet Successfully!! ");

		} else if ("NotDelet".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "Item don't delet!!");

		}


    }


    @FXML
    void EditItem(ActionEvent event)  throws IOException {
    	String message = "EditItem#" +editid.getText()+ editstore.getText() + "#"+ editcolor.getText() + "#" + editquanity.getText()+ "#"+  editprice.getText() + "#" + editprice.getText() + "#"
				+editsale.getText();
    	ConnectController.client.handleMessageFromClientUI(message);
		JOptionPane.showMessageDialog(null, ConnectController.client.servermsg);

    }

    @FXML
    void addItem(ActionEvent event) throws IOException
    {
    	String message = "AddItem#" +store.getText() + "#"+ addcolor.getText() + "#" + quantiy.getText()+ "#"+  addprice.getText() + "#" + addname.getText() + "#"
				+onsale.getText() +"#"+ addimage.getAccessibleText();
    	ConnectController.client.handleMessageFromClientUI(message);
		JOptionPane.showMessageDialog(null, ConnectController.client.servermsg);

    }

    @FXML
    void enableAddItem(ActionEvent event) 
    {
    	addcolor.setDisable(false);
    	addprice.setDisable(false);
    	addname.setDisable(false);
    	addimage.setDisable(false);
    	AddItemButten.setDisable(false);
    	quantiy.setDisable(false);
    	store.setDisable(false);
    	onsale.setDisable(false);

    }

    @FXML
    void enableDelet(ActionEvent event) 
    {
    	DeletId.setDisable(false);
    	DeletItemButten.setDisable(false);

    }

    @FXML
    void enableEdit(ActionEvent event) 
    {
    	fill.setDisable(false);
    	editid.setDisable(false);
    	editprice.setDisable(false);
    	editsale.setDisable(false);
    	editname.setDisable(false);
    	editname.setDisable(false);
    	EditItemButten.setDisable(false);
    	editstore.setDisable(false);
    	editquanity.setDisable(false);
    	

    }

  
    @FXML
    void LogOut(ActionEvent event) throws IOException 
    {
    		String message = "SignOut#" + email.getText();
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
	
   
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}