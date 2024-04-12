package com.example.demo_mall.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemListDto {

    private Long cino;

    private Long pno;

    private int qty;

    private String pname;

    private int price;

    private String imageFile;
}
