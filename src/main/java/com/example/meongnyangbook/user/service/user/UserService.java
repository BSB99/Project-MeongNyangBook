package com.example.meongnyangbook.user.service.user;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.PhoneRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);

    ResponseEntity<ApiResponseDto> signin(LoginRequestDto loginRequestDto, HttpServletResponse response);

    boolean checkAdmin(String adminToken);

    ApiResponseDto sendMessage(PhoneRequestDto phoneRequestDto) throws CoolsmsException;

//    ApiResponseDto sendEmail(EmailRequestDto emailRequestDto) throws MessagingException;

    ApiResponseDto authMessageCode(PhoneRequestDto phoneRequestDto);

    User findUser(String username);

    User findUser(Long userNo);
}
