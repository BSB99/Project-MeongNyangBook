package com.example.meongnyangbook.user.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletRequest request,
            HttpServletResponse response) {
        log.info("로그아웃 컨트롤러");
        userService.logout(userDetails.getUser(), request, response);
        return ResponseEntity.ok().body(new ApiResponseDto("로그아웃 완료", HttpStatus.OK.value()));
    }
}
