package com.example.meongnyangbook.post.comment.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.post.dto.CommentRequestDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;

public interface CommentService {

    CommentResponseDto createComment(User user, CommentRequestDto commentRequestDto);

    CommentResponseDto updateComment(Long Id, User user, CommentRequestDto commentRequestDto);

    ApiResponseDto deleteComment(Long Id, User user);

    Comment findComment(Long id);

    Post findPost(Long id);
}
