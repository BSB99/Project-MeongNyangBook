package com.example.meongnyangbook.user.controller.user;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.PhoneRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "계정인증 전 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/users")
@Slf4j(topic = "UserController")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입")
  @PostMapping("/signup")
  public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto requestDto) {

    return userService.signup(requestDto);
  }

  @Operation(summary = "로그인")
  @PostMapping("/login")
  public ResponseEntity<ApiResponseDto> signin(@RequestBody LoginRequestDto loginRequestDto,
      HttpServletResponse response) {
    return userService.signin(loginRequestDto, response);
  }

  @Operation(summary = "핸드폰 인증")
  @PostMapping("/phone")
  public ResponseEntity<ApiResponseDto> sendMessage(@RequestBody PhoneRequestDto phoneRequestDto)
      throws CoolsmsException {
    ApiResponseDto result = userService.sendMessage(phoneRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "핸드폰 인증 확인")
  @PostMapping("/phone/auth")
  public ResponseEntity<ApiResponseDto> authMessageCode(
      @RequestBody PhoneRequestDto phoneRequestDto) {
    ApiResponseDto result = userService.authMessageCode(phoneRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "이메일 인증")
  @PostMapping("/email")
  public ResponseEntity<ApiResponseDto> sendEmail(
      @Valid @RequestBody EmailRequestDto emailRequestDto) throws MessagingException {
    ApiResponseDto result = userService.sendEmail(emailRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
