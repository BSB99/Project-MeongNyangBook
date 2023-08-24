package com.example.meongnyangbook.user.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.OAuth.OAuthProviderEnum;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  private String address;

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "role", nullable = false)
  private UserRoleEnum role;

  @Column(name = "profile_imgurl")
  private String profileImgurl;


  @Column(name = "oauth_provier", nullable = false)
  @Enumerated(value = EnumType.STRING)

  private OAuthProviderEnum oAuthProvider;


  public User(String username, String password, String nickname, String address, String phoneNumber,
      UserRoleEnum role, OAuthProviderEnum oAuthProvider) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.oAuthProvider = oAuthProvider;
  }

  @Builder
  public User(String username, String password, String nickname, UserRoleEnum role,
      OAuthProviderEnum oAuthProvider) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.role = role;
    this.oAuthProvider = oAuthProvider;
  }

  public void setBlock(UserRoleEnum userRoleEnum) {

    this.role = userRoleEnum;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setProfile(ProfileRequestDto profileRequestDto) {
    this.nickname = profileRequestDto.getNickname();
    this.address = profileRequestDto.getAddress();
    this.phoneNumber = profileRequestDto.getPhoneNumber();
  }

  public void setProfileImgurl(String filePath) {
    this.profileImgurl = filePath;
  }
}
