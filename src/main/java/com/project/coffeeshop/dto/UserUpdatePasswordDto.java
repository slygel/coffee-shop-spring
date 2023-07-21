package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class UserUpdatePasswordDto {

    private String username;
    private String oldPassword;
    private String newPassword;

    public UserUpdatePasswordDto() {
    }

    public UserUpdatePasswordDto(String username, String oldPassword, String newPassword) {
        super();
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
