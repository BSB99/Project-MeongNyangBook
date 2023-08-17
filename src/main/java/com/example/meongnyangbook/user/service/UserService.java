package com.example.meongnyangbook.user.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);
}
