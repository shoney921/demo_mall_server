package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.CartItemDto;
import com.example.demo_mall.mallapi.dto.CartItemListDto;
import com.example.demo_mall.mallapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PreAuthorize("#itemDto.memberId == T(java.lang.Long).parseLong(authentication.name)")
    @PostMapping("/change")
    public List<CartItemListDto> changeCart(@RequestBody CartItemDto itemDto) {
        if (itemDto.getQty() <= 0) {
            return cartService.remove(itemDto.getCino());
        }
        return cartService.addOrModify(itemDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/items")
    public List<CartItemListDto> getCartItems(Principal principal) {
        String memberId = principal.getName();
        log.info("login한 사용자 id:" + memberId);
        return cartService.getCartItems(Long.parseLong(memberId));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/{cino}")
    public List<CartItemListDto> removeFromCart(@PathVariable("cino") Long cino) {
        log.info("cart item no: " + cino);
        return cartService.remove(cino);
    }
}
