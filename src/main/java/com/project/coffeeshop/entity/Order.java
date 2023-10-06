package com.project.coffeeshop.entity;

import com.project.coffeeshop.model.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Order() {
	}

	public Order(OrderModel order) {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Item> items;
	
	@OneToOne(mappedBy = "order")
	private Shipment shipment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(mappedBy = "order")
	private Bill bill;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Feedback> feedback;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	private Date orderDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public Shipment getShipment() {
		return shipment;
	}
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	public Voucher getVoucher() {
		return voucher;
	}
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}
}
