package com.project.coffeeshop.controller;

import com.project.coffeeshop.dto.ResponseDto;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.exception.DeleteResponse;
import com.project.coffeeshop.model.DeliveryInfoModel;
import com.project.coffeeshop.service.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class DeliveryInfoController {

    @Autowired
    private DeliveryInfoService deliveryInfoService;

    @PostMapping("/post/delivery-info")
    public ResponseEntity<?> addNewDeliveryInfo(@RequestBody DeliveryInfoModel deliveryInfoModel){
        boolean result = deliveryInfoService.addDeliveryInfo(deliveryInfoModel);
        if(result){
            return new ResponseEntity<>(new ResponseDto("OK" , "Add new successful"), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new ResponseDto("ERROR" , "Add Fail"), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/delivery-info")
    public ResponseEntity<?> getDeliveryInfoOfUser(){
        ArrayList<DeliveryInfoModel> deliveryInfoModels = new ArrayList<>(deliveryInfoService.getDeliveryInfoOfUser());

        return new ResponseEntity<>(deliveryInfoModels,HttpStatus.OK);
    }

    @DeleteMapping("/delete/delivery-info/{id}")
    public ResponseEntity<?> deleteDeliveryInfoById(@PathVariable("id") Long id){
        try {
            deliveryInfoService.deleteDeliveryInfoOfUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/delivery-info")
    public ResponseEntity<?> deleteDeliveryInfo(@RequestBody DeliveryInfoModel deliveryInfoModel){
        boolean result = deliveryInfoService.deleteDeliveryInfo(deliveryInfoModel);
        if(result){
            return new ResponseEntity<>(new ResponseDto("OK" , "Delete successful"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDto("ERROR" , "Delete Fail"), HttpStatus.BAD_REQUEST);
        }
    }
}
