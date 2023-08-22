package com.example.meongnyangbook.user.dto;

import lombok.Getter;

@Getter
public class PasswordRequestDto {
    private String password;
    private String newPassword;
    private String newPasswordCheck;
}
