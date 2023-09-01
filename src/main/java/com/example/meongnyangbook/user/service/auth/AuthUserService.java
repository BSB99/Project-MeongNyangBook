package com.example.meongnyangbook.user.service.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.PasswordRequestDto;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import com.example.meongnyangbook.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface AuthUserService {

  ApiResponseDto logout(User user, HttpServletRequest request,
      HttpServletResponse response);

  ApiResponseDto setBlockUser(User user, String blockUsername);

  ProfileResponseDto getProfle(User user);

  ApiResponseDto setProfile(User athenthUser, ProfileRequestDto profileRequestDto,
      MultipartFile multipartFiles) throws IOException;

  ApiResponseDto setPassword(User user, PasswordRequestDto passwordRequestDto);

  ApiResponseDto deleteAccount(User user,
      HttpServletRequest request,
      HttpServletResponse response);

  ApiResponseDto sendEmail(EmailRequestDto emailRequestDto) throws MessagingException;

  User findUser(String username);

  String generateAndSetTemporaryPassword(User user);

  ProfileResponseDto getSingleProfle(Long userNo);
}
