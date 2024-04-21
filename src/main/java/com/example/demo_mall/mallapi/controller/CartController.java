package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.controller.advise.ApiResponse;
import com.example.demo_mall.mallapi.dto.CartItemDto;
import com.example.demo_mall.mallapi.dto.CartItemListDto;
import com.example.demo_mall.mallapi.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Cart API", description = "장바구니 API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 추가/변경", description = "장바구니 추가 : cino 미존재, 장바구니 변경 : cino 존재, 장바구니 삭제 : qty = 0 인 경우 삭제")
    @PreAuthorize("#itemDto.memberId == T(java.lang.Long).parseLong(authentication.name)")
    @PostMapping("/change")
    public ApiResponse<List<CartItemListDto>> changeCart(@RequestBody CartItemDto itemDto) {
        List<CartItemListDto> result = itemDto.getQty() <= 0 ? cartService.remove(itemDto.getCino())
                : cartService.addOrModify(itemDto);
        return ApiResponse.success(result);
    }

    @Operation(summary = "장바구니 아이템 조회", description = "장바구니 아이템 조회")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/items")
    public ApiResponse<List<CartItemListDto>> getCartItems(Principal principal) {
        String memberId = principal.getName();
        return ApiResponse.success(cartService.getCartItems(Long.parseLong(memberId)));
    }

    @Operation(summary = "장바구니 아이템 삭제", description = "장바구니 아이템 삭제")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/{cino}")
    public ApiResponse<List<CartItemListDto>> removeFromCart(@PathVariable("cino") Long cino) {
        return ApiResponse.success(cartService.remove(cino));
    }
}
