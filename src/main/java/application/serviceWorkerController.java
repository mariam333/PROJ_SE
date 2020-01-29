package src.main.java.application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.collections.ObservableList;
public class serviceWorkerController implements Initializable 
{

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private TextField date;

    @FXML
    private TextField time;

    @FXML // fx:id="ComplaintsTable"
    private TableView<String> ComplaintsTable; // Value injected by FXMLLoader

    @FXML // fx:id="colnum"
    private TableColumn<String, String> colnum; // Value injected by FXMLLoader

    @FXML // fx:id="ComplIdCol"
    private TableColumn<String, String> ComplIdCol; // Value injected by FXMLLoader

    @FXML // fx:id="shopperid"
    private TableColumn<String, String> shopperid; // Value injected by FXMLLoader

    @FXML // fx:id="topic"
    private TableColumn<String, String> topic; // Value injected by FXMLLoader

    @FXML // fx:id="ContentCol"
    private TableColumn<String, String> ContentCol; // Value injected by FXMLLoader

    @FXML // fx:id="TimeCol"
    private TableColumn<String, String> TimeCol; // Value injected by FXMLLoader

    @FXML // fx:id="raplyCol"
    private TableColumn<String,String> raplyCol; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="IDcomplaint"
    private TextField IDcomplaint; // Value injected by FXMLLoader

    @FXML // fx:id="refound"
    private TextField refound; // Value injected by FXMLLoader

    @FXML // fx:id="replyworker"
    private TextField replyworker; // Value injected by FXMLLoader

    @FXML // fx:id="Reply"
    private Button Reply; // Value injected by FXMLLoader

    @FXML // fx:id="clean"
    private Button clean; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private Button logout; // Value injected by FXMLLoader

    @FXML // fx:id="iDshooper"
    private TextField iDshooper; // Value injected by FXMLLoader

    @FXML // fx:id="show"
    private Button show; // Value injected by FXMLLoader

    ObservableList<complaint> complaints = FXCollections.observableArrayList();
    public void SeTEmail(String theEmail) 
    {
		// TODO Auto-generated method stub
		email.setText(theEmail);
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
    		}else 
    		{
    			JOptionPane.showMessageDialog(null, "Can'tSignOut ");
    		}
    }

    @FXML
    void Replybutton(ActionEvent event) 
    {
    	
    	String message2 = "ReplyComplaints#" + IDcomplaint.getText() + "#" + replyworker.getText();
    	ConnectController.client.handleMessageFromClientUI(message2);
		if ("ReplySuccessfully".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "Done..");
			
			String message1 = "EditRefund#" + iDshooper.getText() + "#" + refound.getText();
			ConnectController.client.handleMessageFromClientUI(message1);
			if ("EditRefundSuccessfully".equals(ConnectController.client.servermsg)) 
			{
				JOptionPane.showMessageDialog(null, "Edit Refund Successfully");
			} else if ("EditRefoundNotSuccesfully".equals(ConnectController.client.servermsg)) 
			{
				JOptionPane.showMessageDialog(null, "Something wrong in edit refund!!");

			}
       } 
	   else if ("ReplyNotSuccessfully".equals(ConnectController.client.servermsg)) 
		{
			JOptionPane.showMessageDialog(null, "Something wrong in reply!!");
			String message1 = "EditRefund#" + iDshooper.getText() + "#" + refound.getText();
			ConnectController.client.handleMessageFromClientUI(message1);
			if ("EditRefundSuccessfully".equals(ConnectController.client.servermsg)) 
			{
				JOptionPane.showMessageDialog(null, "Edit Refound Successfully");
			} else if ("EditRefundNotSuccessfully".equals(ConnectController.client.servermsg)) 
			{
				JOptionPane.showMessageDialog(null, "Something wrong in edit refound!!");

			}	

		}

    }

    @FXML
    void cleanButton(ActionEvent event) 
    {
    	IDcomplaint.setText("");
    	refound.setText("");
    	replyworker.setText("");

    }
    @FXML
    void showAllComplaints(ActionEvent event) //time date ShowComplaintsToHandel
    {
    	String msg = "showAllComplaints%" + date.getText()+"%"+ time.getText(); //get Items sorted by sale wich is the def'
		ConnectController.client.handleMessageFromClientUI(msg);
		String Msg="";
		Msg=ConnectController.client.servermsg;
			String[] Msg1 = Msg.split("/n",-1);
			complaint complaint = new complaint();
			for(String a : Msg1) {
				String[] complaintstring = a.split("%",7);
				complaint.setNum(complaintstring[0]);
				complaint.setID(complaintstring[1]);
				complaint.setShopper(complaintstring[2]);
				complaint.setTopic(complaintstring[3]);
				complaint.setContent(complaintstring[4]);
				complaint.setTime(complaintstring[5]);
				complaint.setRaply(complaintstring[6]);
				
				complaints.add(complaint);
			}
		
		/* add column to the tableview and set its items */
			ComplaintsTable.getColumns().add(colnum);
			ComplaintsTable.getColumns().add(ComplIdCol);
			ComplaintsTable.getColumns().add(shopperid);
			ComplaintsTable.getColumns().add(topic);
			ComplaintsTable.getColumns().add(ContentCol);
			ComplaintsTable.getColumns().add(TimeCol);
			ComplaintsTable.getColumns().add(raplyCol);
    }


    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}