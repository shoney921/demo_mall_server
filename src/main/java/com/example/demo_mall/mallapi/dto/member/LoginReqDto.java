package com.example.demo_mall.mallapi.dto.member;

import lombok.Data;

@Data
public class LoginReqDto {
    private String email;
    private String password;
}
