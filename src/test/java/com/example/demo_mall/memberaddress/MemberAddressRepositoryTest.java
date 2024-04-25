package com.example.demo_mall.memberaddress;

import com.example.demo_mall.domain.MemberAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberAddressRepositoryTest {


    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Test
    @Transactional
    void test01() {
        List<MemberAddress> byMemberId = memberAddressRepository.findByMemberId(2L);

        for (MemberAddress memberAddress : byMemberId) {
            System.out.println("memberAddress = " + memberAddress);
        }
    }
}