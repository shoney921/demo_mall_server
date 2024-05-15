package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Product;
import com.example.demo_mall.mallapi.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ElementCollection 조인해서 조회하는 방법
    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
    @Query("update Product p set p.delFlag = :delFlag where p.pno = :pno")
    void updateToDelete(@Param("delFlag") boolean flag,
                        @Param("pno") Long pno);

    @Query("select p, pi from Product p left join p.imageList pi on pi.ord = 0 where p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
