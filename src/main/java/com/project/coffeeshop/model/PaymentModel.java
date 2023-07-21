package com.project.coffeeshop.model;

import com.project.coffeeshop.entity.Payment;
import lombok.Data;

@Data
public class PaymentModel {

    private Long id;
    private String name;

    public PaymentModel(Payment payment){
        this.id = payment.getId();
        this.name = payment.getName();
    }
}
