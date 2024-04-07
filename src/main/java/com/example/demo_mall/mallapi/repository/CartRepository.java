package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select cart from Cart cart where cart.owner.id = :memberId")
    Optional<Cart> getCartOfMember(@Param("memberId") Long memberId);



}

