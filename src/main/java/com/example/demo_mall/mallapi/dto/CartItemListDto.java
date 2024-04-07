package com.example.demo_mall.mallapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDto {

    private Long cino;

    private int qty;

    private String pname;

    private int price;

    private String imageFile;

    public CartItemListDto(Long cino, int qty, String pname, int price, String imageFile) {
        this.cino = cino;
        this.qty = qty;
        this.pname = pname;
        this.price = price;
        this.imageFile = imageFile;
    }
}
