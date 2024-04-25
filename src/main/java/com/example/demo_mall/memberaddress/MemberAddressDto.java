package com.example.demo_mall.memberaddress;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberAddressDto {
    private Long memberId;
    private Long ano;
    private String name;
    private String zipCode;
    private String address;
    private String addressDetail;
}
