package com.project.coffeeshop.dto;

import lombok.Data;

@Data
public class RefreshRequest {

    Long userId;
    String refreshToken;
}
