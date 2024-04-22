package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.domain.Cart;
import com.example.demo_mall.domain.CartItem;
import com.example.demo_mall.domain.Member;
import com.example.demo_mall.domain.Product;
import com.example.demo_mall.mallapi.dto.CartItemListDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@Log4j2
class CartItemRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void testListOfMember() {
        Long memberId = 9L;

        List<CartItemListDto> itemsOfCartDtoById = cartItemRepository.getItemsOfCartDtoByMemberId(memberId);

        log.info(itemsOfCartDtoById);
    }


    @Test
    public void testGetCartFromItem() {
        Long cartFromItem = cartItemRepository.getCartFromItem(1L);

        log.info(cartFromItem);
    }

    @Transactional
    @Commit
    @Test
    public void testInsertByProduct() {

        Long memberId = 9L;
        Long pno = 184L;
        int qty = 1;

        // 사용자와 상품번호로 장바구니에 아이템이 있는지 확인 (있으면 수량 변경, 없으면 추가)
        CartItem cartItem = cartItemRepository.getItemOfPno(memberId, pno);

        if (cartItem != null) {
            cartItem.changeQty(cartItem.getQty()+1);
            cartItemRepository.save(cartItem);
            return;
        }
        Optional<Cart> cartOfMember = cartRepository.getCartOfMember(memberId);

        Cart cart = null;
        if (cartOfMember.isEmpty()) { // 장바구니 자체가 없는 경우
            Member member = Member.builder().id(memberId).build();
            Cart tempCart = Cart.builder().owner(member).build();
            cart = cartRepository.save(tempCart);
        } else { // 장바구니는 있지만, 장바구니에 아이템이 없는 경우
            cart = cartOfMember.get();
        }

        Product product = Product.builder().pno(pno).build();
        cartItem = CartItem.builder().cart(cart).product(product).qty(qty).build();

        cartItemRepository.save(cartItem);
    }


    @Transactional
    @Commit
    @Test
    public void testUpdateByCino() {
        Long cino = 5L;
        int qty = 4;

        Optional<CartItem> byId = cartItemRepository.findById(cino);

        CartItem cartItem = byId.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);
    }

    @Test
    public void testDeleteThenList() {

        Long cino = 4L;

        Long cno = cartItemRepository.getCartFromItem(cino);

        cartItemRepository.deleteById(cino);

        List<CartItemListDto> cartItemListDtos = cartItemRepository.getItemsOfCartDtoByCart(cno);

        for (CartItemListDto cartItemListDto : cartItemListDtos) {
            log.info(cartItemListDto);
        }

    }

    @Test
    @Transactional
    public void removeTest() {
        Long cino = 10L;

        cartItemRepository.deleteByCino(cino);

        Long cartFromItem = cartItemRepository.getCartFromItem(cino);
    }

}