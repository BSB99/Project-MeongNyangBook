package com.example.meongnyangbook.post.like.controller;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.like.service.LikeService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{post_id}/like")
    public ResponseEntity<ApiResponseDto> createPostLike(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return likeService.createPostLike(post_id, userDetails.getUser());
    }
    @DeleteMapping("/{post_id}/like")
    public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return likeService.deletePostLike(post_id, userDetails.getUser());
    }
}
