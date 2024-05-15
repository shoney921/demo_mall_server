package com.example.demo_mall.memberaddress;

import com.example.demo_mall.mallapi.domain.MemberAddress;
import com.example.demo_mall.mallapi.repository.MemberAddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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