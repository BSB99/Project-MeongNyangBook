package com.example.meongnyangbook.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProfileRequestDto {
    private String nickname;
    private String address;
    private String phoneNumber;
}