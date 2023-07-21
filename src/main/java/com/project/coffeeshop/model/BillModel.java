package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Bill;
import lombok.Data;

@Data
public class BillModel {
	
	private Long id;
	private Double amount;
	private OrderModel order;
	
	public BillModel(Bill bill) {
		this.id = bill.getId();
		this.amount = bill.getAmount();
		this.order = new OrderModel(bill.getOrder());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public OrderModel getOrder() {
		return order;
	}
	public void setOrder(OrderModel order) {
		this.order = order;
	}
}
