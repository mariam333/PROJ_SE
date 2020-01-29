package src.main.java.application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import common.ChatIF;
import client.*;
import common.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ConnectController implements ChatIF, Initializable {
	static ChatClient client = null;

	@FXML // fx:id="image"
	private ImageView image; // Value injected by FXMLLoader

	@FXML // fx:id="host"
	private TextField host; // Value injected by FXMLLoader

	//public void setimage(Image im) {
		//image.setImage(im);
	//}

	@FXML
	void connectclient(ActionEvent event) throws IOException {

		if (host.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter host");

		} else {
			client = new ChatClient("kizlar", host.getText(), 5555, this);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			//HomePageController home = loader.getController();
			//Image im = new Image("images/white.jpg");
			//home.setimage(im);
			Scene regist = new Scene(root);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(regist);
			app_stage.show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub

	}

}