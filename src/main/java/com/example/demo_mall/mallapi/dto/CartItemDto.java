package com.example.demo_mall.mallapi.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long memberId;

    private Long pno;

    private int qty;

    private Long cino;
}
