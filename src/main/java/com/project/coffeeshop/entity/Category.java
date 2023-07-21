package com.project.coffeeshop.entity;

import com.project.coffeeshop.model.CategoryModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Product> products;
	
	public Category(CategoryModel categoryModel) {
		this.id = categoryModel.getId();
		this.name = categoryModel.getName();
	}

	public Category() {}

	public Category(Long id) {
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
