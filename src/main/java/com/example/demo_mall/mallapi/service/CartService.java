package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.dto.CartItemDto;
import com.example.demo_mall.mallapi.dto.CartItemListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface CartService {

    public List<CartItemListDto> addOrModify(CartItemDto cartItemDto);

    public List<CartItemListDto> getCartItems(Long memberId);

    public List<CartItemListDto> remove(Long cino);
}
