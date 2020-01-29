package src.main.java.application;

import com.sun.prism.Image;

import javafx.scene.image.ImageView;

public class ItemCatalogClient extends ItemClient {
	private Integer  itemId;
	private Integer  storeId;
	private ImageView  image;
	private Integer sale;
	private String type;
	private Integer quantity;
	
	public ItemCatalogClient(Integer itemId, String color, double price, String type, Integer storeId, ImageView  image,
			Integer sale, Integer quantity, String name) {
		super( color, price , name);
		this.itemId = itemId;
		this.storeId = storeId;
		this.image = image;
		this.sale = sale;
		this.quantity = quantity;
		this.type = type;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getItemId() {
		return itemId;
	}
	
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public void setImage(ImageView i) {
		this.image = i;
	}
	public void setSale(Integer sale) {
		this.sale = sale;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ItemCatalogClient(){}; //Default const'
	
	public Integer getStoreId() {
		return storeId;
	}
	public ImageView getImage() {
		return image;
	}	
	public Integer getSale() {
		return sale;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public double getPriceAfterSale() {
		if(sale == 0) {
			return getPrice();
		}else {
			return (getPrice()-getPrice()*sale/100);
		}
	}
}