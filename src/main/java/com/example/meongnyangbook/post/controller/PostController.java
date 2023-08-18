package com.example.meongnyangbook.post.controller;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.meongnyangbook.user.entity.User;

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

    @PostMapping("/adoptions/likes/{postId}")
    public ResponseEntity<String> likeAdoptionPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails; // userDetails를 User로 캐스팅
        Post post = postService.getPostById(postId); // postId에 해당하는 게시글 가져오기

        if (post == null) {
            // 게시글이 없는 경우 에러 처리
            return ResponseEntity.badRequest().body("해당하는 게시글이 없습니다.");
        }

        postService.toggleLike(user, post); // 좋아요 토글
        boolean hasLiked = postService.hasLiked(user, post);

        if (hasLiked) {
            return ResponseEntity.ok("분양 게시글을 좋아합니다.");
        } else {
            return ResponseEntity.ok("분양 게시글 좋아요를 취소합니다.");
        }
    }

    @PostMapping("/communities/likes/{postId}")
    public ResponseEntity<String> likeCommunityPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails; // userDetails를 User로 캐스팅
        Post post = postService.getPostById(postId); // postId에 해당하는 게시글 가져오기

        if (post == null) {
            // 게시글이 없는 경우 에러 처리
            return ResponseEntity.badRequest().body("해당하는 게시글이 없습니다.");
        }

        postService.toggleLike(user, post); // 좋아요 토글
        boolean hasLiked = postService.hasLiked(user, post);

        if (hasLiked) {
            return ResponseEntity.ok("커뮤니티 게시글을 좋아합니다.");
        } else {
            return ResponseEntity.ok("커뮤니티 게시글 좋아요를 취소합니다.");
        }
    }
}
