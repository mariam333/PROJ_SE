package src.main.java.application;
import src.main.java.application.ConnectController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import javax.swing.JOptionPane;
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

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;

public class AdministratorController {

  @FXML // fx:id="Reports"
   private TableView<String> Reports1= new TableView<>(); // Value injected by FXMLLoader

   private final ObservableList<String> tvObservableList1 = FXCollections.observableArrayList();//??
   
   @FXML // fx:id="OrderReport"
   private TableColumn<String, Void> OrderReport1 =new TableColumn("Order Report"); // Value injected by FXMLLoader

   @FXML // fx:id="ComplaintReport"
   private TableColumn<String, Void> ComplaintReport1 =new TableColumn("Complaint Report"); // Value injected by FXMLLoader

   @FXML // fx:id="PaymentReport"
   private TableColumn<String, Void> PaymentReport1 =new TableColumn("Payment Report"); // Value injected by FXMLLoader

   @FXML // fx:id="Reports"
   private TableView<String> Reports2= new TableView<>(); // Value injected by FXMLLoader

   private final ObservableList<String> tvObservableList2 = FXCollections.observableArrayList();//??
   
   @FXML // fx:id="OrderReport"
   private TableColumn<String, Void> OrderReport2 =new TableColumn("Order Report");; // Value injected by FXMLLoader

   @FXML // fx:id="ComplaintReport"
   private TableColumn<String, Void> ComplaintReport2 =new TableColumn("Complaint Report");; // Value injected by FXMLLoader

   @FXML // fx:id="PaymentReport"
   private TableColumn<String, Void> PaymentReport2 =new TableColumn("Payment Report");; // Value injected by FXMLLoader

    @FXML // fx:id="diag1"
    private BarChart<?, ?> diag1; // Value injected by FXMLLoader

    @FXML // fx:id="diag2"
    private BarChart<?, ?> diag2; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader
   
    @FXML // fx:id="dateofRep1"
    private Label dateofRep1; // Value injected by FXMLLoader

    @FXML // fx:id="dateofRep2"
    private Label dateofRep2; // Value injected by FXMLLoader
   
    @FXML
    private Label Pranch1;

    @FXML
    private Label Pranch2;
static int count=0;
static int indiag;
static int i1=0;
static int i2=0;
static int i3=0;
static int i4=0;
static int i5=0;
static int i6=0;

    @FXML
    void backtostores(ActionEvent event) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Stores.fxml"));
    AnchorPane root = (AnchorPane) loader.load();
    StoresController user = loader.getController();
//     Image im = new Image("images/background.jpg");to me all comp
    user.AddStores();
    Scene regist = new Scene(root);
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    app_stage.setScene(regist);
    app_stage.show();
    }
    void FullTable1(String store)
    {  
    Pranch1.setText(store);
    String message = "DateOfAllReports#" + store;
  ConnectController.client.handleMessageFromClientUI(message);
  String[] Msg = ConnectController.client.servermsg.split("@");
  String[] Order_rep = Msg[0].split("#");
  String[] Complaint_rep = Msg[1].split("#");
  String[] Pay_rep = Msg[2].split("#");
 
  for( ;i1<Order_rep.length;i1++) {
//   Button date= new Button();
//   date.setText(Order_rep[i]);
//   date.setOnAction("ord"+String.valueOf(i));
//   OrderReport1.add(date);
 
  Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
             @Override
           public TableCell<String, Void> call(final TableColumn<String, Void> param) {
            final TableCell<String, Void> cell = new TableCell<String, Void>() {
          private final Button date = new Button(Order_rep[i1]);
         
          {date.setOnAction((ActionEvent event) ->{
          indiag=0;//to know what diagram we want
          dateofRep1.setText(Order_rep[i1]);
        ordersdata(Order_rep[i1],store);
       
          });}
          @Override
                 public void updateItem(Void item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                         setGraphic(null);
                     } else {
                         setGraphic(date);
                     }
                 }
             };
             return cell;
         }
     };
     OrderReport1.setCellFactory(cellFactory);

    Reports1.getColumns().add(OrderReport1);
  }
     
         
         
         
 
 
  for(; i2<Complaint_rep.length;i2++) {
//   Button date= new Button();
//   date.setText(Complaint_rep[i]);
//   date.setOnAction("comp"+String.valueOf(i));
//   PaymentReport1.add(date);
  Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
             @Override
           public TableCell<String, Void> call(final TableColumn<String, Void> param) {
            final TableCell<String, Void> cell = new TableCell<String, Void>() {
          private final Button date = new Button(Complaint_rep[i2]);
         
          {date.setOnAction((ActionEvent event) ->{
          indiag=0;//to know what diagram we want
          dateofRep1.setText(Complaint_rep[i2]);
        comlaintsdata(Complaint_rep[i2],store);
       
          });}
          @Override
                 public void updateItem(Void item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                         setGraphic(null);
                     } else {
                         setGraphic(date);
                     }
                 }
             };
             return cell;
         }
     };
     ComplaintReport1.setCellFactory(cellFactory);

    Reports1.getColumns().add(ComplaintReport1);
