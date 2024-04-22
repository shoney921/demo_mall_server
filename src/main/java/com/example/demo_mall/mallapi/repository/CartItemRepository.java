package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.domain.CartItem;
import com.example.demo_mall.mallapi.dto.CartItemListDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select " +
            "new com.example.demo_mall.mallapi.dto.CartItemListDto(ci.cino, p.pno, ci.qty, p.pname, p.price, pi.fileName) " +
            "from CartItem ci " +
            "inner join Cart mc on ci.cart = mc  " +
            "left join Product p on ci.product = p " +
            "left join p.imageList pi " +
            "where mc.owner.id = :id " +
            "and pi.ord = 0 " +
            "order by ci.cino desc")
    List<CartItemListDto> getItemsOfCartDtoByMemberId(@Param("memberId") Long id);

    @Query("select ci " +
            "from CartItem ci " +
            "left join Cart c on ci.cart = c " +
            "where c.owner.id = :memberId " +
            "and ci.product.pno = :pno")
    CartItem getItemOfPno(@Param("memberId") Long memberId, @Param("pno") Long pno);

    @Query("select c.cno from Cart c " +
            "left join CartItem ci on ci.cart = c " +
            "where ci.cino = :cino")
    Long getCartFromItem(@Param("cino") Long cino);

    @Query("select " +
            "new com.example.demo_mall.mallapi.dto.CartItemListDto(ci.cino, p.pno, ci.qty, p.pname, p.price, pi.fileName) " +
            "from CartItem ci " +
            "inner join Cart c on ci.cart = c " +
            "left join Product p on ci.product = p " +
            "left join p.imageList pi " +
            "where pi.ord = 0 " +
            "and c.cno = :cno " +
            "order by ci.cino desc")
    List<CartItemListDto> getItemsOfCartDtoByCart(@Param("cno") Long cno);

    void deleteByCino(Long cino);
}

