package com.example.meongnyangbook.post.controller;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<List<Post>> getBestPosts(@AuthenticationPrincipal UserDetails userDetails) {
        List<Post> bestPosts = postService.getPostsByMostLikes();

        // userDetails에 저장된 사용자 정보를 활용하여 좋아요 여부를 판단하는 로직 구현

        return ResponseEntity.ok(bestPosts);
    }
}
