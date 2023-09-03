package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.User;
import lombok.Getter;

@Getter
public class ChatResponseDto {

    private Long id;
    private String username;
    private String nickname;
    private String fileList;

    public ChatResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.fileList = user.getProfileImgurl();
    }
}
