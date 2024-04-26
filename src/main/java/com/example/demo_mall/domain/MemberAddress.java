package com.example.demo_mall.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "member_address")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member"})
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private String zipCode;

    private String address;

    private String addressDetail;

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
