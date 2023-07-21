package com.project.coffeeshop.dto;

import com.project.coffeeshop.model.UserModel;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private String dateOfBirth;
    private String email;
    private String username;
    private String reward;
    private Long point;
    private Boolean isActived;

    public UserDto(UserModel userModel){
        this.id = userModel.getId();
        this.fullName = userModel.getFullName();
        this.dateOfBirth = userModel.getDateOfBirth();
        this.email = userModel.getEmail();
        this.username = userModel.getUsername();
        this.reward = userModel.getReward();
        this.point = userModel.getPoint();
        this.isActived = userModel.getIsActived();
    }

    public UserDto(){}

}
