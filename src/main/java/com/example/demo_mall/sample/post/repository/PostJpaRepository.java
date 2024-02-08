package com.example.demo_mall.sample.post.repository;

import com.example.demo_mall.sample.post.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

}
