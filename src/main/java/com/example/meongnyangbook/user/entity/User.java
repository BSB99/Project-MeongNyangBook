package com.example.meongnyangbook.user.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.OAuth.OAuthProviderEnum;
import jakarta.persistence.*;
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private UserRoleEnum role;

    @Column(name = "oauth_provier", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OAuthProviderEnum oAuthProvider;


    public User(String username, String password, String nickname, String address, String phoneNumber, UserRoleEnum role, OAuthProviderEnum oAuthProvider) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.oAuthProvider = oAuthProvider;
    }

    @Builder
    public User(String username, String password, String nickname, UserRoleEnum role, OAuthProviderEnum oAuthProvider) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.oAuthProvider = oAuthProvider;
    }
}
