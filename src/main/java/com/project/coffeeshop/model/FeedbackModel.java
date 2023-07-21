package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Feedback;

public class FeedbackModel {
	private Long id;
	private String title;
	private String content;
	private OrderModel order;

	public FeedbackModel(Feedback feedback) {
		this.id = feedback.getId();
		this.title = feedback.getTitle();
		this.content = feedback.getContent();
		this.order = new OrderModel(feedback.getOrder());
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
	public OrderModel getOrder() {
		return order;
	}
	public void setOrder(OrderModel order) {
		this.order = order;
	}
}
