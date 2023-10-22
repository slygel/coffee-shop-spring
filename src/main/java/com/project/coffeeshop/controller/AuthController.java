package com.project.coffeeshop.controller;

import com.project.coffeeshop.constant.Constant;
import com.project.coffeeshop.dto.*;
import com.project.coffeeshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
@RestController
public class AuthController {

    @Autowired
    private AuthService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserLoginDto userLoginDto){
        ResponseDto responseDto = userService.authenticateUser(userLoginDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserRegisterDto userRegisterDto){
        String result = userService.register(userRegisterDto);
        ResponseDto responseDto;
        switch (result){
            case Constant.DUPLICATE_USERNAME:
                responseDto = new ResponseDto("ERROR", "Duplicate user");
                break;

            case Constant.USED_EMAIL:
                responseDto = new ResponseDto("ERROR", "Used email");
                break;

            case Constant.OTHER_ERROR:
                responseDto = new ResponseDto("ERROR", "Other error");
                break;

            default:
                responseDto = new ResponseDto("OK", "Successful registration");
                break;
        }
        return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<ResponseDto> admin_register(@RequestBody AdminRegisterDto adminRegisterDto){
        String result = userService.adminRegister(adminRegisterDto);
        ResponseDto responseDto;
        switch (result){
            default:
                responseDto = new ResponseDto("OK" , "Successful registration");
                break;
        }
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/shipper/register")
    public ResponseEntity<ResponseDto> shipper_register(@RequestBody ShipperRegisterDto shipperRegisterDto){
        String result = userService.shipperRegister(shipperRegisterDto);
        ResponseDto responseDto;
        switch (result){
            default:
                responseDto = new ResponseDto("OK" , "Successful registration");
                break;
        }
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody UserUpdatePasswordDto userUpdatePasswordDto){
        String result = userService.updatePassword(userUpdatePasswordDto);
        ResponseDto responseDto;
        switch (result){
            case Constant.WRONG_USERNAME_PASSWORD:
                responseDto = new ResponseDto("ERROR" , "Wrong username or password");
                break;

            case Constant.OTHER_ERROR:
                responseDto = new ResponseDto("ERROR", "Other error");
                break;

            default:
                responseDto = new ResponseDto("OK", "Password is changed");
                break;
        }
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto> refreshToken(@RequestBody RefreshRequest refreshRequest){
        ResponseDto responseDto = userService.refreshToken(refreshRequest);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user/info")
    public ResponseEntity<UserDto> getOwnUserInfo(){
        UserDto userDto = userService.getUserInfo();
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

}
