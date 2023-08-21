package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.entity.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String username;
    private String nickname;
    private String password;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;

    private boolean admin = false;
    private String adminToken = ""; //실제 서비스에서 admin 등록하려면 사업자등록 유효성 검사를 해야할 필요가 있음
}
