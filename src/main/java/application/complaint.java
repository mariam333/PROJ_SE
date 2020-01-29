package src.main.java.application;

import javafx.collections.ObservableList;

public class complaint {
	private String num;
	private String ID;
	private String shopper;
	private String topic;
	private String Content;
	private String Time;
	private String raply;
	public   ObservableList<ItemClient> Items;
	public complaint(String num, String ID, String shopper ,String topic, String Content, String Time ,String raply){
		super();
		this.num = num;
		this.ID = ID;
		this.shopper=shopper;
		this.topic=topic;
		this.Content = Content;
		this.Time=Time;
		this.raply=raply;
	}
	public complaint() {
		// TODO Auto-generated constructor stub
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getShopper() {
		return shopper;
	}
	public void setShopper(String shopper) {
		this.shopper = shopper;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getRaply() {
		return raply;
	}
	public void setRaply(String raply) {
		this.raply = raply;
	}
}