package src.main.java.application;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegistController implements Initializable {

	@FXML // fx:id="MM"
	private TextField MM; // Value injected by FXMLLoader

	@FXML // fx:id="image"
	private ImageView image; // Value injected by FXMLLoader

	@FXML // fx:id="CVV"
	private TextField CVV; // Value injected by FXMLLoader

	@FXML // fx:id="password"
	private TextField password; // Value injected by FXMLLoader

	@FXML // fx:id="visa"
	private TextField visa; // Value injected by FXMLLoader

	@FXML // fx:id="mobile"
	private TextField mobile; // Value injected by FXMLLoader

	@FXML // fx:id="ID"
	private TextField ID; // Value injected by FXMLLoader

	@FXML // fx:id="Fistname"
	private TextField Fistname; // Value injected by FXMLLoader

	@FXML // fx:id="email"
	private TextField email; // Value injected by FXMLLoader

	@FXML // fx:id="lastname"
	private TextField lastname; // Value injected by FXMLLoader

	//public void setimage(Image im) {
			//image.setImage(im);
		//}
	String MyEmail = null;

	public void SeTEmail(String theEmail) {
		// TODO Auto-generated method stub
		MyEmail = theEmail;
	}
	

	@FXML
	void back(ActionEvent event) throws IOException 
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		HomePageController home = loader.getController();
		home.setEmail(email.getText());
		//Image im = new Image("images/pink.jpg");
		//home.setimage(im);
		Scene regist = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(regist);
		app_stage.show();
	}

	@FXML
	void SignUp(ActionEvent event) throws IOException 
	{
		String Email = email.getText();
		String pass = password.getText();
		String firstname = Fistname.getText();
		String last = lastname.getText();
		String tel = mobile.getText();
		String Visa = visa.getText();
		String cvv = CVV.getText();
		String date = MM.getText();
		String id = ID.getText();

		if (Email.equals("") || pass.equals("") || firstname.equals("") || last.equals("") || tel.equals("")
				|| Visa.equals("") || cvv.equals("") || date.equals("") || id.equals("")) {
			JOptionPane.showMessageDialog(null, "One or more files are empty!! ");
		} else if (!(Email.contains("@hotmail.com")) && !(Email.contains("@gmail.com"))) {
			JOptionPane.showMessageDialog(null, "Please enter correct mail ");
		} else if (tel.length() != 10 || !(tel.matches("(05[0-9]+)"))) {
			JOptionPane.showMessageDialog(null, "Please enter correct phone number ");
		} else if (Visa.length() != 16 || (!Visa.matches("[0-9]+"))) {
			JOptionPane.showMessageDialog(null, "Please enter correct visa number ");
		} else if (cvv.length() != 3 || (!cvv.matches("[0-9]+"))) {
			JOptionPane.showMessageDialog(null, "Please enter correct cvv number ");

		} else if (id.length() != 9 || (!id.matches("[0-9]+"))) {
			JOptionPane.showMessageDialog(null, "Please enter correct id ");

		} else if (date.length() != 5 || (!date.matches("(1[0-2]|0[1-9])/(2[0-9])"))) {
			JOptionPane.showMessageDialog(null, "Please enter correct date number ");

		} else {
			String message = "SignUp#" + Fistname.getText() + lastname.getText() + "#" + password.getText() + "#" + email.getText() + "#" + mobile.getText() + "#" + visa.getText() + "#" + CVV.getText() + "#"
					+ MM.getText();
			ConnectController.client.handleMessageFromClientUI(message);

			if (ConnectController.client.servermsg != null && "SignUp".equals(ConnectController.client.servermsg)) {
				JOptionPane.showMessageDialog(null, "You SignUp successfully ");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				Scene regist = new Scene(root);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				app_stage.setScene(regist);
				app_stage.show();
				ConnectController.client.servermsg = null;
			} else if (ConnectController.client.servermsg != null && "SignUpFailed".equals(ConnectController.client.servermsg)) {
				JOptionPane.showMessageDialog(null, " SignUp Failed ");
				ConnectController.client.servermsg = null;

			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}