package com.project.coffeeshop.controller;

import com.project.coffeeshop.dto.OrderDto;
import com.project.coffeeshop.dto.ResponseDto;
import com.project.coffeeshop.exception.DeleteResponse;
import com.project.coffeeshop.model.OrderModel;
import com.project.coffeeshop.service.OrderService;
import com.project.coffeeshop.service.ShipmentService;
import org.hibernate.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderModel>> getAllOrderOfUser(){
        List<OrderModel> orderModels = orderService.getAllOrders();

        return new ResponseEntity<>(orderModels, HttpStatus.OK);
    }

    @PostMapping("/post/order")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDto orderDto){
        try{
            boolean result = orderService.createOrder(orderDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (CacheException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{orderId}/total-amount")
    public ResponseEntity<Double> getTotalAmount(@PathVariable("orderId") Long orderId) {
        double totalAmount = orderService.getTotalAmount(orderId);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SHIPPER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/order-received/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable(value = "id") Long id){
        int result = shipmentService.CompleteOrder(id);
        if(result != 0) {
            return new ResponseEntity<>(new ResponseDto("OK", "Completed"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto("ERROR", "Update fail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/complete")
    public ResponseEntity<List<OrderModel>> getCompleteOrder(){
        List<OrderModel> orderModels = orderService.getCompleteOrder();
        return new ResponseEntity<>(orderModels,HttpStatus.OK);
    }

    @GetMapping("/orders/is-going")
    public ResponseEntity<List<OrderModel>> getIsGoingOrder(){
        List<OrderModel> orderModels = orderService.getIsGoingOrder();
        return new ResponseEntity<>(orderModels,HttpStatus.OK);
    }
}
