package com.example.meongnyangbook.user.dto;

public class SigninResponseDto {
    String username;
    String password;

    public SigninResponseDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