//  
  }
  for(; i3<Pay_rep.length;i3++) {
//   Button date= new Button();
//   date.setText(Pay_rep[i]);
//   date.setOnAction("pay"+String.valueOf(i));
//   ComplaintReport1.add(date);
//  
  Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
             @Override
           public TableCell<String, Void> call(final TableColumn<String, Void> param) {
            final TableCell<String, Void> cell = new TableCell<String, Void>() {
          private final Button date = new Button(Pay_rep[i3]);
         
          {date.setOnAction((ActionEvent event) ->{
          indiag=0;//to know what diagram we want
          dateofRep1.setText(Pay_rep[i3]);
        paymentdata(Pay_rep[i3],store);
       
          });}
          @Override
                 public void updateItem(Void item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                         setGraphic(null);
                     } else {
                         setGraphic(date);
                     }
                 }
             };
             return cell;
         }
     };
     PaymentReport1.setCellFactory(cellFactory);

    Reports1.getColumns().add(PaymentReport1);
  }
    }
 //******************************  
void FullTable2(String store) {
if(count==2)   {
//******showit****
Reports2.setVisible(true);
diag2.setVisible(true);
//******showit****
    Pranch2.setText(Store);
    String message = "DateOfAllReports#" + store;
  ConnectController.client.handleMessageFromClientUI(message);
  String[] Msg = ConnectController.client.servermsg.split("@");
  String[] Order_rep = Msg[0].split("#");
  String[] Complaint_rep = Msg[1].split("#");
  String[] Pay_rep = Msg[2].split("#");
 
  for(; i4<Order_rep.length;i4++) {
// Button date= new Button();
// date.setText(Order_rep[i]);
// date.setOnAction("ord"+String.valueOf(i));
// OrderReport2.add(date);
 
  Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
             @Override
           public TableCell<String, Void> call(final TableColumn<String, Void> param) {
            final TableCell<String, Void> cell = new TableCell<String, Void>() {
          private final Button date = new Button(Order_rep[i4]);
         
          {date.setOnAction((ActionEvent event) ->{
          indiag=1;//to know what diagram we want
          dateofRep2.setText(Order_rep[i4]);
          ordersdata(Order_rep[i4],store);
          });}
          @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(date);
                    }
                }
            };
            return cell;
        }
    };
    OrderReport2.setCellFactory(cellFactory);

   Reports2.getColumns().add(OrderReport2);
  }
  for(; i5<Complaint_rep.length;i5++) {
// Button date= new Button();
// date.setText(Complaint_rep[i]);
// date.setOnAction("comp"+String.valueOf(i));
// PaymentReport2.add(date);
  Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
             @Override
           public TableCell<String, Void> call(final TableColumn<String, Void> param) {
            final TableCell<String, Void> cell = new TableCell<String, Void>() {
          private final Button date = new Button(Order_rep[i5]);
         
          {date.setOnAction((ActionEvent event) ->{
          indiag=1;//to know what diagram we want
          dateofRep2.setText(Complaint_rep[i5]);
          comlaintsdata(Complaint_rep[i5],store);
          });}
          @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(date);
                    }
                }
            };
            return cell;
        }
    };
    ComplaintReport2.setCellFactory(cellFactory);

   Reports2.getColumns().add(ComplaintReport2);
