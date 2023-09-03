package com.example.meongnyangbook.user;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.PhoneRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.dto.UserInfoResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.ResponseEntity;

public interface UserService {

  /**
   * 유저 회원가입
   *
   * @param requestDto
   * @return
   */
  ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);

  /**
   * 유저 로그인
   *
   * @param loginRequestDto
   * @param response
   * @return
   */
  ResponseEntity<ApiResponseDto> signin(LoginRequestDto loginRequestDto,
      HttpServletResponse response);

  boolean checkAdmin(String adminToken);

  /**
   * 휴대폰 인증 메세지 전송
   *
   * @param phoneRequestDto
   * @return
   */
  ApiResponseDto sendMessage(PhoneRequestDto phoneRequestDto) throws CoolsmsException;

  /**
   * 휴대폰 인증 메세지 확인
   *
   * @param phoneRequestDto
   * @return
   */
  ApiResponseDto authMessageCode(PhoneRequestDto phoneRequestDto);

  /**
   * 휴대폰 정보에서 닉네임 찾기
   *
   * @param user
   * @return
   */
  UserInfoResponseDto getUserNickname(User user);

  User findUser(String username);

  User findUser(Long userNo);
}
