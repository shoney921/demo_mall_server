package com.example.demo_mall.sample.member.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data //@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)  //json 변환만 한다면 해당 어노테이션도 사용가능
public class ReqMemberDto {

  private String customerName;
  private String customerLastName;
  private String customerFirstName;
  private String phone;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String state;
  private String postalCode;
  private String country;
  private long salesRepEmployeeNumber;
  private double creditLimit;
}
