package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private Long userId;
  private String nickname;
  private String profileImg;
  private String role;

  public UserInfoResponseDto(User user) {
    this.userId = user.getId();
    this.nickname = user.getNickname();
    this.profileImg = user.getProfileImgurl();
    this.role = user.getRole().toString();
  }
}
