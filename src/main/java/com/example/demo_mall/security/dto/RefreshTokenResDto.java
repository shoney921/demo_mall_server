package com.example.demo_mall.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RefreshTokenResDto {

    private String accessToken;
    private String refreshToken;
}
