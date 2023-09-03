package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private Long userId;
    private String nickname;

    public UserInfoResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
    }
}
