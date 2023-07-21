package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Category;

public class CategoryModel {

	private Long id;
	private String name;
	
	public CategoryModel(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
	
	public CategoryModel() {}

	public CategoryModel(Long id){
		this.id = id;
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

	
}
