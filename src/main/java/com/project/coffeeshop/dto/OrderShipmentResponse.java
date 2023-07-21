package com.project.coffeeshop.dto;

import com.project.coffeeshop.entity.Item;
import com.project.coffeeshop.entity.Shipment;
import com.project.coffeeshop.model.ItemModel;
import com.project.coffeeshop.model.PaymentModel;
import com.project.coffeeshop.model.ShipmentModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderShipmentResponse {

    private Long id;
    private double amount;
    private ShipmentModel shipment;
    private String orderDate;
    private List<ItemModel> items;
    private PaymentModel paymentModel;

    public OrderShipmentResponse(Shipment shipment) {

        this.id = shipment.getOrder().getId();

        this.amount = shipment.getOrder().getBill().getAmount();

        if (shipment.getOrder().getShipment() != null) {
            this.shipment = new ShipmentModel(shipment.getOrder().getShipment());
        }

        if (shipment.getOrder().getOrderDate() != null) {
            this.orderDate = shipment.getOrder().getOrderDate().toString();
        }

        if(shipment.getOrder().getPayment() != null){
            this.paymentModel = new PaymentModel(shipment.getOrder().getPayment());
        }

        List<ItemModel> listItems = new ArrayList<>();
        if (shipment.getOrder().getItems() != null) {
            for(Item item : shipment.getOrder().getItems()) {
                listItems.add(new ItemModel(item));
            }
        }
        this.items = listItems;
    }
}
