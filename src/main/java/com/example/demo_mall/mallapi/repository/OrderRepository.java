package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Order;
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

public interface OrderRepository extends JpaRepository<Order, Long>, ProductSearch {

}
