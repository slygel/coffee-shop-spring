package com.project.coffeeshop.service;

import com.project.coffeeshop.dto.ShipmentDto;
import com.project.coffeeshop.entity.Order;
import com.project.coffeeshop.entity.Shipment;
import com.project.coffeeshop.dto.OrderShipmentResponse;
import com.project.coffeeshop.repo.OrderRepository;
import com.project.coffeeshop.repo.ShipmentRepository;
import com.project.coffeeshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Update false -> true shipment
    public int CompleteOrder(Long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            return shipmentRepository.orderCompleted(orderId);
        }
        return 0;
    }

    // Lấy ra tất các đơn ship
    public List<OrderShipmentResponse> getAllShip(){
        List<OrderShipmentResponse> shipmentDtos = new ArrayList<>();
        List<Shipment> shipments = shipmentRepository.getAllShip();
        Set<Shipment> uniqueShipments = new LinkedHashSet<>(shipments);

        List<Shipment> sortedShipments = new ArrayList<>(uniqueShipments);
        Collections.sort(sortedShipments, Comparator.comparing(Shipment::getId));

        for (Shipment shipment : sortedShipments) {
            shipmentDtos.add(new OrderShipmentResponse(shipment));
        }

        return shipmentDtos;
    }

    // Lấy ra đơn ship đã hoàn thành
    public List<OrderShipmentResponse> getAllShipComplete(){
        List<OrderShipmentResponse> shipmentDtos = new ArrayList<>();
        List<Shipment> shipments = shipmentRepository.getAllOrderCompleted();
        Set<Shipment> uniqueShipments = new LinkedHashSet<>(shipments);

        List<Shipment> sortedShipments = new ArrayList<>(uniqueShipments);
        Collections.sort(sortedShipments, Comparator.comparing(Shipment::getId));

        for (Shipment shipment : sortedShipments) {
            shipmentDtos.add(new OrderShipmentResponse(shipment));
        }

        return shipmentDtos;
    }

    // Lấy ra đơn mà ship đang giao
    public List<OrderShipmentResponse> getAllShipIsGoing(){
        List<OrderShipmentResponse> shipmentDtos = new ArrayList<>();
        List<Shipment> shipments = shipmentRepository.getAllOrderIsGoing();
        Set<Shipment> uniqueShipments = new LinkedHashSet<>(shipments);

        List<Shipment> sortedShipments = new ArrayList<>(uniqueShipments);
        Collections.sort(sortedShipments, Comparator.comparing(Shipment::getId));

        for (Shipment shipment : sortedShipments) {
            shipmentDtos.add(new OrderShipmentResponse(shipment));
        }

        return shipmentDtos;
    }

}
