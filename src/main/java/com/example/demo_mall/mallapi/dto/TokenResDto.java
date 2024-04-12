package com.example.demo_mall.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TokenResDto {
    private Long id;
    private String email;
    private String nickname;
    private List<String> roleNames;
    private Boolean social;
    private String accessToken;
    private String refreshToken;
}
