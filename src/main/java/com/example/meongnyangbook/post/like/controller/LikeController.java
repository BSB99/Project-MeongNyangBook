package com.example.meongnyangbook.post.like.controller;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.like.service.LikeService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> createPostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = likeService.createPostLike(postId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = likeService.deletePostLike(postId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
