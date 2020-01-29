package src.main.java.application;

public class ItemClient {
	String color;
	double price;
	String name;
	public ItemClient( String color, double price, String name) {
		super();
		
		this.color = color;
		this.price = price;
		this.name = name;
	}
	public ItemClient(){};//Default const'

	public String getColor() {
		return color;
	}
	
	
	public void setColor(String color) {
		this.color = color;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}	
	public String getName() {
		return name;
	}

}