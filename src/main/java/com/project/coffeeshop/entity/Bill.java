package com.project.coffeeshop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Bill implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long amount;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private Order order;

	public Bill() {
		// TODO Auto-generated constructor stub
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
