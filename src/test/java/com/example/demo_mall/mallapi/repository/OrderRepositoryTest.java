package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;


    @Test
    void testCreateOrder() {

        Member referenceById = memberRepository.getReferenceById(12L);
        Product product1 = productRepository.getReferenceById(90L);
        Product product2 = productRepository.getReferenceById(91L);

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentMethod("Credit Card");
        paymentInfo.setPaymentStatus("complete");

        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.setShippingAddress("123 Main St");
        shippingInfo.setRecipientName("홍길동");
        shippingInfo.setContactNumber("01022223333");
        shippingInfo.setShippingStatus(ShippingStatus.SHIPPING_PREPARATION);

        // 주문 생성
        Order build = Order.builder()
//                .id(1L)
                .orderDate(LocalDateTime.now())
                .member(referenceById)
                .products(List.of(product1, product2))
                .paymentInfo(paymentInfo)
                .shippingInfo(shippingInfo)
                .orderStatus(OrderStatus.ORDER_RECEIVED)
                .build();

        orderRepository.save(build);
    }

}