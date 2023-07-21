package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Item;

public class ItemModel {

	private Long id;
	private ProductModel product;
	private Double priceIn;
	private int quantity;
	private String type;
	private String size;
	private String ice;	
	private String sugar;
	
	public ItemModel() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemModel(Item item) {
		this.id = item.getId();
		this.product = new ProductModel(item.getProduct());
		this.priceIn = item.getPriceIn();
		this.quantity = item.getQuantity();
		this.type = item.getType();
		this.size = item.getSize();
		this.ice = item.getIce();
		this.sugar = item.getSugar();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	public Double getPriceIn() {
		return product.getPrice();
	}
	public void setPriceIn(Double priceIn) {
		this.priceIn = priceIn;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getIce() {
		return ice;
	}
	public void setIce(String ice) {
		this.ice = ice;
	}
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	
	
}
