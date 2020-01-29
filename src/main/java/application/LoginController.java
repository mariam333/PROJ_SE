package src.main.java.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import src.main.java.application.ConnectController;
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

public class LoginController implements Initializable 
{

	@FXML // fx:id="image"
	private ImageView image; // Value injected by FXMLLoader

	@FXML // fx:id="password"
	private TextField password; // Value injected by FXMLLoader

	@FXML // fx:id="email"
	private TextField email; // Value injected by FXMLLoader
    static List<String> users_with_new_version;
	  public void setarrayversion(List<String> list) {
		  users_with_new_version=list;
			
	    }
	//public void setimage(Image im) {
		//image.setImage(im);
	//}
	  String MyEmail = null;

		public void SeTEmail(String theEmail) {
			// TODO Auto-generated method stub
			MyEmail = theEmail;
		}
	@FXML
	void SignIn(ActionEvent event) throws IOException {

		String Email = email.getText();
		String pass = password.getText();
		String message = "LogIn#" + email.getText() + "#" + password.getText();
		if (Email.equals("") || pass.equals("")) 
		{
			JOptionPane.showMessageDialog(null, "One or more files are empty!! ");
		}
		else {
			ConnectController.client.handleMessageFromClientUI(message);
			if (ConnectController.client.servermsg != null && "NotFoundEmail".equals(ConnectController.client.servermsg)) {
				JOptionPane.showMessageDialog(null, "You are not registed !!");
				ConnectController.client.servermsg=null;


			} else if (ConnectController.client.servermsg != null && "NotFoundPass".equals(ConnectController.client.servermsg)) {
				JOptionPane.showMessageDialog(null, "You entered uncorrect password !!");
				ConnectController.client.servermsg=null;

			}
			else if (ConnectController.client.servermsg != null && "LogInFaild".equals(ConnectController.client.servermsg)) {
				JOptionPane.showMessageDialog(null, "You are login in onther device !!");
				ConnectController.client.servermsg=null;
			}	
			else if (ConnectController.client.servermsg != null && "SignIn".equals(ConnectController.client.servermsg)) 
			{
				if (email.getText().contains("@chwork.co.il")) 
				{

					FXMLLoader loader = new FXMLLoader(getClass().getResource("ChainWorker.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					chainWorkerController employee = loader.getController();
					employee.SeTEmail(email.getText());
					//Image im = new Image("images/background.jpg");
					//employee.setimage(im);
					Scene regist = new Scene(root);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(regist);
					app_stage.show();
					ConnectController.client.servermsg = null;
				}
				else if (email.getText().contains("@servwork.co.il")) 
				{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("serviceWorker.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					serviceWorkerController employee = loader.getController();
					//Image im = new Image("images/background.jpg");
					//employee.setimage(im);
					employee.SeTEmail(email.getText());

					Scene regist = new Scene(root);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(regist);
					app_stage.show();
				} 
				else if (email.getText().contains("@stoman.co.il")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreManager.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					StoreManagerController employee = loader.getController();
					employee.setEmail(email.getText());
					employee.FullTable();
					Scene regist = new Scene(root);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(regist);
					app_stage.show();
				} 
				else if (email.getText().contains("@sysman.co.il")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("SystemManager.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					SystemManagerController employee = loader.getController();
					//Image im = new Image("images/background.jpg");
					//employee.setimage(im);
					employee.setEmail(email.getText());
					Scene regist = new Scene(root);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(regist);
					app_stage.show();
				} 
				else if (email.getText().contains("@admin.co.il")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Strores.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					StoresController employee = loader.getController();
					//Image im = new Image("images/background.jpg");
					//employee.setimage(im);
					employee.AddStores();
					Scene regist = new Scene(root);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(regist);
					app_stage.show();
				} 
				else {
	        		if(users_with_new_version.contains(email.getText()))
	        		{
	        			
	        			users_with_new_version=null;
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					HomePageController user = loader.getController();
			        user.setEmail(email.getText());
					//Image im = new Image("images/background.jpg");
    				//user.setimage(im);					
					Scene regist = new Scene(root);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(regist);
					app_stage.show();
					JOptionPane.showMessageDialog(null, "new version is launched");
	        		}

					}
				}
			}

	}

	@FXML
	void back(ActionEvent event) throws IOException 
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		HomePageController home = loader.getController();
		//Image im = new Image("images/background.jpg");
		//home.setimage(im);
		home.setEmail(email.getText());
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