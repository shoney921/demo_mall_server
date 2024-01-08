package com.example.demo_mall.member.dto;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Data //@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)  //json 변환만 한다면 해당 어노테이션도 사용가능
@Entity(name = "customers")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long customerNumber;
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
