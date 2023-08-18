package com.example.meongnyangbook.post.dto;

import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.user.entity.User;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private String nickname;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
    }
}
