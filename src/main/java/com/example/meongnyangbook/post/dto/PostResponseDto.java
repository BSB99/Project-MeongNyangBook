package com.example.meongnyangbook.post.dto;

import com.example.meongnyangbook.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private String title;
    private String nickname;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
    }
}
