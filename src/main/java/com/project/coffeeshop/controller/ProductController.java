package com.project.coffeeshop.controller;

import com.project.coffeeshop.entity.Product;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.exception.DeleteResponse;
import com.project.coffeeshop.model.ProductModel;
import com.project.coffeeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProduct(){
        ArrayList<ProductModel> productModels = new ArrayList<>(productService.getAllProducts());
        return new ResponseEntity<>(productModels, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/product-id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        try{
            ProductModel productModel = productService.getProductModelById(id);
            return new ResponseEntity<>(productModel, HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/product-name/{name}")
    public ResponseEntity<ProductModel> getProductByName(@PathVariable("name") String name){
        ProductModel productModel = productService.getProductModelByName(name);
        return new ResponseEntity<>(productModel,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post/product")
    public void addProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        try {
            productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/product/{id}")
    public void update(@PathVariable("id") Long id,
                                               @RequestBody Product product){
        productService.updateProduct(id , product);
    }

}
