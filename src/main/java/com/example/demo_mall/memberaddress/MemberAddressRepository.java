package com.example.demo_mall.memberaddress;

import com.example.demo_mall.domain.Member;
import com.example.demo_mall.domain.MemberAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {

    List<MemberAddress> findByMemberId(Long memberId);



}
