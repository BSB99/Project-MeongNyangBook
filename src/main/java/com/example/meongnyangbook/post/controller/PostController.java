package com.example.meongnyangbook.post.controller;

import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.dto.PostResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.service.PostService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import com.example.meongnyangbook.post.community.service.CommunityService; // Import the CommunityService interface

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mya/posts")
public class PostController {
    private final PostService postService;
    private final CommunityService communityService; // Inject the CommunityService

    @Autowired
    public PostController(PostService postService, CommunityService communityService) {
        this.postService = postService;
        this.communityService = communityService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getBestPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Post> bestPosts = postService.getPostsByMostLikes();
        List<PostResponseDto> responseDtos = new ArrayList<>();

        for (Post post : bestPosts) {
            PostResponseDto responseDto = new PostResponseDto();
            // Map post attributes to responseDto here
            responseDtos.add(responseDto);
        }

        // userDetails에 저장된 사용자 정보를 활용하여 좋아요 여부를 판단하는 로직 구현

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/likes/{postId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        Post post = postService.getPostById(postId); // postId에 해당하는 게시글 가져오기

        if (post == null) {
            // 게시글이 없는 경우 에러 처리
            return ResponseEntity.badRequest().body("해당하는 게시글이 없습니다.");
        }

        postService.toggleLike(user, post); // 좋아요 토글
        boolean hasLiked = postService.hasLiked(user, post);

        if (post instanceof com.example.meongnyangbook.post.adoption.entity.Adoption) {
            if (hasLiked) {
                return ResponseEntity.ok("분양 게시글을 좋아합니다.");
            } else {
                return ResponseEntity.ok("분양 게시글 좋아요를 취소합니다.");
            }
        } else if (post instanceof com.example.meongnyangbook.post.community.entity.Community) {
            if (hasLiked) {
                return ResponseEntity.ok("커뮤니티 게시글을 좋아합니다.");
            } else {
                return ResponseEntity.ok("커뮤니티 게시글 좋아요를 취소합니다.");
            }
        } else {
            return ResponseEntity.ok("게시글을 좋아합니다.");
        }
    }

    @PostMapping("/communities/creation")
    public ResponseEntity<CommunityResponseDto> createCommunity(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        CommunityResponseDto responseDto = communityService.createCommunity(requestDto, user);
        return ResponseEntity.ok(responseDto);
    }
}