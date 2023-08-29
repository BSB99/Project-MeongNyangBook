package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private String nickname;

    public UserInfoResponseDto(User user) {
        this.nickname = user.getNickname();
    }
}
