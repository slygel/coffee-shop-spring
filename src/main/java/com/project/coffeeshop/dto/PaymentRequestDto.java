package com.project.coffeeshop.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRequestDto implements Serializable {

    private String status;
    private String message;
    private String URL;
}
