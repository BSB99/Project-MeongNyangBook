package com.example.meongnyangbook.post.comment;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.dto.CommentRequestDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.user.User;

public interface CommentService {

  /**
   * 댓글 생성
   *
   * @param user
   * @param commentRequestDto
   * @return
   */
  ApiResponseDto createComment(User user, CommentRequestDto commentRequestDto);

  /**
   * 댓글 수정
   *
   * @param user
   * @param Id
   * @param commentRequestDto
   * @return
   */
  CommentResponseDto updateComment(Long Id, User user, CommentRequestDto commentRequestDto);

  /**
   * 댓글 삭제
   *
   * @param user
   * @param Id
   * @return
   */
  ApiResponseDto deleteComment(Long Id, User user);

  Comment findComment(Long id);
}
