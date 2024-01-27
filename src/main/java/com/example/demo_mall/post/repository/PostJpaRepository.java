package com.example.demo_mall.post.repository;

import com.example.demo_mall.post.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

}
