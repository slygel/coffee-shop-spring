package com.project.coffeeshop.controller;

import com.project.coffeeshop.dto.OrderShipmentResponse;
import com.project.coffeeshop.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/shipments")
    public ResponseEntity<List<OrderShipmentResponse>> getAllShip(){
        ArrayList<OrderShipmentResponse> shipmentDtos = new ArrayList<>(shipmentService.getAllShip());
        return new ResponseEntity<>(shipmentDtos, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_SHIPPER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/shipments/complete")
    public ResponseEntity<List<OrderShipmentResponse>> getShipComplete(){
        List<OrderShipmentResponse> shipmentDtos = shipmentService.getAllShipComplete();
        return new ResponseEntity<>(shipmentDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SHIPPER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/shipments/is-going")
    public ResponseEntity<List<OrderShipmentResponse>> getShipIsGoing(){
        List<OrderShipmentResponse> shipmentDtos = shipmentService.getAllShipIsGoing();
        return new ResponseEntity<>(shipmentDtos, HttpStatus.OK);
    }
}
