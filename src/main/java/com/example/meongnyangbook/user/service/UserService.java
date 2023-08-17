package com.example.meongnyangbook.user.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.PhoneRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);

    ResponseEntity<ApiResponseDto> signin(LoginRequestDto loginRequestDto, HttpServletResponse response);

    ResponseEntity<ApiResponseDto> sendMessage(PhoneRequestDto phoneRequestDto) throws CoolsmsException;

    ResponseEntity<ApiResponseDto> sendEmail(EmailRequestDto emailRequestDto) throws MessagingException;

    ResponseEntity<ApiResponseDto> authMessageCode(PhoneRequestDto phoneRequestDto);
}
