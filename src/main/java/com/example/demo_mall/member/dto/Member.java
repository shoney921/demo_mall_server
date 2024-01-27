package com.example.demo_mall.member.dto;

import lombok.Data;

@Data
public class Member {

  private long memberId;
  private String name;
  private String birth;
  private String mobile;
  private String email;
  private String password;

}
