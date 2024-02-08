package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
