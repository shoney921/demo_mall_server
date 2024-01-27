package com.example.demo_mall.post.service;

import com.example.demo_mall.post.dto.Post;
import com.example.demo_mall.post.repository.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostJpaRepository postJpaRepository;

    public List<Post> getAllPosts() {
        return postJpaRepository.findAll();
    }

    public Optional<Post> getPost(String id) {
        return postJpaRepository.findById(Long.parseLong(id));
    }

    public Post createPost(Post post) {
        return postJpaRepository.save(post);
    }
}
