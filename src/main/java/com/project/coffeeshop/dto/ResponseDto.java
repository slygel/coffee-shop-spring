package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class ResponseDto {

    private String status;
    private String data;
    private String refreshToken;

    public ResponseDto(String status, String data) {
        super();
        this.status = status;
        this.data = data;
    }

    public ResponseDto(String status, String data , String refreshToken) {
        super();
        this.status = status;
        this.data = data;
        this.refreshToken = refreshToken;
    }

    public ResponseDto(){}
}
