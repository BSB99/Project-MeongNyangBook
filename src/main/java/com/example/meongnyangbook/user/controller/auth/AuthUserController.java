package com.example.meongnyangbook.user.controller.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.PasswordRequestDto;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import com.example.meongnyangbook.user.service.auth.AuthUserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/auth")
@Slf4j(topic = "UserController")
public class AuthUserController {
    private final AuthUserService authUserService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletRequest request,
            HttpServletResponse response) {
        log.info("로그아웃 컨트롤러");
        authUserService.logout(userDetails.getUser(), request, response);
        return ResponseEntity.ok().body(new ApiResponseDto("로그아웃 완료", HttpStatus.OK.value()));
    }

    @PutMapping("/block")
    public ResponseEntity<ApiResponseDto> setBlockUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String blockUserName) {
        ApiResponseDto result = authUserService.setBlockUser(userDetails.getUser(), blockUserName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto result = authUserService.getProfle(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> setProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        ApiResponseDto result = authUserService.setProfile(userDetails.getUser(), profileRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PatchMapping("/password")
    public ResponseEntity<ApiResponseDto> setPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        ApiResponseDto result = authUserService.setPassword(userDetails.getUser(), passwordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @DeleteMapping("/account")
    public ResponseEntity<ApiResponseDto> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) { //회원 탈퇴

        ApiResponseDto result = authUserService.deleteAccount(userDetails.getUser(), request, response);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PostMapping("/email")
    public ResponseEntity<ApiResponseDto> sendEmail(@Valid @RequestBody EmailRequestDto emailRequestDto) throws MessagingException {
        ApiResponseDto result = authUserService.sendEmail(emailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
