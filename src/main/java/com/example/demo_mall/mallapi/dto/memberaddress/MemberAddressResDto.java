package com.example.demo_mall.mallapi.dto.memberaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressResDto {
    private Long ano;
    private String name;
    private String zipCode;
    private String address;
    private String addressDetail;
}
