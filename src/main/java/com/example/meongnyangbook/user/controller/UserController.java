package com.example.meongnyangbook.user.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.PhoneRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/users")
@Slf4j(topic = "UserController")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto requestDto) {

        return userService.signup(requestDto);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> signin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.signin(loginRequestDto, response);
    }

    @PostMapping("/phone")
    public ResponseEntity<ApiResponseDto> sendMessage(@RequestBody PhoneRequestDto phoneRequestDto) throws CoolsmsException {
        return userService.sendMessage(phoneRequestDto);
    }

    @PostMapping("/phone/auth")
    public ResponseEntity<ApiResponseDto> authMessageCode(@RequestBody PhoneRequestDto phoneRequestDto) {
        return userService.authMessageCode(phoneRequestDto);
    }

    @PostMapping("/email")
    public ResponseEntity<ApiResponseDto> sendEmail(@Valid @RequestBody EmailRequestDto emailRequestDto) throws MessagingException {
        return userService.sendEmail(emailRequestDto);
    }
}
