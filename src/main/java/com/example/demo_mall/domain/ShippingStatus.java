package com.example.demo_mall.domain;

public enum ShippingStatus {
    SHIPPING_PREPARATION("배송 준비 중"),
    SHIPPING_IN_PROGRESS("배송 중"),
    DELIVERED("배송 완료"),
    DELIVERY_FAILED("배송 실패");

    private final String status;

    ShippingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
