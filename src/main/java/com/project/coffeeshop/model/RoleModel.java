package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Role;

public class RoleModel {
	private Long id;
	private String name;
	
	public RoleModel(Role role) {
		this.id = role.getId();
		this.name = role.getName();
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
