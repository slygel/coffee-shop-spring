package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Item;
import com.project.coffeeshop.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
	
	private Long id;
	private List<ItemModel> items;
	private String orderDate;
	private ShipmentModel shipment;
	private VoucherModel voucher;
	private UserModel user;
	private PaymentModel paymentModel;
	
	public OrderModel(Order order) {
		this.id = order.getId();
		if (order.getOrderDate() != null) {
			this.orderDate = order.getOrderDate().toString();
		}
		if (order.getShipment() != null) {
			this.shipment = new ShipmentModel(order.getShipment());
		}
		if (order.getVoucher() != null) {
			this.voucher = new VoucherModel(order.getVoucher());
		}
		if (order.getUser() != null) {
			this.user = new UserModel(order.getUser());
		}

		if(order.getPayment() != null){
			this.paymentModel = new PaymentModel(order.getPayment());
		}
		
		List<ItemModel> listItems = new ArrayList<>();
		if (order.getItems() != null) {
			for(Item item : order.getItems()) {
				listItems.add(new ItemModel(item));
			}
		}
		this.items = listItems;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ItemModel> getItems() {
		return items;
	}
	public void setItems(List<ItemModel> items) {
		this.items = items;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public ShipmentModel getShipment() {
		return shipment;
	}
	public void setShipment(ShipmentModel shipment) {
		this.shipment = shipment;
	}
	public VoucherModel getVoucher() {
		return voucher;
	}
	public void setVoucher(VoucherModel voucher) {
		this.voucher = voucher;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}

	public PaymentModel getPaymentModel() {
		return paymentModel;
	}

	public void setPaymentModel(PaymentModel paymentModel) {
		this.paymentModel = paymentModel;
	}
}
