package com.example.meongnyangbook.user.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.dto.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface AuthUserService {

  /**
   * 로그아웃
   *
   * @param user
   * @param request
   * @param response
   * @return
   */
  ApiResponseDto logout(User user, HttpServletRequest request,
      HttpServletResponse response);

  /**
   * 유저 영구정지
   *
   * @param user
   * @param blockUsername
   * @return
   */
  ApiResponseDto setBlockUser(User user, String blockUsername);

  /**
   * 유저 프로필 조회
   *
   * @param user
   * @return
   */
  ProfileResponseDto getProfle(User user);

  /**
   * 상대 유저 프로필 조회
   *
   * @param userNo
   * @return
   */
  ProfileResponseDto getSingleProfle(Long userNo);

  /**
   * 유저 프로필 수정
   *
   * @param athenthUser
   * @param profileRequestDto
   * @param multipartFiles
   * @return
   */
  ApiResponseDto setProfile(User athenthUser, ProfileRequestDto profileRequestDto,
      MultipartFile multipartFiles) throws IOException;

  /**
   * 유저 비밀번호 변경
   *
   * @param user
   * @param passwordRequestDto
   * @return
   */
  ApiResponseDto setPassword(User user, PasswordRequestDto passwordRequestDto);

  /**
   * 계정 삭제
   *
   * @param user
   * @param request
   * @param response
   * @return
   */
  ApiResponseDto deleteAccount(User user,
      HttpServletRequest request,
      HttpServletResponse response);

  /**
   * 유저 인증 이메일 보내기
   *
   * @param emailRequestDto
   * @return
   */
  ApiResponseDto sendEmail(EmailRequestDto emailRequestDto) throws MessagingException;

  /**
   * 유저 프로필 수정
   *
   * @param username
   * @return
   */
  User findUser(String username);

  String generateAndSetTemporaryPassword(User user);


  ApiResponseDto confirmAuth(AuthConfirmRequestDto requestDto);
}
