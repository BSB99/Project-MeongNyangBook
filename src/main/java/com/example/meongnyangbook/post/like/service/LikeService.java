package com.example.meongnyangbook.post.like.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.entity.User;


public interface LikeService {
    ApiResponseDto createPostLike(Long postId, User user);

    ApiResponseDto deletePostLike(Long postId, User user);
}
