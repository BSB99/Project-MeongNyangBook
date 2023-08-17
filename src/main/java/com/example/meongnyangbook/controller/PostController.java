package com.example.meongnyangbook.controller;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mya/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getBestPosts() {
        List<Post> bestPosts = postService.getPostsByMostLikes();
        return ResponseEntity.ok(bestPosts);
    }
}
