package com.example.demo_mall.member.repository;

import com.example.demo_mall.member.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByCustomerNumber(Long id);
}
