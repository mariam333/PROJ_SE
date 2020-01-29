package src.main.java.application;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Order {
	private String content;
	private String status;
	private String PurchesTime;
	private double total;
	private String ID;
	public   ObservableList<ItemClient> Items;
	
	
	public Order(String content, String status, String purchesTime , double money,String id){
		super();
		this.content = content;
		this.status = status;
		PurchesTime = purchesTime;
		this.total=money;
		this.ID=id;
	}
	

	public Order() {};
		// TODO Auto-generated constructor stub
	


	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public double getMoney() {
		return total;
	}

	public void setMoney(double money) {
		this.total = money;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPurchesTime() {
		return PurchesTime;
	}
	public void setPurchesTime(String strDate) {
		PurchesTime = strDate.toString();
	}
	
}