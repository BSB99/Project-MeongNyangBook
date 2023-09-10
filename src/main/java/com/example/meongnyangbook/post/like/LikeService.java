package com.example.meongnyangbook.post.like;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;


public interface LikeService {

  ApiResponseDto createPostLike(Long postId, User user);

  ApiResponseDto deletePostLike(Long postId, User user);

    Boolean confirmPostLike(Long postId, User user);
}
