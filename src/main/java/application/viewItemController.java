/**
 * Sample Skeleton for 'viewItem.fxml' Controller Class
 */

package src.main.java.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class viewItemController implements Initializable{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ItemName"
    private TextField ItemName; // Value injected by FXMLLoader

    @FXML // fx:id="itemImage"
    private ImageView itemImage; // Value injected by FXMLLoader

    @FXML // fx:id="ItemTypTxt"
    private TextField ItemTypTxt; // Value injected by FXMLLoader

    @FXML // fx:id="PriceTxt"
    private TextField PriceTxt; // Value injected by FXMLLoader

    @FXML // fx:id="ItemColorTxt"
    private TextField ItemColorTxt; // Value injected by FXMLLoader

    @FXML // fx:id="ItemIDtxt"
    private TextField ItemIDtxt; // Value injected by FXMLLoader

    @FXML // fx:id="ItemSale"
    private TextField ItemSale; // Value injected by FXMLLoader

    @FXML // fx:id="PriceAfterSale"
    private TextField PriceAfterSale; // Value injected by FXMLLoader

    @FXML // fx:id="addToCartBtn"
    private Button addToCartBtn; // Value injected by FXMLLoader

    @FXML // fx:id="backToCatalogBtn"
    private Button backToCatalogBtn; // Value injected by FXMLLoader
    
    private ItemCatalogClient selectedItem;
    
    public void initData(ItemCatalogClient itemClient)//in order to send object
    {
        selectedItem = itemClient;
        ItemName.setText(selectedItem.getName());
        ItemColorTxt.setText(selectedItem.getColor());
        PriceTxt.setText(Double.toString(selectedItem.getPrice()));
        ItemIDtxt.setText(Integer.toString(selectedItem.getItemId()));
        ImageView im = selectedItem.getImage();
        itemImage.setImage(im);
        ItemSale.setText((Integer.toString(selectedItem.getSale())) + "%");
        if(selectedItem.getSale() != 0) {
        	ItemSale.setText((Integer.toString(selectedItem.getSale())) + "%");
        	PriceAfterSale.setText(Double.toString(selectedItem.getPriceAfterSale()));
        	PriceAfterSale.setVisible(true);
        }
    }


    @FXML
    void goBack(ActionEvent event) throws IOException{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
    		AnchorPane root = (AnchorPane) loader.load();
    		Scene regist = new Scene(root);
    		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		app_stage.setScene(regist);
    		app_stage.show();
    }
    @FXML
    void AddItemToCart(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		OrderController order = loader.getController();
    	order.addItem(selectedItem);
    	Scene regist = new Scene(root);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(regist);
		app_stage.show();
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert ItemName != null : "fx:id=\"ItemName\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert itemImage != null : "fx:id=\"itemImage\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert ItemTypTxt != null : "fx:id=\"ItemTypTxt\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert PriceTxt != null : "fx:id=\"PriceTxt\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert ItemColorTxt != null : "fx:id=\"ItemColorTxt\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert ItemIDtxt != null : "fx:id=\"ItemIDtxt\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert ItemSale != null : "fx:id=\"ItemSale\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert PriceAfterSale != null : "fx:id=\"PriceAfterSale\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert addToCartBtn != null : "fx:id=\"addToCartBtn\" was not injected: check your FXML file 'viewItem.fxml'.";
        assert backToCatalogBtn != null : "fx:id=\"backToCatalogBtn\" was not injected: check your FXML file 'viewItem.fxml'.";

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}