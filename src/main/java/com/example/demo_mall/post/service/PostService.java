package com.example.demo_mall.post.service;

import com.example.demo_mall.post.dto.Post;
import com.example.demo_mall.post.dto.ReqUpdatePostDto;
import com.example.demo_mall.post.repository.PostJpaRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Post updatePost(Long postId, ReqUpdatePostDto reqDto) {
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다: " + postId));

        post.setTitle(reqDto.getTitle());
        post.setContent(reqDto.getContent());

        return postJpaRepository.save(post);
    }

    public void deletePost(String id) {
        postJpaRepository.deleteById(Long.parseLong(id));
    }
}
