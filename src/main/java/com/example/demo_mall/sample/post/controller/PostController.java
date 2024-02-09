package com.example.demo_mall.sample.post.controller;

import com.example.demo_mall.sample.post.dto.Post;
import com.example.demo_mall.sample.post.dto.ReqUpdatePostDto;
import com.example.demo_mall.sample.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable("postId") String id) {
        Optional<Post> post = postService.getPost(id);
        return post.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable("postId") String id, @RequestBody ReqUpdatePostDto req) {
        return postService.updatePost(Long.parseLong(id), req);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") String id) {
        postService.deletePost(id);
    }
}
