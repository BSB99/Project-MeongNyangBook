package com.example.meongnyangbook.post.like.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.entity.User;
import org.springframework.http.ResponseEntity;


public interface LikeService {
    ResponseEntity<ApiResponseDto> createPostLike(Long postId, User user);

    ResponseEntity<ApiResponseDto> deletePostLike(Long postId, User user);
}
