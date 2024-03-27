package com.example.demo_mall.mallapi.dto;

import lombok.Data;

@Data
public class MemberSignupDto {

    private String email;

    private String pw;

    private String nickname;
}
