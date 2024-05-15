package com.example.demo_mall.mallapi.dto.member;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResDto {
    private Long id;
    private String email;
    private String nickname;
    private String mobile;
    private List<String> roleNames;
    private Boolean social;
    private String accessToken;
    private String refreshToken;
}
