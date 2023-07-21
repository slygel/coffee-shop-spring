package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Product;

public class ProductModel {
	
	private Long id;
	private String name;
	private Double price;
	private String image;
	private CategoryModel category;
	
	public ProductModel() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductModel(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.image = product.getImage();
		this.category = new CategoryModel(product.getCategory());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public CategoryModel getCategory() {
		return category;
	}
	public void setCategory(CategoryModel category) {
		this.category = category;
	}
	
	
}
