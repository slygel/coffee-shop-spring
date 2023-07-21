package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class FeedbackDto {

    private String title;
    private String content;
    private Long orderId;

}
