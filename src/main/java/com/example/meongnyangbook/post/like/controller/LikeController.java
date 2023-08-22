package com.example.meongnyangbook.post.like.controller;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.like.service.LikeService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "좋아요 API")
@RequiredArgsConstructor
@RequestMapping("/mya/likes")
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "좋아요 등록")
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> createPostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = likeService.createPostLike(postId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "좋아요 취소")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = likeService.deletePostLike(postId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
