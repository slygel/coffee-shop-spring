package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class ShipperRegisterDto {

    private String fullName;
    private String dateOfBirth;
    private String email;
    private String username;
    private String password;
}
