package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;

    public UserLoginDto(){}

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
