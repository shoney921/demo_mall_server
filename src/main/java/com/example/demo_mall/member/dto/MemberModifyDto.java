package com.example.demo_mall.mallapi.dto;

import lombok.Data;

@Data
public class MemberModifyDto {

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String mobile;
}
