package com.project.coffeeshop.dto;

import com.project.coffeeshop.model.ItemModel;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private List<ItemModel> items;
    private String codeVoucher;
    private Long deliveryId;
    private Long paymentId;
}
