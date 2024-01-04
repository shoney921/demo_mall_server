package com.example.demo_mall.member.dto;

import lombok.Data;

@Data
public class Member {
  private long customerId;
  private String firstName;
  private String lastName;
  private String company;
  private String address;
  private String city;
  private String state;
  private String country;
  private String postalCode;
  private String phone;
  private String fax;
  private String email;
  private long supportRepId;
}
