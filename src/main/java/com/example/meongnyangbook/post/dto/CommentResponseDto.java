package com.example.meongnyangbook.post.dto;

import com.example.meongnyangbook.post.comment.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private Long commentId;
  private String content;
  private Long userId;
  private String userNickname;

  public CommentResponseDto(Comment comment) {
    this.commentId = comment.getId();
    this.content = comment.getContent();
    this.userId = comment.getUser().getId();
    this.userNickname = comment.getUser().getNickname();
  }
}
