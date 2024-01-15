package com.example.demo_mall.member.repository;

import com.example.demo_mall.member.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCustomerNumber(Long id);
}
