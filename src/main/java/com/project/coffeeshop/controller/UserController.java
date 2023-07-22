package com.project.coffeeshop.controller;

import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.exception.DeleteResponse;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.UserModel;
import com.project.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user/info/getAll")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        ArrayList<UserModel> userModels = new ArrayList<>(userService.getAllUsers());
        return new ResponseEntity<>(userModels, HttpStatus.OK);
    }

    // admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user/info/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        try{
            UserModel userModel = userService.getUserById(id);
            return new ResponseEntity<>(userModel,HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/user/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/update/{id}")
    public void update(@PathVariable("id") Long id ,
                       @RequestBody User user){
        userService.update(id,user);
    }
}
