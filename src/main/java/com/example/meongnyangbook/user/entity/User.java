package com.example.meongnyangbook.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "role", nullable = false, unique = true)
    private UserRoleEnum role;

    public User(String username, String password, String nickname, String address, String phonNumber, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phonNumber;
        this.role =role;
    }
}
