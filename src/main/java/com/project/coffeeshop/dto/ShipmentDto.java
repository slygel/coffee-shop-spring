package com.project.coffeeshop.dto;

import com.project.coffeeshop.entity.Shipment;
import com.project.coffeeshop.model.DeliveryInfoModel;
import lombok.Data;

@Data
public class ShipmentDto {

    private Long id;
    private DeliveryInfoModel deliveryInfo;
    private String isCompleted;
    private String shipperId;
    private String shipperName;
    private String shipperPhone;
    private Long orderId;

    public ShipmentDto(Shipment shipment) {
        this.id = shipment.getId();
        if(shipment.getDeliveryInfo() != null){
            this.deliveryInfo = new DeliveryInfoModel(shipment.getDeliveryInfo());
        }
        this.isCompleted = shipment.getIsCompleted();
        this.shipperId = shipment.getShipperId();
        this.shipperName = shipment.getShipperName();
        this.shipperPhone = shipment.getShipperPhone();
        this.orderId = shipment.getOrder().getId();
    }
}
