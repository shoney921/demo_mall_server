package com.example.demo_mall.mallapi.domain;

public enum OrderStatus {
    ORDER_RECEIVED("주문 접수"),
    PAYMENT_COMPLETED("결제 완료"),
    SHIPPING_IN_PROGRESS("배송 준비 중"),
    SHIPPING_COMPLETED("배송 완료"),
    CANCELLED("주문 취소");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
