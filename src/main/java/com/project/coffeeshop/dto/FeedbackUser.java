package com.project.coffeeshop.dto;

import com.project.coffeeshop.entity.Feedback;
import lombok.Data;

@Data
public class FeedbackUser {

    private String title;
    private String content;
    private Long orderId;
    private Long userId;

    public FeedbackUser(Feedback feedback) {
        this.title = feedback.getTitle();
        this.content = feedback.getContent();
        this.orderId = feedback.getOrder().getId();
        this.userId = feedback.getUser().getId();
    }
}
