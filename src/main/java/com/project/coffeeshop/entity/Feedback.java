package com.project.coffeeshop.entity;

import com.project.coffeeshop.model.FeedbackModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Feedback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Feedback() {
		// TODO Auto-generated constructor stub
	}

	public Feedback(FeedbackModel feedback) {
		this.id = feedback.getId();
		this.title = feedback.getTitle();
		this.content = feedback.getContent();
		this.order = new Order();
		this.user = new User();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
