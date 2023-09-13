package com.example.meongnyangbook.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AuthConfirmRequestDto {
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "username은 이메일 형식이어야 합니다. 예) tester123@gmail.com")
    private String email;

    private String phone;
}