//
  }
  for(; i6<Pay_rep.length;i6++) {
// Button date= new Button();
// date.setText(Pay_rep[i]);
// date.setOnAction("pay"+String.valueOf(i));
// ComplaintReport2.add(date);
//
  Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
             @Override
           public TableCell<String, Void> call(final TableColumn<String, Void> param) {
            final TableCell<String, Void> cell = new TableCell<String, Void>() {
          private final Button date = new Button(Pay_rep[i6]);
         
          {date.setOnAction((ActionEvent event) ->{
          indiag=1;//to know what diagram we want
          dateofRep2.setText(Pay_rep[i6]);
          paymentdata(Pay_rep[i6],store);
         
          });}
          @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(date);
                    }
                }
            };
            return cell;
        }
    };
    PaymentReport2.setCellFactory(cellFactory);

   Reports2.getColumns().add(PaymentReport2);
  }
   }
}
   void count(int count1) {count=count1;}


   
    //**********************8
    void ordersdata(String Month1,String store1) {
      String message = "order#" + "report#" + store1 + "#" + Month1;
    ConnectController.client.handleMessageFromClientUI(message);
    String[] Msg = ConnectController.client.servermsg.split("@");
    String[] kinds=Msg[0].split("#");
    String[] amounts=Msg[1].split("#");
   
    final CategoryAxis xAxis = new CategoryAxis();
           final CategoryAxis yAxis = new CategoryAxis();
           if(indiag==0)
    diag1= new BarChart<String,String>(xAxis,yAxis);
           else
        diag2= new BarChart<String,String>(xAxis,yAxis);
    xAxis.setLabel("Kind");      
           yAxis.setLabel("amount");
           XYChart.Series series1 = new XYChart.Series();
           for(int i=0;i<kinds.length;i++) {
           series1.getData().add(new XYChart.Data(kinds[i], amounts[i]));
           }
          // Scene scene  = new Scene(bc,800,600);
        if(indiag==0)
           diag1.getData().addAll(series1);
        else
        diag2.getData().addAll(series1);
       
          // stage.setScene(scene);
          // stage.show();
   
     
       }
   
    void paymentdata(String Month1,String store1) {
    String message = "payment#" + "report#" + store1 + "#" + Month1;
ConnectController.client.handleMessageFromClientUI(message);
String[] Msg = ConnectController.client.servermsg.split("#");
if ((Msg[0] == null) || (Msg[1] == null)|| (Msg[2] == null)|| (Msg[3] == null)) {
JOptionPane.showMessageDialog(null, "Showing  Failed ! ");
} else {
final CategoryAxis xAxis = new CategoryAxis();
       final CategoryAxis yAxis = new CategoryAxis();
       if(indiag==0)
      diag1= new BarChart<String,String>(xAxis,yAxis);
       else
        diag2= new BarChart<String,String>(xAxis,yAxis);
xAxis.setLabel("Week");      
       yAxis.setLabel("Value");
       XYChart.Series series1 = new XYChart.Series();
       series1.getData().add(new XYChart.Data("first week", Msg[0]));
       series1.getData().add(new XYChart.Data("second week", Msg[1]));
       series1.getData().add(new XYChart.Data("Third week",Msg[2]));
       series1.getData().add(new XYChart.Data("Fourth week", Msg[3]));
      // Scene scene  = new Scene(bc,800,600);
       if(indiag==0)
       diag1.getData().addAll(series1);
       else
        diag2.getData().addAll(series1);
      // stage.setScene(scene);
      // stage.show();
}
   

}
    void comlaintsdata(String Month1,String store1) {
      String message = "Complaint#" + "report#" + store1 + "#" + Month1;
    ConnectController.client.handleMessageFromClientUI(message);
    String[] Msg = ConnectController.client.servermsg.split("#");
    if ((Msg[0] == null) || (Msg[1] == null)|| (Msg[2] == null)|| (Msg[3] == null)) {
    JOptionPane.showMessageDialog(null, "Showing  Failed ! ");
    } else {
    final CategoryAxis xAxis = new CategoryAxis();
           final CategoryAxis yAxis = new CategoryAxis();
           if(indiag==0)
    diag1= new BarChart<String,String>(xAxis,yAxis);
           else
            diag2= new BarChart<String,String>(xAxis,yAxis);
    xAxis.setLabel("Week");      
           yAxis.setLabel("Value");
           XYChart.Series series1 = new XYChart.Series();
           series1.getData().add(new XYChart.Data("first week", Msg[0]));
           series1.getData().add(new XYChart.Data("second week", Msg[1]));
           series1.getData().add(new XYChart.Data("Third week",Msg[2]));
           series1.getData().add(new XYChart.Data("Fourth week", Msg[3]));
          // Scene scene  = new Scene(bc,800,600);
           if(indiag==0)
           diag1.getData().addAll(series1);
           else
            diag2.getData().addAll(series1);
          // stage.setScene(scene);
          // stage.show();
    }
     }}