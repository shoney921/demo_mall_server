package com.example.demo_mall.mallapi.dto;

import lombok.Data;

@Data
public class MemberSignupDto {

    private String email;

    private String password;

    private String nickname;

    private String mobile;
}
