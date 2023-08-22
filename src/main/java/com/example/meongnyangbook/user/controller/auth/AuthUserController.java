package com.example.meongnyangbook.user.controller.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import com.example.meongnyangbook.user.service.auth.AuthUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya")
@Slf4j(topic = "UserController")
public class AuthUserController
{
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
    public ResponseEntity<ApiResponseDto> setBlockUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String blockUserName){


        return authUserService.setBlockUser(userDetails.getUser(),blockUserName);
    }
    @GetMapping("/profile")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){

        return authUserService.getProfle(userDetails.getUser());
    }
    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> setProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto){
        return authUserService.setProfile(userDetails.getUser(), profileRequestDto);
    }
    @DeleteMapping("/account")
    public ResponseEntity<ApiResponseDto> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response){ //회원 탈퇴

        return authUserService.deleteAccount(userDetails.getUser(),request,response);
    }
}
