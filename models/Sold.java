package models;

public class Sold {
	int itemId;
	int storeId;
	int shopperId;
	String type;
	String dominantColor;
	String priceRange;

public Sold(String type, String dominantColor,String priceRange)
{
	
	this.type=type;
	this.dominantColor=dominantColor;
	this.priceRange=priceRange;

}



}