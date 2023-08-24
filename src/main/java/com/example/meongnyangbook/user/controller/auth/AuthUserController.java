package com.example.meongnyangbook.user.controller.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.PasswordRequestDto;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import com.example.meongnyangbook.user.service.auth.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "인증후 User API", description = "계정인증 후 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/auth")
@Slf4j(topic = "UserController")
public class AuthUserController {

  private final AuthUserService authUserServiceImpl;

  @Operation(summary = "로그아웃")
  @PostMapping("/logout")
  public ResponseEntity<ApiResponseDto> logout(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      HttpServletRequest request,
      HttpServletResponse response) {
    log.info("로그아웃 컨트롤러");
    authUserServiceImpl.logout(userDetails.getUser(), request, response);
    return ResponseEntity.ok().body(new ApiResponseDto("로그아웃 완료", HttpStatus.OK.value()));
  }

  @Operation(summary = "유저 영구정지")
  @PutMapping("/block")
  public ResponseEntity<ApiResponseDto> setBlockUser(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String blockUserName) {
    ApiResponseDto result = authUserServiceImpl.setBlockUser(userDetails.getUser(), blockUserName);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "프로필 조회")
  @GetMapping("/profile")
  public ResponseEntity<ProfileResponseDto> getProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ProfileResponseDto result = authUserServiceImpl.getProfle(userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "프로필 수정")
  @PutMapping("/profile")
  public ResponseEntity<ApiResponseDto> setProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart("profileRequestDto") ProfileRequestDto profileRequestDto,
      @RequestPart("fileName") MultipartFile multipartFiles) throws IOException {
    ApiResponseDto result = authUserServiceImpl.setProfile(userDetails.getUser(),
        profileRequestDto, multipartFiles);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PatchMapping("/password")
  public ResponseEntity<ApiResponseDto> setPassword(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody PasswordRequestDto passwordRequestDto) {
    ApiResponseDto result = authUserServiceImpl.setPassword(userDetails.getUser(),
        passwordRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "계정 탈퇴")
  @DeleteMapping("/account")
  public ResponseEntity<ApiResponseDto> deleteAccount(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      HttpServletRequest request,
      HttpServletResponse response) { //회원 탈퇴

    ApiResponseDto result = authUserServiceImpl.deleteAccount(userDetails.getUser(), request,
        response);
    return ResponseEntity.status(HttpStatus.OK).body(result);

  }

  @PostMapping("/email")
  public ResponseEntity<ApiResponseDto> sendEmail(
      @Valid @RequestBody EmailRequestDto emailRequestDto) throws MessagingException {
    ApiResponseDto result = authUserServiceImpl.sendEmail(emailRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
