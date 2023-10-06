package com.project.coffeeshop.controller;

import com.project.coffeeshop.model.ItemModel;
import com.project.coffeeshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/items")
    public ResponseEntity<?> getAllItemOfUser(){
        ArrayList<ItemModel> itemModels = new ArrayList<>(itemService.getAllItemUser());
        return new ResponseEntity<>(itemModels, HttpStatus.OK);
    }
}
