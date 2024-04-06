package com.example.demo_mall.mallapi.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long id;

    private Long pno;

    private int qty;

    private Long cino;
}
