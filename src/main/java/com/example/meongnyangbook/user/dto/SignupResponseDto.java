package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {

    private String username;
    private String nickname;
    private String password;
    private String address;
    private String phoneNumber;

    private boolean admin = false;
    private String adminToken = ""; //실제 서비스에서 admin 등록하려면 사업자등록 유효성 검사를 해야할 필요가 있음

    public SignupResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        if(user.getRole().equals(UserRoleEnum.ADMIN)){
            admin = true;
        }
        else{
            admin = false;
        }
    }

}
