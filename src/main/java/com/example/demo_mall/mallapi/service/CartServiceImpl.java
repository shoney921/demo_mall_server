package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.domain.Cart;
import com.example.demo_mall.mallapi.domain.CartItem;
import com.example.demo_mall.mallapi.domain.Member;
import com.example.demo_mall.mallapi.domain.Product;
import com.example.demo_mall.mallapi.dto.CartItemDto;
import com.example.demo_mall.mallapi.dto.CartItemListDto;
import com.example.demo_mall.mallapi.repository.CartItemRepository;
import com.example.demo_mall.mallapi.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;


    @Override
    public List<CartItemListDto> addOrModify(CartItemDto cartItemDto) {
        Long memberId = cartItemDto.getMemberId();
        Long pno = cartItemDto.getPno();
        int qty = cartItemDto.getQty();
        Long cino = cartItemDto.getCino();

        if (cino != null) {
            Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);
            CartItem cartItem = cartItemResult.orElseThrow();
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return getCartItems(memberId);
        }

        Cart cart = getCart(memberId);
        CartItem cartItem = cartItemRepository.getItemOfPno(memberId, pno);

        if (cartItem == null) {
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).qty(qty).build();
        } else {
            cartItem.changeQty(qty);
        }
        cartItemRepository.save(cartItem);
        return getCartItems(memberId);
    }

    private Cart getCart(Long memberId) {
        Optional<Cart> cartOfMember = cartRepository.getCartOfMember(memberId);
        if (cartOfMember.isEmpty()) {
            log.info("Cart of the member is not exist.");
            Member member = Member.builder().id(memberId).build();
            Cart tempCart = Cart.builder().owner(member).build();
            return cartRepository.save(tempCart);
        }
        return cartOfMember.get();
    }

    @Override
    public List<CartItemListDto> getCartItems(Long memberId) {
        return cartItemRepository.getItemsOfCartDtoByMemberId(memberId);
    }

    @Override
    public List<CartItemListDto> remove(Long cino) {
        Long cno = cartItemRepository.getCartFromItem(cino);
        cartItemRepository.deleteById(cino);
        return cartItemRepository.getItemsOfCartDtoByCart(cno);
    }
}
