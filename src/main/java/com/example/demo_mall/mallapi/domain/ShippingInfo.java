package com.example.demo_mall.mallapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ShippingInfo {

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status", nullable = false)
    private ShippingStatus shippingStatus;

}
