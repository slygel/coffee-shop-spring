package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.DeliveryInfo;

public class DeliveryInfoModel {
	
	public DeliveryInfoModel(DeliveryInfo deliveryInfo) {
		this.id = deliveryInfo.getId();
		this.receiver = deliveryInfo.getReceiver();
		this.phoneNumber = deliveryInfo.getPhoneNumber();
		this.address = deliveryInfo.getAddress();
		this.note = deliveryInfo.getNote();
		this.isDefault = deliveryInfo.getIsDefault().equalsIgnoreCase("true");
	}
	
	public DeliveryInfoModel() {
		// TODO Auto-generated constructor stub
	}
	
	private Long id;
	private String receiver;
	private String phoneNumber;
	private String address;
	private String note;
	private Boolean isDefault;

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
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
}
