package com.example.meongnyangbook.user.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import jakarta.persistence.*;
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

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private UserRoleEnum role;


    //address, phoneNumber Dto 상에서 Blank

    //직접 회원가입 시 소셜로그인 구분 Enum Origin값 넣어주기
    public User(String username, String password, String nickname, String address, String phoneNumber, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void setBlock(UserRoleEnum userRoleEnum) {

        this.role =userRoleEnum;
    }

    public void setProfile(ProfileRequestDto profileRequestDto) {
        this.nickname = profileRequestDto.getNickname();
        this.address = profileRequestDto.getAddress();
        this.phoneNumber = profileRequestDto.getPhoneNumber();
    }
}
