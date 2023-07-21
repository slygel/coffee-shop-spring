package com.project.coffeeshop.entity;

import com.project.coffeeshop.model.DeliveryInfoModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class DeliveryInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String receiver;
	private String phoneNumber;
	private String address;
	private String note;
	private String isDefault;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryInfo")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Shipment> shipments;
	
	public DeliveryInfo(DeliveryInfoModel deliveryInfoModel) {
		this.id = deliveryInfoModel.getId();
		this.receiver = deliveryInfoModel.getReceiver();
		this.phoneNumber = deliveryInfoModel.getPhoneNumber();
		this.address = deliveryInfoModel.getAddress();
		this.note = deliveryInfoModel.getNote();
		this.isDefault = deliveryInfoModel.getIsDefault().toString();
	}
	
	public DeliveryInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}
	
}
