package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Qna;
import com.example.demo_mall.mallapi.repository.search.QnaSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long>, QnaSearch {

    @Override
    Page<Qna> findAll(Pageable pageable);
}
