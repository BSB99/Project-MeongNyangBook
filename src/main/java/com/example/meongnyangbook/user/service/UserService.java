package com.example.meongnyangbook.user.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.*;
import com.example.meongnyangbook.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    SignupResponseDto signup(SignupRequestDto requestDto);

    SigninResponseDto signin(LoginRequestDto loginRequestDto, HttpServletResponse response);

    boolean checkAdmin(String adminToken);

    ResponseEntity<ApiResponseDto> logout(User user, HttpServletRequest request, HttpServletResponse response);

    ApiResponseDto sendMessage(PhoneRequestDto phoneRequestDto) throws CoolsmsException;

    ApiResponseDto sendEmail(EmailRequestDto emailRequestDto) throws MessagingException;

    ApiResponseDto authMessageCode(PhoneRequestDto phoneRequestDto);
}
