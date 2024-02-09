package com.example.demo_mall.sample.member.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "Member")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;
  private String name;
  private String birth;
  private String mobile;
  private String email;
  private String password;

}
