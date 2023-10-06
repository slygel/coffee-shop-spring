package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class TransactionDetails {

    private Long orderId;
    private String currency;
    private Long amount;

    public TransactionDetails(Long orderId, String currency, Long amount) {
        this.orderId = orderId;
        this.currency = currency;
        this.amount = amount;
    }
}
