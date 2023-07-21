package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Voucher;

public class VoucherModel {
	private Long id;
	private String code;
	private String description;
	private String name;
	private Double value;
	private String startDate;
	private String expiryDate;
	
	public VoucherModel(Voucher voucher) {
		this.id = voucher.getId();
		this.code = voucher.getCode();
		this.description = voucher.getDescription();
		this.name = voucher.getName();
		this.value = voucher.getValue();
		this.startDate = voucher.getStartDate().toString();
		this.expiryDate = voucher.getExpiryDate().toString();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
}
