package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.DeliveryInfo;
import com.project.coffeeshop.entity.Role;
import com.project.coffeeshop.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

	
	public UserModel(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.dateOfBirth = user.getDateOfBirth().toString();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.reward = user.getReward();
		this.point = user.getPoint();
		this.isActived = user.getIsActived().equalsIgnoreCase("true");
		
		List<DeliveryInfoModel> infoModels = new ArrayList<>();
		for(DeliveryInfo deliveryInfo : user.getDeliveryInfos() ) {
			DeliveryInfoModel deliveryInfoModel = 
					new DeliveryInfoModel(deliveryInfo);
			infoModels.add(deliveryInfoModel);
		}
		this.deliveryInfos = infoModels;
		
		List<RoleModel> roleModels = new ArrayList<>();
		for(Role role : user.getRoles()) {
			roleModels.add(new RoleModel(role));
		}
		this.roles = roleModels;
	}

	private Long id;
	private String fullName;
	private String dateOfBirth;

	private String email;
	private String username;
	private String password;

	private String reward;
	private Long point;
	private List<DeliveryInfoModel> deliveryInfos;

	private Boolean isActived;
	private List<RoleModel> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public List<DeliveryInfoModel> getDeliveryInfos() {
		return deliveryInfos;
	}

	public void setDeliveryInfos(List<DeliveryInfoModel> deliveryInfos) {
		this.deliveryInfos = deliveryInfos;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getIsActived() {
		return isActived;
	}

	public void setIsActived(Boolean isActived) {
		this.isActived = isActived;
	}

	public List<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}

}
