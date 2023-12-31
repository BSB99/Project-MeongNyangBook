package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {

  private String username;
  private String nickname;
  private String address;
  private String phoneNumber;
  private String introduce;
  private String fileList;


  public ProfileResponseDto(User user) {
    this.username = user.getUsername();
    this.nickname = user.getNickname();
    this.address = user.getAddress();
    this.phoneNumber = user.getPhoneNumber();
    this.introduce = user.getIntroduce();
    this.fileList = user.getProfileImgurl();
  }

}
